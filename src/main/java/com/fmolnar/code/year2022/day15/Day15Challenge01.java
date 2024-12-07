package com.fmolnar.code.year2022.day15;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15Challenge01 {
    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day15/input.txt");

        int ySearchFor = 2000000;
        PointXY sensor;
        PointXY beacon ;
        Set<PointXY> blocked = new HashSet<>();
        Set<PointXY> beacons = new HashSet<>();
        Set<PointXY> sensors = new HashSet<>();
        for(String line : lines){
            String[] instructions = line.split(":");
            sensor = pointSearch(instructions[0]);
            beacon = pointSearch(instructions[1]);
            long manDist = calculateManhattanDistance(sensor, beacon);
            if(sensor.isInManDistance(manDist, ySearchFor)){
                addAllBlockedPoint(sensor, manDist, blocked, ySearchFor);
            }
            beacons.add(beacon);
            sensors.add(sensor);
        }

        blocked.removeAll(beacons);
        blocked.removeAll(sensors);
        System.out.println("First: " + blocked.size());
    }

    private void addAllBlockedPoint(PointXY pointXYSearch, long manDist, Set<PointXY> blocked, int ySearchFor) {
        long yDiff = Math.abs(pointXYSearch.y - ySearchFor);
        long xDiffMax = manDist - yDiff;
        for(long x = (-1 * xDiffMax) + pointXYSearch.x; x<=xDiffMax + pointXYSearch.x; x++){
            blocked.add(new PointXY((int) x, ySearchFor));
        }
    }

    private long calculateManhattanDistance(PointXY pointXYSearch, PointXY beacon) {
        long diffX = Math.abs(pointXYSearch.x  - beacon.x);
        long diffY = Math.abs(pointXYSearch.y  - beacon.y);
        return diffX + diffY;
    }

    private PointXY pointSearch(String i1) {
        int x = Integer.valueOf(i1.substring((i1.indexOf("x=")+2), i1.indexOf(",")));
        int y = Integer.valueOf(i1.substring(i1.indexOf("y=")+2));
        return new PointXY(x,y);
    }

    record PointXY(int x, int y){
        public boolean isInManDistance(long manDist, int ySearchFor) {
            if(ySearchFor<=(y + manDist) && ((y-manDist)<=ySearchFor)){
                return true;
            }
            return false;
        }
    };
}
