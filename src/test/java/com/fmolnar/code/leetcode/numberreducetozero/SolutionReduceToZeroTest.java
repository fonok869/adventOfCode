package com.fmolnar.code.leetcode.numberreducetozero;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionReduceToZeroTest {

    @Test
    void shouldReduceToZero() {
        Solution s = new Solution();
        assertThat(s.numberOfSteps(14)).isEqualTo(6);
    }
}