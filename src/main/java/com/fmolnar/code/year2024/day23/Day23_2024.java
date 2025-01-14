package com.fmolnar.code.year2024.day23;

import com.fmolnar.code.AdventOfCodeUtils;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23_2024 {

    private final Map<String, Set<String>> mapsConnextion;
    private final SimpleGraph<String, DefaultEdge> graph;

    public Day23_2024() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2024/day23/input.txt");

        graph = new SimpleGraph<>(DefaultEdge.class);
        mapsConnextion = new HashMap<>();
        for (String line : lines) {
            String[] computers = line.split("-");
            getConnections(computers[0], computers[1]);
            getConnections(computers[1], computers[0]);
            graph.addVertex(computers[0]);
            graph.addVertex(computers[1]);
            graph.addEdge(computers[0], computers[1]);
        }
    }

    public void calculateDay232024() throws IOException {

        Set<Set<String>> interconnectedTrios = new HashSet<>();
        for (String pc1 : mapsConnextion.keySet()) {
            Set<String> pc1Connections = mapsConnextion.get(pc1);
            for (String pc2 : pc1Connections) {
                Set<String> pc2Connections = mapsConnextion.get(pc2);
                for (String pc3 : pc2Connections) {
                    Set<String> pc3Connections = mapsConnextion.get(pc3);
                    if (!pc3.equals(pc1) && pc3Connections.contains(pc2) && pc3Connections.contains(pc1)) {
                        interconnectedTrios.add(Set.of(pc1, pc2, pc3));
                    }
                }
            }

        }

        long interconnectedTriosContainingT = interconnectedTrios.stream().filter(this::anyComputerStartsWithT).count();
        System.out.println(interconnectedTriosContainingT);

        Set<String> result = (Set<String>) new BronKerboschCliqueFinder(graph).maximumIterator().next();
        System.out.println(result.stream().sorted().collect(Collectors.joining(",")));
    }

    private boolean anyComputerStartsWithT(Set<String> computers) {
        if (computers.stream().filter(c -> c.startsWith("t")).findFirst().isPresent()) {
            return true;
        }
        return false;
    }

    private void getConnections(String pc1, String pc2) {
        if (!mapsConnextion.containsKey(pc1)) {
            mapsConnextion.put(pc1, new HashSet<>());
        }
        mapsConnextion.get(pc1).add(pc2);

    }
}
