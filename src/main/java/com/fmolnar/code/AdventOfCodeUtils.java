package com.fmolnar.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdventOfCodeUtils {

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
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                map.put(new PointXY(i, j), Integer.parseInt(String.valueOf(line.charAt(j))));
            }
        }
        return map;
    }

    public static Map<PointXY, String> getMapStringInput(List<String> lines) {
        Map<PointXY, String> map = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                map.put(new PointXY(j, i), String.valueOf(line.charAt(j)));
            }
        }
        return map;
    }
}
