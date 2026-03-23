package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FirstNonRepeatingCharacterTest {

    @Test
    void shouldWrite() {
        assertThat(new FirstNonRepeatingCharacter().notRepeating("abcdefghijklmonabcdef")).isEqualTo('g');
    }
}