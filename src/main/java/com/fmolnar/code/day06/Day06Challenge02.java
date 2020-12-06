package com.fmolnar.code.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day06Challenge02 {

    public Day06Challenge02() {
    }

    public void calculate() throws IOException {
        List<Integer> allYes = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream("/day06/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            List<String> questions = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    questions.add(line);
                } else {
                    allYes.add(convertQuestionsToYes(questions));
                    questions = new ArrayList<>();
                }
            }
        }

        System.out.println("Sum: " + allYes.stream().mapToInt(i -> i).sum());
    }

    private Integer convertQuestionsToYes(List<String> questions) {
        // 1 Line
        int sizeofQuestion = questions.size();
        if (sizeofQuestion == 1) {
            String line = questions.get(0);
            return line.length();
        }

        // 1 < Line
        Set<Character> allLettersPresentInAllLines = new HashSet<>();
        Set<Character> lineToCheck = new HashSet<>();
        allLettersPresentInAllLines.addAll(questions.get(0).chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
        for (int i = 1; i < sizeofQuestion; i++) {
            String line = questions.get(i);
            lineToCheck.addAll(line.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
            allLettersPresentInAllLines = getLettersInBoth(allLettersPresentInAllLines, lineToCheck);
            lineToCheck = new HashSet<>();
        }
        return allLettersPresentInAllLines.size();
}

    private Set<Character> getLettersInBoth(Set<Character> actualAllLettersPresentAllLines, Set<Character> lineToCheck) {
        Set newAllLettersPresentInAllLines = new HashSet();
        for (Character character : actualAllLettersPresentAllLines) {
            if (lineToCheck.contains(character)) {
                newAllLettersPresentInAllLines.add(character);
            }
        }
        return newAllLettersPresentInAllLines;
    }
}
