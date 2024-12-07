package com.fmolnar.code.year2024.day07;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Day07 {
    public void calculteDay07() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day07/input.txt");

        Map<PointXY, String> mapsIntegs = AdventOfCodeUtils.getMapStringInput(lines);
        // Map<PointXY, Integer> mapNumbers = AdventOfCodeUtils.getMapIntegersInput(lines);

        for (String line : lines) {
            System.out.println(line);
        }
    }
}
