package com.fmolnar.code.day15;

import java.util.ArrayList;
import java.util.List;

public class Day15Challenge01 {

    public Day15Challenge01() {
        //15,5,1,4,7,0
        szamok.add(15L);
        szamok.add(5L);
        szamok.add(1L);
        szamok.add(4L);
        szamok.add(7L);
        szamok.add(0L);
    }

    List<Long> szamok = new ArrayList<>();
    int maxNumber = 2020;

    public void calculate2() {
        long lastNumber = -1;
        for (int i = szamok.size() - 1; i < maxNumber; i++) {
            lastNumber = szamok.get(i);
            checkConditions(lastNumber);
        }
        System.out.println(szamok.get(maxNumber - 1));
    }

    private void checkConditions(long lastNumber) {
        if (firstNumber(lastNumber)) {
            szamok.add(0L);
        } else {
            long newNumber = calculDiff(lastNumber);
            szamok.add(newNumber);
        }
    }

    private long calculDiff(long lastNumber) {
        int index1 = szamok.size();
        int index2 = -1;
        for (int jj = szamok.size() - 2; 0 <= jj; jj--) {
            if (szamok.get(jj) == lastNumber) {
                index2 = jj + 1;
                break;
            }
        }
        return index1 - index2;
    }

    private boolean firstNumber(long lastNumber) {
        List<Long> ujSzamok = new ArrayList<>();
        for (int ii = 0; ii < szamok.size() - 1; ii++) {
            ujSzamok.add(szamok.get(ii));
        }
        return !ujSzamok.contains(lastNumber);
    }
}
