package com.fmolnar.code.year2024.day15;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.Direction;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day15_2024v2 {
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
//
//            for (int j = 0; j < maxY; j++) {
//                for (int x = 0; x < maxX; x++) {
//                    if (mapActual.keySet().contains(new PointXY(x, j))) {
//                        System.out.print(mapActual.get(new PointXY(x, j)));
//                    }
//                }
//                System.out.println();
//            }
//            System.out.println();
        }
        long sum = 0;
        for (Map.Entry<PointXY, String> entry : mapActual.entrySet()) {
            PointXY point = entry.getKey();
            String actual = entry.getValue();
            if ("O".equals(actual)) {
                sum += point.y() * 100l + point.x();
            }
        }

        System.out.println(sum);
    }

    private static PointXY leptetes(Map<PointXY, String> map, Direction direction, PointXY starter) {
        PointXY newPoint = starter.add(direction.toPoint());
        if (".".equals(map.get(newPoint))) {
            map.put(starter, ".");
            map.put(newPoint, "@");
            return newPoint;
        } else if ("#".equals(map.get(newPoint))) {
            return starter;
        } else if ("O".equals(map.get(newPoint))) {
            int hossz = 1;
            PointXY newPointHossz = newPoint;
            for (int xLepes = 1; xLepes < 100; xLepes++) {
                newPointHossz = newPointHossz.add(direction.toPoint());
                if ("O".equals(map.get(newPointHossz))) {
                    hossz++;
                } else {
                    break;
                }
            }
            if (".".equals(map.get(newPointHossz))) {
                map.put(starter, ".");
                map.put(newPoint, "@");
                map.put(newPointHossz, "O");
                return newPoint;
            } else {
                return starter;
            }
        }
        return starter;
    }
}
