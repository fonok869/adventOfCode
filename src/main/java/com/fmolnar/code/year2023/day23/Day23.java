package com.fmolnar.code.year2023.day23;

import com.fmolnar.code.FileReaderUtils;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23 {

    static Set<Point> forests = new HashSet<>();

    Set<Point> directionsAll = Set.of(new Point((short) 0, (short) -1), new Point((short) 1, (short) 0), new Point((short) -1, (short) 0),
            new Point((short) 0, (short) 1));

    static short maxY = 0;
    static short maxX = 0;

    static Point endPoint;

    static Point beginPoint = new Point(((short) 0), (short) 1);


    public void calculate() throws IOException {

        long startTime = System.currentTimeMillis();
        List<String> lines = FileReaderUtils.readFile("/2023/day23/input.txt");

        maxY = (short) lines.size();
        maxX = (short) lines.get(0).length();
        endPoint = new Point(((short) (maxY - 1)), ((short) (maxX - 2)));

        for (short y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (short x = 0; x < line.length(); x++) {
                char charActual = line.charAt(x);
                switch (charActual) {
                    case '#': {
                        forests.add(new Point(y, x));
                        break;
                    }
                }
            }
        }

        List<Point> vertices = new ArrayList<>();

        vertices.add(beginPoint);
        for (short y = 1; y < maxY - 1; y++) {
            for (short x = 1; x < maxX - 1; x++) {
                int nbEdge = 0;
                Point toCheckNeighBours = new Point(y, x);
                if (forests.contains(toCheckNeighBours)) {
                    continue;
                }
                for (Point direction : directionsAll) {
                    if (forests.contains(new Point((short) (y + direction.y), (short) (x + direction.x)))) {
                        nbEdge++;
                    }
                }
                if (nbEdge < 2) {
                    vertices.add(new Point(y, x));
                }
            }
        }


        vertices.add(endPoint);

        Map<Point, Set<Edge>> edges = new HashMap<>();


        vertices.forEach(vertice -> edges.put(vertice, new HashSet<>()));

        calculateEdges(vertices, maxX, maxY, edges);

        // DFS --> List<Point> visited ++
        // neighbours
        // All neighbours
        Set<Integer> maxs = new HashSet<>();
        dfs(beginPoint, maxs, edges);

        System.out.println("Max: " + maxs.stream().mapToInt(s -> s).max().getAsInt());
        System.out.println("Max size: " + maxs.size());

        long endTime = System.currentTimeMillis();

        System.out.println("Time: " + (endTime-startTime)/1000L);

    }


    private void dfs(Point beginPoint, Collection<Integer> maxs, Map<Point, Set<Edge>> edges) {
        List<Point> alreadyVisited = new ArrayList<>();
        alreadyVisited.add(beginPoint);
        for (Edge destinationEdge : edges.get(beginPoint)) {
            if (!alreadyVisited.contains(destinationEdge.toSource)) {
                doRecursiveDFS(destinationEdge.toSource, alreadyVisited, maxs, edges);
            }
        }
    }

    private void doRecursiveDFS(Point beginPoint, List<Point> alreadyVisited, Collection<Integer> maxs, Map<Point, Set<Edge>> edges) {
        if (beginPoint.equals(endPoint)) {
            alreadyVisited.add(beginPoint);
            int max = calculateDistance(alreadyVisited, edges);
            //System.out.println(max + " " + alreadyVisited);
            maxs.add(max);
            return;
        }
        List<Point> alreadyVisitedNew = new ArrayList<>(alreadyVisited);
        alreadyVisitedNew.add(beginPoint);
        for (Edge destinationEdge : edges.get(beginPoint)) {
            if (!alreadyVisitedNew.contains(destinationEdge.toSource)) {
                doRecursiveDFS(destinationEdge.toSource, alreadyVisitedNew, maxs, edges);
            }
        }
    }

    private Integer calculateDistance(List<Point> alreadyVisited, Map<Point, Set<Edge>> edges) {
         int sum = 0;
        for (int i = 0; i < alreadyVisited.size() - 1; i++) {
            Point pointFrom = alreadyVisited.get(i);
            Point pointTo = alreadyVisited.get(i + 1);

            Set<Edge> edges1 = edges.get(pointFrom);

            Edge edgeTo = edges1.stream().filter(edge -> edge.toSource.equals(pointTo)).collect(Collectors.toList()).get(0);
            sum += edgeTo.value;
            }
        return sum-alreadyVisited.size()+1;
    }

    private void calculateEdges(List<Point> vertices, short maxX, short maxY, Map<Point, Set<Edge>> edges) {
        for (Point vertice : vertices) {

            ////////////////////////////////
            Set<Point> newVertices = new HashSet<>();
            newVertices.add(vertice);
            Node nodeInit = new Node(vertice, newVertices);
            Set<Node> nodesActual = new HashSet<>();
            Set<Node> nodesActualRestant = new HashSet<>();
            nodesActual.add(nodeInit);
            while (true) {
                for (Node nodeActualToExamine : nodesActual) {
                    int nbOfForest = 0;
                    Point ujPointToAdd = null;
                    for (Point direction : directionsAll) {
                        Point ujPont = new Point((short) (nodeActualToExamine.point.y + direction.y), (short) (nodeActualToExamine.point.x + direction.x));
                        if (0 <= ujPont.x && ujPont.x < maxX && 0 <= ujPont.y && ujPont.y < maxY) {
                            Point pointToCheck = ujPont;
                            if (forests.contains(pointToCheck)) {
                                nbOfForest++;
                                continue;
                            }

                            if (nodeActualToExamine.points.contains(pointToCheck)) {
                                continue;
                            }

                            if (vertices.contains(ujPont)) {
                                edges.get(vertice).add(new Edge((short) (nodeActualToExamine.points.size() + 1), ujPont));
                                break;
                            }

                            ujPointToAdd = pointToCheck;
                            //System.out.println(pointToCheck);
                            nodesActualRestant.add(nodeActualToExamine.addNewInitPoint(ujPointToAdd));
                        }
                    }
                    nbOfForest = 0;
                }

                if (nodesActualRestant.isEmpty()) {
                    break;
                }
                nodesActual.clear();
                nodesActual.addAll(nodesActualRestant);
                nodesActualRestant.clear();
            }

        }
    }

    record Edge(short value, Point toSource) {
    }

    private Set<Point> init4Directions() {
        return directionsAll;
    }

    record Point(short y, short x) {
    }

    record Node(Point point, Set<Point> points) {

        Node addNewInitPoint(Point pointK) {
            Set<Point> newHashset = new HashSet<>(points);
            newHashset.add(pointK);
            return new Node(pointK, newHashset);
        }
    }

    ;

}
