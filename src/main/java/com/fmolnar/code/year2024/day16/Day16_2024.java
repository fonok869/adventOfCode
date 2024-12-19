package com.fmolnar.code.year2024.day16;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.Direction;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day16_2024 {

    PriorityQueue<Node> priorityQueue = new PriorityQueue<>((n1, n2) -> {
        if (n1.costUntilNow() < n2.costUntilNow()) {
            return -1;
        } else if (n1.costUntilNow() > n2.costUntilNow()) {
            return 1;
        }
        return 0;
    });
    PointXY START;
    PointXY END;
    Node INIT1;
    int xMax;
    int yMax;
    Map<PointXY, Set<Node>> pointsWithDistance = new HashMap<>();
    Map<PointXY, String> pointsMap;


    public void calculateDay162024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day16/input.txt");

        pointsMap = AdventOfCodeUtils.getMapStringInput(lines);

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'S') {
                    START = new PointXY(x, y);
                } else if (line.charAt(x) == 'E') {
                    END = new PointXY(x, y);
                }
            }
        }

        int xMax = lines.get(0).length();
        int yMax = lines.size();


        INIT1 = new Node(START, 0, Direction.RIGHT, new HashSet<>());

        priorityQueue.add(INIT1);
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                pointsWithDistance.put(new PointXY(x, y), new HashSet<>());
            }
        }


        dijkstra(xMax, yMax);

    }

    private void dijkstra(int xMax, int yMax) {

        Set<Node> alreadyVisited = new HashSet<>();

        pointsWithDistance.get(START).add(INIT1);
        Set<PointXY> ponitsXY = new HashSet<>();
        int min = -1;
        //alreadyVisited.add(INIT1);
        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.remove();

            if (min != -1 && min < current.costUntilNow()) {
                break;
            }
            if (current.pointXY().equals(END)) {
                min = current.costUntilNow();
                ponitsXY.addAll(current.pointsAlreadyested());
                System.out.println("Dijkstra: " + current.costUntilNow());
                continue;
            }

            if (alreadyVisited.contains(current)) {
                continue;
            }


            alreadyVisited.add(current);

            dijkstraCalcul(current, alreadyVisited, priorityQueue, xMax, yMax);
        }


        printOut(xMax, yMax, ponitsXY);

        System.out.println(ponitsXY.size() + 1);
    }

    private static void printOut(int xMax, int yMax, Set<PointXY> ponitsXY) {
        for (int j = 0; j < yMax; j++) {
            for (int x = 0; x < xMax; x++) {
                if (ponitsXY.contains(new PointXY(x, j))) {
                    System.out.print("O");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void dijkstraCalcul(Node current, Set<Node> alreadyVisited, PriorityQueue<Node> priorityQueue, int xMax, int yMax) {

        for (Node node : current.next(pointsMap)) {

            if (!alreadyVisited.contains(node)) {
                int newDistance = node.costUntilNow();

                Node ancien = null;
                if (pointsWithDistance.get(node.pointXY()) != null && pointsWithDistance.get(node.pointXY()).contains(node)) {
                    ancien = pointsWithDistance.get(node.pointXY()).stream().filter(n -> node.equals(n)).findFirst().get();
                }

                if (ancien != null && newDistance < ancien.costUntilNow()) {
                    pointsWithDistance.get(node.pointXY()).remove(ancien);
                    pointsWithDistance.get(node.pointXY()).add(node);
                } else if (ancien != null && newDistance == ancien.costUntilNow()) {
                    //printOut(xMax, yMax, ancien.pointsAlreadyested());
                    pointsWithDistance.get(node.pointXY()).remove(ancien);
                    ancien.pointsAlreadyested().addAll(new HashSet<>(node.pointsAlreadyested()));
                    node.pointsAlreadyested().addAll(new HashSet<>(ancien.pointsAlreadyested()));
                    //printOut(xMax, yMax, node.pointsAlreadyested());
                    pointsWithDistance.get(node.pointXY()).add(node);
                    pointsWithDistance.get(node.pointXY()).add(ancien);
                } else if (ancien == null) {
                    pointsWithDistance.get(node.pointXY()).add(node);
                }

                priorityQueue.add(node);

            }

        }
    }

}

record Node(PointXY pointXY, int costUntilNow, Direction direction, Set<PointXY> pointsAlreadyested) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(pointXY, node.pointXY) && direction == node.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointXY, direction);
    }

    public List<Node> next(Map<PointXY, String> pointsMap) {
        List<Node> nodes = new ArrayList<>();

        // left
        PointXY left = pointXY.add(direction.turnLeft().toPoint());
        if (Set.of(".", "E").contains(pointsMap.get(left))) {
            Set<PointXY> pointsAlready = new HashSet<>(pointsAlreadyested);
            pointsAlready.add(pointXY());
            nodes.add(new Node(pointXY, costUntilNow + 1000, direction.turnLeft(), pointsAlready));
        }

        PointXY right = pointXY.add(direction.turnRight().toPoint());
        if (Set.of(".", "E").contains(pointsMap.get(right))) {
            Set<PointXY> pointsAlready = new HashSet<>(pointsAlreadyested);
            pointsAlready.add(pointXY());
            nodes.add(new Node(pointXY, costUntilNow + 1000, direction.turnRight(), pointsAlready));
        }

        // forward
        PointXY forward = pointXY().add(direction().toPoint());

        Set<PointXY> pointsAlready = new HashSet<>(pointsAlreadyested);
        pointsAlready.add(pointXY());
        pointsAlready.add(forward);
        for (int i = 1; i < 150; i++) {
            PointXY leftForward = forward.add(direction.turnLeft().toPoint());
            PointXY rightForward = forward.add(direction.turnRight().toPoint());
            if (Set.of(".", "E").contains(pointsMap.get(leftForward)) || Set.of(".", "E").contains(pointsMap.get(rightForward))) {
                nodes.add(new Node(forward, costUntilNow + i, direction, pointsAlready));
                break;
            } else if ("#".equals(pointsMap.get(forward))) {
                break;
            } else {
                pointsAlready.add(forward);
                forward = forward.add(direction.toPoint());
            }

        }


        return nodes;

    }
}
