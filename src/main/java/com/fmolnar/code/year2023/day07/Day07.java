package com.fmolnar.code.year2023.day07;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 {

    public static Map<String, Integer> dictionnaire = new HashMap<>();


    public static void calculate() throws IOException {

        intiMap();
        List<String> lines = FileReaderUtils.readFile("/2023/day07/input.txt");
        List<Poker> pokers = new ArrayList<>();
        for (String line : lines) {
            pokers.add(new LinePoker(line.substring(0, line.indexOf(' ')), Integer.valueOf(line.substring(line.indexOf(' ') + 1))).calculatePoker());
        }
        Collections.sort(pokers, (p1, p2) -> {
            if (p1.value < p2.value) {
                return -1;
            }

            if (p1.value > p2.value) {
                return 1;
            }

            if (p1.value == p2.value) {
                for (int i = 0; i < p1.poker.cards.length(); i++) {
                    if (p1.poker.cards.charAt(i) != p2.poker.cards.charAt(i)) {
                        String p1String = String.valueOf(p1.poker.cards.charAt(i));
                        String p2String = String.valueOf(p2.poker.cards.charAt(i));
                        return dictionnaire.get(p1String) < dictionnaire.get(p2String) ? -1 : 1;

                    }
                }
            }
            throw new RuntimeException("Nincs itt");
        });


        long sumTotal = 0;
        for (int i = 0; i < pokers.size(); i++) {
            Poker pokerAct = pokers.get(i);
            sumTotal += Long.valueOf(i + 1) * Long.valueOf(pokerAct.poker.bid);
        }
        System.out.println("Toto: " + sumTotal);

    }

    private static void intiMap() {
        dictionnaire.put("2", 2);
        dictionnaire.put("3", 3);
        dictionnaire.put("4", 4);
        dictionnaire.put("5", 5);
        dictionnaire.put("6", 6);
        dictionnaire.put("7", 7);
        dictionnaire.put("8", 8);
        dictionnaire.put("9", 9);
        dictionnaire.put("T", 10);
        dictionnaire.put("J", 1);
        dictionnaire.put("Q", 12);
        dictionnaire.put("K", 13);
        dictionnaire.put("A", 14);
    }

    record Poker(int value, LinePoker poker) {
    }

    record LinePoker(String cards, int bid) {

        Poker calculatePoker() {
            Map<String, Integer> frequences = new HashMap<>();
            for (int i = 0; i < cards.length(); i++) {
                frequences.compute(String.valueOf(cards.charAt(i)), (k, v) -> (v == null ? 1 : v + 1));
            }
            int sum = 0;

            if("JJJJJ".equals(cards)){
                System.out.println("Itt volt");
            } else if (frequences.containsKey("J")) {
                final int jValue = frequences.get("J");
                frequences.remove("J");
                int max = frequences.entrySet().stream().map(Map.Entry::getValue).mapToInt(s -> s).max().getAsInt();

                String maxString = null;
                for (Map.Entry<String, Integer> freq : frequences.entrySet()) {
                    if(max == freq.getValue()){
                        maxString = freq.getKey();
                        break;
                    }
                }
                frequences.compute(maxString, (k, v) -> v + jValue);
            }

            for (Map.Entry<String, Integer> freq : frequences.entrySet()) {
                sum = bonus(freq.getValue(), sum);
            }
            return new Poker(sum, this);
        }

        private void assertCompare(Map<String, Integer> frequences, Map<String, Integer> frequences2) {

            if(frequences.size() != frequences2.size()){
                throw new RuntimeException("Ket size nem ugyanaz");
            }
            for(Map.Entry<String, Integer> ints : frequences.entrySet()){
                if(ints.getValue() != frequences2.get(ints.getKey())){
                    throw new RuntimeException("Szopas van nem ugyanaz a szam");
                }
            }
        }

        private int bonus(Integer value, int sum) {
            if (value == 1) {
                return sum;
            } else if (value == 2) {
                return sum + 100;
            } else if (value == 3) {
                return sum + 1000;
            } else if (value == 4) {
                return sum + 10000;
            } else if (value == 5) {
                return sum + 100000;
            }
            throw new RuntimeException("Nem lemmene itt lenni");
        }
    }

    ;
}
