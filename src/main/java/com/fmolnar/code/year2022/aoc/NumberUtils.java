package com.fmolnar.code.year2022.aoc;

public class NumberUtils {

    public static String printInBase(long number, long base) {
        long actualNumber = number;
        String transformed = "";
        for (long helyiertek = 0; helyiertek < 20; helyiertek++) {
            long maradek = actualNumber % base;
            actualNumber -= maradek;
            actualNumber = actualNumber / base;
            transformed = maradek + transformed;
            if (actualNumber < 1) {
                break;
            }
        }
        return transformed;
    }

    public static long printValueDecimalFrom(String input, long base) {
        long multiplication = 1l;
        long sum = 0l;
        for (int i = input.length() - 1; 0 <= i; i--) {
            long value = Long.valueOf(input.substring(i, i + 1));
            sum += value * multiplication;
            multiplication *= base;
        }
        return sum;
    }
}
