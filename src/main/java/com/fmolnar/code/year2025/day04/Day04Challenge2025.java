package com.fmolnar.code.year2025.day04;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day04Challenge2025 {
    Set<Point> rolls = new HashSet<>();

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day04/input.txt");
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '@') {
                    rolls.add(new Point(y, x));
                }
            }
        }

        Set<Point> forkLifts = getForkLifts(rolls, lines.size(), lines.get(0).length());
        System.out.println("Size: " + (rolls.size() - forkLifts.size()));
    }

    private Set<Point> getForkLifts(Set<Point> rolls2, int size, int length) {
        Set<Point> directions = getDirectionPoint();
        Set<Point> forkLifts = new HashSet<>();
        Set<Point> rolls = new HashSet<>(rolls2);
        Set<Point> roolsToCheck = new HashSet<>(rolls2);
        while (true) {
            for (Point point : roolsToCheck) {
                int counter = 0;
                for (Point direction : directions) {
                    if (rolls.contains(point.add(direction))) {
                        counter++;
                    }
                }
                if (counter < 4) {
                    forkLifts.add(point);
                }

            }
            if (forkLifts.isEmpty()) {
                break;
            }
            roolsToCheck.removeAll(forkLifts);
            rolls.removeAll(forkLifts);
            forkLifts = new HashSet<>();
        }
        return rolls;
    }

    private Set<Point> getDirectionPoint() {
        Set<Point> directions = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                directions.add(new Point(i, j));
            }
        }
        directions.remove(new Point(0, 0));
        return directions;
    }
}

record Point(int y, int x) {
    Point add(Point p) {
        return new Point(y + p.y, x + p.x);
    }

}
