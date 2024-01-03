package com.fmolnar.code.year2023.day05;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day05 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day05/input.txt");

        Set<Long> seedsByDefault = new HashSet<>();
        List<SolMap> solmaps = new ArrayList<>();
        List<SolLine> solLines = new ArrayList<>();
        SolMap actual = null;
        for (String line : lines) {
            if (line.startsWith("seeds")) {
                String[] ranges = line.substring(line.indexOf(":") + 1).trim().split(" ");
                for(int i=0; i< ranges.length; i++){
                    seedsByDefault.add(Long.valueOf(ranges[i]));
                }
            } else if (line.contains("map:")) {
                if (!solLines.isEmpty()) {
                    solmaps.add(new SolMap(solLines));
                    solLines = new ArrayList<>();

                }
            } else if (!line.isEmpty()) {
                String[] ins = line.split(" ");
                long destination = Long.valueOf(ins[0]);
                long source = Long.valueOf(ins[1]);
                long rang = Long.valueOf(ins[2]);
                solLines.add(new SolLine(destination, source, rang));
            }

        }

        long max = seedsByDefault.stream().mapToLong(s -> s).max().getAsLong();
        long min = max;
        Map<Integer, Map<Long, Long>> results = new HashMap<>();
        for (SolMap solMap : solmaps) {
            Map<Long, Long> solMapResult = new HashMap<>();
            seedsByDefault.forEach(s -> solMapResult.put(s, s));

            for (SolLine solLine : solMap.seeds) {
                long sourceStart = solLine.source;
                long sourceEnd = solLine.source + solLine.range - 1;
                long destinationStart = solLine.destination;
                long destinationEnd = solLine.destination + solLine.range - 1;
                for (Long seedToSearchFor : seedsByDefault) {
                    if (sourceStart <= seedToSearchFor && seedToSearchFor <= sourceEnd) {
                        long diff = seedToSearchFor - sourceStart;
                        long destVal = destinationStart + diff;
                        solMapResult.put(seedToSearchFor, destVal);
                    }
                }
            }


            seedsByDefault.clear();
            for (Map.Entry<Long, Long> entry : solMapResult.entrySet()) {
                seedsByDefault.add(entry.getValue());
            }

        }

        System.out.println("Solution: " + seedsByDefault.stream().mapToLong(s -> s).min().getAsLong());
    }

    public record SolMap(List<SolLine> seeds) {

    }

    public record SolLine(long destination, long source, long range) {

    }
}
