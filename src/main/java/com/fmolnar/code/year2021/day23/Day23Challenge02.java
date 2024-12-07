package com.fmolnar.code.year2021.day23;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23Challenge02 {
    private static final int DEEPNESS = 4;
    private static final int SEVEN = 7;
    private static final int FOUR = 4;
    private static final int MAX = SEVEN + 4 * DEEPNESS;

    private Map<Integer, Map<Integer, Integer>> distanceCache = new HashMap<>();

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2021/day23/input.txt");

        byte[] config = extracted(lines);
        Step step0 = new Step(config, 0);
        long init = System.currentTimeMillis();

        fillOurDistanceCache();


        PriorityQueue<StepWrapper> prioWrapper = new PriorityQueue<StepWrapper>(new Comparator<StepWrapper>() {
            @Override
            public int compare(StepWrapper o1, StepWrapper o2) {
                if (o1.estimation < o2.estimation) {
                    return -1;
                } else if (o1.estimation > o2.estimation) {
                    return 1;
                }
                return 0;
            }
        });
        prioWrapper.add(new StepWrapper(step0, (calculateEstimation(step0.config) + 0)));
        List<Step> steps = new ArrayList<>();
        Set<Integer> minimumsForDone = new HashSet<>();
        int counter = 0;
        while (minimumsForDone.size() == 0) {

            StepWrapper stepWrapper = prioWrapper.poll();
            steps.add(stepWrapper.step());
            if (counter != 0) {
                for (; ; ) {
                    StepWrapper before = prioWrapper.poll();
                    if (stepWrapper.estimation() == before.estimation()) {
                        steps.add(before.step());
                    } else {
                        prioWrapper.add(before);
                        break;
                    }
                }
            }

            //System.out.println(stepWrapper);
            if (counter % 1000 == 1) {
                System.out.println("Step :" + counter++ + " Size: " + steps.size() + " " + "PrioSize : " + prioWrapper.size() + " estimation:" + stepWrapper.estimation);
            }
            counter++;
            calculateAllPossibilities(steps, minimumsForDone);
            prioWrapper.addAll(steps.stream().map(s -> new StepWrapper(s, (calculateEstimation(s.config) + s.cost))).collect(Collectors.toList()));
            steps.clear();
        }


        System.out.println("Second: " + minimumsForDone.stream().mapToInt(s -> s).min().getAsInt());

        System.out.println("Time elapsed: " + ((System.currentTimeMillis() - init) / 1000));
    }

    private void fillOurDistanceCache() {
        int multiplicateur = 1;
        for (byte bit = 1; bit < 5; bit++) {
            Map<Integer, Integer> bitMap = new HashMap<>();
            for (int i = 0; i < MAX; i++) {
                if ((SEVEN + FOUR + FOUR + FOUR + bit - 1) == i) {
                    bitMap.put(i, 0);
                    continue;
                }
                byte[] onlyActualByte = new byte[MAX];
                onlyActualByte[i] = bit;
                List<Step> steps = new ArrayList<>();
                steps.add(new Step(onlyActualByte, 0));
                Set<Integer> minSets = new HashSet<>();
                for (int k = 0; k < 2; k++) {
                    calculateAllPossibilities(steps, new HashSet<>());
                    for (Step step : steps) {
                        if (step.config[SEVEN + FOUR + FOUR + FOUR + bit - 1] == bit) {
                            minSets.add(step.cost);
                        }
                    }

                }
                int min = minSets.stream().mapToInt(s -> s).min().getAsInt();
                bitMap.put(i, min);
            }
            // Same row --> do not go up
            bitMap.put((SEVEN + bit - 1), 3 * multiplicateur);
            bitMap.put((SEVEN + FOUR + bit - 1), 2* multiplicateur);
            bitMap.put((SEVEN + FOUR + FOUR + bit - 1), 1*multiplicateur);
            multiplicateur = multiplicateur*10;
            distanceCache.put(Integer.valueOf(bit), bitMap);
        }
    }

    public void calculateAllPossibilities(List<Step> steps, Set<Integer> minimumsForDone) {


        List<Step> newSteps = new ArrayList<>();
        //mar van finished --> akko az osszest filterezzuk a legkisebb szerint
        steps.forEach(
                s -> {
                    newSteps.addAll(s.getAllNewMovementUpStep());
                    newSteps.addAll(s.getAllNewMovementDownStep());
                    if (s.isDone()) {
                        newSteps.add(s);
                    }
                }
        );
        steps.clear();
        newSteps.forEach(
                s -> {
                    if (s.isDone()) {
                        minimumsForDone.add(s.cost);
                    }
                }
        );
        if (0 < minimumsForDone.size()) {
            int min = minimumsForDone.stream().mapToInt(s -> s).min().getAsInt();
            steps.addAll(newSteps.stream().filter(s -> min <= s.cost).collect(Collectors.toList()));
            return;
        } else {
            steps.addAll(newSteps);
        }
        newSteps.clear();
    }

    protected int calculateEstimation(byte[] config) {
        List<Integer> estimations = new ArrayList<>();
        for (int byteActual = 1; byteActual < 5; byteActual++) {
            estimations.add(getDistanceForAllByte(config, (byte) byteActual));
        }
        return estimations.stream().mapToInt(s -> s).sum();
    }


    private int getDistanceForAllByte(byte[] config, byte byteActual) {
        int distance = 0;
        int jump = 6;
        for (int i = 0; i < MAX; i++) {
            if (config[i] == byteActual) {
                distance += distanceCache.get(Integer.valueOf(byteActual)).get(i);
                if ((SEVEN + FOUR + FOUR + FOUR + byteActual - 1) == i) {
                    jump--;
                }
            }
        }
        // Always 3 other value
        switch (byteActual) {
            // A : 1
            case (byte) 1:
                distance -= jump;
                break;
            case (byte) 2:
                distance -= jump * 10;
                break;
            case (byte) 3:
                distance -= jump * 100;
                break;
            case (byte) 4:
                distance -= jump * 1000;
                break;
            default:
        }
        return distance;
    }

    private byte[] extracted(List<String> lines) {
        byte[] config = new byte[MAX];
        int counter = SEVEN;
        for (String line : lines) {
            if (line.contains("A") || line.contains("B") || line.contains("C")) {
                for (int i = 0; i < line.length(); i++) {
                    char onlyChar = line.charAt(i);
                    if (onlyChar != '#') {
                        switch (onlyChar) {
                            case 'A':
                                config[counter++] = 1;
                                break;
                            case 'B':
                                config[counter++] = 2;
                                break;
                            case 'C':
                                config[counter++] = 3;
                                break;
                            case 'D':
                                config[counter++] = 4;
                                break;
                        }
                    }
                }
            }
            if (counter == 11) {
                //-- 2 new lines
                config[counter++] = 4;
                config[counter++] = 3;
                config[counter++] = 2;
                config[counter++] = 1;
                //---------------
                config[counter++] = 4;
                config[counter++] = 2;
                config[counter++] = 1;
                config[counter++] = 3;
            }
        }
        return config;
    }

    record StepWrapper(Step step, int estimation) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StepWrapper that)) return false;
            return estimation == that.estimation;
        }

        @Override
        public int hashCode() {
            return Objects.hash(estimation);
        }

    }

    record Step(byte[]config, int cost) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Step step)) return false;
            return cost == step.cost && Arrays.equals(config, step.config);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(cost);
            result = 31 * result + Arrays.hashCode(config);
            return result;
        }

        boolean isByteDone(byte amp) {
            if (config[SEVEN + amp - 1] == amp && config[SEVEN + FOUR + amp - 1] == amp &&
                    config[SEVEN + FOUR + FOUR + amp - 1] == amp && config[SEVEN + FOUR + FOUR + FOUR + amp - 1] == amp) {
                return true;
            }
            return false;
        }

        ;

        boolean isDone() {
            if (config[SEVEN] == (byte) 1 && config[SEVEN + FOUR] == (byte) 1 && config[SEVEN + 2 * FOUR] == (byte) 1 && config[SEVEN + 3 * FOUR] == (byte) 1) {
                if (config[SEVEN + 1] == (byte) 2 && config[SEVEN + FOUR + 1] == (byte) 2 && config[SEVEN + 2 * FOUR + 1] == (byte) 2 && config[SEVEN + 3 * FOUR + 1] == (byte) 2) {
                    if (config[SEVEN + 2] == (byte) 3 && config[SEVEN + FOUR + 2] == (byte) 3 && config[SEVEN + 2 * FOUR + 2] == (byte) 3 && config[SEVEN + 3 * FOUR + 2] == (byte) 3) {
                        if (config[SEVEN + 3] == (byte) 4 && config[SEVEN + FOUR + 3] == (byte) 4 && config[SEVEN + 2 * FOUR + 3] == (byte) 4 && config[SEVEN + 3 * FOUR + 3] == (byte) 4) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        List<Step> getAllNewMovementUpStep() {
            List<Step> newSteps = new ArrayList<>();
            for (byte position = SEVEN; position < MAX; position++) {
                byte amp = config[position];
                if (amp == (byte) 0) {
                    continue;
                } else if (position < SEVEN + FOUR) {
                    if ((byte) (position - (SEVEN - 1)) == amp && config[position + FOUR] == amp
                            && config[position + FOUR + FOUR] == amp && config[position + FOUR + FOUR + FOUR] == amp) {
                        // already done column
                        continue;
                    } else {
                        calculateLeftLimit(position, newSteps, 0);
                        calculateRightLimit(position, newSteps, 0);
                    }
                } else if (position < SEVEN + FOUR + FOUR) {
                    // move ha felette minden ures
                    if ((byte) (position - (SEVEN + FOUR - 1)) == amp && config[position + FOUR] == amp
                            && config[position + FOUR + FOUR] == amp) {
                        //DONE
                    } else if (canMoveUp(position)) {
                        // ki tud lepni
                        calculateLeftLimit(position, newSteps, 1);
                        calculateRightLimit(position, newSteps, 1);
                    }
                } // 3. level
                else if (position < SEVEN + FOUR + FOUR + FOUR) {
                    // move ha felette minden ures
                    if ((byte) (position - (SEVEN + FOUR + FOUR - 1)) == amp && config[position + FOUR] == amp) {
                        //DONE
                    } else if (canMoveUp(position)) {
                        // ki tud lepni
                        calculateLeftLimit(position, newSteps, 2);
                        calculateRightLimit(position, newSteps, 2);
                    }
                } // 4. level
                else if (position < SEVEN + FOUR + FOUR + FOUR + FOUR) {
                    // move ha felette minden ures
                    if ((byte) (position - (SEVEN + FOUR + FOUR + FOUR - 1)) == amp) {
                        //DONE
                    } else if (canMoveUp(position)) {
                        // ki tud lepni
                        calculateLeftLimit(position, newSteps, 3);
                        calculateRightLimit(position, newSteps, 3);
                    }
                }

            }
            return newSteps;
        }

        private boolean canMoveUp(byte position) {
            for (int positionAct = position - FOUR; SEVEN - 1 < positionAct; positionAct = positionAct - FOUR) {
                if (config[position - FOUR] != 0) {
                    return false;
                }
            }
            return true;
        }

        private byte calculateRightLimit(byte position, List<Step> newSteps, int level) {
            short rightHalfWay = (short) (position - (level * FOUR) - 5);
            for (short pos = rightHalfWay; pos < SEVEN; pos++) {
                if (config[pos] != (byte) 0) {
                    break;
                }
                int costNew = cost + getCostDistanceFirstLevel(config[position], rightHalfWay, pos, (short) (2 + level));
                byte[] newConf = getNewConfig(position, pos);
                newSteps.add(new Step(newConf, costNew));
            }
            return (byte) SEVEN - 1;
        }

        private void calculateLeftLimit(byte position, List<Step> newSteps, int level) {
            short leftHalfWay = (short) (position - (level * FOUR) - 6);
            for (short pos = leftHalfWay; 0 <= pos; pos--) {
                if (config[pos] != (byte) 0) {
                    break;
                }
                int costNew = cost + getCostDistanceFirstLevel(config[position], leftHalfWay, pos, (short) (2 + level));
                byte[] newConf = getNewConfig(position, pos);
                newSteps.add(new Step(newConf, costNew));
            }
        }

        private byte[] getNewConfig(byte positionFrom, short positionTo) {
            byte[] copiedConfig = Arrays.copyOf(config, config.length);
            copiedConfig[positionTo] = copiedConfig[positionFrom];
            copiedConfig[positionFrom] = 0;
            return copiedConfig;
        }

        private int getCostDistanceFirstLevel(byte amp, short positionFrom, short positionEnd, short defaultJump) {
            int jump = Math.abs(positionEnd - positionFrom) * 2 + defaultJump;
            if (positionEnd == 6 || positionEnd == 0) {
                jump--;
            }
            return getCostFromDistance(amp,jump);
        }

        public Collection<Step> getAllNewMovementDownStep() {
            List<Step> newSteps = new ArrayList<>();
            for (int i = 0; i < SEVEN; i++) {
                byte amp = config[i];
                if (amp == (byte) 0) {
                    // way is still Free
                    continue;
                } else if (isPlaceAvaliable(amp)) {

                    int horizontalStep = ifWayIsFree(i, amp);
                    if (horizontalStep == -1) {
                        continue;
                    } else {
                        // meg kell nezni milyen helyre kell rakni
                        int positionNew = getStepsNewPosition(amp, config);
                        int diffMax = MAX - positionNew;
                        int verticalStep = 2;
                        if ((3 * FOUR) < diffMax) {
                            verticalStep = 2;
                        } else if ((2 * FOUR) < diffMax) {
                            verticalStep = 3;
                        } else if (FOUR < diffMax) {
                            verticalStep = 4;
                        } else {
                            verticalStep = 5;
                        }
                        int newCost = cost + getCostFromDistance(amp, horizontalStep + verticalStep);
                        byte[] newConf = getNewConfig((byte) i, (short) positionNew);
                        newSteps.add(new Step(newConf, newCost));
                    }


                }
            }
            return newSteps;
        }

        private int getCostFromDistance(byte amp, int jump) {
            switch (amp) {
                // A : 1
                case (byte) 1:
                    return jump;
                case (byte) 2:
                    return jump * 10;
                case (byte) 3:
                    return jump * 100;
                case (byte) 4:
                    return jump * 1000;
                default:
            }
            throw new RuntimeException("Nincs");
        }

        private int getStepsNewPosition(byte amp, byte[] config) {
            for (int row = MAX - 5 + amp; SEVEN - 1 < row; row = row - FOUR) {
                // We suppose there is no composition like this
                // #A#
                // #.#
                if (config[row] == amp) {
                    continue;
                } else if (config[row] == (byte) 0) {
                    return row;
                }
            }
            throw new RuntimeException("Should not be here!");
        }

        private int ifWayIsFree(int posRow, byte amp) {
            int destination = SEVEN - 1 + amp;
            int horizontalStepCounter = 0;
            if ((destination - posRow) <= 5) {
                // is on the right
                for (int posRowStep = posRow; (destination - 5) <= posRowStep; posRowStep--) {
                    if (posRowStep != posRow) {
                        if (config[posRowStep] != (byte) 0) {
                            return -1;
                        } else if (posRowStep == 5) {
                            horizontalStepCounter++;
                        } else {
                            horizontalStepCounter += 2;
                        }
                    }
                }
                return horizontalStepCounter;
            } else {
                // on the left
                for (int posRowStep = posRow; posRowStep <= (destination - 6); posRowStep++) {
                    if (posRowStep != posRow) {
                        if (config[posRowStep] != (byte) 0) {
                            return -1;
                        } else if (posRowStep == 1) {
                            horizontalStepCounter++;
                        } else {
                            horizontalStepCounter += 2;
                        }
                    }

                }
                return horizontalStepCounter;
            }
        }

        private boolean isPlaceAvaliable(byte amp) {
            for (int row = MAX - 5 + amp; SEVEN - 1 < row; row = row - FOUR) {
                // We suppose there is no composition like this
                // #A#
                // #.#
                if (config[row] == amp) {
                    continue;
                } else if (config[row] == (byte) 0) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }
}