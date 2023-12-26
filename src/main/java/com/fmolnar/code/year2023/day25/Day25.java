package com.fmolnar.code.year2023.day25;

import com.fmolnar.code.FileReaderUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.interfaces.MinimumSTCutAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day25 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day25/input.txt");
        List<Node> nodes = new ArrayList<>();

        for (String line : lines) {
            String[] lineArray = line.split(":");
            nodes.add(new Node(lineArray[0], Arrays.stream(lineArray[1].trim().split(" ")).collect(Collectors.toSet())));
        }

        Set<String> allVertices = new HashSet<>();

        Graph<Integer, DefaultEdge> undirectedGrapghs = new WeightedMultigraph<>(DefaultEdge.class);

        Graph<String, DefaultWeightedEdge> graph =
                new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);



        // Set<String> Vertices
        for (Node nodeActual : nodes) {
            allVertices.add(nodeActual.init);
            Set<String> celok = nodeActual.otherNodes;
            for(String cel : celok){
                allVertices.add(cel);
            }
        }

        Graphs.addAllVertices(graph, allVertices);

        for (Node nodeActual : nodes) {
            Set<String> celok = nodeActual.otherNodes;
            for(String cel : celok){
                graph.addEdge(nodeActual.init, cel);
            }
        }

        MinimumSTCutAlgorithm<String, DefaultWeightedEdge> mc = new EdmondsKarpMFImpl<>(graph);
        System.out.println("Minimum s-t cut weight: "+mc.calculateMinCut("zfk", "tmc"));
        System.out.println("Source partition S: "+mc.getSourcePartition());
        System.out.println("Sink partition T: "+mc.getSinkPartition());
        System.out.println("Cut edges (edges with their tail in S and their head in T): "+mc.getCutEdges());
        System.out.println(mc.getSourcePartition().size() * mc.getSinkPartition().size());


    }

    record Node(String init, Set<String> otherNodes) {
    }
}
