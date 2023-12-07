package com.fmolnar.code.year2023.day06;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day06 {

    public static void calculate() throws IOException {
        //List<TimeDistance> timesNew = Arrays.asList(new TimeDistance(7, 9), new TimeDistance(15, 40), new TimeDistance(30, 200));
        List<TimeDistance> timesNew = Arrays.asList(new TimeDistance(56,499), new TimeDistance(97, 2210), new TimeDistance(77, 1097),new TimeDistance(93, 1440));

        TimeDistance times = timesNew.stream()
                .reduce(new TimeDistance(0, 0), (a, b) -> new TimeDistance(Long.valueOf(String.valueOf(a.time) + String.valueOf(b.time)), Long.valueOf(String.valueOf(a.distance) + String.valueOf(b.distance))));


        System.out.println("Record : " + LongStream.rangeClosed(0, times.time).filter(init -> times.distance < (init * (times.time - init))).count());
    }

    public static void calculat2() throws IOException {
        List<TimeDistance> times = Arrays.asList(new TimeDistance(7l, 9l), new TimeDistance(15l, 40l), new TimeDistance(30l, 200l));
        //List<TimeDistance> times = Arrays.asList(new TimeDistance(56,499), new TimeDistance(97, 2210), new TimeDistance(77, 1097),
        List<Long> counts = new ArrayList<>();
        times.forEach(t -> {
            counts.add(LongStream.rangeClosed(0, t.time).filter(init -> t.distance < (init * (t.time - init))).count());
        });

        System.out.println("Max: " + counts.stream().mapToLong(s -> s).reduce(1, (a, b) -> a * b));

    }

    record TimeDistance(long time, long distance) {
    }
}
