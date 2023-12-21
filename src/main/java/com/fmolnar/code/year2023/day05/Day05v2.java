package com.fmolnar.code.year2023.day05;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day05v2 {

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day05/input.txt");

        Set<SeedRange> seedsByDefault = new HashSet<>();
        List<SolMapRange> solmapRanges = new ArrayList<>();
        List<SolLineRange> lineRanges = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("seeds")) {
                String[] ranges = line.substring(line.indexOf(":") + 1).trim().split(" ");
                for (int i = 0; i < ranges.length; i = i + 2) {
                    long init = Long.valueOf(ranges[i].trim());
                    long end = init + Long.valueOf(ranges[i + 1].trim());
                    seedsByDefault.add(new SeedRange(init, end));
                }
            } else if (line.contains("map:")) {
                if (!lineRanges.isEmpty()) {
                    solmapRanges.add(new SolMapRange(lineRanges));
                    lineRanges = new ArrayList<>();

                }
            } else if (!line.isEmpty()) {
                String[] ins = line.split(" ");
                long destination = Long.valueOf(ins[0]);
                long source = Long.valueOf(ins[1]);
                long rang = Long.valueOf(ins[2]);
                lineRanges.add(new SolLineRange(new SeedRange(source, source + rang - 1), new SeedRange(destination, destination + rang - 1)));
            }

        }

        System.out.println(solmapRanges);

        List<SeedRange> seedRangeToTest = new ArrayList<>();

        for (SolMapRange solMap : solmapRanges) {

            seedsByDefault.forEach(seedRange ->
                    seedRangeToTest.addAll(solMap.calculatenewRanges(seedRange))
            );

            seedsByDefault.clear();
            seedsByDefault.addAll(seedRangeToTest);
            seedRangeToTest.clear();

        }

        System.out.println("toto");


    }

    public record SeedRange(long from, long to) {
    }

    public record SolMapRange(List<SolLineRange> ranges) {


        public SolMapRange {
        }

        public List<SeedRange> calculatenewRanges(SeedRange seedRange) {
            List<SeedRange> newSeedRanges = new ArrayList<>();
            Set<Long> beginning = ranges.stream().map(range -> range.from.from).collect(Collectors.toSet());
            List<Long> beginList = ranges.stream().map(range -> range.from.from).collect(Collectors.toList());
            Map<Long, SeedRange> beginningSeedRange = ranges.stream().map(SolLineRange::from).collect(Collectors.toMap(SeedRange::from, Function.identity()));
            Set<Long> ending = ranges.stream().map(range -> range.from.to).collect(Collectors.toSet());
            List<Long> endingList = ranges.stream().map(range -> range.from.to).collect(Collectors.toList());
            Map<Long, SeedRange> endingSeedRange = ranges.stream().map(SolLineRange::from).collect(Collectors.toMap(SeedRange::to, Function.identity()));
            Map<SeedRange, SeedRange> transformer = ranges.stream().collect(Collectors.toMap(SolLineRange::from, SolLineRange::to));

            List<Long> limits = ranges.stream()
                    .map(range -> Arrays.asList(range.from.from, range.from.to)).
                    collect(ArrayList::new, List::addAll, List::addAll);
            Collections.sort(limits);

            List<Long> limitsWithRange = new ArrayList<>();
            limitsWithRange.addAll(limits);
            limitsWithRange.add(seedRange.from);
            limitsWithRange.add(seedRange.to);


            Collections.sort(limitsWithRange);

            int beginIndex = limitsWithRange.indexOf(seedRange.from);
            int endIndex = limitsWithRange.lastIndexOf(seedRange.to);


            for (int index = beginIndex + 1; index <= endIndex; index++) {
                long numberActual = limitsWithRange.get(index);
                // Eleje nincs benne sehol
                if(index == endIndex && numberActual <limits.stream().mapToLong(szam->szam).min().getAsLong()){
                    newSeedRanges.add(seedRange);
                    // Ninncs tovabbi treatment
//                } else if (index == endIndex && (index == 0 || endIndex == limitsWithRange.size() - 1)) {
//                    // eleje es atmegy
//                    newSeedRanges.add(seedRange);
//                } else if (index == endIndex) {
//                    // koztes
//                    SeedRange seedRangeInside = endingSeedRange.get(limitsWithRange.get(index + 1));
//                    long beginActual = (limitsWithRange.get(index - 1) - seedRangeInside.from);
//                    long endActual = (numberActual - seedRangeInside.from);
//                    SeedRange transformerTo = transformer.get(seedRangeInside);
//                    newSeedRanges.add(new SeedRange(transformerTo.from + beginActual, transformerTo.from + endActual));
//                    System.out.println("Koztes : new SeedRange(transformerTo.from + beginActual, transformerTo.from + endActual)" + new SeedRange(transformerTo.from + beginActual, transformerTo.from + endActual));
                } else if (beginning.contains(numberActual)) {
                    // Eleje -1 SeedRange

                    SeedRange actual = new SeedRange(limitsWithRange.get(index - 1), numberActual - 1);
                    if(numberActual== seedRange.to){
                        actual = new SeedRange(limitsWithRange.get(index - 1), numberActual );
                    }
                    newSeedRanges.add(actual);
                    System.out.println("// From new SeedRange(limitsWithRange.get(index - 1), numberActual - 1)" + actual);
                    seedRange=new SeedRange(numberActual, seedRange.to);
//                } else if (ending.contains(numberActual)) {
//                    // transformaltSzam
//                    SeedRange seedEndRange = endingSeedRange.get(numberActual);
//                    long beginActual = (limitsWithRange.get(index - 1) - seedEndRange.from);
//                    long endActual = (numberActual - seedEndRange.from);
//                    SeedRange transformerTo = transformer.get(seedEndRange);
//                    newSeedRanges.add(new SeedRange(transformerTo.from + beginActual, transformerTo.from + endActual));
//                    seedRange = new SeedRange(numberActual+1 , seedRange.to);
//                    //TODO
//                } else {
//                    System.out.println("Nemm kellene itt lennie");
//                }
                }

            }

            Long[] array = limitsWithRange.toArray(Long[]::new); // fill the array

            System.out.println();

            System.out.println("Limits");
            limits.forEach(s -> System.out.print(s + " "));
            System.out.println("\nLimits with range");
            limitsWithRange.forEach(s -> System.out.print(s + " "));

            System.out.println(" ");
            return newSeedRanges;
        }

    }


    public record SolLineRange(SeedRange from, SeedRange to) {

    }

}
