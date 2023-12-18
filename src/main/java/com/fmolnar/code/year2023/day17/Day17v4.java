package com.fmolnar.code.year2023.day17;

import com.fmolnar.code.FileReaderUtils;
import com.fmolnar.code.year2021.day25.Day25Challenge01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day17v4 {

    static int xMax = 0;
    static int yMax = 0;
    static int[][] matrix;

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day17/input.txt");

        int maxX = lines.get(0).length();
        xMax = maxX;
        int maxY = lines.size();
        yMax = maxY;
        matrix = new int[maxY][maxX];
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++) {
                matrix[j][i] = Integer.valueOf(String.valueOf(line.charAt(i)));
            }
        }

        Map<Point, Set<Node>> mins = new HashMap<>();
        Node initNode = new Node(new Point(0, 0), 0, Direction.S, 0);
        Set<Node> initHashSet = new HashSet<>();
        initHashSet.add(initNode);
        mins.put(initNode.pointActual, initHashSet);
        Set<Node> nodes = new HashSet<>();
        Set<Node> newNodes = new HashSet<>();
        Set<Node> allNodes = new HashSet<>();
        allNodes.add(initNode);
        nodes.add(initNode);
        Set<Node> checked = new HashSet<>();
        int i = 0;
        looper: while (true) {

            int minToSearchFor = allNodes.stream().filter(node -> !checked.contains(node)).mapToInt(s -> s.costUntilNow).min().getAsInt();

            Set<Node> toSearchFors = mins.entrySet().stream().map(Map.Entry::getValue).flatMap(s->s.stream()).filter(node -> !checked.contains(node)).filter(s -> s.costUntilNow == minToSearchFor).collect(Collectors.toSet());

            for(Node toSearchFor : toSearchFors) {
                newNodes.addAll(toSearchFor.next());
                checked.add(toSearchFor);
                //System.out.println("Actual: " + toSearchFor);

                for (Node newN : newNodes) {
                    Set<Node> nodeNow = mins.get(newN.pointActual);
                    if (nodeNow == null) {
                        Set<Node> nodes1 = new HashSet<>();
                        nodes1.add(newN);
                        mins.put(newN.pointActual, nodes1);
                    } else {
                        mins.get(newN.pointActual).add(newN);
                        // Mert min az ugyanaz az ertek
                    }
                    //System.out.println(newN);
                    if (newN.isFinished()) {
                        System.out.println("Eredmeny: " + newN.costUntilNow);
                        break looper;
                    }
                }
                allNodes.addAll(newNodes);
                //System.out.println("i: " + i++);

                nodes.clear();
                nodes.addAll(newNodes);
                newNodes.clear();
            }
        }

        System.out.println("Toto");

        for (Node node : allNodes) {
            if (node.isFinished()) {
                System.out.println("Result: " + node.costUntilNow);
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

    ;


}
