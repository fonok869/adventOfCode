package com.fmolnar.code.codewars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopWords {

    public static List<String> top3(String s) {
        Map<String, Integer> occured = new LinkedHashMap<>();

        String word = "";
        s = s + " ";
        for (int i = 0; i < s.length(); i++) {
            word = addWord(s.toLowerCase().charAt(i), occured, word);
        }

        return sortMostOccured(occured);
    }

    private static List<String> sortMostOccured(Map<String, Integer> occured) {
        List<String> mostOccuredCandidate = new ArrayList<>();
        if (occured.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, List<String>> frequence = new HashMap<>();
        List<Integer> nbOccuredMax = new ArrayList<>();
        for (Map.Entry<String, Integer> example : occured.entrySet()) {
            Integer nbOccured = example.getValue();
            String word = example.getKey();
            if (frequence.containsKey(nbOccured)) {
                List<String> words = frequence.get(nbOccured);
                words.add(word);
                frequence.put(nbOccured, words);
            } else {
                nbOccuredMax.add(nbOccured);
                List<String> words = new ArrayList<>();
                words.add(word);
                frequence.put(nbOccured, words);
            }
        }

        List<Integer> orderdNbMax = nbOccuredMax.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        for (int i = 0; i < 3; i++) {
            if(i == orderdNbMax.size()){
                break;
            }
            int maxValue = orderdNbMax.get(i);
            List<String> occurs = frequence.get(maxValue);
            for (String occur : occurs) {
                mostOccuredCandidate.add(occur);
                if (mostOccuredCandidate.size() == 3) {
                    return mostOccuredCandidate;
                }
            }
        }

        return mostOccuredCandidate;

    }

    public static String addWord(char letter, Map<String, Integer> wordsOccured, String existant) {
        if (Character.isLetter(letter) || ('\'' == letter && existant.length() != 0)) {
            return existant + letter;
        } else {
            if (existant.length() != 0) {
                Integer occured = wordsOccured.get(existant);
                if (occured == null) {
                    wordsOccured.put(existant, 1);
                } else {
                    wordsOccured.put(existant, ++occured);
                }
            }
            return "";
        }

    }
}
