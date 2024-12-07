package com.fmolnar.code.year2022.day21;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21Challenge01 {

    public static final String ROOT = "root";
    Map<String, Ins> dictionnaire = new HashMap<>();
    private static String TWO_POINTS = ":";

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day21/input.txt");

        for (String line : lines) {
            String root = line.substring(0, line.indexOf(TWO_POINTS));
            if (10 < line.length()) {
                String instruction = line.substring(line.indexOf(TWO_POINTS) + 2);
                String[] ins1 = instruction.split(" ");
                dictionnaire.put(root, new Ins(root, ins1[0], ins1[1], ins1[2]));
            } else {
                long value = Long.valueOf(line.substring(line.indexOf(TWO_POINTS) + 1).trim());
                dictionnaire.put(root, new Ins(root, value));
            }
        }


        // Add dependancy
        for (Map.Entry<String, Ins> ins : dictionnaire.entrySet()) {
            Ins instruction = ins.getValue();
            if (!instruction.isDone()) {
                Ins leftIns = dictionnaire.get(instruction.leftIns);
                leftIns.addDependence(instruction.root);
                Ins rightIns = dictionnaire.get(instruction.rightIns);
                rightIns.addDependence(instruction.root);
            }
        }

        // calculate
        loop:
        for (long i = 0; i < 50; i++) {
            for (Map.Entry<String, Ins> ins : dictionnaire.entrySet()) {
                Ins instruction = ins.getValue();
                if (instruction.isDone()) {
                    instruction.calculDependancy();
                    if (ROOT.equals(instruction.root)) {
                        System.out.println("Resultat: " + instruction.value);
                        break loop;
                    }
                }
            }
        }
    }

    class Ins {
        final String root;
        long value = -1;
        String leftIns;
        long leftValue = -1;
        String rightIns;
        long rightValue = -1;
        String operation;
        List<String> dependances = new ArrayList<>();

        public Ins(String root, long value) {
            this.root = root;
            this.value = value;
        }

        public Ins(String root, String leftIns, String operation, String rightIns) {
            this.root = root;
            this.leftIns = leftIns;
            this.rightIns = rightIns;
            this.operation = operation;
        }

        void addDependence(String dependance) {
            this.dependances.add(dependance);
        }


        boolean isDone() {
            return value != -1;
        }

        void hasAllValues() {
            if (leftValue != -1 && rightValue != -1) {
                if ("-".equals(operation)) {
                    value = leftValue - rightValue;
                } else if ("+".equals(operation)) {
                    value = rightValue + leftValue;
                } else if ("*".equals(operation)) {
                    value = leftValue * rightValue;
                } else if ("/".equals(operation)) {
                    value = leftValue / rightValue;
                } else {
                    System.out.println("Nem kellene itt lennie");
                }
            }
        }

        public void calculDependancy() {
            dependances.forEach(
                    d -> {
                        Ins ins = dictionnaire.get(d);
                        if (ins.leftIns.equals(root)) {
                            ins.leftValue = this.value;
                        } else if (ins.rightIns.equals(root)) {
                            ins.rightValue = this.value;
                        }
                        ins.hasAllValues();
                    }
            );
        }
    }
}
