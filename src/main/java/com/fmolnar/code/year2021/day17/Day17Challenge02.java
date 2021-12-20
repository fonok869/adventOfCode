package com.fmolnar.code.year2021.day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day17Challenge02 {

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

        List<Integer> potentielX = new ArrayList<>();
        for (int i = 2; i < xTargetMax + 1; i++) {
            int sum = i * (i + 1) / 2;
            if (xTargetMin <= sum && sum <= xTargetMax) {
                potentielX.add(i);
            }
        }

        // get All X
        int minMaxLocal = potentielX.stream().mapToInt(s -> s).max().getAsInt();
        for (int xPot = minMaxLocal + 1; xPot <= xTargetMax; xPot++) {
            for (int levonas = 1; levonas < xPot; levonas++) {
                int sum = (xPot * (xPot + 1) / 2) - ((xPot - levonas) * (xPot - levonas + 1) / 2);
                if (xTargetMin <= sum && sum <= xTargetMax) {
                    potentielX.add(xPot);
                    break;
                }
            }
        }


        // get All Y
        List<Integer> potentielY = new ArrayList<>();
        for (int j = yTargetMaxAbs; 0 <= j; j--) {
            if (yTargetMinAbs <= j && j <= yTargetMaxAbs) {
                potentielY.add(j);
            }
        }

        int yMaxPossibility = potentielY.stream().mapToInt(s -> s).max().getAsInt();

        List<Integer> yToChecks = new ArrayList<>();
        for (int i = yMaxPossibility; yTargetMax <= i; i--) {
            yToChecks.add(i);
        }

        long goodTrajectories = 0L;
        for (int xKezdet : potentielX) {
            for (int yKezdet : yToChecks) {
                if (isInTheArea(xKezdet, yKezdet)) {
                    goodTrajectories++;
                }
            }
        }

        System.out.println("All Good trajectories: " + goodTrajectories);


    }

    private boolean isInTheArea(int xKezdet, int yKezdet) {
        int xTotal = 0;
        int yTotal = 0;
        for (int i = 1; i < 500; i++) {
            if (i == 1) {
                xTotal = xKezdet;
                yTotal = yKezdet;
            } else {
                if (0 < xKezdet) {
                    xKezdet = xKezdet - 1;
                }
                xTotal = xTotal + xKezdet;

                yKezdet = yKezdet - 1;
                yTotal = yTotal + yKezdet;
            }

            if (xTargetMin <= xTotal && xTotal <= xTargetMax && yTargetMax <= yTotal && yTotal <= yTargetMin) {
                return true;
            } else if (xTargetMax < xTotal || yTotal < yTargetMax) {
                return false;
            }
        }
        System.out.println("Error!!!!!!!!!!!");
        return false;
    }
}
