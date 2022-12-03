package com.fmolnar.code.year2022.day03;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day03 {

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day03/input.txt");

        List<Character> prioties1 = new ArrayList<>();
        List<Character> prioties2 = new ArrayList<>();

        for (String line : lines) {
            String one = line.substring(0, line.length() / 2);
            String second = line.substring(line.length() / 2);
            for (int i = 0; i < second.length(); i++) {
                if (one.contains(Character.toString(second.charAt(i)))) {
                    prioties1.add(second.charAt(i));
                    break;
                }
            }
        }

        for (int j = 0; j < lines.size(); j = j + 3) {
            String one = lines.get(j);
            String second = lines.get(j + 1);
            String third = lines.get(j + 2);
            for (int i = 0; i < second.length(); i++) {
                if (one.contains(Character.toString(second.charAt(i))) && third.contains(Character.toString(second.charAt(i)))) {
                    prioties2.add(second.charAt(i));
                    break;
                }
            }
        }


        System.out.println("First: " + getPrioritySum(prioties1));
        System.out.println("Second: " + getPrioritySum(prioties2));
    }

    private int getPrioritySum(List<Character> prioties2) {
        return prioties2.stream().mapToInt(
                s -> {
                    int value = Integer.valueOf(s) - 96;
                    if (0 < value) {
                        return value;
                    } else {
                        return value + 58;
                    }
                }
        ).sum();
    }
}
