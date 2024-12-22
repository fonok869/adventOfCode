package com.fmolnar.code.year2024.day22;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day22_2024Test {

    @Test
    void mix() {
        Day22_2024 day22_2024 = new Day22_2024();

        assertThat(day22_2024.mix(42, 15)).isEqualTo(37l);
    }

    @Test
    void prune() {
        Day22_2024 day22_2024 = new Day22_2024();

        assertThat(day22_2024.prune(100000000)).isEqualTo(16113920l);

    }
}