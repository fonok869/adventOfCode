package com.fmolnar.code.year2025.day02;

import java.util.ArrayList;
import java.util.List;

public record IdRanges(Long init, Long end) {

    List<Long> getPolyandromNumbers() {
        List<Long> polyandromNumbers = new ArrayList<>();

        int beginDigits = String.valueOf(init).length();
        int endDigits = String.valueOf(end).length();


        if (beginDigits == endDigits) {
            long stepInit = Long.valueOf(String.valueOf(init).substring(0, (beginDigits / 2)));
            long stepMax = Long.valueOf(String.valueOf(end).substring(0, (beginDigits / 2)));

            for (long i = stepInit; i <= end; i++) {
                Long actualVAlueToCheck = Long.valueOf(String.valueOf(i) + String.valueOf(i));
                if (actualVAlueToCheck < init) {
                    continue;
                } else if (actualVAlueToCheck <= end) {
                    polyandromNumbers.add(actualVAlueToCheck);
                } else {
                    break;
                }
            }
        } else {
            for (long i = init; i <= end; i++) {
                String actualNumber = String.valueOf(i);
                if (actualNumber.length() % 2 == 0) {
                    if (actualNumber.substring(0, actualNumber.length() / 2).equals(actualNumber.substring(actualNumber.length() / 2))) {
                        polyandromNumbers.add(i);
                    }
                }
            }
        }

        
        return polyandromNumbers;
    }

}
