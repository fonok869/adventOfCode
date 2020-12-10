package com.fmolnar.code.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10Challenge01 {

    public Day10Challenge01() {
    }

    List<Integer> numbers = new ArrayList<>();
    List<Integer> step1 = new ArrayList<>();
    List<Integer> step3 = new ArrayList<>();

    public void calculate() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/day10/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    numbers.add(Integer.valueOf(line));
                }
            }
        }
        Collections.sort(numbers);
        comtpage();
        Long step1Number = step1.stream().mapToInt(s -> s).count();
        Long ste2Number = step3.stream().mapToInt(s -> s).count() + 1;
        System.out.println("Egyszer: " + step1Number);
        System.out.println("3-szor: " + ste2Number);
        System.out.println("Szorzat: " + ste2Number * step1Number);

    }

    private void comtpage() {
        Integer elozo = 0;
        for (Integer numb : numbers) {
            Integer kulonbseg = numb - elozo;
            if (kulonbseg == 1) {
                step1.add(numb);
            } else if (kulonbseg == 3) {
                step3.add(numb);
            } else if (kulonbseg > 3) {
                System.out.println("Breaked");
                break;
            }
            elozo = numb;
        }


    }
}
