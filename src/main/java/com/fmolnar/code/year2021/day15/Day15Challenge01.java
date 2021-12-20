package com.fmolnar.code.year2021.day15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Day15Challenge01 {

    List<List<Integer>> matrix = new ArrayList<>();
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

        int[][] balOldalso = new int[yMax][xMax];
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                balOldalso[y][x] = matrix.get(y).get(x);
            }
        }

        Calculate calculate = new Calculate();
        calculate.initMethod(balOldalso, new Node(0, 0));

    }

    class Calculate {

        int dist[];
        List<List<Node>> adjacentList;
        Set<Integer> visited;
        PriorityQueue<Node> pqueue;
        int vHalmaz; // Number of ver
        int xMaxAct = 0;
        int yMaxAct = 0;


        private void initMethod(int[][] matrixToCheck, Node initNode) {

            yMaxAct = matrixToCheck.length;
            xMaxAct = matrixToCheck[0].length;
            vHalmaz = yMaxAct * xMaxAct;
            int source = 0;

            dist = new int[vHalmaz];
            visited = new HashSet<>();
            pqueue = new PriorityQueue<Node>(vHalmaz, new Node());

            adjacentList = new ArrayList<>();

            //Initalize adjacent

            for (int y = 0; y < yMaxAct; y++) {
                for (int x = 0; x < xMaxAct; x++) {
                    List<Node> pointsNeighbours = new ArrayList<>();

                    // 1 Step Right
                    if (x + 1 < xMaxAct) {
                        int node = (x + 1) + (xMaxAct) * y;
                        int value = matrixToCheck[y][x + 1];
                        pointsNeighbours.add(new Node(node, value));
                    }

                    // 1 Step Left
                    if (0 < x - 1) {
                        int node = (x - 1) + (xMaxAct) * y;
                        int value = matrixToCheck[y][x - 1];
                        pointsNeighbours.add(new Node(node, value));
                    }

                    // 1 Step Up
                    if (0 < y - 1) {
                        int node = (x) + (xMaxAct) * (y - 1);
                        int value = matrixToCheck[y - 1][x];
                        pointsNeighbours.add(new Node(node, value));
                    }

                    // 1 Step DownWard
                    if (y + 1 < yMaxAct) {
                        int node = (x) + (xMaxAct) * (y + 1);
                        int value = matrixToCheck[y + 1][x];
                        pointsNeighbours.add(new Node(node, value));
                    }
                    adjacentList.add(pointsNeighbours);
                }
            }

            algo_dijkstra(adjacentList, 0);

            System.out.println("Shortest route: " + dist[vHalmaz - 1]);

        }

        // Dijkstra's Algorithm implementation
        public void algo_dijkstra(List<List<Node>> adj_list, int src_vertex) {
            this.adjacentList = adj_list;

            for (int i = 0; i < vHalmaz; i++) {
                dist[i] = Integer.MAX_VALUE;
            }

            // first add source vertex to PriorityQueue
            pqueue.add(new Node(src_vertex, 0));

            // Distance to the source from itself is 0
            dist[src_vertex] = 0;
            while (visited.size() != vHalmaz && !pqueue.isEmpty()) {

                // u is removed from PriorityQueue and has min distance
                int u = pqueue.remove().node;

                if (visited.contains(u)) {
                    continue;
                }

                // add node to finalized list (visited)
                visited.add(u);
                graph_adjacentNodes(u);
            }
        }

        // this methods processes all neighbours of the just visited node
        private void graph_adjacentNodes(int u) {
            int edgeDistance = -1;
            int newDistance = -1;

            // process all neighbouring nodes of u
            for (int i = 0; i < adjacentList.get(u).size(); i++) {
                Node v = adjacentList.get(u).get(i);

                //  proceed only if current node is not in 'visited'
                if (!visited.contains(v.node)) {
                    edgeDistance = v.cost;
                    newDistance = dist[u] + edgeDistance;

                    // compare distances
                    if (newDistance < dist[v.node])
                        dist[v.node] = newDistance;

                    // Add the current vertex to the PriorityQueue
                    pqueue.add(new Node(v.node, dist[v.node]));
                }
            }
        }
    }

    // Node class
    class Node implements Comparator<Node> {
        public int node;
        public int cost;

        public Node() {
        } //empty constructor

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2) {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }
    }
}


