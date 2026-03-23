package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BinarySearchTest {

    @Test
    void shouldMakeBinarySearch() {
        assertThat(new BinarySearch().binarySearch(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 10))
                .isEqualTo(9);
    }
}