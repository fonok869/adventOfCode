package com.fmolnar.code.year2022.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {

    private List<Integer> szamok = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2022/day01/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader))) {
            String line;
            int osszeg = 0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    int szam = Integer.valueOf(line);
                    osszeg = szam + osszeg;
                } else {
                    szamok.add(osszeg);
                    osszeg = 0;
                }
            }
            szamok.add(osszeg);
        }


        Collections.sort(szamok);
        Collections.reverse(szamok);
        System.out.println("First Result: " + (szamok.get(0)));
        System.out.println("Second Result: " + (szamok.get(0) + szamok.get(1) + szamok.get(2)));

    }

}
