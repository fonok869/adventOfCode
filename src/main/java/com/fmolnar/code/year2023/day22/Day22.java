package com.fmolnar.code.year2023.day22;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 {

    public static void main(String[] args) throws IOException {
       calculate();
    }

    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day22/input.txt");

        Set<TeglaTest> initTestek = new HashSet<>();
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            int indexOfTilde = line.indexOf('~');
            String firstPart = line.substring(0, indexOfTilde);
            String secondPart = line.substring(indexOfTilde + 1);

            String[] beginIndex = firstPart.split(",");
            String[] endIndex = secondPart.split(",");
            PointXYZ beginPoint = new PointXYZ(Integer.valueOf(beginIndex[0]), Integer.valueOf(beginIndex[1]), Integer.valueOf(beginIndex[2]));
            PointXYZ endPoint = new PointXYZ(Integer.valueOf(endIndex[0]), Integer.valueOf(endIndex[1]), Integer.valueOf(endIndex[2]));
            Direction dir = getDirectionTest(endPoint, beginPoint);
            TeglaTest teglaTestActual = new TeglaTest(index, beginPoint, endPoint, dir);
            initTestek.add(teglaTestActual);
        }


        Set<TeglaTest> zStables = new HashSet<>();

        executeFalling(initTestek, zStables);

        List<TeglaTest> teglaTests = new ArrayList<>(zStables);
        Collections.sort(teglaTests, (TeglaTest t1, TeglaTest t2) -> Integer.compare(t1.begin.z, t2.begin.z));

        Map<Integer, Set<TeglaTest>> zAndTeglatestekDropedMin = new HashMap<>();
        Map<Integer, Set<TeglaTest>> zAndTeglatestekDropedMax = new HashMap<>();

        for (TeglaTest teglaTestActualDropped : teglaTests) {
            // Megcsin 1 Sor
            zAndTeglatestekDropedMin.compute(teglaTestActualDropped.getMinZ(), (k,v) -> { return addTeglatest(v, teglaTestActualDropped);});

            // Megcsin 1 Sor
            zAndTeglatestekDropedMax.compute(teglaTestActualDropped.getMaxZ(), (k,v) -> { return addTeglatest(v, teglaTestActualDropped);});
        }

        countAllTartas(lines.size(), zStables, zAndTeglatestekDropedMax, zAndTeglatestekDropedMin);
        // -----------------------------


    }
    private static Set<TeglaTest> addTeglatest(Set<TeglaTest> already, TeglaTest teglaTestActual){
        Set<TeglaTest> testek = already == null ? new HashSet<>() : already;
        testek.add(teglaTestActual);
        return testek;
    }

    private static void executeFalling(Set<TeglaTest> initTestek, Set<TeglaTest> zStables) {

        Map<Integer, Set<TeglaTest>> zAndTeglatestek = new HashMap<>();
        for (TeglaTest teglaTestActual : initTestek) {
            // Megcsin 1 Sor
            zAndTeglatestek.compute(teglaTestActual.getMinZ(), (k, oldSet) -> {return addTeglatest(oldSet, teglaTestActual);});
        }
        // -----------------------------
        Map<PointXY, Integer> pointZMax = new HashMap<>();
        int maxFalling = initTestek.stream().mapToInt(TeglaTest::getMaxZ).max().getAsInt();

        for (int zActual = 1; zActual <= maxFalling; zActual++) {
            Set<TeglaTest> zActuals = zAndTeglatestek.get(zActual);
            if (zActuals == null || zActuals.isEmpty()) {
                continue;
            }
            for (TeglaTest teglatestZActual : zActuals) {
                // Lemehet-e ?
                if (teglatestZActual.direction == Direction.X) {
                    int newZSzint = zActual;
                    int yGeneral = teglatestZActual.begin.y;
                    for (int zCsokkeno = zActual; 1 <= zCsokkeno; zCsokkeno--) {
                        final int zCsokkenofinal = zCsokkeno;
                        if (IntStream.rangeClosed(Math.min(teglatestZActual.begin.x, teglatestZActual.end.x), Math.max(teglatestZActual.begin.x, teglatestZActual.end.x)).allMatch(
                                x -> {
                                    Integer actualZValueForPointXY = pointZMax.get(new PointXY(x, yGeneral));
                                    if (actualZValueForPointXY == null || actualZValueForPointXY < zCsokkenofinal) {
                                        return true;
                                    }
                                    return false;
                                }
                        )) {
                            newZSzint = zCsokkeno;
                        } else {
                            break;
                        }
                    }
                    zStables.add(teglatestZActual.newZ(newZSzint));
                    for (int i = Math.min(teglatestZActual.begin.x, teglatestZActual.end.x); i <= Math.max(teglatestZActual.begin.x, teglatestZActual.end.x); i++) {
                        pointZMax.put(new PointXY(i, yGeneral), newZSzint);
                    }

                } else if (teglatestZActual.direction == Direction.Y) {
                    int newZSzint = zActual;
                    int xGeneral = teglatestZActual.begin.x;
                    for (int zCsokkeno = zActual; 1 <= zCsokkeno; zCsokkeno--) {
                        final int zCsokkenofinal = zCsokkeno;
                        if (IntStream.rangeClosed(Math.min(teglatestZActual.begin.y, teglatestZActual.end.y), Math.max(teglatestZActual.begin.y, teglatestZActual.end.y)).allMatch(
                                y -> {
                                    Integer actualZValueForPointXY = pointZMax.get(new PointXY(xGeneral, y));
                                    if (actualZValueForPointXY == null || actualZValueForPointXY < zCsokkenofinal) {
                                        return true;
                                    }
                                    return false;
                                }
                        )) {
                            newZSzint = zCsokkeno;
                        } else {
                            break;
                        }
                    }
                    zStables.add(teglatestZActual.newZ(newZSzint));
                    for (int j = Math.min(teglatestZActual.begin.y, teglatestZActual.end.y); j <= Math.max(teglatestZActual.begin.y, teglatestZActual.end.y); j++) {
                        pointZMax.put(new PointXY(xGeneral, j), newZSzint);
                    }
                } else {
                    // ZZ
                    int zCsokkeno = zActual;
                    int xGeneral = teglatestZActual.begin.x;
                    int yGeneral = teglatestZActual.begin.y;
                    Integer actualZValueForPointXY = pointZMax.get(new PointXY(teglatestZActual.begin.x, teglatestZActual.begin.y));
                    if (actualZValueForPointXY == null) {
                        zCsokkeno = 1;
                    } else {
                        // Itt lehet mar rajta van
                        zCsokkeno = actualZValueForPointXY + 1;
                    }

                    zStables.add(teglatestZActual.newZ(zCsokkeno));
                    pointZMax.put(new PointXY(xGeneral, yGeneral), (zCsokkeno + teglatestZActual.getZLength()));
                }
            }

        }
    }

    private static void countAllTartas(int maxSize, Set<TeglaTest> teglaTests, Map<Integer, Set<TeglaTest>> zAndTeglatestekDropedMax, Map<Integer, Set<TeglaTest>> zAndTeglatestekDropedMin) {
        Set<TeglaTest> secondBoucle = new HashSet<>(teglaTests);
        Map<TeglaTest, Set<TeglaTest>> fuggesMin = new HashMap<>();
        Map<TeglaTest, Set<TeglaTest>> fuggesMax = new HashMap<>();

        for (TeglaTest teglatestRahelyezkedo : secondBoucle) {

            int minZ = teglatestRahelyezkedo.getMinZ();
            Set<PointXYZ> pointsToCheckMinZ = teglatestRahelyezkedo.getAllMinusZPoints();
            // Maxot kell szamitani nem a min-t mert csak azok tarthatjak
            Set<TeglaTest> minZmaxZs = zAndTeglatestekDropedMax.get(minZ - 1);
            Set<TeglaTest> minZTartja = new HashSet<>();

            if (minZmaxZs != null && !minZmaxZs.isEmpty()) {
                minZmaxZs.forEach(teglaTest -> {
                    for (PointXYZ pointXYZ : pointsToCheckMinZ) {
                        if (teglaTest.isInside(pointXYZ)) {
                            minZTartja.add(teglaTest);
                            return;
                        }
                    }
                });
            }
            fuggesMin.put(teglatestRahelyezkedo, minZTartja);
        }

        Set<TeglaTest> nonDisintegrable = fuggesMin.entrySet().stream().filter(entry -> 1 == entry.getValue().size()).map(Map.Entry::getValue).flatMap(Set::stream).collect(Collectors.toSet());
        Set<TeglaTest> allTeglatestek = new HashSet<>(teglaTests);
        allTeglatestek.removeAll(nonDisintegrable);

        System.out.println("Non nonDisintegrable: " + nonDisintegrable.size());
        System.out.println("Disintegrable: " + allTeglatestek.size());

        // Part 2

        int sum = 0;
        for (TeglaTest teglaTestToDelete : nonDisintegrable) {
            Set<TeglaTest> initTeglatest = new HashSet<>(teglaTests);
            initTeglatest.remove(teglaTestToDelete);
            Set<TeglaTest> zStables = new HashSet<>();
            executeFalling(initTeglatest, zStables);
            int moved = 0;
            for (TeglaTest newZStable : zStables) {
                if (!initTeglatest.contains(newZStable)) {
                    sum++;
                }
            }
        }

        System.out.println("Moved sum: " + sum);
    }

    private static Direction getDirectionTest(PointXYZ endPoint, PointXYZ beginPoint) {
        if (endPoint.z != beginPoint.z) {
            return Direction.Z;
        }

        if (endPoint.y != beginPoint.y) {
            return Direction.Y;
        }


        if (endPoint.x != beginPoint.x) {
            return Direction.X;
        }

        return Direction.Z;

    }

    enum Direction {
        X,
        Y,
        Z;
    }

    record PointXY(int x, int y) {
    }

    ;

    record PointXYZ(int x, int y, int z) {
        PointXYZ newZ(int znew) {
            return new PointXYZ(x, y, znew);
        }
    }

    ;

    record TeglaTest(int index, PointXYZ begin, PointXYZ end, Direction direction) {

        boolean isInside(PointXYZ pointXYZ) {
            return begin.x <= pointXYZ.x && pointXYZ.x <= end.x &&
                    begin.y <= pointXYZ.y && pointXYZ.y <= end.y &&
                    begin.z <= pointXYZ.z && pointXYZ.z <= end.z;
        }

        Set<PointXYZ> getAllMinusZPoints() {
            Set<PointXYZ> newPoinst = new HashSet<>();
            if (direction == Direction.X) {
                for (int i = begin.x; i <= end.x; i++) {
                    newPoinst.add(new PointXYZ(i, begin.y, begin.z - 1));
                }
            } else if (direction == Direction.Y) {
                for (int i = begin.y; i <= end.y; i++) {
                    newPoinst.add(new PointXYZ(begin.x, i, begin.z - 1));
                }
            } else {
                newPoinst.add(new PointXYZ(begin.x, begin.y, begin.z - 1));
            }
            return newPoinst;
        }

        Set<PointXYZ> getAllPlusZPoints() {
            Set<PointXYZ> newPoinst = new HashSet<>();
            if (direction == Direction.X) {
                for (int i = begin.x; i <= end.x; i++) {
                    newPoinst.add(new PointXYZ(i, begin.y, begin.z + 1));
                }
            } else if (direction == Direction.Y) {
                for (int i = begin.y; i <= end.y; i++) {
                    newPoinst.add(new PointXYZ(begin.x, i, begin.z + 1));
                }
            } else {
                newPoinst.add(new PointXYZ(begin.x, begin.y, end.z + 1));
            }
            return newPoinst;
        }

        @Override
        public String toString() {
            return "TeglaTest{" +
                    "x=" + begin.x + ":" + end.x +
                    ";y=" + begin.y + ":" + end.y +
                    ";z=" + begin.z + ":" + end.z +
                    ", direction=" + direction +
                    '}';
        }

        int getMinZ() {
            if (direction == Direction.Z) {
                return Math.min(begin.z, end.z);
            }
            return begin.z;
        }

        int getMaxZ() {
            if (direction == Direction.Z) {
                return Math.max(begin.z, end.z);
            }
            return begin.z;
        }

        TeglaTest newZ(int newZSzint) {
            if (direction == Direction.X || direction == Direction.Y) {
                return new TeglaTest(index, begin.newZ(newZSzint), end.newZ(newZSzint), direction);
            } else {
                // Assume mindig jo sorrend
                return new TeglaTest(index, begin.newZ(newZSzint), end.newZ(newZSzint + (end.z - begin.z)), direction);
            }
        }

        int getZLength() {
            return Math.abs(end.z - begin.z);
        }
    }

    ;
}
