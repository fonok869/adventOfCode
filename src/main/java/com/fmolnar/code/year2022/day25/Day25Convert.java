package com.fmolnar.code.year2022.day25;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25Convert {

    Map<String, Long> dict = new HashMap<>();
    Map<Integer, Long> maxLimits = new HashMap<>();
    Map<Integer, Long> valueMaxs = new HashMap<>();
    Map<Integer, Long> sumMaxs = new HashMap<>();
    Map<Integer, Long> startsFromTwo = new HashMap<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day25/input.txt");

        dict.put("2", 2l);
        dict.put("1", 1l);
        dict.put("0", 0l);
        dict.put("-", -1l);
        dict.put("=", -2l);

        long limitMax = 2L;
        long intervalleMax = 5L;
        long valueMax = 1L;
        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                maxLimits.put(i, limitMax);
                valueMaxs.put(i, valueMax);
            } else {
                limitMax = limitMax + intervalleMax;
                maxLimits.put(i, limitMax);
                valueMax = 5l * valueMax;
                valueMaxs.put(i, valueMax);
            }
            sumMaxs.put(i, 2l * valueMaxs.entrySet().stream().mapToLong(Map.Entry::getValue).sum());
        }

        for (String line : lines) {
            int maxHelyiertek = 0;
            long szamTotTest = Long.valueOf(line);
            for (int i = 0; i < 20; i++) {
                if (szamTotTest <= sumMaxs.get(i)) {
                    maxHelyiertek = i;
                    break;
                }
            }


            String number = "";
            double osztas = 0;
            long maradek = 0;
            for (int j = maxHelyiertek; 0 <= j; j--) {
                if (szamTotTest == 0) {
                    number = number + "0";
                    continue;
                }
                osztas = szamTotTest * (1.0) / valueMaxs.get(j);

                if (1 < osztas && maxLimits.get(j) < szamTotTest) {
                    number = number + "2";
                    maradek = szamTotTest - (2 * valueMaxs.get(j));
                } else if (2.0 == osztas) {
                    number = number + "2";
                    maradek = szamTotTest - (2 * valueMaxs.get(j));
                } else if (0 <= osztas) {
                    number = number + "1";
                    maradek = szamTotTest - (1 * valueMaxs.get(j));
                } else if (-1.0 <= osztas) {
                    number = number + "-";
                    maradek = szamTotTest - (-1 * valueMaxs.get(j));
                } else if (-2.0 <= osztas) {
                    number = number + "=";
                    maradek = szamTotTest - (-2 * valueMaxs.get(j));
                } else if (osztas < -2.0) {
                    number = number + "=";
                    maradek = szamTotTest - (-2 * valueMaxs.get(j));
                }
                szamTotTest = maradek;
            }

            System.out.println("Szam: " + line + " " + number);

        }

    }


}
