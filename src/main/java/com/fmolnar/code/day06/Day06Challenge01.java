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

public class Day06Challenge01 {

    public Day06Challenge01() {
    }

    public void calculate() throws IOException {
        List<Integer> allYes =new ArrayList<>();
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

        System.out.println("Sum: "+ allYes.stream().mapToInt(i -> i).sum());
    }

    private Integer convertQuestionsToYes(List<String> questions) {
        Set<Character> characters = new HashSet<>();
        for(String line : questions){
            characters.addAll(line.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
        }
        return characters.size();
    }
}
