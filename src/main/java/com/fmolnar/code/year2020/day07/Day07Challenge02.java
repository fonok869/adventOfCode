package com.fmolnar.code.year2020.day07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07Challenge02 {


    private static final String BAGS_CONTAIN = "bags contain";
    public static final String BAG = "bag";

    List<Long> summa = new ArrayList<>();

    public Day07Challenge02() {
    }

    private Map<String, List<String>> codeRules = new HashMap<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day07/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                makeBagsComposition(line);
            }
        }

        calculateCucc("shiny gold",1);
        System.out.println("Eredmeny: " + summa.stream().mapToLong(s->s).sum());
    }


    public void makeBagsComposition(String line) {
        int index = line.indexOf(BAGS_CONTAIN);
        String bagType = line.substring(0, index - 1);
        String[] toto = line.substring(index + BAGS_CONTAIN.length()).split(",");
        List<String> bagsType = new ArrayList<>();

        for (int i = 0; i < toto.length; i++) {
            String element = toto[i];
            for (int j = 0; j < element.length(); j++) {
                if (Character.isDigit(element.charAt(j))) {
                    int indexBag = element.indexOf(BAG);
                    // if a bag can contain more than 9 bags from the same color
                    if (Character.isDigit(element.charAt(j + 1))) {
                        bagsType.add(element.substring(1, indexBag - 1));
                        break;
                    } else {
                        bagsType.add(element.substring(1, indexBag - 1));
                        break;
                    }
                }
            }
        }
        codeRules.put(bagType, bagsType);
    }

    public void calculateCucc(String searchedColor, long multiplyer) {
        if (codeRules.containsKey(searchedColor)) {
            List<String> elemek = codeRules.get(searchedColor);
            long elofordulas=0;
            String keresettTaska;
            for (String elem : elemek) {
                if (Character.isDigit(elem.charAt(0))) {
                    if (Character.isDigit(elem.charAt(1))) {
                        keresettTaska = elem.substring(3);
                        elofordulas = Long.valueOf(elem.substring(0, 2))*multiplyer;
                        summa.add(elofordulas);
                        calculateCucc(keresettTaska, elofordulas);
                    } else {
                        keresettTaska = (elem.substring(2));
                        elofordulas = Long.valueOf(elem.substring(0, 1))*multiplyer;
                        summa.add(elofordulas);
                        calculateCucc(keresettTaska, elofordulas);
                    }
                }
            }

        }
    }
}

