package com.fmolnar.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AdventOfCodeUtils {

    public static Set<PointXY> directionsDiagonal = Set.of(
            new PointXY(1, 1),
            new PointXY(1, -1),
            new PointXY(-1, -1),
            new PointXY(-1, 1));

    public static Set<PointXY> directionsNormals = Set.of(
            new PointXY(0, 1),
            new PointXY(0, -1),
            new PointXY(1, 0),
            new PointXY(-1, 0));

    public static List<String> readFile(String fullpath) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream reader = AdventOfCodeUtils.class.getResourceAsStream(fullpath);
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader))) {
            String line;
            while ((line = file.readLine()) != null) {
                lines.add(line);
            }

        }
        return lines;
    }

    public static Map<PointXY, Integer> getMapIntegersInput(List<String> lines) {
        Map<PointXY, Integer> map = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) != '.') {
                    map.put(new PointXY(x, y), Integer.parseInt(String.valueOf(line.charAt(x))));
                }
            }
        }
        return map;
    }

    public static Map<PointXY, String> getMapStringInput(List<String> lines) {
        Map<PointXY, String> map = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(new PointXY(x, y), String.valueOf(line.charAt(x)));
            }
        }
        return map;
    }
}
