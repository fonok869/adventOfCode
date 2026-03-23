package com.fmolnar.code.leetcode.matrixmax;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatrixMaxTest {

    @Test
    void shouldCalculate() {
        Solution sol = new Solution();
        assertThat(sol.largestSubmatrix(new int[][]{{1, 0, 1, 0, 1}})).isEqualTo(3);
    }
}