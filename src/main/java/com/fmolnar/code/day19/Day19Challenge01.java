package com.fmolnar.code.day19;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19Challenge01 {

    public Day19Challenge01() {
    }

    Map<Integer, List<String>> rulesMap = new HashMap<>();
    List<String> messages = new ArrayList<>();
    Map<Integer, Set<String>> keyMap = new HashMap<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day19/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (line.startsWith("a") || line.startsWith("b")) {
                        messages.add(line);
                    } else if (!isAOrB(line)) {
                        transformLine(line);
                    }
                }
            }
        }
        // a b
        while ((keyMap.size()) != rulesMap.size() + 2) {
            iterateDictionary();
        }
        System.out.println("Count: " + checkMessages());
    }

    private int checkMessages() {
        int counter = 0;
        Set<String> rulesToMatch = keyMap.get(0);
        System.out.println("Meret: " + rulesToMatch.size());
        System.out.println("Message Meret: " + messages.size());
        for (String message : messages) {
            if (rulesToMatch.contains(message)) {
                counter++;
            }
        }
        return counter;
    }

    private void iterateDictionary() {
        for (Map.Entry<Integer, List<String>> rule : rulesMap.entrySet()) {
            List<String> newList = new ArrayList<>();
            List<String> withoutWhiteSpace = new ArrayList<>();
            for (String codex : rule.getValue()) {
                List<String> transformedCodex = transformCodexLine(codex);
                //allTransformed.add(!transformedCodex.matches(".*\\d.*")); // any number
                newList.addAll(transformedCodex);
            }
            if (0L == newList.stream().filter(s -> s.matches(".*\\d.*")).count()) {
                withoutWhiteSpace.addAll(removeAllSpaces(newList));
                keyMap.put(rule.getKey(), new HashSet<>(withoutWhiteSpace));
                System.out.println("SzamElotte: " + rule.getKey() + " List :" + rule.getValue());
                System.out.println("Szam : " + rule.getKey() + " List :" + withoutWhiteSpace);
                rulesMap.put(rule.getKey(), withoutWhiteSpace);
                continue;
            }
            rulesMap.put(rule.getKey(), newList);
        }
    }

    private List<String> removeAllSpaces(List<String> newList) {
        return newList.stream().map(st -> st.replaceAll("\\s+", "")).collect(Collectors.toList());
    }

    private List<String> transformCodexLine(String codexLine) {
        List<String> newStringList = new ArrayList<>();
        String patternAnyDigit = "(\\d+)";
        Matcher matcherAnyDigit = Pattern.compile(patternAnyDigit).matcher(codexLine);
        if (matcherAnyDigit.find()) {
            if (isDigitFound(matcherAnyDigit.group(0))) {
                Set<String> toReplace = keyMap.get(Integer.valueOf(matcherAnyDigit.group(0)));
                for (String toRep : toReplace) {
                    String newCodexLine = codexLine;
                    String number = matcherAnyDigit.group(0);
                    int firstIndex = newCodexLine.indexOf(number);
                    int lastIndex = newCodexLine.lastIndexOf(number);
                    if(firstIndex == lastIndex){
                        newStringList.add(newCodexLine.replace(number, toRep));
                    } else {
                        String uj = newCodexLine.substring(0,Math.max(firstIndex-1, 0)) + toRep + newCodexLine.substring(Math.min(firstIndex + number.length(), number.length()));
                        newStringList.add(uj);
                    }
                }
            } else { // nincs KeyMap-ben
                newStringList.add(codexLine);
            }
        } else { // nincs szam benne
            newStringList.add(codexLine);
        }
        return newStringList;
    }

    private boolean isDigitFound(String group) {
        int key = Integer.valueOf(group);
        if (keyMap.containsKey(key)) {
            return true;
        }
        return false;
    }

    private boolean isAOrB(String ab) {
        String patternS = "((\\d+)\\:\\s\"(a|b)\")";
        Pattern pattern = Pattern.compile(patternS);
        Matcher matcher = pattern.matcher(ab);
        if (matcher.find()) {
            keyMap.put(Integer.valueOf(matcher.group(2)), new HashSet<String>(Arrays.asList(matcher.group(3))));
            return true;
        }
        return false;
    }

    private void transformLine(String rule) {
        List<String> values = new ArrayList<>();
        if (rule.contains("|")) {
            String doubleS = "^((\\d+)\\:\\s(\\d+\\s\\d+)\\s\\|\\s(\\d+\\s\\d+))$";
            Pattern doubleString = Pattern.compile(doubleS);
            Matcher matcherDouble = doubleString.matcher(rule);
            if (matcherDouble.find()) {
                values.add(matcherDouble.group(3));
                values.add(matcherDouble.group(4));
                System.out.println("Transformed: " + Integer.valueOf(matcherDouble.group(2)) + " List: " + values);
                rulesMap.put(Integer.valueOf(matcherDouble.group(2)), values);
            } else {
                String doubleSimple = "^((\\d+)\\:\\s(\\d+)\\s\\|\\s(\\d+))$";
                Pattern doubleSimplePattern = Pattern.compile(doubleSimple);
                Matcher doubleSimpleMatcher = doubleSimplePattern.matcher(rule);
                System.out.println("Line : " + rule);
                if (doubleSimpleMatcher.find()) {
                    values.add(doubleSimpleMatcher.group(3));
                    values.add(doubleSimpleMatcher.group(4));
                    System.out.println("Transformed: " + Integer.valueOf(doubleSimpleMatcher.group(2)) + " List: " + values);
                    rulesMap.put(Integer.valueOf(doubleSimpleMatcher.group(2)), values);
                } else {
                    String oneTwo = "^((\\d+)\\:\\s(\\d+)\\s\\|\\s(\\d+\\s\\d+))$";
                    Matcher matcherOneTwo = Pattern.compile(oneTwo).matcher(rule);
                    if(matcherOneTwo.find()){
                        values.add(matcherOneTwo.group(3));
                        values.add(matcherOneTwo.group(4));
                        System.out.println("Transformed: " + Integer.valueOf(matcherOneTwo.group(2)) + " List: " + values);
                        rulesMap.put(Integer.valueOf(matcherOneTwo.group(2)), values);
                    } else{
                        throw new RuntimeException("Case not implemented");
                    }
                }

            }
        } else {
            String simple = "^((\\d+)\\:\\s(\\d+\\s\\d+))$";
            Pattern simpleString = Pattern.compile(simple);
            Matcher matcherSimple = simpleString.matcher(rule);
            if (matcherSimple.find()) {
                // TODO enleverv plus tard
                String toto = matcherSimple.group(3);
                values.add(toto);
                System.out.println("Transformed: " + Integer.valueOf(matcherSimple.group(2)) + " List: " + values);
                rulesMap.put(Integer.valueOf(matcherSimple.group(2)), values);
            } else {
                String simp = "^((\\d+)\\:\\s(\\d+))$";
                Pattern simpleStr = Pattern.compile(simp);
                Matcher matcher = simpleStr.matcher(rule);
                if (matcher.find()) {
                    String toto = matcher.group(3);
                    values.add(toto);
                    System.out.println("Transformed: " + Integer.valueOf(matcher.group(2)) + " List: " + values);
                    rulesMap.put(Integer.valueOf(matcher.group(2)), values);
                } else {
                    throw new RuntimeException("Case not implemented");

                }
            }
        }


    }

}
