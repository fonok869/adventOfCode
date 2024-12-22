package com.fmolnar.code.year2024;

import com.fmolnar.code.year2024.day21.Day21_2024;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day20Test {

    @Test
    public void shouldeturn() {
        Day21_2024 day = new Day21_2024(new ArrayList<>());

        List<String> lista = new ArrayList<>();
        lista.add("<A^A>^^AvvvA");
        lista.add("<A^A^>^AvvvA");
        lista.add("<A^A^^>AvvvA");
        assertThat(day.getAllDirectionalKepads(lista)).contains("v<<A>>^A<A>AvA<^AA>A<vAAA>^A");
    }

    @Test
    public void shouldeturn2() {
        Day21_2024 day = new Day21_2024(new ArrayList<>());

        List<String> lista = new ArrayList<>();
        lista.add("v<<A>>^A<A>AvA<^AA>A<vAAA>^A");
        Set<Integer> integs = new HashSet<>();
        day.getAllDirectionalKepads(lista);
        assertThat(integs).contains(1);

    }

    @Test
    public void shouldeturn3() {
        Day21_2024 day = new Day21_2024(new ArrayList<>());

        List<String> lista = new ArrayList<>();
        lista.add("<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A");
        Set<Integer> integs = new HashSet<>();
        day.getAllDirectionalKepads(lista);
        assertThat(integs).contains(1);
    }


    @ParameterizedTest
    @MethodSource("testOperator")
    public void shouldeturn1(List before, List after) {
        Day21_2024 day = new Day21_2024(new ArrayList<>());

        assertThat(day.getAllDirectionalKepads(before)).containsAll(after);
    }

    private static Stream<Arguments> testOperator() {
        return Stream.of(
//
                Arguments.of(List.of("<"), List.of("v<<A", "<v<A")),
                Arguments.of(List.of("v"), List.of("v<A", "<vA")),
                Arguments.of(List.of("A"), List.of("A")),
                Arguments.of(List.of(">"), List.of("vA"))
        );
    }
}
