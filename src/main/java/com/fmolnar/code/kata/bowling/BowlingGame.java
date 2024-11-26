package com.fmolnar.code.kata.bowling;

import java.util.List;

public record BowlingGame(
        List<BowlingFrame> bowlingGame) {


    public static final int MAX_NUMBER_OF_THROWN = 10;

    public int result() {
        boolean wasStrike = false;
        int sum = 0;
        for (int i = 0; i < bowlingGame.size(); i++) {
            BowlingFrame bowlingFrame = bowlingGame.get(i);
            if (wasStrike) {
                sum += bowlingFrame.rolls()[0].result();
                wasStrike = false;
            }
            if (bowlingFrame.isStrike()) {
                wasStrike = true;
            }
            if (i < MAX_NUMBER_OF_THROWN) {
                sum += bowlingFrame.points();
            }
        }
        return sum;
    }
}
