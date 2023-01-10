package com.fmolnar.code.year2022.day19;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Challenge02 {

    private static final String BLUEPRINT = "Blueprint ";
    private static final String TWO_POINTS = ":";
    private static final String patternOre = "^(.*)Each ore robot costs (\\d*)";
    private static final String patternClay = "^(.*)Each clay robot costs (\\d*)";
    private static final String patternObsidianOre = "^(.*)Each obsidian robot costs (\\d*) ore and";
    private static final String patternObsidianClay = "^(.*)and (\\d*) clay";
    private static final String patternGeodeOre = "^(.*)Each geode robot costs (\\d*) ore";
    private static final String patternGeodeObsidian = "^(.*)and (\\d*) obsidian";

    private static final short maxMinutes = 32;
    private static final int limitMinutes = 18;
    private static final int limitGeode = 0;
    private static final int limitObsidian = 1;
    private static final int limitGeodeRobot = 1;

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day19/input.txt");


        List<BluePrint> bluesPrint = new ArrayList<>();
        for (String line : lines) {

            short bluePrintNumber = Byte.valueOf(line.substring(line.indexOf(BLUEPRINT) + BLUEPRINT.length(), line.indexOf(TWO_POINTS)));

            short oreRobot = getNumber(line, patternOre);
            short clayRobot = getNumber(line, patternClay);
            short obsidianOre = getNumber(line, patternObsidianOre);
            short obsidianClay = getNumber(line, patternObsidianClay);
            short geodeOre = getNumber(line, patternGeodeOre);
            short geodeObsidian = getNumber(line, patternGeodeObsidian);

            bluesPrint.add(new BluePrint(bluePrintNumber, oreRobot, clayRobot, obsidianOre, obsidianClay, geodeOre, geodeObsidian));
        }


        long init = System.currentTimeMillis();
        List<Integer> maxes = new ArrayList<>();
        short zero = (short) 0;
        short zeroByte = (short) 0;
        for (short i = 0; i < 3; i++) {
            List<Short> maxValues = new ArrayList<>();
            BluePrint bluePrint = bluesPrint.get(i);
            Set<Step> firstStep = new HashSet<>();
            firstStep.add(new Step(zero, zero, zeroByte, zero, zeroByte, zero, zeroByte, zero, zeroByte));
            for (short profondeur = 0; profondeur < maxMinutes; profondeur++) {
                Set<Step> newSteps = new HashSet<>();
                for (Step step : firstStep) {
                    newSteps.addAll(step.calculateAllNewSteps(i, maxValues, bluePrint));
                }
                System.out.println("Minutes: " + profondeur + " size: " + newSteps.size());
                firstStep = new HashSet<>(newSteps);
            }
            System.out.println("Round: " + (i + 1));
            System.out.println("Max: " + maxValues.stream().mapToInt(s -> s).max().getAsInt());
            maxes.add(maxValues.stream().mapToInt(s -> s).max().getAsInt());
            System.gc();
        }

        int maxAll = maxes.get(0) * maxes.get(1) * maxes.get(2);

        System.out.println("Max Value: " + maxAll);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - init) / 1000L) + " [sec]");


    }


    record Step(short minute, short ore, short oreRobot, short clay, short clayRobot,
                short obsidian, short obsidianRobot, short geode, short geodeRobot) {
        List<Step> calculateAllNewSteps(short line, List<Short> maxValues, BluePrint bluePrint) {
            List<Step> allPossibleStep = new ArrayList<>();
            if (minute == (short) (maxMinutes - 1)) {
                maxValues.add((short) (geodeRobot + geode));
                return allPossibleStep;
            }

            short maxLimit = (short) 25;
            short maxLimit2 = (short) 26;
            short maxLimit3 = (short) 27;
            short maxLimit31 = (short) 20;
            short kicsiLimit = (short) 21;


            if (maxLimit < minute && (obsidianRobot < 1 || clayRobot < 3)) {
                return allPossibleStep;
            }

            if (maxLimit31 < minute && (obsidianRobot < 2 || clayRobot < 4)) {
                return allPossibleStep;
            }


            if (maxLimit < minute && (geodeRobot) < 1) {
                return allPossibleStep;
            }

            if (maxLimit2 < minute && (geodeRobot + geode) < 3) {
                return allPossibleStep;
            }

            if (maxLimit2 < minute && (obsidianRobot < 3 || clayRobot < 6)) {
                return allPossibleStep;
            }

            if (line == 2 && maxLimit3 < minute && (obsidianRobot < 5 || clayRobot < 10)) {
                return allPossibleStep;
            }

            if (line == 2 && maxLimit31 < minute && (geodeRobot) < 1) {
                return allPossibleStep;
            }

            if (line == 2 && maxLimit < minute && (geodeRobot+geode) < 16) {
                return allPossibleStep;
            }

            //0 sor
            if (line == 0 && (maxLimit3 < minute) && (geode < 8)) {
                return allPossibleStep;
            }

            // 1 sor
            if (line == 1 && (maxLimit3 < minute) && (geode < 4)) {
                return allPossibleStep;
            }

            if (((2 * bluePrint.obsidanClay) < clay) && minute < kicsiLimit) {
                return allPossibleStep;
            }

            if (((2 * bluePrint.geodeObsidian) < obsidian) && minute < kicsiLimit) {
                return allPossibleStep;
            }

            if (minute == (maxMinutes - 1) && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 2)) {
                return allPossibleStep;
            }

            if (minute == (maxMinutes - 2) && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 4)) {
                return allPossibleStep;
            }

            if ((minute == (maxMinutes - 3)) && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 8)) {
                return allPossibleStep;
            }

            if ((minute == (maxMinutes - 4)) && (clayRobot + clay) < (bluePrint.obsidanClay / 2)) {
                return allPossibleStep;
            }

            if ((minute == (maxMinutes - 5)) && (clayRobot + clay) < (bluePrint.obsidanClay / 4)) {
                return allPossibleStep;
            }

            if ((minute == (maxMinutes - 6)) && (clayRobot + clay) < (bluePrint.obsidanClay / 8)) {
                return allPossibleStep;
            }

            if ((minute == (maxMinutes - 7)) && (clayRobot + clay) < (bluePrint.obsidanClay / 16)) {
                return allPossibleStep;
            }


            // only default ore-collecting
            allPossibleStep.add(new Step((short) (minute + 1), (short) (ore + oreRobot + 1), oreRobot, (short) (clay + clayRobot), (clayRobot), (short) (obsidian + obsidianRobot), (obsidianRobot), (short) (geode + geodeRobot), (geodeRobot)));

            if (bluePrint.oreRobots <= ore) {
                allPossibleStep.add(new Step(((short) (minute + 1)), (short) (ore + oreRobot + 1 - bluePrint.oreRobots), (short) (oreRobot + (short) 1), (short) (clay + clayRobot), clayRobot, (short) (obsidian + obsidianRobot), obsidianRobot, (short) (geode + geodeRobot), geodeRobot));
            }

            if (bluePrint.clayRobots <= ore) {
                allPossibleStep.add(new Step(((short) (minute + 1)), (short) (ore + oreRobot + 1 - bluePrint.clayRobots), oreRobot, (short) (clay + clayRobot), (short) (clayRobot + 1), (short) (obsidian + obsidianRobot), (obsidianRobot), (short) (geode + geodeRobot), (geodeRobot)));
            }

            if (bluePrint.obsidanOre <= ore && bluePrint.obsidanClay <= clay) {
                allPossibleStep.add(new Step(((short) (minute + 1)), (short) (ore + oreRobot + 1 - bluePrint.obsidanOre), (oreRobot), (short) (clay + clayRobot - bluePrint.obsidanClay), (clayRobot), (short) (obsidian + obsidianRobot), (short) (obsidianRobot + 1), (short) (geode + geodeRobot), (geodeRobot)));
            }

            if (bluePrint.geodeOre <= ore && bluePrint.geodeObsidian <= obsidian) {
                allPossibleStep.add(new Step(((short) (minute + 1)), (short) (ore + oreRobot + 1 - bluePrint.geodeOre), (oreRobot), (short) (clay + clayRobot), (clayRobot), (short) (obsidian + obsidianRobot - bluePrint.geodeObsidian), (obsidianRobot), (short) (geode + geodeRobot), (short) (geodeRobot + 1)));
            }
            return allPossibleStep;
        }

        ;
    }

    private short getNumber(String line, String pattern) {
        Matcher matcher1 = Pattern.compile(pattern).matcher(line);
        if (matcher1.find()) {
            return Short.valueOf(matcher1.group(2));
        }
        return 0;
    }

    record BluePrint(short number, short oreRobots, short clayRobots, short obsidanOre, short obsidanClay,
                     short geodeOre, short geodeObsidian) {

    }


}
