package com.fmolnar.code.year2023.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day17Practise {

    int xMax;
    int yMax;
    Point start = new Point(0, 0);
    Point end;
    static Map<Point, Integer> values = new HashMap<>();
    static Map<Point, Set<Node>> pointsWithDistance = new HashMap<>();
    PriorityQueue<Node> priorityQueue = new PriorityQueue<>((n1, n2) -> {
        if (n1.costUntilNow < n2.costUntilNow) {
            return -1;
        } else if (n1.costUntilNow > n2.costUntilNow) {
            return 1;
        }
        return 0;
    });
    Node INIT1;
    Node INIT2;

    public static void main(String[] args) throws IOException {
        Day17Practise d = new Day17Practise();
        d.calculateDay17();
    }

    private void calculateDay17() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day17/input.txt");
        yMax = lines.size();
        xMax = lines.get(0).length();
        end = new Point(xMax - 1, yMax - 1);
        for (int y = 0; y < yMax; y++) {
            String line = lines.get(y);
            for (int x = 0; x < xMax; x++) {
                values.put(new Point(x, y), Integer.valueOf(line.charAt(x)));
            }
        }

        initDijkstra();

        dijkstra();
    }

    private void dijkstra() {

        Set<Point> alreadyVisited = new HashSet<>();

        pointsWithDistance.get(start).add(INIT1);
        alreadyVisited.add(start);
        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.remove();

            if (current.point.equals(end)) {
                System.out.println("Dijkstra: " + current.costUntilNow);
                break;
            }

            if (alreadyVisited.contains(current)) {
                continue;
            }

            dijkstraCalcul(current, alreadyVisited, priorityQueue);
        }
    }

    private void dijkstraCalcul(Node current, Set<Point> alreadyVisited, PriorityQueue<Node> priorityQueue) {

        for (Node node : current.next()) {

            if (!alreadyVisited.contains(node)) {
                int newDistance = node.costUntilNow;

                Node ancien = null;
                if (pointsWithDistance.get(node.point).contains(node)) {
                    ancien = pointsWithDistance.get(node.point).stream().filter(n -> n.equals(node)).findFirst().get();
                }

                if (ancien != null && newDistance < ancien.costUntilNow) {
                    pointsWithDistance.get(node.point).add(node);
                } else if (ancien == null) {
                    pointsWithDistance.get(node.point).add(node);
                }

                priorityQueue.add(node);

            }

        }
    }

    private void initDijkstra() {
        //INIT1 = new Node(start, values.get(start), Direction.S, 0);
        INIT2 = new Node(start, values.get(start), Direction.E, 0);
        //priorityQueue.add(INIT1);
        priorityQueue.add(INIT2);
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                pointsWithDistance.put(new Point(x, y), new HashSet<>());
            }
        }
    }

    enum Direction {
        N(0, -1), E(1, 0), S(0, 1), W(-1, 0);

        private final int x;
        private final int y;

        Direction(int x, int y) {

            this.x = x;
            this.y = y;
        }

        public Point forward(Point pointExistant) {
            return new Point(pointExistant.x + x, pointExistant.y + y);
        }

        public Direction getLeft() {
            return switch (this) {
                case N -> W;
                case W -> S;
                case S -> E;
                case E -> N;
            };
        }

        public Direction getRight() {
            return switch (this) {
                case N -> E;
                case E -> S;
                case S -> W;
                case W -> N;
            };
        }
    }

    record Node(Point point, int costUntilNow, Direction direction, int forwardStep) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return forwardStep == node.forwardStep && Objects.equals(point, node.point) && direction == node.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, direction, forwardStep);
        }

        public List<Node> next() {
            List<Node> nodes = new ArrayList<>();

            if (3 < forwardStep) {
                return nodes;
            }

            if (forwardStep <= 2) {
                Point nextStep = direction.forward(point());
                int nextValue = getCost(nextStep);
                if (0 <= nextValue) {
                    nodes.add(new Node(nextStep, costUntilNow, direction, forwardStep + 1));
                }
            }

            // LEFT
            Direction directionLeft = direction.getLeft();
            Point newLeftPoint = directionLeft.forward(point());
            int nextLeftValue = getCost(newLeftPoint);
            if (0 <= nextLeftValue) {
                nodes.add(new Node(newLeftPoint, costUntilNow + nextLeftValue, directionLeft, 0));
            }


            // RIGHT
            Direction directionRight = direction.getRight();
            Point newRightPoint = directionRight.forward(point());
            int nextRightValue = getCost(newRightPoint);
            if (0 <= nextRightValue) {
                nodes.add(new Node(newRightPoint, costUntilNow + nextRightValue, directionRight, 0));
            }
            return nodes;
        }

        private int getCost(Point nextStep) {
            if (pointsWithDistance.keySet().contains(nextStep)) {
                return values.get(nextStep);
            }
            return -1;
        }
    }

    record Point(int x, int y) {

    }
}
