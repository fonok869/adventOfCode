package com.fmolnar.code.year2022.day09;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day09Challenge02 {
    Map<String, Point> directions = new HashMap<>();
    Map<Integer, Point> leadersPoistion = new HashMap<>(10);
    Set<Point> tails = new HashSet<>();
    Set<Point> second = new HashSet<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day09/input.txt");
        directions.put("L", new Point(-1, 0));
        directions.put("U", new Point(0, 1));
        directions.put("R", new Point(1, 0));
        directions.put("D", new Point(0, -1));

        for (int i = 0; i < 10; i++) {
            leadersPoistion.put(i, new Point(0, 0));
        }

        for (String line : lines) {
            String[] splitted = line.split(" ");
            Point direction = directions.get(splitted[0]);
            for (int j = 0; j < Integer.valueOf(splitted[1]); j++) {
                Point pointH = leadersPoistion.get(0);
                pointH = new Point(pointH.x + direction.x, pointH.y + direction.y);
                leadersPoistion.put(0, pointH);
                for (int leader = 0; leader < 9; leader++) {
                    leadersPoistion.put(leader+1,makeStepsBetweenPoints(leadersPoistion.get(leader), leadersPoistion.get(leader + 1)));
                }
                second.add(leadersPoistion.get(1));
                tails.add(leadersPoistion.get(9));
            }
        }

        System.out.println("Day09Challenge01: " + second.size());
        System.out.println("Day09Challenge02: " + tails.size());
    }

    private Point makeStepsBetweenPoints(Point pointH, Point pointL) {

        int diff = Math.abs(pointL.x - pointH.x) + Math.abs(pointL.y - pointH.y);
        if (diff <= 1) {
            // Touching
            // do not move
        } else if (diff == 2) {
            // Same row
            if (pointH.x == pointL.x) {
                pointL = new Point(pointL.x, pointL.y + (pointH.y - pointL.y) / 2);
                // Same line
            } else if (pointH.y == pointL.y) {
                pointL = new Point(pointL.x + (pointH.x - pointL.x) / 2, pointL.y);
            } else {
                // Diagonal
                // do not move
            }
        } else if (diff == 3) {
            if (Math.abs(pointH.x - pointL.x) == 1) {
                pointL = new Point(pointL.x + (pointH.x - pointL.x), pointL.y + (pointH.y - pointL.y) / 2);
            } else if (Math.abs(pointH.y - pointL.y) == 1) {
                pointL = new Point(pointL.x + (pointH.x - pointL.x) / 2, pointL.y + (pointH.y - pointL.y));
            } else {
                System.out.println("Should NOT be here 1");
            }

        } else if(diff == 4){
            pointL = new Point(pointL.x + (pointH.x - pointL.x)/2, pointL.y + (pointH.y - pointL.y)/2);
        } else {
            System.out.println("Should NOT be here 2");
        }
        return pointL;

    }
    record Point(int x, int y) { };
}
