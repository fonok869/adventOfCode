package com.fmolnar.code.year2023.day23;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class Day23Solution2WithoutThreads {

    static Set<Point> forests = new HashSet<>();

    static Set<Point> directionsAll = Set.of(new Point(0, -1), new Point(1, 0), new Point(-1, 0),
    new Point(0,1));

    static int maxY = 0;
    static int maxX = 0;

    public static void main(String[] args) throws IOException {
        calculate();
    }
    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day23/input.txt");

        maxY = lines.size();
        maxX = lines.get(0).length();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char charActual = line.charAt(x);
                switch (charActual) {
                    case '#': {
                        forests.add(new Point(x, y));
                        break;
                    }
                }
            }
        }

        shortestPathWithInverseDijkstra(forests, maxX, maxY);

    }

    private static void shortestPathWithInverseDijkstra(Set<Point> forests, int maxX, int maxY) {

        Queue<Node> pqueue = new PriorityBlockingQueue<>(10, Comparator.comparingInt(n->-1*n.points.size()));

        pqueue.add(new Node(new Point(1, 0), Set.of(new Point(1, 0))));

        Set<Integer> maxs = ConcurrentHashMap.newKeySet();

        int max = 0;
        while (!pqueue.isEmpty()) {

            // Remove U node legkisebb cost
            Node nodeActualToExamine = pqueue.remove();

            if (nodeActualToExamine.point.equals(new Point(maxX - 2, maxY - 1))) {
                System.out.println((max++) + " Eredmeny: " + nodeActualToExamine.points.size());
                maxs.add(nodeActualToExamine.points.size() - 1);
                if (max % 200 == 1) {
                    System.out.println("Local Max : " + maxs.stream().mapToInt(s -> s).max().getAsInt());
                }

            }
            max = szomszedokKalkulasa(nodeActualToExamine, pqueue, maxs, max);
        }
        System.out.println("Toto: " + maxs.stream().mapToInt(s -> s).max().getAsInt());
    }

    private static int szomszedokKalkulasa(Node nodeActualToExamine, Queue<Node> pqueue, Set<Integer> maxs, int max) {
        Set<Point> directions = init4Directions();

        int nbOfForest = 0;
        while(true) {
            Point ujPointToAdd = null;
            for (Point direction : directions) {
                Point ujPont = new Point(nodeActualToExamine.point.x + direction.x, nodeActualToExamine.point.y + direction.y);
                if (0 <= ujPont.x && ujPont.x < maxX && 0 <= ujPont.y && ujPont.y < maxY) {
                    Point pointToCheck = ujPont;
                    if (forests.contains(pointToCheck)) {
                        nbOfForest++;
                        continue;
                    }

                    if(nodeActualToExamine.points.contains(pointToCheck)){
                        continue;
                    }

                    ujPointToAdd = pointToCheck;
                }
            }
            if(nbOfForest<2){
                break;
            }
            if(ujPointToAdd == null){
               break;
            }
            nodeActualToExamine = nodeActualToExamine.addNewInitPoint(ujPointToAdd);

            if(nodeActualToExamine.point.equals(new Point(maxX - 2, maxY - 1))){
                System.out.println(max++ + " Eredmeny: " + nodeActualToExamine.points.size());
                maxs.add(nodeActualToExamine.points.size() - 1);
                if (max % 200 == 1) {
                    System.out.println("Local Max : " + maxs.stream().mapToInt(s -> s).max().getAsInt());
                }
                return max;
            }
            nbOfForest = 0;
        }

        for (Point direction : directions) {
            Point ujPont = new Point(nodeActualToExamine.point.x + direction.x, nodeActualToExamine.point.y + direction.y);

            if (0 <= ujPont.x && ujPont.x < maxX && 0 <= ujPont.y && ujPont.y < maxY) {

                Point pointToCheck = ujPont;
                if (forests.contains(pointToCheck)) {
                    continue;
                }
                if(nodeActualToExamine.points.contains(pointToCheck)){
                    continue;
                }
                if (!nodeActualToExamine.points.contains(ujPont)) {
                    Node uj = nodeActualToExamine.addNewInitPoint(ujPont);
                    //System.out.println("Uj Node hozzadasa: " + uj);
                    pqueue.add(uj);
                }
            }
        }
        return max;
    }

    private static Set<Point> init4Directions() {
        return directionsAll;
    }

    record Point(int x, int y) {
    }

    record Node(Point point, Set<Point> points) {

        @Override
        public String toString() {
            return "Node{" +
                    "point=" + point +
                    '}';
        }

        Node addNewInitPoint(Point pointK) {
            Set<Point> newHashset = new HashSet<>(points);
            newHashset.add(pointK);
            return new Node(pointK, newHashset);
        }
    }

    ;

}
