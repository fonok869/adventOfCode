package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SumOfAllSubsetsSumTest {

    @Test
    void shouldHaveAllSubsets() {
        assertThat(new SumOfAllSubsetsSum().subSets(new int[]{1, 2, 3})).isEqualTo(24);
    }

}