package com.fmolnar.code.year2022.day15;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15Challenge02 {

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day15/input.txt");

        Pointer sensor;
        Pointer beacon;
        final int maxValue = 4000000;
        List<PointXY> points = new ArrayList<>();
        for (String line : lines) {
            String[] instructions = line.split(":");
            sensor = pointSearch(instructions[0]);
            beacon = pointSearch(instructions[1]);
            points.add(new PointXY(sensor.x, sensor.y, calculateManhattanDistance(sensor, beacon)));
        }

        Collections.sort(points, (o1, o2) -> o1.manDist()>o2.manDist() ? 1 : 1);

        final List<PointXY> finalPoints = new ArrayList<>(points);
        points.forEach( pointXY -> {
            PointXY pActual = new PointXY(pointXY.x, pointXY.y, pointXY.manDist +1);

            long minX = pActual.x - pActual.manDist;
            long maxX = pActual.x + pActual.manDist;
            looper: for(long i=minX; i<=maxX; i++){
                long y1 = (pActual.manDist - Math.abs(i-pActual.x)) + pActual.y;
                long y2 = (pActual.manDist - Math.abs(i-pActual.x))*(-1) + pActual.y;
                if(0<=i && i<=maxValue){
                    checkPointInsideInOthers(maxValue, i, y1, finalPoints);
                    checkPointInsideInOthers(maxValue, i, y2, finalPoints);
                }
            }
        });

    }

    private void checkPointInsideInOthers(int maxValue, long i, long y, List<PointXY> finalPoints) {
        if(0<= y && y <=maxValue){
            boolean breaked = false;
            for (PointXY tocheck : finalPoints) {
                if (tocheck.isInManDistance((int)i, (int) y)) {
                    breaked = true;
                    break;
                }
            }
            if (!breaked) {
                long result = i * 4000000l + y;
                throw new RuntimeException("Result: "+ result);
            }
        }
    }

    private long calculateManhattanDistance(Pointer pointXYSearch, Pointer beacon) {
        long diffX = Math.abs(pointXYSearch.x - beacon.x);
        long diffY = Math.abs(pointXYSearch.y - beacon.y);
        return diffX + diffY;
    }

    record Pointer(int x, int y) {  } ;

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
}
