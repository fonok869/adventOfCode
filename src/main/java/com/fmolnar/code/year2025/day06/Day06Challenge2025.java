package com.fmolnar.code.year2025.day06;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06Challenge2025 {
    public void calculate() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2025/day06/input.txt");

        List<Map<Integer, Long>> list = new ArrayList<>();
        int length = lines.getLast().replaceAll(" ", "").length();

        Map<Integer, String> relaciok = new HashMap<>();
        for (int j = 0; j < lines.size(); j++) {
            Map<Integer, Long> map = new HashMap<>();
            if (j == lines.size() - 1) {
                String lineToTreat = lines.get(j);
                String[] sor = lineToTreat.replaceAll(" ", ",").split(",");

                int index = 0;
                for (int i = 0; i < sor.length; i++) {
                    if (!"".equals(sor[i])) {
                        relaciok.put(index++, sor[i]);
                    }
                }
            } else {
                String lineToTreat = lines.get(j);
                String[] sor = lineToTreat.replaceAll(" ", ",").split(",");
                int index = 0;
                for (int i = 0; i < sor.length; i++) {
                    if (!"".equals(sor[i])) {
                        map.put(index++, Long.parseLong(sor[i]));
                    }
                }
                list.add(map);
            }
        }
        int index2 = 0;
        List<Long> szamok = new ArrayList<>();
        List<Long> szum = new ArrayList<>();
        for (int j = 0; j < lines.get(0).length(); j++) {
            String szam = "";
            for (int i = 0; i < lines.size() - 1; i++) {
                szam = szam + lines.get(i).substring(j, j + 1);
            }
            String szamAct = szam.replaceAll(" ", "");
            if (0 < szamAct.length()) {
                Long actual = Long.parseLong(szamAct);
                szamok.add(actual);
            } else {
                if ("+".equals(relaciok.get(index2++))) {
                    szum.add(szamok.stream().mapToLong(s -> s).sum());
                } else {
                    szum.add(szamok.stream().mapToLong(s -> s).reduce(1, (v1, v2) -> v1 * v2));
                }
                szamok = new ArrayList<>();
            }
        }
        if ("+".equals(relaciok.get(index2++))) {
            szum.add(szamok.stream().mapToLong(s -> s).sum());
        } else {
            szum.add(szamok.stream().mapToLong(s -> s).reduce(1, (v1, v2) -> v1 * v2));
        }
        System.out.println(szum.stream().mapToLong(s -> s).sum());
        long value = osszeadas(list, relaciok);
        System.out.println(osszeadas(list, relaciok));

    }

    private long osszeadas(List<Map<Integer, Long>> list, Map<Integer, String> relaciok) {
        long value = 0;
        for (Map.Entry<Integer, String> entry : relaciok.entrySet()) {
            if ("*".equals(entry.getValue())) {
                value += szorzas(entry.getKey(), list);
            } else if ("+".equals(entry.getValue())) {
                value += osszeadas(entry.getKey(), list);
            }
        }
        return value;
    }

    private long osszeadas(Integer key, List<Map<Integer, Long>> list) {
        long toto = 0;
        for (Map<Integer, Long> entry : list) {
            toto += entry.get(key);
        }
        return toto;
    }

    private long szorzas(Integer key, List<Map<Integer, Long>> list) {
        long toto = 1;
        for (Map<Integer, Long> entry : list) {
            toto *= entry.get(key);
        }
        return toto;
    }
}
