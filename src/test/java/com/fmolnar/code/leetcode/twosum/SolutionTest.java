package com.fmolnar.code.leetcode.twosum;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    void shouldMakeSimpleQuestion() {
        Solution solution = new Solution();
        assertThat(solution.twoSum(new int[]{2, 7, 11, 15}, 9)).isEqualTo(new int[]{0, 1});
    }
}