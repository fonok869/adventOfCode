package com.fmolnar.code.year2021.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24Challenge01 {

    private List<String> lines = new ArrayList<>();
    Map<String, Long> axes = new HashMap<>();

    private static final String REGEX = "^[a-z]{3}\\s([w-z0-9-]{1,4})\\s([w-z0-9-]{1,4})";


    public void calculate() throws IOException {
        init();
        InputStream reader = getClass().getResourceAsStream("/2021/day24/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }
        calculNumber();

        System.out.println("Sum: " + lines);
    }

    private void init() {
        axes.put("x", 0L);
        axes.put("y", 0L);
        axes.put("z", 0L);
    }

    private void calculNumber() {
        String tester = "13579246899999";

        int counter = 0;
        int j=0;
        for (String line : lines) {
            if (line.startsWith("inp")) {
                axes.put("w", Long.valueOf(tester.substring(counter, counter + 1)));
                System.out.println(j + " w: " + axes.get("w"));
                counter++;
            } else {
                Matcher match = Pattern.compile(REGEX).matcher(line);
                String placeHolder;
                long szam = 0;
                if (match.find()) {
                    placeHolder = match.group(1);
                    String second = match.group(2);
                    try {
                        szam = Long.valueOf(second);
                    } catch (NumberFormatException ex) {
                        szam = axes.get(second);
                    }
                } else {
                    System.out.println("line: " + line + "did not MATCH");
                    return;
                }
                j++;
                szam = executeOperation(line, placeHolder, szam);
                System.out.println(j + " | " + line + " | " + placeHolder + " " +  szam);
                axes.put(placeHolder, szam);
            }

        }
        System.out.println("Z: " + axes.get("z"));
        System.out.println("W: " + axes.get("w"));
    }

    private long executeOperation(String line, String placeHolder, long szam) {
        String op = line.substring(0, 3);
        switch (op) {
            case "add": {
                return axes.get(placeHolder) + szam;
            }
            case "mul": {
                return axes.get(placeHolder) * szam;
            }
            case "div": {
                return axes.get(placeHolder) / szam;
            }
            case "mod": {
                return axes.get(placeHolder) % szam;
            }
            case "eql": {
                return axes.get(placeHolder) == szam ? 1L : 0L;
            }
            default:
                throw new RuntimeException("Itt nem kellene lennie");
        }
    }


}
