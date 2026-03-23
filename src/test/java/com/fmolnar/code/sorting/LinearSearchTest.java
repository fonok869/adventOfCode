package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinearSearchTest {

    @Test
    void shouldPickUpValue() {
        assertThat(new LinearSearch().getPosition(new int[]{1, 2, 3, 10, 200, 5}, 5)).isEqualTo(5);
    }
    
}