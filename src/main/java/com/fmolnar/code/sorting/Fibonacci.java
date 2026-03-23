package com.fmolnar.code.sorting;

public class Fibonacci {

    int recursive(int n) { // O(n) space call stack
        if (n <= 1) {
            return n;
        }
        return recursive(n - 1) + recursive(n - 2); // O(2^n) time
    }

    int dynamicProgramiqueFibonacci(int n) {
        int fib[] = new int[n + 1];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i <= n; i++) { // O(n) time
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n]; // O(n) space
    }
}
