package com.fmolnar.code.year2021.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22Challenge01 {

    private List<String> lines = new ArrayList<>();
    public static final String commandX = "((-?\\d{1,5})..(-?\\d{1,5}))";
    private Pattern matcherX = Pattern.compile(commandX);

    public static final String commandY = "((-?\\d{1,5})..(-?\\d{1,5}))";
    private Pattern matcherY = Pattern.compile(commandY);

    public static final String commandZ = "((-?\\d{1,5})..(-?\\d{1,5}))";
    private Pattern matcherZ = Pattern.compile(commandZ);

    Map<Coordinate, Coordinate> on = new HashMap<>();
    Map<Coordinate, Coordinate> off = new HashMap<>();

    List<Coordinate> coordinatesOrder = new ArrayList<>();
    List<CoordinatePair> coordinatePairs = new ArrayList<>();

    Set<Coordinate> allOn = new HashSet<>();


    //on x=10..12,y=10..12,z=10..12
    //on x=11..13,y=11..13,z=11..13

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day22/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String xGroup = line.substring(line.indexOf('x'), line.indexOf('y'));
                    String yGroup = line.substring(line.indexOf('y'), line.indexOf('z'));
                    String zGroup = line.substring(line.indexOf('z'));

                    Matcher matchX = matcherX.matcher(xGroup);
                    int x1 = 0;
                    int x2 = 0;
                    int y1 = 0;
                    int y2 = 0;
                    int z1 = 0;
                    int z2 = 0;

                    if (matchX.find()) {
                        x1 = Integer.valueOf(matchX.group(2));
                        x2 = Integer.valueOf(matchX.group(3));
                    }

                    Matcher matchY = matcherY.matcher(yGroup);
                    if (matchY.find()) {
                        y1 = Integer.valueOf(matchY.group(2));
                        y2 = Integer.valueOf(matchY.group(3));
                    }

                    Matcher matchZ = matcherZ.matcher(zGroup);
                    if (matchZ.find()) {
                        z1 = Integer.valueOf(matchZ.group(2));
                        z2 = Integer.valueOf(matchZ.group(3));
                    }


                    if (line.startsWith("on")) {
                        coordinatePairs.add(new CoordinatePair(true, new Coordinate(x1, y1, z1), new Coordinate(x2, y2, z2)));
                    } else {
                        coordinatePairs.add(new CoordinatePair(false, new Coordinate(x1, y1, z1), new Coordinate(x2, y2, z2)));
                    }
                }
            }
        }

        int max = 50;
        int min = -50;
        for (CoordinatePair coordinatePairActual : coordinatePairs) {
            boolean on = coordinatePairActual.on;
            Coordinate coordinateBeginning = coordinatePairActual.c1;
            Coordinate coordianteEnd = coordinatePairActual.c2;

            if ((coordinateBeginning.x < min && coordianteEnd.x < min) || (coordinateBeginning.y < min && coordianteEnd.y < min) ||
                    (coordinateBeginning.z < min && coordianteEnd.z < min) || (max < coordinateBeginning.x && max < coordianteEnd.x)
                    || (max < coordinateBeginning.y && max < coordianteEnd.y) || (max < coordinateBeginning.z && max < coordianteEnd.z)) {
                continue;
            }

            if (on) {
                for (int x = coordinateBeginning.x; x <= coordianteEnd.x; x++) {
                    if (!(min <= x && x <= max)) {
                        continue;
                    }
                    for (int y = coordinateBeginning.y; y <= coordianteEnd.y; y++) {
                        if (!(min <= y && y <= max)) {
                            continue;
                        }
                        for (int z = coordinateBeginning.z; z <= coordianteEnd.z; z++) {
                            if (!(min <= z && z <= max)) {
                                continue;
                            }
                            Coordinate coordinateActual = new Coordinate(x, y, z);
                            allOn.add(coordinateActual);
                        }
                    }
                }
            } else {
                for (int x = coordinateBeginning.x; x <= coordianteEnd.x; x++) {
                    if (!(min <= x && x <= max)) {
                        continue;
                    }
                    for (int y = coordinateBeginning.y; y <= coordianteEnd.y; y++) {
                        if (!(min <= y && y <= max)) {
                            continue;
                        }
                        for (int z = coordinateBeginning.z; z <= coordianteEnd.z; z++) {
                            if (!(min <= z && z <= max)) {
                                continue;
                            }
                            Coordinate coordinateActual = new Coordinate(x, y, z);
                            if (allOn.contains(coordinateActual)) {
                                allOn.remove(coordinateActual);
                            }
                        }
                    }
                }
            }
        }


        System.out.println("Day22Challenge01: " + allOn.size());
    }

    public static record CoordinatePair (boolean on, Coordinate c1, Coordinate c2) {

    }

    public static record Coordinate(int x, int y, int z) {
    }
}
