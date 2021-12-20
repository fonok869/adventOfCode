package com.fmolnar.code.year2021.day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08Challenge02 {

    public void calculate() throws IOException {
        Long sum = 0L;
        InputStream reader = getClass().getResourceAsStream("/2021/day08/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    int lineIndex = line.indexOf("|");
                    String[] args = line.substring(lineIndex + 2).split(" ");
                    Map<String, Integer> values = caulculateDictionnary(line);
                    String value = "";
                    for (int i = 0; i < args.length; i++) {
                        Set<Character> searched = args[i].chars().mapToObj(s -> (char) s).collect(Collectors.toSet());
                        for (Map.Entry<String, Integer> maper : values.entrySet()) {
                            Set<Character> proposition = maper.getKey().chars().mapToObj(s -> (char) s).collect(Collectors.toSet());
                            if (proposition.size() == searched.size() && proposition.containsAll(searched)) {
                                value = value + String.valueOf(maper.getValue());
                                break;
                            }
                        }
                    }
                    sum += Long.valueOf(value);
                }
            }
        }

        System.out.println("Sum: " + sum);
    }

    private Map<String, Integer> caulculateDictionnary(String line) {
        Map<String, Integer> map = new HashMap<>();
        int lineIndex = line.indexOf("|");
        String[] args = line.substring(0, lineIndex - 1).split(" ");
        List<Character> alreadyTaken = new ArrayList<>();
        // 1-7
        String one = "";
        String four = "";
        String eight = "";
        String seven = "";
        List<String> fiveLines = new ArrayList<>();
        List<String> sixLines = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String letters = args[i];
            int letterslength = letters.length();
            if (letterslength == 2) {
                one = letters;
                map.put(one, 1);
            } else if (letterslength == 3) {
                seven = letters;
                map.put(seven, 7);
            } else if (letterslength == 4) {
                four = letters;
                map.put(four, 4);
            } else if (letterslength == 7) {
                eight = letters;
                map.put(eight, 8);
            } else if (letterslength == 5) {
                fiveLines.add(letters);
            } else if (letterslength == 6) {
                sixLines.add(letters);
            }
        }
        //
        char felsoLec = kivonat(seven, one, Collections.emptyList());
        alreadyTaken.add(felsoLec);

        List<String> fiveLinesPlus4 = new ArrayList<>();
        fiveLinesPlus4.addAll(fiveLines);
        fiveLinesPlus4.add(four);

        char kozepsoLec = commonInAll(fiveLinesPlus4, alreadyTaken);
        alreadyTaken.add(kozepsoLec);

        char alsoLec = commonInAll(fiveLines, alreadyTaken);
        alreadyTaken.add(alsoLec);

        List<String> sixLinesPlus1 = new ArrayList<>();
        sixLinesPlus1.addAll(sixLines);
        sixLinesPlus1.add(one);

        char jobbAlso = commonInAll(sixLinesPlus1, alreadyTaken);
        alreadyTaken.add(jobbAlso);

        char jobbFelso = kivonat(one, " ", alreadyTaken);
        alreadyTaken.add(jobbFelso);

        char balFelso = kivonat(four, " ", alreadyTaken);
        alreadyTaken.add(balFelso);

        char balAlso = kivonat(eight, " ", alreadyTaken);


        for (String fiver : fiveLines) {
            List<Character> charsToCheck = fiver.chars().mapToObj(s -> (char) s).collect(Collectors.toList());
            // 2
            if (charsToCheck.containsAll(Arrays.asList(balAlso, jobbFelso))) {
                map.put(fiver, 2);
                // 3
            } else if (charsToCheck.containsAll(Arrays.asList(jobbAlso, jobbFelso))) {
                map.put(fiver, 3);
            } else {
                // 5
                map.put(fiver, 5);
            }
        }

        for (String sixer : sixLines) {
            List<Character> charsToCheck = sixer.chars().mapToObj(s -> (char) s).collect(Collectors.toList());
            // 6
            if (!charsToCheck.contains(jobbFelso)) {
                map.put(sixer, 6);
                // 0
            } else if (!charsToCheck.contains(kozepsoLec)) {
                map.put(sixer, 0);
            } else {
                // 9
                map.put(sixer, 9);
            }
        }

        return map;
    }

    private Character commonInAll(List<String> lines, List<Character> notPresent) {
        for (int i = 0; i < lines.size(); i++) {
            List<Character> charsToCheck = lines.get(i).chars().mapToObj(s -> (char) s).collect(Collectors.toList());
            for (Character charToCheck : charsToCheck) {
                if (notPresent.contains(charToCheck)) {
                    continue;
                }
                boolean isPresentAll = true;
                for (int j = i + 1; j < lines.size(); j++) {
                    List<Character> charsToCheckDetail = lines.get(j).chars().mapToObj(s -> (char) s).collect(Collectors.toList());

                    if (!charsToCheckDetail.contains(charToCheck)) {
                        isPresentAll = isPresentAll && false;
                    }
                }
                if (isPresentAll) {
                    return charToCheck;
                }

            }
        }
        return null;

    }

    private Character kivonat(String maxString, String minString, List<Character> notCount) {
        List<Character> chars = minString.chars().mapToObj(s -> (char) s).collect(Collectors.toList());
        for (int i = 0; i < maxString.length(); i++) {
            if (!chars.contains(maxString.charAt(i)) && !notCount.contains(maxString.charAt(i))) {
                return maxString.charAt(i);
            }
        }
        return null;
    }
}
