package com.fmolnar.code.year2021.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day14Challenge02 {

    private List<String> lines = new ArrayList<>();

    String alap = "";
    private Map<String, String> polys = new HashMap<>();
    private Map<String, Long> lettersOccurence = new HashMap<>();
    private Map<String, Long> charOccurences = new HashMap<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day14/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counter = 0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (counter == 0) {
                        alap = line;
                    } else {
                        String[] args = line.split(" -> ");
                        polys.put(args[0], args[1]);
                    }
                    lines.add(alap);
                    counter++;
                }
            }
        }

        initAlapLine();
        initLetterOccurence();
        int step = 40;
        for (int i = 0; i < step; i++) {
            growing();
        }

        List<Long> integs = lettersOccurence.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList());
        long max = integs.stream().mapToLong(i -> i).max().getAsLong();
        long min = integs.stream().mapToLong(i -> i).min().getAsLong();
        System.out.println("Sum: " + (max - min));
    }

    private void growing() {
        Map<String, Long> alreadyMap = new HashMap<>(charOccurences);
        Map<String, Long> newMap = new HashMap<>();

        for(Map.Entry<String, Long> map : alreadyMap.entrySet()){
            String code = map.getKey();
            Long occurenceActual = map.getValue();
            String newPol = polys.get(code);

            // First
            String first = code.substring(0,1) + newPol;
            if(newMap.containsKey(first)){
                long occurenceUntilNow = newMap.get(first);
                newMap.put(first, occurenceUntilNow + occurenceActual);
            } else {
                newMap.put(first, occurenceActual);
            }

            // Second
            String second =  newPol +code.substring(1) ;
            if(newMap.containsKey(second)){
                long occurenceUntilNow = newMap.get(second);
                newMap.put(second, occurenceUntilNow + occurenceActual);
            } else {
                newMap.put(second, occurenceActual);
            }

            //Letter
            if(lettersOccurence.containsKey(newPol)){
                long occurenceActualPoly = lettersOccurence.get(newPol);
                lettersOccurence.put(newPol, (occurenceActualPoly + occurenceActual));
            }else {
                lettersOccurence.put(newPol, (occurenceActual));
            }
        }

        charOccurences = newMap;
    }

    private void initLetterOccurence() {
        for (int i = 0; i < alap.length(); i++) {
            String code = "";
            if (i == alap.length() - 1) {
                code = alap.substring(i);
            } else {
                code = alap.substring(i, i + 1);
            }

            if (lettersOccurence.containsKey(code)) {
                long occurence = lettersOccurence.get(code);
                lettersOccurence.put(code, occurence + 1);
            } else {
                lettersOccurence.put(code, 1L);
            }
        }
    }

    private void initAlapLine() {
        for (int i = 0; i < alap.length() - 1; i++) {
            String code = alap.substring(i, i + 2);
            if (charOccurences.containsKey(code)) {
                long occurence = charOccurences.get(code);
                charOccurences.put(code, occurence + 1);
            } else {
                charOccurences.put(code, 1L);
            }
        }
    }
}
