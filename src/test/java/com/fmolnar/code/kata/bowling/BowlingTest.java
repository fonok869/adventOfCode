package com.fmolnar.code.kata.bowling;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingTest {

    @ParameterizedTest
    @MethodSource("pinsProperties")
    void shouldNotHaveAnyPoint(String pinSign, int value) {
        Assertions.assertThat(new Roll(pinSign).result()).isEqualTo(value);
    }

    @ParameterizedTest
    @MethodSource("frames")
    void shouldMakeFrameForOneRound(Roll roll1, Roll roll2, int expected) {
        Assertions.assertThat(new BowlingFrame(roll1, roll2).points()).isEqualTo(expected);
    }

    @Test
    void shouldStrike() {
        assertThat(new BowlingFrame(new Roll("X")).points()).isEqualTo(10);
    }

    @Test
    void shouldSpare() {
        assertThat(new BowlingFrame(new Roll("5"), new Roll("/")).points()).isEqualTo(10);
    }

    @Test
    void shouldMakeWholeGame() {
        BowlingFrame frame = new BowlingFrame(new Roll("5"), new Roll("4"));
        List<BowlingFrame> bowlingGame = List.of(frame, frame, frame, frame, frame, frame, frame, frame, frame, frame);
        Assertions.assertThat(new BowlingGame(bowlingGame).result()).isEqualTo(90);
    }

    @Test
    void shouldMakeEmptyGame() {
        BowlingFrame frame = new BowlingFrame(new Roll("0"), new Roll("0"));
        List<BowlingFrame> bowlingGame = List.of(frame, frame, frame, frame, frame, frame, frame, frame, frame, frame);
        assertThat(new BowlingGame(bowlingGame).result()).isEqualTo(0);
    }

    @Test
    void shouldMakeStrikesGame() {
        BowlingFrame frame = new BowlingFrame(new Roll("5"), new Roll("/"));
        List<BowlingFrame> bowlingGame = List.of(frame, frame, frame, frame, frame, frame, frame, frame, frame, frame, new BowlingFrame(new Roll("5")));
        assertThat(new BowlingGame(bowlingGame).result()).isEqualTo(150);
    }

    @Test
    void shouldMakeStrikesGames() {
        BowlingFrame frame = new BowlingFrame(new Roll("5"), new Roll("/"));
        List<BowlingFrame> bowlingGame = List.of(frame, frame, frame, frame, frame, frame, frame, frame, new BowlingFrame(new Roll("9"), new Roll("-")), new BowlingFrame(new Roll("4"), new Roll("4")));
        assertThat(new BowlingGame(bowlingGame).result()).isEqualTo(141);
    }


    private static Stream<Arguments> frames() {
        return Stream.of(
                Arguments.of(new Roll("1"), new Roll("2"), 3),
                Arguments.of(new Roll("2"), new Roll("3"), 5),
                Arguments.of(new Roll("5"), new Roll("-"), 5)
        );
    }

    private static Stream<Arguments> pinsProperties() {
        return Stream.of(
                Arguments.of("0", 0),
                Arguments.of("1", 1),
                Arguments.of("2", 2),
                Arguments.of("3", 3),
                Arguments.of("4", 4),
                Arguments.of("5", 5),
                Arguments.of("6", 6),
                Arguments.of("7", 7),
                Arguments.of("8", 8),
                Arguments.of("9", 9),
                Arguments.of("-", 0),
                Arguments.of("X", 10)
        );
    }
}
