package com.fmolnar.code.year2023.day19;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19v2 {

    static Map<String, String> dictRules = new HashMap<>();
    static List<StartingPoint> startingPoints = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day19/input.txt");

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
        List<LineRange> wons = new ArrayList<>();
        LineRange init = new LineRange(new RangeX(1, 4000), new RangeM(1, 4000), new RangeA(1, 4000), new RangeS(1, 4000));
        String nextLetters = "in";
        recursiveRangeSplit(nextLetters, init, wons);

        long sumAll = 0;

        for(LineRange lineRange : wons){
            sumAll+= lineRange.sumUp();
        }
        System.out.println(sumAll);
    }

    private static void recursiveRangeSplit(String nextLetters, LineRange lineRange, List<LineRange> wons) {
        String actual = dictRules.get(nextLetters);
        String[] actualPieces = actual.split(",");
        for (String ruleOrder : actualPieces) {
            int kettospont = ruleOrder.indexOf(":");
            if (kettospont != -1) {
                // kisebb
                String firstPartStringOfRule = ruleOrder.substring(0, kettospont);
                String secondPartStringOfRule = ruleOrder.substring(kettospont + 1);
                int kisebbRelacio = firstPartStringOfRule.indexOf('<');
                int nagyobbRelacio = firstPartStringOfRule.indexOf('>');
                if (kisebbRelacio != -1) {
                    String letterCondition = firstPartStringOfRule.substring(0, kisebbRelacio);
                    int letterValue = Integer.valueOf(firstPartStringOfRule.substring(kisebbRelacio + 1));
                    List<LineRange> kisebbek = getFromLineRangeOKKisebb(lineRange, letterCondition, letterValue);
                    LineRange kisebb = kisebbek.get(0);
                    if("A".equals(secondPartStringOfRule)){
                        wons.add(kisebb);
                    } else if ("R".equals(secondPartStringOfRule)){
                        // elveszitjuk
                    } else {

                        recursiveRangeSplit(secondPartStringOfRule,kisebb,wons);
                    }
                    lineRange = kisebbek.get(1);
                } else {
                    String letterCondition = firstPartStringOfRule.substring(0, nagyobbRelacio);
                    int letterValue = Integer.valueOf(firstPartStringOfRule.substring(nagyobbRelacio + 1));
                    List<LineRange> nagyobbak = getFromLineRangeOKKisebb(lineRange, letterCondition, letterValue+1);
                    LineRange nagyobb = nagyobbak.get(1);
                    if("A".equals(secondPartStringOfRule)){
                        wons.add(nagyobb);
                    } else if ("R".equals(secondPartStringOfRule)){
                        // elveszitjuk
                    } else {
                        recursiveRangeSplit(secondPartStringOfRule,nagyobb,wons);
                    }
                    lineRange = nagyobbak.get(0);
                }
            } else {
                // Direct Command
                if ("A".equals(ruleOrder)) {
                    wons.add(lineRange);
                    return;
                } else if ("R".equals(ruleOrder)) {
                    return;
                } else {
                    nextLetters = ruleOrder;
                    recursiveRangeSplit(nextLetters, lineRange, wons);
                    return;
                }

            }
        }
    }

    public static List<LineRange> getFromLineRangeOKKisebb(LineRange lineRange, String letterCondition, int letterValue) {

        int rangeActualMax = 0;
        int rangeActualMin = 0;
        List<LineRange> ranges = new ArrayList<>();
        if ("x".equals(letterCondition)) {
            rangeActualMax = lineRange.rangeX.to;
            rangeActualMin = lineRange.rangeX.from;
            RangeX range = new RangeX(rangeActualMin, letterValue - 1);
            RangeX rangeKivul = new RangeX(letterValue, rangeActualMax);

            if (rangeActualMax < letterValue) {
                // egesz megy vissza
                ranges.add(lineRange);
                ranges.add(null);
                return ranges;
            } else if (letterValue < rangeActualMin) {
                ranges.add(null);
                ranges.add(lineRange);
                return ranges;
            } else if (rangeActualMin < letterValue && letterValue < rangeActualMax) {
                ranges.add(lineRange.modify(range));
                ranges.add(lineRange.modify(rangeKivul));
                return ranges;
            }
        } else if ("m".equals(letterCondition)) {
            rangeActualMax = lineRange.rangeM.to;
            rangeActualMin = lineRange.rangeM.from;
            RangeM range = new RangeM(rangeActualMin, letterValue - 1);
            RangeM rangeKivul = new RangeM(letterValue, rangeActualMax);

            if (rangeActualMax < letterValue) {
                // egesz megy vissza
                ranges.add(lineRange);
                ranges.add(null);
                return ranges;
            } else if (letterValue < rangeActualMin) {
                ranges.add(null);
                ranges.add(lineRange);
                return ranges;
            } else if (rangeActualMin < letterValue && letterValue < rangeActualMax) {
                ranges.add(lineRange.modify(range));
                ranges.add(lineRange.modify(rangeKivul));
                return ranges;
            }
        } else if ("a".equals(letterCondition)) {
            rangeActualMax = lineRange.rangeA.to;
            rangeActualMin = lineRange.rangeA.from;
            RangeA range = new RangeA(rangeActualMin, letterValue - 1);
            RangeA rangeKivul = new RangeA(letterValue, rangeActualMax);

            if (rangeActualMax < letterValue) {
                // egesz megy vissza
                ranges.add(lineRange);
                ranges.add(null);
                return ranges;
            } else if (letterValue < rangeActualMin) {
                ranges.add(null);
                ranges.add(lineRange);
                return ranges;
            } else if (rangeActualMin < letterValue && letterValue < rangeActualMax) {
                ranges.add(lineRange.modify(range));
                ranges.add(lineRange.modify(rangeKivul));
                return ranges;
            }
        } else if ("s".equals(letterCondition)) {
            rangeActualMax = lineRange.rangeS.to;
            rangeActualMin = lineRange.rangeS.from;
            RangeS range = new RangeS(rangeActualMin, letterValue - 1);
            RangeS rangeKivul = new RangeS(letterValue, rangeActualMax);

            if (rangeActualMax < letterValue) {
                // egesz megy vissza
                ranges.add(lineRange);
                ranges.add(null);
                return ranges;
            } else if (letterValue < rangeActualMin) {
                ranges.add(null);
                ranges.add(lineRange);
                return ranges;
            } else if (rangeActualMin < letterValue && letterValue < rangeActualMax) {
                ranges.add(lineRange.modify(range));
                ranges.add(lineRange.modify(rangeKivul));
                return ranges;
            }

        }


        throw new RuntimeException("Nem tudtuk lebontani");
    }


    private static void getFromLineRangeOK(LineRange lineRange, String letterCondition) {
//        if ("x".equals(letterCondition)) {
//            return point.x;
//        }
//
//        if ("m".equals(letterCondition)) {
//            return point.m;
//        }
//
//        if ("a".equals(letterCondition)) {
//            return point.a;
//        }
//
//        if ("s".equals(letterCondition)) {
//            return point.s;
//        }
//        throw new RuntimeException("Nem kellene itt lennie");
    }

    record LineRange(RangeX rangeX, RangeM rangeM, RangeA rangeA, RangeS rangeS) {

        long sumUp(){
            return rangeX.sumUp()*rangeA.sumUp()*rangeM.sumUp()*rangeS.sumUp();
        }


        public LineRange modify(RangeX rangeXA) {
            return new LineRange(rangeXA, rangeM, rangeA, rangeS);
        }

        public LineRange modify(RangeM rangeMA) {
            return new LineRange(rangeX, rangeMA, rangeA, rangeS);
        }

        public LineRange modify(RangeA rangeAA) {
            return new LineRange(rangeX, rangeM, rangeAA, rangeS);
        }

        public LineRange modify(RangeS rangeSA) {
            return new LineRange(rangeX, rangeM, rangeA, rangeSA);
        }
    }

    ;

    record RangeX(int from, int to) implements IRange {

        long sumUp(){
            return to-from+1;
        }
        @Override
        public int getFrom() {
            return from;
        }

        @Override
        public int getTo() {
            return to;
        }
    }

    ;

    record RangeM(int from, int to) implements IRange {

        long sumUp(){
            return to-from+1;
        }
        @Override
        public int getFrom() {
            return from;
        }

        @Override
        public int getTo() {
            return to;
        }
    }

    ;

    record RangeA(int from, int to) implements IRange {

        long sumUp(){
            return to-from+1;
        }
        @Override
        public int getFrom() {
            return from;
        }

        @Override
        public int getTo() {
            return to;
        }
    }

    ;

    record RangeS(int from, int to) implements IRange {

        long sumUp(){
            return to-from+1;
        }
        @Override
        public int getFrom() {
            return from;
        }

        @Override
        public int getTo() {
            return to;
        }
    }

    interface IRange {
        int getFrom();

        int getTo();
    }

    ;

    record StartingPoint(int x, int m, int a, int s) {
        long sum() {
            return x + m + a + s;
        }
    }

    ;
}
