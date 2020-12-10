package com.fmolnar.code.day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08Challenge02 {

    public static final String JMP = "jmp";
    public static final String NOP = "nop";

    public Day08Challenge02() {
    }

    List<Integer> alreadyVisited = new ArrayList<>();
    List<Long> accumulator = new ArrayList<>();
    List<String> instructions = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day08/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    instructions.add(line);
                }
            }
        }
        lepdeles();
        System.out.println("Accumulator Level:  " + accumulator.stream().mapToLong(s -> s).sum());
    }

    String pattern = "(^[a-z]{3})\\s(\\+|\\-)([0-9]*)$";
    Pattern patternToLine = Pattern.compile(pattern);

    public void lepdeles() {
        Boolean breaked = false;
        for (int i = 0; i < instructions.size(); i++) {
            accumulator = new ArrayList<>();
            String actual = instructions.get(i);
            List<String> listToUse = null;

            // Replace
            if (actual.startsWith(NOP)) {
               listToUse = createNewListWithNewString(i, JMP);
            } else if (actual.startsWith(JMP)) {
                listToUse = createNewListWithNewString(i, NOP);
            } else {
                continue;
            }

            // Check the new series
            int toFinish = 0;
            breaked = false;
            while (listToUse.size() > toFinish) {
                alreadyVisited.add(toFinish);
                int newValue2 = calculateNextStep(toFinish, listToUse);
                toFinish = newValue2;
                if (alreadyVisited.contains(toFinish)) {
                    breaked = true;
                    break;
                }
            }
            if (!breaked) {
                return;
            }
            alreadyVisited = new ArrayList<>();
        }
    }

    private List<String> createNewListWithNewString(int index, String newStringToPut) {
        List<String> arrayLister = new ArrayList<>();
        for (int j = 0; j < instructions.size(); j++) {
            if (j == index) {
                String toReplace = instructions.get(index);
                String newString = newStringToPut + toReplace.substring(3);
                arrayLister.add(newString);
            } else {
                arrayLister.add(instructions.get(j));
            }
        }
        return arrayLister;
    }

    public int calculateNextStep(int actual, List<String> listToUse) {
        String line = listToUse.get(actual);
        Matcher matcher = patternToLine.matcher(line);
        String sign = null;
        String command = null;
        String lepesek = null;
        if (matcher.find()) {
            sign = matcher.group(2);
            command = matcher.group(1);
            lepesek = matcher.group(3);
        }

        if (NOP.equals(command)) {
            int toto = actual + 1;
            return toto;
        }

        if (JMP.equals(command)) {
            if ("+".equals(sign)) {
                actual = actual + Integer.valueOf(lepesek);
            } else {
                actual = actual - Integer.valueOf(lepesek);
            }
            return actual;
        }

        if ("acc".equals(command)) {
            if ("+".equals(sign)) {
                accumulator.add(Long.valueOf(lepesek));
            } else {
                accumulator.add(Long.valueOf("-" + lepesek));
            }
            return actual + 1;
        }


        return 0;
    }
}
