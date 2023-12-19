package com.fmolnar.code.year2023.day19;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {

    static Map<String, String> dictRules = new HashMap<>();
    static List<StartingPoint> startingPoints = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day19/input.txt");

        boolean firstPart = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                firstPart = false;
                continue;
            }

            int zarojelNyit = line.indexOf('{');
            int zarojelZar = line.indexOf('}');

            if (firstPart) {
                String ruleName = line.substring(0, zarojelNyit);
                String tovabbit = line.substring(zarojelNyit + 1, zarojelZar);
                dictRules.put(ruleName, tovabbit);
            } else {
                //Second PArt
                String[] points = line.substring(zarojelNyit + 1, zarojelZar).split(",");
                int x = -1;
                int m = -1;
                int a = -1;
                int s = -1;
                for (String point : points) {
                    String[] pair = point.split("=");
                    if (pair[0].equals("x")) {
                        x = Integer.valueOf(pair[1]);
                    }
                    if (pair[0].equals("m")) {
                        m = Integer.valueOf(pair[1]);
                    }
                    if (pair[0].equals("a")) {
                        a = Integer.valueOf(pair[1]);
                    }
                    if (pair[0].equals("s")) {
                        s = Integer.valueOf(pair[1]);
                    }
                }

                startingPoints.add(new StartingPoint(x, m, a, s));

            }
        }

        long sum = 0;
        for (StartingPoint point : startingPoints) {

            String resultPoint = "";
            String nextLetters = "in";
            while (StringUtils.isEmpty(resultPoint)) {
                String actual = dictRules.get(nextLetters);
                String[] actualPieces = actual.split(",");

                for (String ruleOrder : actualPieces) {
                    int conditionSend = ruleOrder.indexOf(":");
                    if (conditionSend != -1) {
                        // kisebb
                        String firstPartStringOfRule = ruleOrder.substring(0, conditionSend);
                        String secondPartStringOfRule = ruleOrder.substring(conditionSend+1);
                        int kisebbRelacio = firstPartStringOfRule.indexOf('<');
                        int nagyibbRelacio = firstPartStringOfRule.indexOf('>');


                        if (kisebbRelacio != -1) {
                            String letterCondition = firstPartStringOfRule.substring(0,kisebbRelacio);
                            int letterValue = Integer.valueOf(firstPartStringOfRule.substring(kisebbRelacio+1));
                            int letterActualValue = getFromPoint(point, letterCondition);
                            if(letterActualValue<letterValue){
                                if("A".equals(secondPartStringOfRule)){
                                    resultPoint = "A";
                                    sum +=point.sum();
                                    break;
                                } else if("R".equals(secondPartStringOfRule)){
                                    resultPoint = "R";
                                    break;
                                } else {
                                    nextLetters = secondPartStringOfRule;
                                    break;
                                }
                            } else {
                                continue;
                            }

                        } else {
                            String letterCondition = firstPartStringOfRule.substring(0,nagyibbRelacio);
                            int letterValue = Integer.valueOf(firstPartStringOfRule.substring(nagyibbRelacio+1));
                            int letterActualValue = getFromPoint(point, letterCondition);
                            if(letterActualValue>letterValue){
                                if("A".equals(secondPartStringOfRule)){
                                    resultPoint = "A";
                                    sum +=point.sum();
                                    break;
                                } else if("R".equals(secondPartStringOfRule)){
                                    resultPoint = "R";
                                    break;
                                } else {
                                    nextLetters = secondPartStringOfRule;
                                    break;
                                }
                            } else {
                                continue;
                            }

                        }
                    }
                    else {
                        // Direct Command
                        if("A".equals(ruleOrder)){
                            resultPoint = "A";
                            sum +=point.sum();
                            break;
                        } else if("R".equals(ruleOrder)){
                            resultPoint = "R";
                            break;
                        } else {
                            nextLetters = ruleOrder;
                            break;
                        }

                    }
                }

            }
        }

        System.out.println(sum);
    }

    private static int getFromPoint(StartingPoint point, String letterCondition) {
        if("x".equals(letterCondition)){
            return point.x;
        }

        if("m".equals(letterCondition)){
            return point.m;
        }

        if("a".equals(letterCondition)){
            return point.a;
        }

        if("s".equals(letterCondition)){
            return point.s;
        }
       throw new RuntimeException("Nem kellene itt lennie");
    }

    record MiniRule(String rule, String result) {
    }

    ;

    record StartingPoint(int x, int m, int a, int s) {
        long sum(){
            return x + m + a + s;
        }
    }

    ;
}
