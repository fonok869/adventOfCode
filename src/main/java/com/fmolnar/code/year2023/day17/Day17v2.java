package com.fmolnar.code.year2023.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day17v2 {

    public static final int THREE = 3;
    static List<List<Integer>> matrix = new ArrayList<>();
    static int xMax = 0;
    static int yMax = 0;

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day17/input.txt");

        int maxX = lines.get(0).length();
        int maxY = lines.size();
        int[][] matrix = new int[maxY][maxX];
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++) {
                matrix[j][i] = Integer.valueOf(String.valueOf(line.charAt(i)));
            }
        }


        initMethod(matrix, new Node(0, 0, 0, 0));
    }

    record Node(int node, int value, int xLepes, int yLepes) {
    }

    ;

    private static void initMethod(int[][] matrixToCheck, Node initNode) {

        Map<Integer, Node> initMapNodes = new HashMap<>();
        List<Node> startNode = new ArrayList<>();
        startNode.add(initNode);

        int yMaxAct = matrixToCheck.length;
        int xMaxAct = matrixToCheck[0].length;
        int vHalmaz = yMaxAct * xMaxAct;

        int dist[] = new int[vHalmaz];

        for (int i = 0; i < vHalmaz; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        dist[0] = 1;

        Set<Integer> alreadyVisited = new HashSet<>();
        Set<Node> forced = new HashSet<>();

        while (true) {

            List<Integer> mins = new ArrayList<>();
            for (int i = 0; i < dist.length; i++) {
                if (!alreadyVisited.contains(i)) {
                    mins.add(dist[i]);
                }
            }
            int minDist = mins.stream().mapToInt(s -> s).min().getAsInt();
            int nodeActualNumber = -1;
            for (int i = 0; i < vHalmaz; i++) {
                if (!alreadyVisited.contains(i) && dist[i] == minDist) {
                    nodeActualNumber = i;
                    initNode = initMapNodes.get(nodeActualNumber);
                    break;
                }
            }


            // Szomszedok
            int x = nodeActualNumber % xMaxAct;
            int y = (nodeActualNumber - x) / yMaxAct;
            System.out.println("(y = " + y + " ) ( x = " + x + ") " + " dist : " + minDist);
            List<Node> neighboursToCheck = new ArrayList<>();

            getAllSzomszedok(matrixToCheck, initNode, x, xMaxAct, y, neighboursToCheck, yMaxAct, forced, minDist);
            neighboursToCheck.forEach(node -> {
                        Integer distanceActual = dist[node.node];
                        if ((minDist + node.value) < distanceActual) {
                            dist[node.node] = minDist + node.value;
                            initMapNodes.put(node.node, new Node(node.node, node.value, node.xLepes, node.yLepes));
                        }
                    }
            );

            alreadyVisited.add(nodeActualNumber);
            initMapNodes.put(nodeActualNumber, initNode);

            if (nodeActualNumber == vHalmaz - 1) {
                System.out.println("Min dist: " + dist[nodeActualNumber]);
                break;
            }


        }


    }

    private static void getAllSzomszedok(int[][] matrixToCheck, Node initNode, int x, int xMaxAct, int y, List<Node> neighboursToCheck, int yMaxAct, Set<Node> checkAllForcedPoint, int min) {
        // 1 Step Right
        if (0 <= x && (x + 1) < xMaxAct) {
            if (initNode.xLepes + 1 <= THREE) {
                int node = (x + 1) + xMaxAct * y;
                int value = matrixToCheck[y][x + 1];
                neighboursToCheck.add(new Node(node, value, initNode.xLepes + 1, 0));
            }
        }

        // 1 Step Left
        if (0 <= (x - 1) && x < xMaxAct) {
            if (initNode.xLepes + 1 <= THREE) {
                int node = (x - 1) + xMaxAct * y;
                int value = matrixToCheck[y][x - 1];
                neighboursToCheck.add(new Node(node, value, initNode.xLepes + 1, 0));
            }
        }

        // 1 Step Up
        if (0 <= (y - 1) && y < yMaxAct) {
            if (initNode.yLepes + 1 <= THREE) {
                int node = x + xMaxAct * (y - 1);
                int value = matrixToCheck[y - 1][x];
                neighboursToCheck.add(new Node(node, value, 0, initNode.yLepes + 1));
            }
        }

        // 1 Step DownWard
        if (0 <= y && (y + 1) < yMaxAct) {
            if (initNode.yLepes + 1 <= THREE) {
                int node = x + xMaxAct * (y + 1);
                int value = matrixToCheck[y + 1][x];
                neighboursToCheck.add(new Node(node, value, 0, initNode.yLepes + 1));
            }
        }
    }


}
