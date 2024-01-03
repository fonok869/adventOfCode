package com.fmolnar.code.year2023.day05;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05v2 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

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
                    Collections.sort(lineRanges, new Comparator<SolLineRange>() {
                        @Override
                        public int compare(SolLineRange o1, SolLineRange o2) {
                            if (o1.from.from < o2.from.from) {
                                return -1;
                            } else if (o2.from.from < o1.from.from) {
                                return 1;
                            }
                            return 0;
                        }
                    });
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

        List<SeedRange> seedRangeToTest = new ArrayList<>();
        for (SolMapRange solMap : solmapRanges) {
            seedsByDefault.forEach(seedRange -> {
                        Collection<SeedRange> after = solMap.calculatenewRanges(seedRange);
                        seedRangeToTest.addAll(after);
                    }
            );
            seedsByDefault.clear();
            seedsByDefault.addAll(seedRangeToTest);
            seedRangeToTest.clear();

        }

        List<Long> longs = seedsByDefault.stream().mapToLong(range -> range.from).boxed().collect(Collectors.toList());
        Collections.sort(longs);
        System.out.println("Result : " + longs.get(0));

    }

    public record SeedRange(long from, long to) {
        boolean isCorrect() {
            if (from <= to) {
                return true;
            }
            return false;
        }
    }

    public record SolMapRange(List<SolLineRange> ranges) {
        public SeedRange getNewTranformedRanges(SolLineRange lineRange, SeedRange seedRange, Collection<SeedRange> nonTouched) {
            // full outside
            if (lineRange.from.to < seedRange.from || seedRange.to < lineRange.from.from) {
                nonTouched.add(seedRange);
                return null;
                // full inside
            } else if (lineRange.from.from <= seedRange.from && seedRange.to <= lineRange.from.to) {
                long fromRelativ = seedRange.from - lineRange.from.from;
                long toRelativ = seedRange.to - lineRange.from.from;
                return new SeedRange(lineRange.toTranform.from + fromRelativ, lineRange.toTranform.from + toRelativ);
                // Cut Before
            } else if (seedRange.from < lineRange.from.from && lineRange.from.from <= seedRange.to && seedRange.to <= lineRange.from.to) {
                return cutBefore(lineRange, seedRange, nonTouched);
                // Cut After
            } else if (lineRange.from.from <= seedRange.from && seedRange.from <= lineRange.from.to && lineRange.from.to < seedRange.to) {
                long fromRelativ = seedRange.from - lineRange.from.from;
                nonTouched.add(new SeedRange(lineRange.from.to + 1, seedRange.to));
                return new SeedRange(lineRange.toTranform.from + fromRelativ, lineRange.toTranform.to);
            } else if (seedRange.from < lineRange.from.from && lineRange.from.to < seedRange.to) {
                nonTouched.add(new SeedRange(lineRange.from.to + 1, seedRange.to));
                return cutBefore(lineRange, new SeedRange(seedRange.from, lineRange.from.to), nonTouched);
            } else {
                System.out.println(lineRange);
                System.out.println(seedRange);
                throw new RuntimeException("Cas non géré");
            }
        }

        private static SeedRange cutBefore(SolLineRange lineRange, SeedRange
                seedRange, Collection<SeedRange> nonTouched) {
            long toRelativ = seedRange.to - lineRange.from.from;
            nonTouched.add(new SeedRange(seedRange.from, lineRange.from.from - 1));
            return new SeedRange(lineRange.toTranform.from, lineRange.toTranform.from + toRelativ);
        }

        public Collection<SeedRange> calculatenewRanges(SeedRange seedRange) {
            Set<SeedRange> newSeedRanges = new HashSet<>();
            Set<SeedRange> transformed = new HashSet<>();
            Set<SeedRange> nonTouched = new HashSet<>();
            for (SolLineRange solLineRange : ranges) {
                if (nonTouched.isEmpty()) {
                    transformed.add(getNewTranformedRanges(solLineRange, seedRange, nonTouched));
                    nonTouched.remove(seedRange);
                    nonTouched = nonTouched.stream().filter(SeedRange::isCorrect).collect(Collectors.toSet());
                } else {
                    newSeedRanges.clear();
                    newSeedRanges.addAll(nonTouched);
                    nonTouched.clear();
                    for (SeedRange seedRangeToCheck : newSeedRanges) {
                        transformed.add(getNewTranformedRanges(solLineRange, seedRangeToCheck, nonTouched));
                    }
                    nonTouched = nonTouched.stream().filter(SeedRange::isCorrect).collect(Collectors.toSet());
                }
            }
            //if not inside --> we have to add it
            if (transformed.stream().filter(Objects::nonNull).filter(SeedRange::isCorrect).collect(Collectors.toList()).isEmpty()) {
                transformed.add(seedRange);
            }
            transformed.addAll(nonTouched);
            return transformed.stream().filter(Objects::nonNull).filter(SeedRange::isCorrect).collect(Collectors.toList());
        }
    }


    public record SolLineRange(SeedRange from, SeedRange toTranform) {
    }

}


