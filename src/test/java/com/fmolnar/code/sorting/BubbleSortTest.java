package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BubbleSortTest {

    @Test
    void shouldSortWithBubble() {
        assertThat(new BubbleSort().bubbleSort(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1})).isEqualTo(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
    }
}