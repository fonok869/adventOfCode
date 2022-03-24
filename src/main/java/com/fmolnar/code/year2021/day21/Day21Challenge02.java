package com.fmolnar.code.year2021.day21;

import com.fmolnar.code.basic.AbstractLspBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day21Challenge02 {

//    int player1Position = 4;
//    int player2Position = 8;

    private final static int player1Position = 9;
    private final static int player2Position = 10;

    private final Map<Integer, Integer> diceValues = new HashMap<Integer, Integer>() {{
        put(3, 1);
        put(4, 3);
        put(5, 6);
        put(6, 7);
        put(7, 6);
        put(8, 3);
        put(9, 1);
    }};
    private static final int maxPoint = 21;
    Map<StepIteration, Long> steps = new HashMap<>();

    public void calculate() throws IOException {

        steps = Collections.singletonMap(new StepIteration(player1Position, 0, player2Position, 0), 1L);
        for (int i = 1; ; i++) {
            Map<StepIteration, Long> alreadySteps = new HashMap(steps);
            Map<StepIteration, Long> newSteps = new HashMap();
            boolean isEntered = false;
            for (Map.Entry<StepIteration, Long> stepIter : alreadySteps.entrySet()) {
                StepIteration stepIterationActual = stepIter.getKey();
                Long stepOccurence = stepIter.getValue();
                if (stepIterationActual.isFinished()) {
                    newSteps.merge(stepIterationActual, stepOccurence, (l1,l2) -> l1+l2);
                    continue;
                }
                isEntered = true;
                for (Map.Entry<Integer, Integer> diceValue : diceValues.entrySet()) {
                    Integer diceValueActual = diceValue.getKey();
                    Integer diceOccurence = diceValue.getValue();
                    Long multiplication = stepOccurence * (Long.valueOf(diceOccurence));
                    if (i % 2 == 1) {
                        long s1 = (stepIterationActual.p1Position + diceValueActual - 1) % 10 + 1;
                        long sTotal = s1 + stepIterationActual.p1TotalScore;
                        newSteps.merge(new StepIteration(s1, sTotal, stepIterationActual.p2Position, stepIterationActual.p2TotalScore), multiplication, (l1,l2) -> l1+l2);
                    } else {
                        long s1 = (stepIterationActual.p2Position + diceValueActual - 1) % 10 + 1;
                        long sTotal = s1 + stepIterationActual.p2TotalScore;
                        newSteps.merge(new StepIteration(stepIterationActual.p1Position, stepIterationActual.p1TotalScore, s1, sTotal), multiplication, (l1,l2) -> l1+l2);
                    }
                }
            }

            if (!isEntered) {
                break;
            }
            steps = new HashMap(newSteps);
        }

        Long firstUniverse = steps.entrySet().stream().filter(s -> s.getKey().p1TotalScore >= maxPoint).mapToLong(s -> s.getValue().longValue()).sum();
        Long secondUniverse = steps.entrySet().stream().filter(s -> s.getKey().p2TotalScore >= maxPoint).mapToLong(s -> s.getValue().longValue()).sum();
        System.out.println("Day21Challenge02: " + (firstUniverse > secondUniverse ? firstUniverse : secondUniverse));
    }

//    public static record StepIteration(long p1Position, long p1TotalScore, long p2Position, long p2TotalScore) {
//
//        public boolean isFinished() {
//            return maxPoint <= p1TotalScore() || maxPoint <= p2TotalScore();
//        }
//
//    }

    public static class StepIteration extends AbstractLspBean {

        public long p1Position;
        public long p1TotalScore;
        public long p2Position;
        public long p2TotalScore;

        public long getP1Position() {
            return p1Position;
        }

        public long getP1TotalScore() {
            return p1TotalScore;
        }

        public long getP2Position() {
            return p2Position;
        }

        public long getP2TotalScore() {
            return p2TotalScore;
        }

        public StepIteration(long p1Position, long p1TotalScore, long p2Position, long p2TotalScore) {
            this.p1Position = p1Position;
            this.p1TotalScore = p1TotalScore;
            this.p2Position = p2Position;
            this.p2TotalScore = p2TotalScore;
        }


        @Override
        public Object[] getFunctionalPropertiesForObjectEquality() {
            return new Object[]{p1Position, p1TotalScore, p2Position, p2TotalScore};
        }

        public boolean isFinished() {
            return maxPoint <= p1TotalScore || maxPoint <= p2TotalScore;
        }

    }
}
