package com.fmolnar.code.year2024.day15;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.Direction;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Day15_2024 {
    public void calculateday152024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day15/input.txt");

        List<String> mapperLines = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                break;
            }
            i++;
            mapperLines.add(line);
        }

        List<String> instructions = new ArrayList<>();
        for (int index = i; index < lines.size(); index++) {
            String lineInstructions = lines.get(index);
            for (int x = 0; x < lineInstructions.length(); x++) {
                instructions.add(String.valueOf(lineInstructions.charAt(x)));
            }
        }

        int maxX = lines.get(0).length();
        int maxY = mapperLines.size();

        Map<PointXY, String> mapActual = AdventOfCodeUtils.getMapStringInput(mapperLines);

        Map<PointXY, String> pointsActual = new HashMap<>();
        for (int j = 0; j < maxY; j++) {
            for (int x = 0; x < maxX; x++) {
                PointXY actualPoint = new PointXY(x, j);
                if (mapActual.keySet().contains(actualPoint)) {
                    if (".".equals(mapActual.get(actualPoint))) {
                        pointsActual.put(new PointXY(2 * x, j), ".");
                        pointsActual.put(new PointXY(2 * x + 1, j), ".");
                    } else if ("O".equals(mapActual.get(actualPoint))) {
                        pointsActual.put(new PointXY(2 * x, j), "[");
                        pointsActual.put(new PointXY(2 * x + 1, j), "]");
                    } else if ("#".equals(mapActual.get(actualPoint))) {
                        pointsActual.put(new PointXY(2 * x, j), "#");
                        pointsActual.put(new PointXY(2 * x + 1, j), "#");
                    } else if ("@".equals(mapActual.get(actualPoint))) {
                        pointsActual.put(new PointXY(2 * x, j), "@");
                        pointsActual.put(new PointXY(2 * x + 1, j), ".");
                    }
                }
            }
        }
        mapActual = new HashMap<>(pointsActual);
        //printOut(maxY, maxX, mapActual);


        Optional<PointXY> start = mapActual.entrySet().stream().filter(s -> "@".equals(s.getValue())).map(s -> s.getKey()).findFirst();
        PointXY starter = start.get();

        for (int indexIns = 0; indexIns < instructions.size(); indexIns++) {
            String instruction = instructions.get(indexIns);
            System.out.println(instruction);
            if (instruction.equals(">")) {
                starter = leptetes(mapActual, Direction.RIGHT, starter);
            } else if (instruction.equals("<")) {
                starter = leptetes(mapActual, Direction.LEFT, starter);
            } else if (instruction.equals("v")) {
                starter = leptetes(mapActual, Direction.DOWN, starter);
            } else if (instruction.equals("^")) {
                starter = leptetes(mapActual, Direction.UP, starter);
            }
            //printOut(maxY, maxX, mapActual);

        }
        long sum = 0;
        for (Map.Entry<PointXY, String> entry : mapActual.entrySet()) {
            PointXY point = entry.getKey();
            String actual = entry.getValue();
            if ("[".equals(actual)) {
                sum += point.y() * 100l + point.x();
            }
        }

        System.out.println(sum);
    }


    private static void printOut(int maxY, int maxX, Map<PointXY, String> mapActual) {
        for (int j = 0; j < maxY; j++) {
            for (int x = 0; x < maxX * 2; x++) {
                if (mapActual.keySet().contains(new PointXY(x, j))) {
                    System.out.print(mapActual.get(new PointXY(x, j)));
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static PointXY leptetes(Map<PointXY, String> map, Direction direction, PointXY starter) {
        PointXY newPoint = starter.add(direction.toPoint());
        if (".".equals(map.get(newPoint))) {
            map.put(starter, ".");
            map.put(newPoint, "@");
            return newPoint;
        } else if ("#".equals(map.get(newPoint))) {
            return starter;
        } else if (Set.of("[", "]").contains(map.get(newPoint))) {
            if (Set.of(Direction.RIGHT, Direction.LEFT).contains(direction)) {
                return ifRightOrLeft(map, direction, starter, newPoint);
            } else {
                return upAndDown(map, direction, starter);
            }
        }
        return starter;
    }

    private static PointXY upAndDown(Map<PointXY, String> map, Direction direction, PointXY starter) {
        Set<PointXY> toExamine = new HashSet<>();
        Set<PointXY> alReadyExaminedAndOK = new HashSet<>();
        PointXY newStarter = starter.add(direction.toPoint());
        PointXY newPointHossz = starter;
        while (true) {
            newPointHossz = newPointHossz.add(direction.toPoint());
            String actualMotif = map.get(newPointHossz);
            if ("]".equals(actualMotif)) {
                toExamine.add(newPointHossz.add(Direction.LEFT.toPoint()));
                alReadyExaminedAndOK.add(newPointHossz);
            } else if ("[".equals(actualMotif)) {
                toExamine.add(newPointHossz.add(Direction.RIGHT.toPoint()));
                alReadyExaminedAndOK.add(newPointHossz);
            } else if ("#".equals(actualMotif)) {
                // Nem tud mozdulni
                return starter;
            } else if (".".equals(actualMotif)) {
                if (canBePlaced(map, alReadyExaminedAndOK, toExamine, direction)) {
                    // eltolas
                    // Osszes eltolasa
                    eltolasOsszes(map, alReadyExaminedAndOK, direction);
                    map.put(newStarter, "@");
                    map.put(starter, ".");
                    return newStarter;
                } else {
                    return starter;
                }
            }
        }
    }

    private static void eltolasOsszes(Map<PointXY, String> map, Set<PointXY> alReadyExaminedAndOK, Direction direction) {
        int yMin = alReadyExaminedAndOK.stream().mapToInt(PointXY::y).min().getAsInt();
        int yMax = alReadyExaminedAndOK.stream().mapToInt(PointXY::y).max().getAsInt();

        int xMin = alReadyExaminedAndOK.stream().mapToInt(PointXY::x).min().getAsInt();
        int xMax = alReadyExaminedAndOK.stream().mapToInt(PointXY::x).max().getAsInt();
        if (direction == Direction.UP) {
            for (int y = yMin; y <= yMax; y++) {
                for (int x = xMin; x <= xMax; x++) {
                    PointXY point = new PointXY(x, y);
                    if (alReadyExaminedAndOK.contains(point)) {
                        PointXY pointToChange = new PointXY(x, y - 1);
                        String motif = map.get(point);
                        map.put(pointToChange, motif);
                        map.put(point, ".");
                    }
                }
            }

        } else if (direction == Direction.DOWN) {
            for (int y = yMax; yMin <= y; y--) {
                for (int x = xMin; x <= xMax; x++) {
                    PointXY point = new PointXY(x, y);
                    if (alReadyExaminedAndOK.contains(point)) {
                        PointXY pointToChange = new PointXY(x, y + 1);
                        String motif = map.get(point);
                        map.put(pointToChange, motif);
                        map.put(point, ".");
                    }
                }
            }

        }
    }

    private static boolean canBePlaced(Map<PointXY, String> map, Set<PointXY> alReadyExamined, Set<PointXY> toExamine, Direction direction) {
        Set<PointXY> toExamineNow = new HashSet<>();
        if (alReadyExamined.containsAll(toExamine) || toExamine.isEmpty()) {
            return true;
        } else {
            for (PointXY starter : toExamine) {
                if (alReadyExamined.contains(starter)) {
                    continue;
                }
                alReadyExamined.add(starter);
                PointXY newPointHossz = starter;
                for (int i = 0; i < 100; i++) {
                    newPointHossz = newPointHossz.add(direction.toPoint());
                    String actualMotif = map.get(newPointHossz);
                    if ("]".equals(actualMotif)) {
                        toExamineNow.add(newPointHossz.add(Direction.LEFT.toPoint()));
                        alReadyExamined.add(newPointHossz);
                    } else if ("[".equals(actualMotif)) {
                        toExamineNow.add(newPointHossz.add(Direction.RIGHT.toPoint()));
                        alReadyExamined.add(newPointHossz);
                    } else if ("#".equals(actualMotif)) {
                        // Nem tud mozdulni
                        return false;
                    } else if (".".equals(actualMotif)) {
                        toExamineNow.addAll(toExamine);
                        if (canBePlaced(map, alReadyExamined, toExamineNow, direction)) {
                            // eltolas
                            return true;
                        } else {
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }

    private static PointXY ifRightOrLeft(Map<PointXY, String> map, Direction direction, PointXY starter, PointXY newPoint) {
        int hossz = 1;
        PointXY newPointHossz = newPoint;
        for (int xLepes = 1; xLepes < 100; xLepes++) {
            newPointHossz = newPointHossz.add(direction.toPoint());
            if (Set.of("[", "]").contains(map.get(newPointHossz))) {
                hossz++;
            } else {
                break;
            }
        }
        if (".".equals(map.get(newPointHossz))) {
            // osszes eltolasa
            eltolas(hossz, starter, direction, map);
            return starter.add(direction.toPoint());
        } else {
            return starter;
        }
    }

    private static void eltolas(int hossz, PointXY starter, Direction direction, Map<PointXY, String> map) {

        // felveszi elozot
        // leptet egyet
        //
        map.put(starter, ".");
        PointXY newPoint = starter.add(direction.toPoint());
        String motif = map.get(newPoint);
        map.put(newPoint, "@");

        for (int i = 0; i < hossz; i++) {
            newPoint = newPoint.add(direction.toPoint());
            String beforMotif = map.get(newPoint);
            map.put(newPoint, motif);
            motif = beforMotif;
        }
    }
}
