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

    private static final int maxMinutes = 32;
    private static final int limitMinutes = 18;
    private static final int limitGeode = 0;
    private static final int limitObsidian = 1;
    private static final int limitGeodeRobot = 1;

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day19/input.txt");


        List<BluePrint> bluesPrint = new ArrayList<>();
        for (String line : lines) {

            int bluePrintNumber = Integer.valueOf(line.substring(line.indexOf(BLUEPRINT) + BLUEPRINT.length(), line.indexOf(TWO_POINTS)));

            int oreRobot = getNumber(line, patternOre);
            int clayRobot = getNumber(line, patternClay);
            int obsidianOre = getNumber(line, patternObsidianOre);
            int obsidianClay = getNumber(line, patternObsidianClay);
            int geodeOre = getNumber(line, patternGeodeOre);
            int geodeObsidian = getNumber(line, patternGeodeObsidian);

            bluesPrint.add(new BluePrint(bluePrintNumber, oreRobot, clayRobot, obsidianOre, obsidianClay, geodeOre, geodeObsidian));
        }


        long init = System.currentTimeMillis();
        List<Integer> maxes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Integer> maxValues = new ArrayList<>();
            BluePrint bluePrint = bluesPrint.get(i);
            Set<Step> firstStep = new HashSet<>();
            firstStep.add(new Step(bluePrint, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            for (int profondeur = 0; profondeur < maxMinutes; profondeur++) {
                Set<Step> newSteps = new HashSet<>();
                for (Step step : firstStep) {
                    newSteps.addAll(step.calculateAllNewSteps(maxValues));
                }
                System.out.println("Minutes: " + profondeur);
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
        System.out.println("Time: " + ((end - init) / 1000L / 60L) + " [minutes]");


    }


    record Step(BluePrint bluePrint, int minute, int ore, int oreRobot, int clay, int clayRobot, int obsidian,
                int obsidianRobot, int geode, int geodeRobot) {
        List<Step> calculateAllNewSteps(List<Integer> maxValues) {
            List<Step> allPossibleStep = new ArrayList<>();
            if (minute == maxMinutes - 1) {
                maxValues.add(geodeRobot + geode);
                //System.out.println("Level: " + minute + " geode: " + geode + " GeodeRobot: " + geodeRobot);
                return allPossibleStep;
            }

            if (maxMinutes - 6 < minute && (geodeRobot) < 2) {
                return allPossibleStep;
            }


            if (minute < 25 && (bluePrint.oreRobots + bluePrint.clayRobots + bluePrint.obsidanOre + bluePrint.geodeOre) < ore) {
                return allPossibleStep;
            }


            if (minute < 25 && (bluePrint.geodeObsidian <= obsidian)) {
                return allPossibleStep;
            }

            if (25 < minute && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 2)) {
                return allPossibleStep;
            }

            if (25 < minute && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 4)) {
                return allPossibleStep;
            }

            if (25 < minute && (obsidianRobot + obsidian) < (bluePrint.geodeObsidian / 8)) {
                return allPossibleStep;
            }

            if (25 < minute && (clayRobot + clay) < (bluePrint.obsidanClay / 2)) {
                return allPossibleStep;
            }

            if (25 < minute && (clayRobot + clay) < (bluePrint.obsidanClay / 4)) {
                return allPossibleStep;
            }

            if (25 < minute && (clayRobot + clay) < (bluePrint.obsidanClay / 8)) {
                return allPossibleStep;
            }

            if (maxMinutes - 8 < minute && (clayRobot + clay) < (bluePrint.obsidanClay / 16)) {
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

    private int getNumber(String line, String pattern) {
        Matcher matcher1 = Pattern.compile(pattern).matcher(line);
        if (matcher1.find()) {
            return Integer.valueOf(matcher1.group(2));
        }
        return 0;
    }

    record BluePrint(int number, int oreRobots, int clayRobots, int obsidanOre, int obsidanClay, int geodeOre,
                     int geodeObsidian) {

    }


}
