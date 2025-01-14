package com.fmolnar.code.kata.refactoring.trivia;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TriviaTest {

    @Test
    void shouldNotHaveLessThan2Players() {
        Game game = new Game();
        game.add("Bob");
        assertThat(game.isPlayable()).isFalse();
    }

    @Test
    void shouldHaveAtLeast2Players() {
        Game game = new Game();
        game.add("Bob");
        game.add("John");
        assertThat(game.isPlayable()).isTrue();
    }
}
