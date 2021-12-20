package com.fmolnar.code.year2021.day15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15ChallengeTry01 {

    private List<String> lines = new ArrayList<>();
    List<List<Integer>> matrix = new ArrayList<>();
    Set<Integer> hossz = new HashSet<>();
    int xMax = 0;
    int yMax = 0;
    Set<String> permutaciok = new HashSet<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day15/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    List<Integer> riskLevel = new ArrayList<>();
                    for (int i = 0; i < line.length(); i++) {
                        if (i == (line.length() - 1)) {
                            riskLevel.add(Integer.valueOf(line.substring(i)));
                        } else {
                            riskLevel.add(Integer.valueOf(line.substring(i, i + 1)));
                        }
                    }
                    matrix.add(riskLevel);
                }
            }
        }
        xMax = matrix.get(0).size();
        yMax = matrix.size();

        looping();
    }

    private void looping() {

        int x = 0;
        int y = 0;
        int sum = 0;

        int xLepes = 10;
        int xLepes2Sum = 20;
        if ((x + 1) < xMax) {
            xLepes = matrix.get(y).get(x + 1);
            xLepes2Sum = xLepes + oneStepAfterMin(x + 1, y);
        }
        int yLepes = 10;
        int yLepes2Sum = 20;
        if ((y + 1) < yMax) {
            yLepes = matrix.get(y + 1).get(x);
            yLepes2Sum = yLepes + oneStepAfterMin(1, y + 1);
        }



        if (xLepes2Sum < yLepes2Sum) {
            sum += xLepes;
            calculateNextStep(sum, x + 1, y);
        } else {
            sum += yLepes;
            calculateNextStep(sum, x, y + 1);
        }


    }

    private void calculateNextStep(int sum, int x, int y) {

        if(x<xMax && y<yMax){
            System.out.println("x: " + x + " y: " +y + " value: " + matrix.get(y).get(x));
        }
        int xLepes = 10;
        int xLepes2Sum = 20;
        int xLepes3Sum = 30;
        if ((x + 1) < xMax && y<yMax) {
            xLepes = matrix.get(y).get(x + 1);
            xLepes2Sum = xLepes + oneStepAfterMin(x + 1, y);
            int xOneStep = oneStepAfterXDirection(x+1, y);
            if(xOneStep == x+1){
                xLepes3Sum = xLepes2Sum + oneStepAfterMin(x+1, y+1);
            } else {
                xLepes3Sum = xLepes2Sum + oneStepAfterMin(x+2, y);
            }
        }
        int yLepes = 10;
        int yLepes2Sum = 20;
        int yLepes3Sum = 30;
        if ((y + 1) < yMax && x<xMax) {
            yLepes = matrix.get(y + 1).get(x);
            yLepes2Sum = yLepes + oneStepAfterMin(x, y + 1);
            int xOneStep = oneStepAfterXDirection(x, y+1);
            if(xOneStep == x){
                yLepes3Sum = yLepes2Sum + oneStepAfterMin(x, y+2);
            } else {
                yLepes3Sum = yLepes2Sum + oneStepAfterMin(x+1, y+1);
            }
        }

        if (xLepes == yLepes && xLepes == 10) {
            System.out.println("Sum: " + sum);
            return;
        }
        // xLepes
        if (xLepes3Sum < yLepes3Sum) {
            sum += xLepes;
            calculateNextStep(sum, x + 1, y);
        } else {
            sum += yLepes;
            calculateNextStep(sum, x, y + 1);
        }
    }

    private int oneStepAfterMin(int x, int y) {
        int xLepes = 10;
        if ((x + 1) < xMax) {
            xLepes = matrix.get(y).get(x + 1);
        }
        int yLepes = 10;
        if ((y + 1) < yMax) {
            yLepes = matrix.get(y + 1).get(x);
        }

        if (xLepes <= yLepes) {
            return xLepes;
        } else {
            return yLepes;
        }
    }

    private int oneStepAfterXDirection(int x, int y) {
        int xLepes = 10;
        if ((x + 1) < xMax) {
            xLepes = matrix.get(y).get(x + 1);
        }
        int yLepes = 10;
        if ((y + 1) < yMax) {
            yLepes = matrix.get(y + 1).get(x);
        }

        if (xLepes <= yLepes) {
            return x+1;
        } else {
            return x;
        }
    }


}
