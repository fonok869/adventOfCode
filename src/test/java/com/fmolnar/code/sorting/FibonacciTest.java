package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FibonacciTest {

    @Test
    void shouldHaveRecursive() {
        assertThat(new Fibonacci().recursive(2)).isEqualTo(1);
    }

    @Test
    void shouldHaveRecursive2() {
        assertThat(new Fibonacci().recursive(4)).isEqualTo(3);
    }

    @Test
    void shouldHaveDPFibonacci() {
        assertThat(new Fibonacci().dynamicProgramiqueFibonacci(2)).isEqualTo(1);
    }

    @Test
    void shouldHaveDPFibonacci2() {
        assertThat(new Fibonacci().dynamicProgramiqueFibonacci(4)).isEqualTo(3);
    }
}