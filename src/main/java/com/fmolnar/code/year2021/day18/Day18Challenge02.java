package com.fmolnar.code.year2021.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18Challenge02 {
    private List<String> lines = new ArrayList<>();
    private String REGEX_DECIMAL = "[,\\[\\]]+([0-9]{2})[,\\[\\]]+";
    private static String LAST_DECIMAL = "(\\d{1,2})[,\\[\\]]*$";
    private static String FIRST_DECIMAL = "^[,\\[\\]]*(\\d{1,2})";
    private static String DECIMAL_PAIRS = "(\\d{1,2}),(\\d{1,2})";
    private static String FIRST_PAIRS = "^[,\\[\\]\\d]*(\\[(\\d{1,10}),(\\d{1,10})\\])";

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day18/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;

            while ((line = file.readLine()) != null) {
                lines.add(line);
            }


        }

        List<String> linesToIterate = new ArrayList<>(lines);
        List<Long> maxValues = new ArrayList<>();
        lines.forEach(
                line -> {
                    linesToIterate.forEach(
                            lineToCheck -> {
                                if (!line.equals(lineToCheck)) {
                                    Line lineCompound = new Line("[" + line + "," + lineToCheck + "]");
                                    long max = calculMagnitude(lineCompound.getLine());
                                    maxValues.add(max);
                                }

                            }
                    );
                }
        );

        System.out.println("Max: " + maxValues.stream().mapToLong(s->s).max().getAsLong());
    }

    protected long calculMagnitude(String line) {
        String newLineBefore = "";
        String newLineAfter = "";
        long sum = 0;
        while (true) {
            Matcher matcher = Pattern.compile(FIRST_PAIRS).matcher(line);
            if (matcher.find()) {
                String pair = matcher.group(1);
                if (line.length() == pair.length()) {
                    return calculSum(matcher);
                }
                int index = line.indexOf(pair);
                newLineBefore = line.substring(0, index);
                newLineAfter = line.substring((index + pair.length()));
                sum = calculSum(matcher);
                line = newLineBefore + sum + newLineAfter;
            }
        }
    }

    private long calculSum(Matcher matcher) {
        long firstValue = Long.valueOf(matcher.group(2)) * 3;
        long secondValue = Long.valueOf(matcher.group(3)) * 2;
        return firstValue + secondValue;
    }

    public static class Line {
        private String line;
        private boolean split;
        private boolean explode;
        private int position = -1;

        public Line(String line) {
            this.line = line;
            while (needToExplodeOrSplit()) {
                if (explode) {
                    doExplode();
                    this.explode = false;
                    this.position = -1;

                } else if (split) {
                    doSplit();
                    this.split = false;
                    this.position = -1;
                }

            }
        }

        public String getLine() {
            return line;
        }

        private void doSplit() {
            String numberToSplit = line.substring(this.position, this.position + 2);
            Integer value = Integer.valueOf(numberToSplit);
            int firstValue = value / 2;
            int secondValue = (int) Math.ceil((value / 2.0));
            String newPair = "[" + firstValue + "," + secondValue + "]";
            String before = line.substring(0, this.position);
            String after = line.substring(this.position + 2);
            this.line = before + newPair + after;
        }

        private void doExplode() {
            // Get value [a,b]
            int firstIndexAccolade = line.substring(this.position).indexOf("]");
            String pairs = line.substring(this.position + 1, this.position + firstIndexAccolade);
            Matcher matcherPair = Pattern.compile(DECIMAL_PAIRS).matcher(pairs);
            int firstPairValue = -1;
            int secondPairValue = -1;
            if (matcherPair.find()) {
                firstPairValue = Integer.valueOf(matcherPair.group(1));
                secondPairValue = Integer.valueOf(matcherPair.group(2));
            } else {
                throw new RuntimeException("Problema Van, Pairben nincs matching");
            }

            // new Firtst Part
            String newFirstPart = getNewFirstPart(firstPairValue);

            // new Second Part

            String newSecondPart = getNewSecondPart(secondPairValue, this.position + firstIndexAccolade);
            //System.out.println(newFirstPart + "0" + newSecondPart);

            this.line = newFirstPart + "0" + newSecondPart;

        }

        private String getNewSecondPart(int secondPairValue, int indexAcolade) {
            String newSecondPart = "";
            // Search for decimalValueBefore
            String oldSecondPart = line.substring(indexAcolade + 1);
            Matcher matcherFirstDecimal = Pattern.compile(FIRST_DECIMAL).matcher(oldSecondPart);
            if (matcherFirstDecimal.find()) {
                // Value found
                String numberToChange = matcherFirstDecimal.group(1);
                int indexFirstNumber = oldSecondPart.indexOf(numberToChange);
                int newNumber = Integer.valueOf(numberToChange) + secondPairValue;
                if (numberToChange.length() < 2) {
                    newSecondPart = oldSecondPart.substring(0, indexFirstNumber) + newNumber + oldSecondPart.substring(indexFirstNumber + 1);
                } else if (numberToChange.length() == 2) {
                    // Ketjegyu
                    newSecondPart = oldSecondPart.substring(0, indexFirstNumber) + newNumber + oldSecondPart.substring(indexFirstNumber + 2);
                }
            } else {
                // nincs szamjegy
                newSecondPart = oldSecondPart;
            }
            return newSecondPart;
        }

        private String getNewFirstPart(int firstPairValue) {
            String newFirstPart = "";
            // Search for decimalValueBefore
            String oldFirstPart = line.substring(0, this.position);
            Matcher matcherLastDecimal = Pattern.compile(LAST_DECIMAL).matcher(oldFirstPart);
            if (matcherLastDecimal.find()) {
                // Value found
                String numberToChange = matcherLastDecimal.group(1);
                int indexLastNumber = oldFirstPart.lastIndexOf(numberToChange);
                int newNumber = Integer.valueOf(numberToChange) + firstPairValue;
                if (numberToChange.length() < 2) {
                    newFirstPart = oldFirstPart.substring(0, indexLastNumber) + newNumber + oldFirstPart.substring(indexLastNumber + 1);
                } else if (numberToChange.length() == 2) {
                    // Ketjegyu
                    newFirstPart = oldFirstPart.substring(0, indexLastNumber) + newNumber + oldFirstPart.substring(indexLastNumber + 2);
                }
            } else {
                // nincs szamjegy
                newFirstPart = oldFirstPart;
            }
            return newFirstPart;
        }

        private boolean needToExplodeOrSplit() {
            int counterLeft = 0;
            for (int i = 0; i < line.length(); i++) {
                char charActual = line.charAt(i);
                if (charActual == '[') {
                    counterLeft++;
                } else if (charActual == ']') {
                    counterLeft--;
                }
                if (counterLeft == 5) {
                    this.position = i;
                    this.explode = true;
                    return true;
                }
            }
            for (int i = 0; i < line.length(); i++) {
                if (i < line.length() - 1) {
                    String numberString = line.substring(i, i + 2);
                    try {
                        int number = Integer.valueOf(numberString);
                        if (number > 9) {
                            this.split = true;
                            this.position = i;
                            return true;
                        }
                    } catch (NumberFormatException ex) {
                        // Continue
                    }
                }
            }
            return false;
        }
    }
}
