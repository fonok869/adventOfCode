package com.fmolnar.code.leetcode.duplicateelements;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DuplicateElements {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
        Map<Integer, Long> counts = numbers.stream().collect(Collectors.groupingBy(Function.identity(),
                Collectors.counting()));
        System.out.println(counts);
    }
}
