package com.fmolnar.code.leetcode.removeduplicate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    void shouldSendBack() {
        Solution s = new Solution();
        assertThat(s.removeDuplicates("abbaca")).isEqualTo("ca");
    }

    @Test
    void shouldSendBackSecondMethod() {
        Solution s = new Solution();
        assertThat(s.removeDuplicates2("abbaca")).isEqualTo("ca");
    }
}