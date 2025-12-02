package com.fmolnar.code.year2025.day02;

import java.util.HashSet;
import java.util.Set;

public record IdRanges2(Long init, Long end) {
    Set<Long> getPolyandromNumbers() {
        Set<Long> polyandromNumbers = new HashSet<>();

        int beginDigits = String.valueOf(init).length();
        int endDigits = String.valueOf(end).length();

        outerloop:
        for (long i = init; i <= end; i++) {
            String actualNumber = String.valueOf(i);
            digitloop:
            for (int digitActual = 1; digitActual <= endDigits / 2 + 1; digitActual++) {
                if (actualNumber.length() % digitActual != 0) {
                    //nem lehet nem jon ki
                    continue;
                }
                String pattern = actualNumber.substring(0, digitActual);
                int multiplication = actualNumber.length() / digitActual;
                patternloop:
                for (int index = 0; index < multiplication; index++) {
                    if (!pattern.equals(actualNumber.substring(index * digitActual, (index + 1) * digitActual))) {
                        continue digitloop;
                    }
                    if (pattern.equals(actualNumber)) {
                        continue outerloop;
                    }
                }
                polyandromNumbers.add(Long.valueOf(actualNumber));
                continue outerloop;
            }
        }


        System.out.println(this);
        polyandromNumbers.forEach(System.out::println);

        return polyandromNumbers;
    }
}
