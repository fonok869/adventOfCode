package com.fmolnar.code.year2022.day25;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25 {

    Map<String, Long> dict = new HashMap<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day25/input.txt");

        dict.put("2", 2l);
        dict.put("1", 1l);
        dict.put("0", 0l);
        dict.put("-", -1l);
        dict.put("=", -2l);
        List<Long> sums = new ArrayList<>();
        long five = 5;
        for (String line : lines) {

            long szam = 0;
            int counter = 0;
            long init = 1;
            for (int j = line.length() - 1; 0 <= j; j--) {
                String letter = "";
                if (j == line.length() - 1) {
                    letter = line.substring(j);
                } else {
                    letter = line.substring(j, j + 1);
                }
                szam += init * dict.get(letter);
                init *= 5;
            }
            System.out.println(szam);
            sums.add(szam);
            System.out.println("Lines: " + line);
        }

        long sumTotal = sums.stream().mapToLong(s -> s).sum();
        System.out.println("Total: "+ sumTotal);
        Map<Integer, Long> longers = new HashMap<>();
        Map<Integer, Long> maxValues = new HashMap<>();

        long szam = 1L;
        long maxValue = 0;
        int imax = 0;
        for (int i = 0; i < 30; i++) {
            if (sumTotal < szam) {
                break;
            }
            if (i == 0) {
                maxValues.put(0, 2L);
                maxValue = 2L;
            } else {
                szam *= 5l;
                maxValue += 2L * szam;
            }
            longers.put(i, szam);
            if (0 < i) {
                maxValues.put(i, maxValue);
            }
            imax = i;
        }

        //
        List<Long> longs = new ArrayList<>();

        longs.add(1L);
        longs.add(2L);
        longs.add(3L);
        longs.add(4L);
        longs.add(5L);
        longs.add(6L);
        longs.add(7L);
        longs.add(8L);
        longs.add(9L);
        longs.add(10L);
        longs.add(15L);


        Map<Long, String> dictInverse = new HashMap<>();
        dictInverse.put(2L, "2");
        dictInverse.put(1L, "1");
        dictInverse.put(0L, "0");
        dictInverse.put(-1L, "-");
        dictInverse.put(-2L, "=");


        System.out.println("sumTotal: " + sumTotal);
    }

    void calculate2() {


    }
}
