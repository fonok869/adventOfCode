package com.fmolnar.code.year2023.day23;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23v3 {

    static Set<Point> forests = new HashSet<>();
    static Set<Point> slopeJobbra = new HashSet<>();
    static Set<Point> slopLefele = new HashSet<>();

    static int maxY = 0;
    static int maxX = 0;

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day23/input.txt");

        maxY = lines.size();
        maxX = lines.get(0).length();
        int counterO = 0;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char charActual = line.charAt(x);
                switch (charActual) {
                    case 'O': {
                        counterO++;
                        break;
                    }
                    case '#': {
                        forests.add(new Point(x, y));
                        break;
                    }
                    case '>': {
                        slopeJobbra.add(new Point(x, y));
                        break;
                    }
                    case 'v': {
                        slopLefele.add(new Point(x, y));
                        break;
                    }
                }
            }
        }

        Map<Point, Integer> distances = shortestPathWithInverseDijkstra(forests, slopeJobbra, slopLefele, maxX, maxY);

        System.out.println("LAst: " + distances.get(new Point(maxX - 2, maxY - 1)));
        plotValues(distances);
    }

    private static void plotValues(Map<Point, Integer> distances) {
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                Point actual = new Point(i, j);
                if (forests.contains(actual)) {
                    System.out.print("  # ");
                } else if (slopeJobbra.contains(actual)) {
                    System.out.print("  > ");
                } else if (slopLefele.contains(actual)) {
                    System.out.print("  v ");
                } else if (distances.get(actual) != null && distances.get(actual) != Integer.MAX_VALUE) {
                    System.out.print(" " + distances.get(actual));
                } else {
                    System.out.print("  . ");
                }
            }
            System.out.println();
        }
    }

    private static Map<Point, Integer> shortestPathWithInverseDijkstra(Set<Point> forests, Set<Point> slopeJobbra, Set<Point> slopLefele, int maxX, int maxY) {
        Set<Point> alreadyVisited = new HashSet<>();

        Map<Point, Integer> distances = new HashMap<>();
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Point pointActual = new Point(i, j);
                if (forests.contains(pointActual) || slopeJobbra.contains(pointActual) || slopLefele.contains(pointActual)) {

                } else {
                    distances.put(pointActual, Integer.MAX_VALUE);
                }
            }
        }

        int maradekPont = maxX * maxY - forests.size() - slopeJobbra.size() - slopLefele.size();

        Comparator<Node> nodeComparator = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.cost < o2.cost)
                    return -1;
                if (o1.cost > o2.cost)
                    return 1;
                return 0;
            }
        };

        Queue<Node> pqueue = new PriorityQueue<>(nodeComparator);

        pqueue.add(new Node(new Point(1, 0), 0));

        distances.put(new Point(1, 0), 0);

        while (alreadyVisited.size() != maradekPont && !pqueue.isEmpty()) {

            // Remove U node legkisebb cost
            Node nodeActualToExamine = pqueue.remove();

            if (nodeActualToExamine.point.equals(new Point(maxX - 2, maxY - 1))) {
                System.out.println("Eredmeny: " + distances.get(nodeActualToExamine.point));
                plotValues(distances);
                //break;
            }

            // Ha mar lattuk off
            if (alreadyVisited.contains(nodeActualToExamine.point)) {
                if(distances.get(nodeActualToExamine) != null && distances.get(nodeActualToExamine)< nodeActualToExamine.cost()){
                    alreadyVisited.remove(nodeActualToExamine.point);
                } else {
                    continue;
                }
            }

            szomszedokKalkulasa(nodeActualToExamine, alreadyVisited, distances, pqueue);
        }

        System.out.println("Toto");

        return distances;


    }

    private static void szomszedokKalkulasa(Node nodeActualToExamine, Set<Point> alreadyVisited, Map<Point, Integer> distances, Queue<Node> pqueue) {
        List<Point> directions = init4Directions();
        List<Point> ujPontok = directions.stream().map(p -> new Point(p.x + nodeActualToExamine.point.x, p.y + nodeActualToExamine.point.y)).collect(Collectors.toList());

        for (Point direction : directions) {
            Point ujPont = new Point(nodeActualToExamine.point.x + direction.x, nodeActualToExamine.point.y + direction.y);

            if (0 <= ujPont.x && ujPont.x < maxX && 0 <= ujPont.y && ujPont.y < maxY) {

                Point pointToCheck = ujPont;
                if (forests.contains(pointToCheck)) {
                    continue;
                }
                int korrekcio = 0;

                if (slopeJobbra.contains(pointToCheck)) {
                    if(direction.equals(new Point(1, 0))){
                        System.out.println("Jobbra: " + pointToCheck);
                        pointToCheck = new Point(pointToCheck.x + 1, pointToCheck.y);
                        korrekcio = -1;
                    } else {
                        continue; // visszafele nem mehet
                    }

                } else if (slopLefele.contains(pointToCheck)) {
                    if(direction.equals(new Point(0, 1))) {
                        System.out.println("Lefele: " + pointToCheck);
                        pointToCheck = new Point(pointToCheck.x, pointToCheck.y + 1);
                        korrekcio = -1;
                    } else {
                        continue; //visszafele nem mehet
                    }
                }

                if (!alreadyVisited.contains(pointToCheck)) {
                    int edgeDistance = -1 + korrekcio;
                    int newDistance = distances.get(nodeActualToExamine.point) + edgeDistance;

                    if (distances.get(pointToCheck) == null || newDistance < distances.get(pointToCheck)) {
                        distances.put(pointToCheck, newDistance);

                    }

                    pqueue.add(new Node(pointToCheck, distances.get(pointToCheck)));
                }
            }
        }

        // hozzaadjuk mar visitalt
        alreadyVisited.add(nodeActualToExamine.point);
    }

    private static List<Point> init4Directions() {
        List<Point> arrays = new ArrayList<>();
        arrays.add(new Point(0, 1));
        arrays.add(new Point(0, -1));
        arrays.add(new Point(1, 0));
        arrays.add(new Point(-1, 0));
        return arrays;
    }

    record Point(int x, int y) {
    }

    record Node(Point point, Integer cost) {
    }

    ;

}
