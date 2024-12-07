package com.fmolnar.code.year2022.day10;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day10/input.txt");

        int cycle = 1;
        int point = 1;
        int positionCRT = 0;
        List<String> pixels = new ArrayList<>();
        List<Integer> sums = new ArrayList<>();
        for (String line : lines) {
            String[] splitted = line.split(" ");
            sumUp(cycle, point, sums);
            if (splitted.length == 1) {
                calculCRT(positionCRT++, point, pixels);
                cycle++;
            } else {
                calculCRT(positionCRT++, point, pixels);
                cycle++;
                calculCRT(positionCRT++, point, pixels);
                sumUp(cycle++, point, sums);
                point += Integer.valueOf(splitted[1]);
            }
        }

        System.out.println("First: " + sums.stream().mapToInt(s->s).sum());

        System.out.println("Second: ");
        for (int i = 0; i < pixels.size(); i++) {
            if (i % 40 == 0) {
                System.out.println();
            }
            System.out.print(pixels.get(i));

        }
    }

    private void sumUp(int cycle, int point, List<Integer> sums) {
        if((cycle%40) == 20){
            sums.add(cycle*point);
        }
    }

    private void calculCRT(int cycle, int point, List<String> integs) {
        if (Math.abs(point - (cycle % 40)) < 2) {
            integs.add("#");
        } else {
            integs.add(".");
        }
    }
}
