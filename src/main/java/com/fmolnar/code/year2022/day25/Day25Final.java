package com.fmolnar.code.year2022.day25;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25Final {

    Map<String, Long> dict = new HashMap<>();

    public void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2022/day25/input.txt");

        dict.put("2", 2l);
        dict.put("1", 1l);
        dict.put("0", 0l);
        dict.put("-", -1l);
        dict.put("=", -2l);

        for (String line : lines) {
            Long l1 = Long.valueOf(line);
            long five = 5L;
            long szorzo = 5L;
            long maradek = 0l;
            String number = "";
            for (int i = 0; i < 25; i++) {
                maradek = l1 % five;
                szorzo = l1 / five;
                l1 = l1 - maradek;
                number = maradek + number;
                if (l1 == 0) {
                    System.out.println(line + " " + number);
                }
                if (1 < i) {
                    five = five * 5L;
                }


            }

        }

    }
}

