package com.fmolnar.code.year2024.day14;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day14_2024 {
    public void calculateday142024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day14/input.txt");

        int maxX = 101;
        int maxY = 103;
        List<Pointer> pointers = new ArrayList<>();
        List<Pointer> pointers100 = new ArrayList<>();
        for (String line : lines) {
            int indexOf = line.indexOf('v');
            String subStringP = line.substring(2, indexOf).trim();
            String subStringV = line.substring(indexOf + 2).trim();
            String[] poisitons = subStringP.split(",");
            String[] velocities = subStringV.split(",");
            PointXY position = new PointXY(Integer.parseInt(poisitons[0]), Integer.parseInt(poisitons[1]));
            PointXY velocity = new PointXY(Integer.parseInt(velocities[0]), Integer.parseInt(velocities[1]));
            pointers.add(new Pointer(position, velocity));
        }

        int maxSteps = 10000;
        Set<PointXY> points = new HashSet<>();
        int steps = 0;
        outer:
        for (int i = 0; i < maxSteps; i++) {
            List<Pointer> newPointers = new ArrayList<>();
            for (Pointer pointer : pointers) {
                PointXY pointXYNew = pointer.p().add(pointer.v());
                int xFinal = getValueInRangeMax(0, maxX - 1, pointXYNew.x());
                int yFinal = getValueInRangeMax(0, maxY - 1, pointXYNew.y());
                pointXYNew = new PointXY(xFinal, yFinal);
                newPointers.add(new Pointer(pointXYNew, pointer.v()));
            }

            pointers = new ArrayList<>(newPointers);
            points = pointers.stream().map(Pointer::p).collect(Collectors.toSet());

            if (i == 99) {
                pointers100 = new ArrayList<>(pointers);
            }

            if (hasFourStraightLines(points)) {
                steps = i + 1;
                for (int j = 0; j < maxY; j++) {
                    for (int x = 0; x < maxX; x++) {
                        if (points.contains(new PointXY(x, j))) {
                            System.out.print("#");
                        } else {
                            System.out.print(".");
                        }
                    }
                    System.out.println();
                }
                break outer;
            }
        }

        System.out.println("Steps: " + steps);

        // count
        long firstQuadre = 0;
        long secondQuadre = 0;
        long thirdQuadre = 0;
        long fourthQuadre = 0;
        for (Pointer pointer : pointers100) {
            if (pointer.p().x() < maxX / 2 && pointer.p().y() < maxY / 2) {
                firstQuadre++;
            } else if (pointer.p().x() > maxX / 2 && pointer.p().y() < maxY / 2) {
                secondQuadre++;
            } else if (pointer.p().x() < maxX / 2 && pointer.p().y() > maxY / 2) {
                thirdQuadre++;
            } else if (pointer.p().x() > maxX / 2 && pointer.p().y() > maxY / 2) {
                fourthQuadre++;
            }
        }

        long total = firstQuadre * secondQuadre * thirdQuadre * fourthQuadre;
        System.out.println("total : " + total);
    }

    private int getValueInRangeMax(int rangeBeginInclusive, int rangeEndInclusive, int number) {
        int returnValue = number;
        if (number < 0) {
            return (number + rangeEndInclusive + 1) % (rangeEndInclusive + 1);
        }
        if (rangeEndInclusive < number) {
            return number % (rangeEndInclusive + 1);
        }
        return returnValue;
    }

    private boolean hasFourStraightLines(Set<PointXY> points) {

        int countAllCorner = 0;
        for (PointXY pointXY : points) {
            int counterCorener = 0;
            for (PointXY direction : AdventOfCodeUtils.directionsNormals) {
                PointXY newPoints = pointXY.add(direction);
                if (points.contains(newPoints)) {
                    newPoints = newPoints.add(direction);
                    if (points.contains(newPoints)) {
                        newPoints = newPoints.add(direction);
                        if (points.contains(newPoints)) {
                            counterCorener++;
                        }
                    }
                }
            }
            if (counterCorener == 2) {
                countAllCorner++;
            }
        }
        if (3 < countAllCorner) {
            return true;
        }
        return false;
    }
}

record Pointer(PointXY p, PointXY v) {

}
