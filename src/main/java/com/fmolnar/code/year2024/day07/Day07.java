package com.fmolnar.code.year2024.day07;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day07 {
    public void calculteDay07() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day07/input.txt");

        List<Unity> unities = new ArrayList<>();

        for (String line : lines) {
            int doubleDots = line.indexOf(':');
            Long number = Long.valueOf(line.substring(0, doubleDots));
            unities.add(new Unity(number, Arrays.asList(line.substring(doubleDots + 2).split(" "))));
        }

        long value = 0;
        for (Unity actualUnity : unities) {
            if (canBe(actualUnity.number(), actualUnity.numbersToCheck())) {
                value += actualUnity.number();
            }
        }

        System.out.println(value);
    }

    private boolean canBe(Long actualValue, List<String> numbersToCheck) {
        List<Long> szamok = numbersToCheck.stream().filter(s -> !"".equals(s)).mapToLong(s -> Long.parseLong(s)).boxed().collect(Collectors.toList());

        List<Boolean> success = new ArrayList<>();
        int index = 2;

        // Addition
        callRecursive(szamok.get(0) + szamok.get(1), szamok, index, actualValue, success);

        // Multiplication
        callRecursive(szamok.get(0) * szamok.get(1), szamok, index, actualValue, success);

        // Merging
        callRecursive(Long.valueOf(String.valueOf(szamok.get(0)) + String.valueOf(szamok.get(1))), szamok, index, actualValue, success);

        if (success.contains(true)) {
            return true;
        }
        return false;
    }

    private void callRecursive(long actualValue, List<Long> szamok, int index, Long celszam, List<Boolean> success) {
        if (szamok.size() <= index) {
            if (actualValue == celszam) {
                success.add(true);
            }
        } else {
            callRecursive(actualValue + szamok.get(index), szamok, index + 1, celszam, success);
            callRecursive(actualValue * szamok.get(index), szamok, index + 1, celszam, success);
            callRecursive(Long.valueOf(String.valueOf(actualValue) + String.valueOf(szamok.get(index))), szamok, index + 1, celszam, success);
        }
    }
}

record Unity(long number, List<String> numbersToCheck) {
};
