package com.fmolnar.code.year2025.day02;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day02Challenge2025 {
    public void calculate() throws IOException {
        List<IdRanges> idRanges = new ArrayList<>();
        List<IdRanges2> idRanges2 = new ArrayList<>();
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day02/input.txt");
        String[] arrays = lines.get(0).split(",");
        for (String line : arrays) {
            String[] numbers = line.split("-");
            ;
            idRanges.add(new IdRanges(Long.valueOf(numbers[0]), Long.valueOf(numbers[1])));
            idRanges2.add(new IdRanges2(Long.valueOf(numbers[0]), Long.valueOf(numbers[1])));

        }
//        idRanges.stream().map(IdRanges::getPolyandromNumbers).flatMap(List::stream).forEach(System.out::println);

        idRanges2.forEach(toto -> System.out.println("Diff: " + (toto.end() - toto.init())));
        System.out.println("First: ");
        //idRanges.stream().map(IdRanges::getPolyandromNumbers).flatMap(List::stream).reduce(Long::sum).ifPresent(System.out::println);
        idRanges2.stream().map(IdRanges2::getPolyandromNumbers).flatMap(Set::stream).reduce(Long::sum).ifPresent(System.out::println);
    }
}

