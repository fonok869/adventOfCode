package com.fmolnar.code.kata;

import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MapComputeTest {

    @Test
    public void testMapComputeBiFunctional() {

        Map<String, Integer> stringMaps = new HashMap<>();
        stringMaps.put(String.valueOf(1), 15);
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                stringMaps.compute(String.valueOf(i), (k, v) -> v == null ? 1 : v + 1);
            }
        }

        assertThat(stringMaps).containsEntry("0", 2).containsEntry("1", 17).containsEntry("2", 2);
    }

    @Test
    public void testMapComputeBiFunctionalMapSet() {
        Map<String, Set<Integer>> stringMaps = new HashMap<>();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                final int integerToSet = i;
                stringMaps.compute(String.valueOf(i), (k, oldSet) -> {
                    Set<Integer> ints = oldSet == null ? new HashSet<>() : oldSet;
                    ints.add(integerToSet);
                    return ints;
                });
            }
        }

        assertThat(stringMaps).containsEntry("0", Sets.set(0, 0)).containsEntry("1", Sets.set(1, 1)).containsEntry("2", Sets.set(2, 2));
    }

    @Test
    public void testComparator(){
        Node node1 = new Node(1,1);
        Node node2 = new Node(21,25);
        Node node3 =new Node(15,300);

        List<Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        Collections.sort(nodes, Comparator.comparingInt(n-> n.value));
        assertThat(nodes).containsSequence(node1, node3, node2);

        Collections.sort(nodes, Comparator.comparingInt(n-> -1*n.value));
        assertThat(nodes).containsSequence(node2, node3, node1);
    }

    record Node(int value, int position){}
}
