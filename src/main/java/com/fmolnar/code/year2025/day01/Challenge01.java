package com.fmolnar.code.year2025.day01;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge01 {

    Map<Integer, Integer> LEFTS = new HashMap<Integer, Integer>();
    Map<Integer, Integer> RIGHTS = new HashMap<Integer, Integer>();

    public void calculate() throws IOException {

        initLeft();
        initRight();
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day01/input.txt");

        int init = 50;
        int counterZERO = 0;
        int counterONE = 0;

        for (String line : lines) {
            Integer step = Integer.valueOf(line.substring(1));

            if (line.startsWith("L")) {

                for (int i = 0; i < step; i++) {
                    init = LEFTS.get(init);
                    if (init == 0) {
                        counterZERO++;
                    }
                }
            }
            if (line.startsWith("R")) {
                for (int i = 0; i < step; i++) {
                    init = RIGHTS.get(init);
                    if (init == 0) {
                        counterZERO++;
                    }
                }
            }
            if (init == 0) {
                counterONE++;
            }


        }
        System.out.println("First: " + counterONE);
        System.out.println("Seconde: " + counterZERO);

    }

    private void initRight() {
        for (int i = 0; i < 100; i++) {
            if (i == 99) {
                RIGHTS.put(99, 0);
            } else {
                RIGHTS.put(i, i + 1);
            }
        }
    }

    private void initLeft() {
        for (int i = 99; -1 < i; i--) {
            if (i == 0) {
                LEFTS.put(i, 99);
            } else {
                LEFTS.put(i, i - 1);

            }
        }
    }

}
