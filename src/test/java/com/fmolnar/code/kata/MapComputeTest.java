package com.fmolnar.code.kata;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void testIdentityHashMap() {
        Map<Node, Integer> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put(new Node(1, 1), 2);
        identityHashMap.put(new Node(1, 3), 2);
        identityHashMap.put(new Node(1, 1), 10);

        System.out.println(identityHashMap);

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
    public void testComparator() {
        Node node1 = new Node(1, 1);
        Node node2 = new Node(21, 25);
        Node node3 = new Node(15, 300);

        List<Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        Collections.sort(nodes, Comparator.comparingInt(n -> n.value));
        assertThat(nodes).containsSequence(node1, node3, node2);

        Collections.sort(nodes, Comparator.comparingInt(n -> -1 * n.value));
        assertThat(nodes).containsSequence(node2, node3, node1);
    }

    @Test
    public void testMapFlaten() {

        Map<String, List<Integer>> maps = new HashMap<>();
        maps.put("toto", List.of(1, 2, 3));
        maps.put("titi", List.of(10, 11, 12));

        List<Integer> integs = maps.entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).collect(Collectors.toList());

        assertThat(integs).containsExactly(1, 2, 3, 10, 11, 12);
    }

    @Test
    public void testOffsetDateTimeFromInstant() {
        Instant instant = Instant.now();
        OffsetDateTime toto = OffsetDateTime.parse("2019-06-12T00:00:00+02:00");

        OffsetDateTime result = OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());

        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(toto));
        System.out.println(toto);

        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(result));
        System.out.println(result);
    }

    @Test
    public void testArray() {
        List<String> toto = Arrays.asList("kaka");
        toto.add("Kikik");

        toto.forEach(System.out::println);
    }

    @Test
    public void testOptionalMap() {
        Optional<String> toto = Optional.empty();
        assertThat(makeMap(Optional.empty())).isEqualTo("Legkulsoben vagyunk");
        assertThat(makeMap(Optional.ofNullable(null))).isEqualTo("Legkulsoben vagyunk");
        assertThat(makeMap(Optional.of("Kaka"))).isEqualTo("Bement a kakaba");
        assertThat(makeMap(Optional.of("fbsdhf"))).isEqualTo("Kiki");
    }

    String makeMap(Optional<String> optional){
        return optional.map(s -> {
            if (s.equals("Kaka")) {
                return "Bement a kakaba";
            }
            return "Kiki";
        }).orElse("Legkulsoben vagyunk");
    }

    record Node(int value, int position) {
    }
}
