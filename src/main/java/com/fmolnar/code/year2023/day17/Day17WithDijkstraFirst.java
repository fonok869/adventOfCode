package com.fmolnar.code.year2023.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day17WithDijkstraFirst {

    static int xMax = 0;
    static int yMax = 0;
    static int[][] matrix;

    public static void main(String[] args) throws IOException {
        calculate();
    }

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day17/input.txt");

        int maxX = lines.get(0).length();
        xMax = maxX;
        int maxY = lines.size();
        yMax = maxY;
        matrix = new int[maxY][maxX];
        for (int j = 0; j < maxY; j++) {
            String line = lines.get(j);
            for (int i = 0; i < maxX; i++) {
                matrix[j][i] = Integer.valueOf(String.valueOf(line.charAt(i)));
            }
        }
        calculateDijkstra();
    }

    private static void calculateDijkstra() {

        Map<Point, Set<Node>> distances = new HashMap<>();

        for (int j = 0; j < yMax; j++) {
            for (int i = 0; i < xMax; i++) {
                distances.put(new Point(j, i), new HashSet<>());
            }
        }
        Node initNode = new Node(new Point(0, 0), 0, Direction.S, 0);
        Comparator<Node> nodeComparator = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.costUntilNow < o2.costUntilNow)
                    return -1;
                if (o1.costUntilNow > o2.costUntilNow)
                    return 1;
                return 0;
            }
        };

        Set<Node> alreadyVisited = new HashSet<>();
        Queue<Node> pqueue = new PriorityQueue<>(nodeComparator);
        pqueue.add(initNode);
        distances.get(initNode.pointActual).add(initNode);

        while (!pqueue.isEmpty()) {

            // Remove U node legkisebb cost
            Node nodeActualToExamine = pqueue.remove();

            if (nodeActualToExamine.pointActual.equals(new Point(yMax - 1, xMax - 1))) {
                System.out.println("Dijkstra: " + distances.get(nodeActualToExamine.pointActual).stream().mapToInt(Node::costUntilNow).min().getAsInt());
                break;
            }

            if (alreadyVisited.contains(nodeActualToExamine)) {
                continue;
            }

            alreadyVisited.add(nodeActualToExamine);

            caulculateDijkstra(nodeActualToExamine, pqueue, alreadyVisited, distances);
        }


    }

    private static void caulculateDijkstra(Node nodeActualToExamine, Queue<Node> pqueue, Set<Node> alreadyVisited, Map<Point, Set<Node>> distances) {

        for (Node node : nodeActualToExamine.next()) {

            if (!alreadyVisited.contains(node)) {
                int newDistance = node.costUntilNow;

                Node ancien = null;
                if (distances.get(node.pointActual).contains(node)) {
                    ancien = distances.get(node.pointActual).stream().filter(n -> n.equals(node)).findFirst().get();
                }

                if (ancien != null && newDistance < ancien.costUntilNow) {
                    distances.get(node.pointActual).add(node);
                } else if(ancien == null){
                    distances.get(node.pointActual).add(node);
                }
                pqueue.add(node);
            }
        }
    }

    enum Direction {
        E(new Point(0, 1)),
        W(new Point(0, -1)),
        S(new Point(1, 0)),
        N(new Point(-1, 0));

        private Point direction;

        Direction(Point point) {
            direction = point;
        }

        Point addPoint(Point point2) {
            return new Point(direction.y + point2.y, direction.x + point2.x);
        }

        Direction getLeft() {
            switch (this) {
                case E -> {
                    return N;
                }
                case N -> {
                    return W;
                }
                case W -> {
                    return S;
                }
                case S -> {
                    return E;
                }
            }
            throw new RuntimeException("Gond van");
        }

        Direction getRight() {
            switch (this) {
                case W -> {
                    return N;
                }
                case N -> {
                    return E;
                }
                case E -> {
                    return S;
                }
                case S -> {
                    return W;
                }
            }
            throw new RuntimeException("Gond van : Righttal");
        }

    }

    record Point(int y, int x) {
    }

    ;

    record Node(Point pointActual, int costUntilNow, Direction direction, int dirMove) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return dirMove == node.dirMove && Objects.equals(pointActual, node.pointActual) && direction == node.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pointActual, direction, dirMove);
        }

        boolean isFinished() {
            if (pointActual.x == xMax - 1 && pointActual.y == yMax - 1 && dirMove <= 3) {
                return true;
            }
            return false;
        }

        List<Node> next() {
            List<Node> links = new LinkedList<>();

            if (3 < dirMove) {
                return links;
            }

            // forward
            if (dirMove <= 2) {
                Point nextPoint = direction.addPoint(pointActual);
                int newCost = getCost(nextPoint);
                if (0 <= newCost) {
                    links.add(new Node(nextPoint, costUntilNow + newCost, direction, dirMove + 1));
                }
            }

            // LEFT
            Direction directionLeft = direction.getLeft();
            Point nextPointLeft = directionLeft.addPoint(pointActual);
            int newCostLeft = getCost(nextPointLeft);
            if (0 <= newCostLeft) {
                links.add(new Node(nextPointLeft, costUntilNow + newCostLeft, directionLeft, 1));
            }

            // RIGHT
            Direction directionRight = direction.getRight();
            Point nextRightPoint = directionRight.addPoint(pointActual);
            int newRightCost = getCost(nextRightPoint);
            if (0 <= newRightCost) {
                links.add(new Node(nextRightPoint, costUntilNow + newRightCost, directionRight, 1));
            }
            return links;
        }

        private int getCost(Point nextPoint) {
            if (0 <= nextPoint.x && 0 <= nextPoint.y && nextPoint.x < xMax && nextPoint.y < yMax) {
                return matrix[nextPoint.y][nextPoint.x];
            }
            return -1;
        }
    }


}
