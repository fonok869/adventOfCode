package com.fmolnar.code.year2025.day03;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day03Challenge2025 {

    public void calculate() throws IOException {


        List<String> lines = AdventOfCodeUtils.readFile("/2025/day03/input.txt");
        List<Integer> summas = new ArrayList<>();
        for (String line : lines) {
            Map<Integer, List<Integer>> frequencia = new HashMap<>();
            for (int i = 0; i < line.length(); i++) {
                int actualValue = Integer.valueOf(line.substring(i, i + 1));
                if (!frequencia.containsKey(actualValue)) {
                    frequencia.put(actualValue, new ArrayList<>());
                }
                frequencia.get(actualValue).add(i);
            }
            int maxSzam = getMaxSzam(frequencia, line.length());
            System.out.println(maxSzam);
            summas.add(maxSzam);
        }

        System.out.println(summas.stream().mapToInt(Integer::intValue).sum());

    }

    private int getMaxSzam(Map<Integer, List<Integer>> frequencia, int length) {
        for (int szam = 9; 0 <= szam; szam--) {
            if (frequencia.get(szam) == null) {
                continue;
            }
            if (frequencia.get(szam).size() == 1) {
                // utolso
                if (frequencia.get(szam).contains(length - 1)) {
                    for (int elotti = szam - 1; 0 < elotti; elotti--) {
                        if (frequencia.get(elotti) != null && frequencia.get(elotti).size() > 0) {
                            return Integer.valueOf(String.valueOf(elotti) + String.valueOf(szam));
                        }
                    }
                }
                // utana
                int position = frequencia.get(szam).get(0);
                for (int utani = szam - 1; 0 < utani; utani--) {
                    if (frequencia.get(utani) != null && frequencia.get(utani).stream().filter(utaniActual -> position < utaniActual).findFirst().isPresent()) {
                        return Integer.valueOf(String.valueOf(szam) + String.valueOf(utani));
                    }
                }

                // utana legnagyobb szam
            }
            if (frequencia.get(szam).size() > 1) {
                return Integer.valueOf(String.valueOf(szam) + String.valueOf(szam));
            }
        }
        return 0;
    }
}
