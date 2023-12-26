package com.fmolnar.code.year2023.day22;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Day22FirstPart {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day22/input.txt");
        Map<PointXY, Integer> pointZMax = new HashMap<>();
        Map<Integer, Set<TeglaTest>> zAndTeglatestek = new HashMap<>();

        Set<TeglaTest> initTestek = new HashSet<>();
        for (String line : lines) {
            int indexOfTilde = line.indexOf('~');
            String firstPart = line.substring(0, indexOfTilde);
            String secondPart = line.substring(indexOfTilde + 1);

            String[] beginIndex = firstPart.split(",");
            String[] endIndex = secondPart.split(",");
            PointXYZ beginPoint = new PointXYZ(Integer.valueOf(beginIndex[0]), Integer.valueOf(beginIndex[1]), Integer.valueOf(beginIndex[2]));
            PointXYZ endPoint = new PointXYZ(Integer.valueOf(endIndex[0]), Integer.valueOf(endIndex[1]), Integer.valueOf(endIndex[2]));
            Direction dir = getDirectionTest(endPoint, beginPoint);
            TeglaTest teglaTestActual = new TeglaTest(beginPoint, endPoint, dir);
            initTestek.add(teglaTestActual);

            // Megcsin 1 Sor
            Set<TeglaTest> alreadyInside = zAndTeglatestek.get(teglaTestActual.getMinZ());
            if (alreadyInside == null) {
                alreadyInside = new HashSet<>();
            }
            alreadyInside.add(teglaTestActual);
            zAndTeglatestek.put(teglaTestActual.getMinZ(), alreadyInside);
            // -----------------------------

        }

        int maxFalling = initTestek.stream().mapToInt(TeglaTest::getMaxZ).max().getAsInt();
        Set<TeglaTest> zStables = new HashSet<>();

        for (int zActual = 1; zActual <= maxFalling; zActual++) {
            Set<TeglaTest> zActuals = zAndTeglatestek.get(zActual);
            if(zActuals == null || zActuals.isEmpty()){
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
                        }
                        else {
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

            System.out.println("X");
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    System.out.print(pointZMax.get(new PointXY(j,i)) + " ");
                }
                System.out.println();
            }

            System.out.println();

        }

        List<TeglaTest> teglaTests = new ArrayList<>(zStables);
        Collections.sort(teglaTests, (TeglaTest t1, TeglaTest t2) -> Integer.compare(t1.begin.z,t2.begin.z));
        teglaTests.forEach(System.out::println);


    }

    private static Direction getDirectionTest(PointXYZ endPoint, PointXYZ beginPoint) {
        if (endPoint.z != beginPoint.z) {
            return Direction.Z;
        }

        if (endPoint.y != beginPoint.y) {
            return Direction.Y;
        }


        return Direction.X;

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

    record TeglaTest(PointXYZ begin, PointXYZ end, Direction direction) {

        @Override
        public String toString() {
            return "TeglaTest{" +
                    "x=" + begin.x +":"+end.x +
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
                return new TeglaTest(begin.newZ(newZSzint), end.newZ(newZSzint), direction);
            } else {
                // Assume mindig jo sorrend
                return new TeglaTest(begin.newZ(newZSzint), end.newZ(newZSzint + (end.z - begin.z)), direction);
            }
        }

        int getZLength() {
            return Math.abs(end.z - begin.z);
        }
    }

    ;
}
