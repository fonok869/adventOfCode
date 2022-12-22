package com.fmolnar.code.year2022.day21;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {

    public static final String ROOT = "root";
    public static final String HUMN = "humn";
    Map<String, Notion> dictionnaire = new HashMap<>();
    private static String TWO_POINTS = ":";

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day21/input.txt");
        String PATTERN_NUMBER = "^.*(\\((\\d+)(-|\\+|\\*|/)(\\d+)\\))";
        Pattern matcher = Pattern.compile(PATTERN_NUMBER);

        for (String line : lines) {
            String root = line.substring(0, line.indexOf(TWO_POINTS));
            if (10 < line.length()) {
                String instruction = line.substring(line.indexOf(TWO_POINTS) + 2);
                String[] ins1 = instruction.split(" ");
                if (ROOT.equals(root)) {
                    dictionnaire.put(root, new Notion(root, ins1[0], "=", ins1[2]));
                } else {
                    dictionnaire.put(root, new Notion(root, ins1[0], ins1[1], ins1[2]));
                }
            } else {
                long value = Long.valueOf(line.substring(line.indexOf(TWO_POINTS) + 1).trim());


                dictionnaire.put(root, new Notion(root, value));

            }
        }

        Notion root = dictionnaire.get(ROOT);
        for (int i = 0; i < 80; i++) {
            root.remplace();
            System.out.println(root);
            root.simplification();
            System.out.println(root);
        }


    }

    class Notion {
        final String root;
        String leftSide;
        String rightSide;
        String operator;
        long value = -1;
        Set<String> contains = new HashSet<>();

        Notion(String root, long value) {
            this.root = root;
            this.value = value;
        }

        public Notion(String root, String leftSide, String operator, String rightSide) {
            this.root = root;
            this.leftSide = leftSide;
            addContains(leftSide);
            this.rightSide = rightSide;
            addContains(rightSide);
            this.operator = operator;
        }

        String getOperation() {
            return leftSide + operator + rightSide;
        }

        public Set<String> getContains() {
            return contains;
        }

        void addContains(String add) {
            contains.add(add);
        }

        boolean isDone() {
            return this.value != -1;
        }

        @Override
        public String toString() {
            return "Notion{" +
                    "leftSide='" + leftSide + '\'' +
                    ", operator='" + operator + '\'' +
                    ", rightSide='" + rightSide + '\'' +
                    '}';
        }

        void simplification() {

            this.leftSide = findAndRemplace(this.leftSide);
            this.rightSide = findAndRemplace(this.rightSide);
        }

        private String findAndRemplace(String side) {
            String PATTERN_NUMBER = "^.*(\\((\\d+)(-|\\+|\\*|/)(\\d+)\\))";
            Pattern matcher = Pattern.compile(PATTERN_NUMBER);
            Matcher match = matcher.matcher(side);
            while (match.find()) {
                String all = match.group(1);
                String firstNumber = match.group(2);
                String operation = match.group(3);
                String secondNumber = match.group(4);
                long resultat = 0L;
                long first = Long.valueOf(firstNumber);
                long second = Long.valueOf(secondNumber);
                if ("+".equals(operation)) {
                    resultat = first + second;
                } else if ("-".equals(operation)) {
                    resultat = first - second;
                } else if ("*".equals(operation)) {
                    resultat = first * second;
                } else if ("/".equals(operation)) {
                    resultat = first / second;
                }
                side = side.substring(0, side.indexOf(all)) + resultat + side.substring(Math.min(side.indexOf(all) + all.length(), side.length()));
                match = matcher.matcher(side);
            }
            return side;
        }

        void remplace() {
            Set<String> newContains = new HashSet<>();
            contains.forEach(
                    s -> {
                        if (HUMN.equals(s)) {
                            return;
                        }
                        String newString = "";
                        Notion notionToRemplace = dictionnaire.get(s);
                        if (leftSide.contains(notionToRemplace.root)) {
                            if (notionToRemplace.isDone()) {
                                while (leftSide.contains(notionToRemplace.root)) {
                                    newString = newString + leftSide.substring(0, leftSide.indexOf(notionToRemplace.root)) + notionToRemplace.value + leftSide.substring(Math.min(leftSide.indexOf(notionToRemplace.root) + notionToRemplace.root.length(), leftSide.length()));

                                    leftSide = newString;
                                }
                            } else {
                                int i = 0;
                                while (leftSide.contains(notionToRemplace.root)) {
                                    newString = newString + leftSide.substring(0, leftSide.indexOf(notionToRemplace.root)) + "(" + notionToRemplace.getOperation() + ")" + leftSide.substring(Math.min(leftSide.indexOf(notionToRemplace.root) + notionToRemplace.root.length(), leftSide.length()));
                                    newContains.addAll(notionToRemplace.getContains());
                                    leftSide = newString;
                                    if (5 < i++) {
                                        System.out.println("Breaked 1 ");
                                        break;
                                    }
                                }
                            }
                        }

                        newString = "";
                        if (rightSide.contains(notionToRemplace.root)) {
                            if (notionToRemplace.isDone()) {
                                while (rightSide.contains(notionToRemplace.root)) {
                                    newString = newString + rightSide.substring(0, rightSide.indexOf(notionToRemplace.root)) + notionToRemplace.value + rightSide.substring(Math.min(rightSide.indexOf(notionToRemplace.root) + notionToRemplace.root.length(), rightSide.length()));
                                    rightSide = newString;
                                }
                            } else {
                                int i = 0;
                                while (rightSide.contains(notionToRemplace.root)) {
                                    newString = newString + rightSide.substring(0, rightSide.indexOf(notionToRemplace.root)) + "(" + notionToRemplace.getOperation() + ")" + rightSide.substring(Math.min(rightSide.indexOf(notionToRemplace.root) + notionToRemplace.root.length(), rightSide.length()));
                                    newContains.addAll(notionToRemplace.getContains());
                                    rightSide = newString;
                                    if (5 < i++) {
                                        System.out.println("Breaked 2");
                                        break;
                                    }
                                }
                            }
                        }
                    }
            );
            contains = new HashSet<>(newContains);
        }
    }


}

