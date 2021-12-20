package com.fmolnar.code.year2021.day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day17Challenge01 {

    private List<String> lines = new ArrayList<>();

    //target area: x=207..263, y=-115..-63

    int xTargetMin = 207;
    int xTargetMax = 263;
    int yTargetMin = -63;
    int yTargetMax = -115;

//    int xTargetMin = 20;
//    int xTargetMax = 30;
//    int yTargetMin = -5;
//    int yTargetMax = -10;
    int yTargetMinAbs = Math.abs(yTargetMin);
    int yTargetMaxAbs = Math.abs(yTargetMax);

    public void calculate() throws IOException {

        int maxGlobal = 0;
        List<Integer> potentielY = new ArrayList<>();
        for (int j = yTargetMaxAbs; 0 <= j; j--) {
            if (yTargetMinAbs <= j && j <= yTargetMaxAbs) {
                potentielY.add(j);
                int sumMax = j * (j - 1) / 2;
                if (sumMax > maxGlobal) {
                    maxGlobal = sumMax;
                }
            }
        }
        System.out.println("Max: " + maxGlobal);

    }
}
