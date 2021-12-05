package com.fmolnar.code.year2020.day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02Challenge02 {

    String pattern = "((\\d+)\\-(\\d+))\\s([a-z])\\:\\s([a-z]+)";
    Pattern patternToLine = Pattern.compile(pattern);

    public void calculateDay202() throws IOException {
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
            int firstLetterPlace = Integer.valueOf(matcher.group(2));
            int secondLetterPlace = Integer.valueOf(matcher.group(3));
            Character letter = matcher.group(4).charAt(0);
            String text = matcher.group(5);
            if(text.length()< firstLetterPlace || text.length()< secondLetterPlace){
                return false;
            }
            boolean firstLetterRightPlace = isRightPlace(letter,text,firstLetterPlace);
            boolean secondLetterRightPlace = isRightPlace(letter,text,secondLetterPlace);
            if(Boolean.TRUE.equals(firstLetterRightPlace) && Boolean.FALSE.equals(secondLetterRightPlace)){
                return true;
            }
            if(Boolean.FALSE.equals(firstLetterRightPlace) && Boolean.TRUE.equals(secondLetterRightPlace)){
                return true;
            }
        }
        return false;
    }

    private boolean isRightPlace(Character letter, String text, int place) {
        if(text.charAt(place-1) == letter){
            return true;
        }
        return false;
    }
}
