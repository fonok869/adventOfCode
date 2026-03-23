package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SumOfAllSubsetsTest {

    @Test
    void shouldHaveAllSubsets() {
        assertThat(new SumOfAllSubsets().subSets(new int[]{1, 2, 3})).isEqualTo(24);
    }
}