package com.fmolnar.code.year2023.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day17v3 {

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


        initMethod(matrix);
    }

    record Node(Point p, int value, int xLepes, int yLepes, int minLengthIdaig) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return xLepes == node.xLepes && yLepes == node.yLepes && minLengthIdaig == node.minLengthIdaig && Objects.equals(p, node.p);
        }

        @Override
        public int hashCode() {
            return Objects.hash(p, xLepes, yLepes, minLengthIdaig);
        }
    }

    record Point(int y, int x) {
    }

    ;

    private static void initMethod(int[][] matrixToCheck) {

        Set<Node> alls = new HashSet<>();
        List<Node> startNode = new ArrayList<>();

        Node initNode = new Node(new Point(0, 0), 0, 0, 0, 1);

        int yMaxAct = matrixToCheck.length;
        int xMaxAct = matrixToCheck[0].length;
        int vHalmaz = yMaxAct * xMaxAct;


        Set<Node> alreadyVisited = new HashSet<>();
        Set<Node> forced = new HashSet<>();

        while (true) {


            Node nodeToCheck = null;
            int minValue = Integer.MAX_VALUE;
            // initNodeSzamol
            if (alreadyVisited.isEmpty()) {
                nodeToCheck = initNode;
                minValue = initNode.minLengthIdaig;
            } else {
                final int minValue2 = alls.stream().filter(node -> !alreadyVisited.contains(node)).mapToInt(Node::minLengthIdaig).min().getAsInt();
                nodeToCheck = alls.stream().filter(node -> !alreadyVisited.contains(node)).filter(node -> node.minLengthIdaig == minValue2).findFirst().get();
                minValue = minValue2;
            }

            // Szomszedok
            Set<Node> neighboursToCheck = new HashSet<>();

            getAllSzomszedok(matrixToCheck, nodeToCheck, xMaxAct, yMaxAct, neighboursToCheck);

            alls.addAll(neighboursToCheck);

            alreadyVisited.add(nodeToCheck);

            if (nodeToCheck.p.equals(new Point(yMaxAct - 1, xMaxAct - 1))) {
                System.out.println("Min dist: " + nodeToCheck.minLengthIdaig);
                break;
            }


        }


    }

    private static void getAllSzomszedok(int[][] matrixToCheck, Node initNode, int xMaxAct, int yMaxAct, Set<Node> neighboursToCheck) {
        int x = initNode.p.x;
        int y = initNode.p.y;
        // 1 Step Right
        if (0 <= x && (x + 1) < xMaxAct && 0 <= y && y < yMaxAct) {
            if (initNode.xLepes + 1 <= THREE) {
                int xNew = (x + 1);
                int value = matrixToCheck[y][xNew];
                neighboursToCheck.add(new Node(new Point(y, xNew), value, initNode.xLepes + 1, 0, initNode.minLengthIdaig + value));
            }
        }

        // 1 Step Left
        if (0 <= (x - 1) && x < xMaxAct && 0 <= y && y < yMaxAct) {
            if (initNode.xLepes + 1 <= THREE) {
                int xNew = (x - 1);
                int value = matrixToCheck[y][xNew];
                neighboursToCheck.add(new Node(new Point(y, xNew), value, initNode.xLepes + 1, 0, initNode.minLengthIdaig + value));
            }
        }

        // 1 Step Up
        if (0 <= (y - 1) && y < yMaxAct && 0 <= x && x < xMaxAct) {
            if (initNode.yLepes + 1 <= THREE) {
                int yNew = (y - 1);
                int value = matrixToCheck[y - 1][x];
                neighboursToCheck.add(new Node(new Point(yNew, x), value, 0, initNode.yLepes + 1, initNode.minLengthIdaig + value));
            }
        }

        // 1 Step DownWard
        if (0 <= y && (y + 1) < yMaxAct && 0 <= x && x < xMaxAct) {
            if (initNode.yLepes + 1 <= THREE) {
                int yNew =(y + 1);
                int value = matrixToCheck[y + 1][x];
                neighboursToCheck.add(new Node(new Point(yNew, x), value, 0, initNode.yLepes + 1, initNode.minLengthIdaig + value));
            }
        }
    }


}
