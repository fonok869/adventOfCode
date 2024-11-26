package com.fmolnar.code.kata.bowling;

import java.util.Arrays;

public record BowlingFrame(Roll... rolls) {

    public int points() {
        if (isStrike()) {
            return 10;
        }
        return Arrays.stream(rolls).mapToInt(Roll::result).sum();
    }

    public boolean isStrike() {
        return Arrays.stream(rolls).anyMatch(roll -> "/".equals(roll.pin()));
    }
}
