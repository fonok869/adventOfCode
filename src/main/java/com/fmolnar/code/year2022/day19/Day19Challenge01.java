package com.fmolnar.code.year2022.day19;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Challenge01 {
    private static final String BLUEPRINT = "Blueprint ";
    private static final String TWO_POINTS = ":";
    private static final String patternOre = "^(.*)Each ore robot costs (\\d*)";
    private static final String patternClay = "^(.*)Each clay robot costs (\\d*)";
    private static final String patternObsidianOre = "^(.*)Each obsidian robot costs (\\d*) ore and";
    private static final String patternObsidianClay = "^(.*)and (\\d*) clay";
    private static final String patternGeodeOre = "^(.*)Each geode robot costs (\\d*) ore";
    private static final String patternGeodeObsidian = "^(.*)and (\\d*) obsidian";

    private static final int maxMinutes = 23;
    private static final int limitMinutes = 18;
    private static final int limitObsidian = 1;

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day19/input.txt");

        List<BluePrint> bluesPrint = new ArrayList<>();
        for (String line : lines) {

            short bluePrintNumber = Short.valueOf(line.substring(line.indexOf(BLUEPRINT) + BLUEPRINT.length(), line.indexOf(TWO_POINTS)));

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
        for (int i = 0; i < bluesPrint.size(); i++) {
            List<Integer> maxValues = new ArrayList<>();
            BluePrint bluePrint = bluesPrint.get(i);
            Set<Step> firstStep = new HashSet<>();
            firstStep.add(new Step(bluePrint, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            for (int profondeur = 1; profondeur < maxMinutes + 2; profondeur++) {
                Set<Step> newSteps = new HashSet<>();
                for (Step step : firstStep) {
                    newSteps.addAll(step.calculateAllNewSteps(maxValues));
                }
                firstStep = new HashSet<>(newSteps);
            }
            maxes.add((i + 1) * maxValues.stream().mapToInt(s -> s).max().getAsInt());
            System.gc();
        }


        System.out.println("Max Value: " + maxes.stream().mapToInt(s -> s).sum());
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - init) / 1000L) + " [sec]");
    }


    record Step(BluePrint bluePrint, int minute, int ore, int oreRobot, int clay, int clayRobot, int obsidian,
                int obsidianRobot, int geode, int geodeRobot) {
        List<Step> calculateAllNewSteps(List<Integer> maxValues) {
            List<Step> allPossibleStep = new ArrayList<>();
            if (minute == maxMinutes) {
                maxValues.add(geodeRobot + geode);
                return allPossibleStep;
            }

            if (limitMinutes < minute && (obsidianRobot < limitObsidian)) {
                return allPossibleStep;
            }

            if (8 < ore) {
                return allPossibleStep;
            }

            if (bluePrint.number == 1 && 23 < minute && ((geode + geodeRobot) < 4 ||
                    2 * bluePrint.geodeObsidian < obsidian)) {
                return allPossibleStep;
            }

            if (bluePrint.number == 2 && 23 < minute && ((geode + geodeRobot) < 5 || 2 * bluePrint.geodeObsidian < obsidian)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 1 && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 2)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 2 && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 4)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 3 && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 8)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 4 && (clayRobot + clay) < (bluePrint.obsidanClay / 2)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 5 && (clayRobot + clay) < (bluePrint.obsidanClay / 4)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 6 && (clayRobot + clay) < (bluePrint.obsidanClay / 8)) {
                return allPossibleStep;
            }

            if (minute == maxMinutes - 7 && (clayRobot + clay) < (bluePrint.obsidanClay / 16)) {
                return allPossibleStep;
            }

            // only default ore-collecting
            allPossibleStep.add(new Step(bluePrint, minute + 1, ore + oreRobot + 1, oreRobot, clay + clayRobot, clayRobot, obsidian + obsidianRobot, obsidianRobot, geode + geodeRobot, geodeRobot));

            if (bluePrint.oreRobots <= ore) {
                allPossibleStep.add(new Step(bluePrint, minute + 1, ore + oreRobot + 1 - bluePrint.oreRobots, oreRobot + 1, clay + clayRobot, clayRobot, obsidian + obsidianRobot, obsidianRobot, geode + geodeRobot, geodeRobot));
            }

            if (bluePrint.clayRobots <= ore) {
                allPossibleStep.add(new Step(bluePrint, minute + 1, ore + oreRobot + 1 - bluePrint.clayRobots, oreRobot, clay + clayRobot, clayRobot + 1, obsidian + obsidianRobot, obsidianRobot, geode + geodeRobot, geodeRobot));
            }

            if (bluePrint.obsidanOre <= ore && bluePrint.obsidanClay <= clay) {
                allPossibleStep.add(new Step(bluePrint, minute + 1, ore + oreRobot + 1 - bluePrint.obsidanOre, oreRobot, clay + clayRobot - bluePrint.obsidanClay, clayRobot, obsidian + obsidianRobot, obsidianRobot + 1, geode + geodeRobot, geodeRobot));
            }

            if (bluePrint.geodeOre <= ore && bluePrint.geodeObsidian <= obsidian) {
                allPossibleStep.add(new Step(bluePrint, minute + 1, ore + oreRobot + 1 - bluePrint.geodeOre, oreRobot, clay + clayRobot, clayRobot, obsidian + obsidianRobot - bluePrint.geodeObsidian, obsidianRobot, geode + geodeRobot, geodeRobot + 1));
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

    record BluePrint(short number, short oreRobots, short clayRobots, short obsidanOre, short obsidanClay, short geodeOre,
                     short geodeObsidian) {

    };
}
