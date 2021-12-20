package com.fmolnar.code.year2021.day15;

import com.fmolnar.code.year2020.day24.Day24Challenge02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day15challengeTry02 {

    List<List<Integer>> matrix = new ArrayList<>();
    Set<Integer> hossz = new HashSet<>();
    int xMax = 0;
    int yMax = 0;


    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day15/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    List<Integer> riskLevel = new ArrayList<>();
                    for (int i = 0; i < line.length(); i++) {
                        if (i == (line.length() - 1)) {
                            riskLevel.add(Integer.valueOf(line.substring(i)));
                        } else {
                            riskLevel.add(Integer.valueOf(line.substring(i, i + 1)));
                        }
                    }
                    matrix.add(riskLevel);
                }
            }
        }
        xMax = matrix.get(0).size();
        yMax = matrix.size();

        long timeBegining = System.currentTimeMillis();
        int xHalf = xMax / 2;
        int[][] balOldalso = new int[yMax][xHalf+1];
        //int[][] balFelso = new int[yMax][xMax];
        for (int x = 0; x < xHalf+1 ; x++) {
            for (int y = 0; y < yMax; y++) {
                balOldalso[y][x] = matrix.get(y).get(x);
            }
        }

        Calculate calculate = new Calculate();
        int[][] du = calculate.initMethod(balOldalso, new Point(0,0,0));
        //balOldalso = initMethod(balOldalso, new Point(0,0,0));


        long now = System.currentTimeMillis();
        long middle = now - timeBegining/1000;
        System.out.println("Time after first: " + middle);

        int[][] jobbOldalso = new int[yMax][xHalf];

        for (int x = xHalf; x < xMax ; x++) {
            for (int y = 0; y < yMax; y++) {
                int xx = x % xHalf;
                jobbOldalso[yMax - 1 - y][xHalf - 1 - xx] = matrix.get(y).get(x);
            }
        }

        Calculate calculate2 = new Calculate();
        int[][] du2 = calculate2.initMethod(jobbOldalso, new Point(0,0,jobbOldalso[0][0]));
        xMax = xHalf;


        List<Integer> mins = new ArrayList<>();
        for(int i=0; i<yMax; i++){
            int value = (du[xHalf][i] + du2[xHalf-1][yMax-1-i]);
            mins.add(value);
            System.out.println("Min: " + value);
        }

        long end = System.currentTimeMillis();
        System.out.println("Time after first: " + ((end-timeBegining)/1000));

        System.out.println("MinGlobal: " + mins.stream().mapToInt(s->s).min().getAsInt());
        //jobbOldalso = initMethod(jobbOldalso, new Point(xHalf-1, yMax-1, matrix.get(yMax-1).get(xMax-1)));





        // itt kell folytatni

        // utolso matrix plusz 1-essevel megnezni

        System.out.println("Toto");
    }

    class Calculate {

        int dist[][];
        Map<Point, List<Point>> adjacentList;
        Set<Point> visited;
        PriorityQueue<Point> pqueue;
        int vHalmaz; // Number of ver
        int xMaxAct = 0;
        int yMaxAct = 0;


        private int[][] initMethod(int[][] matrixCucc, Point init) {

            xMaxAct = matrixCucc.length;
            yMaxAct = matrixCucc[0].length;
            vHalmaz = yMaxAct * xMaxAct;
            int source = 0;

            dist = new int[xMaxAct][yMaxAct];
            visited = new HashSet<>();
            pqueue = new PriorityQueue<Point>(vHalmaz, new Point());

            adjacentList = new HashMap<>();

            //Initalize adjacent
            for (int x = 0; x < xMaxAct; x++) {
                for (int y = 0; y < yMaxAct; y++) {
                    List<Point> pointsNeighbours = new ArrayList<>();

                    // 1 Step Right
                    if (x + 1 < xMaxAct) {
                        pointsNeighbours.add(new Point(x + 1, y, matrixCucc[x + 1][y]));
                    }

                    // 1 Step Backward
                    if (y + 1 < yMaxAct) {
                        pointsNeighbours.add(new Point(x, y + 1, matrixCucc[x][y + 1]));
                    }
                    adjacentList.put(new Point(x, y), pointsNeighbours);
                }
            }

            algo_dijkstra(adjacentList, init);
            System.out.println("Yolo");

            // Print the shortest path from source node to all the nodes
            System.out.println("The shorted path from source node to other nodes:");
            System.out.println("Source\t\t" + "Point#\t\t" + "Distance");

            int[][] actualNew = new int[yMaxAct][xMaxAct];
            for (int i = 0; i < xMaxAct; i++) {
                for (int j = 0; j < yMaxAct; j++) {
                    actualNew[j][i] = dist[i][j];
                    System.out.println(source + " \t\t " + i + "  j: " + j + " \t\t " + dist[i][j]);
                    //dist[i][j] = Integer.MAX_VALUE;
                }
            }
            return actualNew;
        }

        // Dijkstra's Algorithm implementation
        public void algo_dijkstra(Map<Point, List<Point>> adj_list, Point point) {
            this.adjacentList = new HashMap<>(adj_list);

            for (int i = 0; i < xMaxAct; i++) {
                for (int j = 0; j < yMaxAct; j++) {
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }

            // first add source vertex to PriorityQueue
            pqueue.add(point);

            // Distance to the source from itself is 0
            dist[point.x][point.y] = 0;
            while (visited.size() != vHalmaz) {

                // u is removed from PriorityQueue and has min distance
                Point u = pqueue.remove();

                // add node to finalized list (visited)
                visited.add(u);
                graph_adjacentNodes(u);
            }
        }

        private void graph_adjacentNodes(Point u) {
            int edgeDistance = -1;
            int newDistance = -1;

            // process all neighbouring nodes of u
            for (int i = 0; i < adjacentList.get(u).size(); i++) {
                Point v = adjacentList.get(u).get(i);

                //  proceed only if current node is not in 'visited'
                if (!visited.contains(v)) {
                    edgeDistance = v.value;
                    newDistance = dist[u.x][u.y] + edgeDistance;

                    // compare distances
                    if (newDistance < dist[v.x][v.y])
                        dist[v.x][v.y] = newDistance;

                    // Add the current vertex to the PriorityQueue
                    pqueue.add(new Point(v.x, v.y, dist[v.x][v.y]));
                }
            }
        }
    }


    class Point implements Comparator<Point> {
        int x;
        int y;
        int value;

        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compare(Point p1, Point p2) {
            if (p1.value < p2.value) {
                return -1;
            }
            if (p1.value > p2.value) {
                return 1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}


