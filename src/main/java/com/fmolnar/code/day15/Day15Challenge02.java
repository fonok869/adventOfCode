package com.fmolnar.code.day15;

import java.util.HashMap;
import java.util.Map;

public class Day15Challenge02 {

    public Day15Challenge02() {
        //15,5,1,4,7,0
        firstLetter.put(15L,1L);
        firstLetter.put(5L,2L);
        firstLetter.put(1L,3L);
        firstLetter.put(4L,4L);
        firstLetter.put(7L,5L);
        firstLetter.put(0L,6L);
        hosszusag = firstLetter.size();
    }

    int hosszusag;
    Map<Long, Long> firstLetter = new HashMap();

    public void calculate() {
        long lastNumber = 0L;
        for (int i = hosszusag + 1; i < 30000000; i++) {
            lastNumber = checkConditions(lastNumber, i);
        }
        System.out.println(lastNumber);
    }

    private long checkConditions(long lastNumber, long index) {
        if (firstNumber(lastNumber, index)) {
            return checkIfAlreadyInList(0, index, lastNumber);
        } else {
            return checkIfAlreadyInList(lastNumber, index, -1);
        }
    }

    private long checkIfAlreadyInList(long number, long index, long lastNumber) {
        long diff = 0L;
        if (lastNumber != -1 && !firstLetter.containsKey(lastNumber)) {
            firstLetter.put(lastNumber, index);
            return diff;
        } else if (firstLetter.containsKey(number)) {
            long index1 = firstLetter.get(number);
            diff = index - index1;
        }
        firstLetter.put(number, index);
        return diff;
    }

    private boolean firstNumber(long lastNumber, long actualIndex) {
        if (!firstLetter.containsKey(lastNumber) || actualIndex == (hosszusag + 1)) {
            return true;
        }
        return false;
    }
}
