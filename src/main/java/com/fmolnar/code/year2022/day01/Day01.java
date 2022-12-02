package com.fmolnar.code.year2022.day01;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {

    private List<Integer> szamok = new ArrayList<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day01/input.txt");

        int osszeg = 0;
        for (String line : lines) {
            if (StringUtils.isNotEmpty(line)) {
                osszeg = Integer.valueOf(line) + osszeg;
            } else {
                szamok.add(osszeg);
                osszeg = 0;
            }
        }
        // Last line
        szamok.add(osszeg);

        Collections.sort(szamok);
        Collections.reverse(szamok);
        System.out.println("First Result: " + (szamok.get(0)));
        System.out.println("Second Result: " + (szamok.get(0) + szamok.get(1) + szamok.get(2)));

    }

}
