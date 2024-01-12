package com.fmolnar.code.year2022.day15;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15Challenge02Multithreading {

    AtomicInteger atomicInteger = new AtomicInteger();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day15/input.txt");

        //int ySearchFor = 10;
        Pointer sensor;
        Pointer beacon;
        final int maxValue = 4000000;
        final long init = System.currentTimeMillis();
        List<PointXY> points = new ArrayList<>();
        for (String line : lines) {
            String[] instructions = line.split(":");
            sensor = pointSearch(instructions[0]);
            beacon = pointSearch(instructions[1]);
            long manDist = calculateManhattanDistance(sensor, beacon);
            points.add(new PointXY(sensor.x, sensor.y, manDist));
        }

        Collections.sort(points, Comparator.comparingLong(pointXY -> -1 * pointXY.manDist()));


        final List<PointXY> finalPoints = new ArrayList<>(points);

        final int maxThreads = 50;
        Thread[] threads = new Thread[maxThreads];
        for (int r = 0; r < maxThreads; r++) {
            int minX = (int) (((r * (1.0) / (maxThreads * 1.0))) * maxValue * 1.0);
            int maxX = (int) ((((r + 1) * 1.0 / (maxThreads * 1.0))) * maxValue * 1.0);
            threads[r] = createThread(minX, maxX, maxValue, finalPoints, init);
            threads[r].start();
        }
    }

    private Thread createThread(final int min, final int max, final int maxValue, List<PointXY> finalPoints, long init) {
        return new Thread(new Runnable() {
            @Override
            public void run() {

                looper:
                for (int i = min; i <= max; i++) {
                    //System.out.println("i: " + i);
                    for (int j = 0; j <= maxValue; j++) {
                        boolean breaked = false;
                        for (PointXY pointXY : finalPoints) {
                            if (pointXY.isInManDistance(i, j)) {
                                breaked = true;
                                break;
                            }
                        }
                        if (!breaked) {
                            long result = i * 4000000l + j;
                            System.out.println("Result: " + result + " i: " + i + " j: " + j);
                            break looper;
                        }
                    }
                    atomicInteger.incrementAndGet();
                }
            }
        });
    }


    private long calculateManhattanDistance(Pointer pointXYSearch, Pointer beacon) {
        long diffX = Math.abs(pointXYSearch.x - beacon.x);
        long diffY = Math.abs(pointXYSearch.y - beacon.y);
        return diffX + diffY;
    }

    record Pointer(int x, int y) {

    }

    ;

    private Pointer pointSearch(String i1) {
        int x = Integer.valueOf(i1.substring((i1.indexOf("x=") + 2), i1.indexOf(",")));
        int y = Integer.valueOf(i1.substring(i1.indexOf("y=") + 2));
        return new Pointer(x, y);
    }

    record PointXY(int x, int y, long manDist) {
        public boolean isInManDistance(int xx, int yy) {
            if ((Math.abs(x - xx) + Math.abs(y - yy)) <= manDist) {
                return true;
            }
            return false;
        }
    }

    ;
}
