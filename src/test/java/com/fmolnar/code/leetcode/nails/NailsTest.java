package com.fmolnar.code.leetcode.nails;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NailsTest {

    @Test
    void shouldTestNails() {
        Nails n = new Nails();
        assertThat(n.maxEqualNails(new int[]{1, 2, 3, 4}, 2)).isEqualTo(3);
    }
}