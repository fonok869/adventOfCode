package com.fmolnar.code.year2020.day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02Challenge {

    String pattern = "((\\d+)\\-(\\d+))\\s([a-z])\\:\\s([a-z]+)";
    Pattern patternToLine = Pattern.compile(pattern);

    public void calculateDay2() throws IOException {
        getList();
    }

    public List<String> getList() throws IOException {
        List<String> passwordsValid = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream ("/2020/day02/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String values;
            while ((values = file.readLine()) != null) {
                if (isPasswordValid(values)) {
                    passwordsValid.add(values);
                }
            }
        }
        System.out.println(passwordsValid.size());
        return passwordsValid;
    }

    private boolean isPasswordValid(String line) {
        Matcher matcher = patternToLine.matcher(line);
        if (matcher.find()) {
            int minOccurence = Integer.valueOf(matcher.group(2));
            int maxOccurence = Integer.valueOf(matcher.group(3));
            int occurences = countLetters(matcher.group(4).charAt(0), matcher.group(5));
            if (minOccurence <= occurences && occurences <= maxOccurence) {
                return true;
            }
        }
        return false;
    }

    private int countLetters(Character letter, String text) {
        int occurence = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == letter) {
                occurence++;
            }
        }
        return occurence;
    }
}
