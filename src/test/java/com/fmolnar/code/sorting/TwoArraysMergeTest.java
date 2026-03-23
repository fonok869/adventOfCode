package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TwoArraysMergeTest {

    @Test
    void shouldHaveTwoMerges() {
        assertThat(new TwoArraysMerge().merging(new int[]{1, 3, 5}, new int[]{2, 4, 6})).isEqualTo(new int[]{1, 2, 3, 4, 5, 6});
    }
}