package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaximumSearchTest {

    @Test
    void shouldHaveMaxValue() {
        assertThat(new MaximumSearch().maximum(new int[]{1, 2, 555, 300, 2333333, 2})).isEqualTo(2333333);
    }
}