package com.fmolnar.code.year2020.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day11Challenge01 {

    public Day11Challenge01() {
    }

    List<String> lines = new ArrayList<>();

    char[][] lastMatrix;
    char[][] newMatrix;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day11/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }
        transformToMatrix();
        int counter = 0;
        while (!chekIfGood()) {
            if(counter!=0){
                copyArray();
            }
            checkStep();
            counter++;
        }

        System.out.println("Occupied seat: " + calculateOccupiedSeat());
    }

    private int calculateOccupiedSeat() {
        int occupied = 0;
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                if (Objects.equals(newMatrix[i][j], '#')) {
                    occupied = occupied + 1;
                }
            }
        }
        return occupied;
    }

    private boolean chekIfGood() {
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                if (newMatrix[i][j] != lastMatrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkStep() {
        for (int i = 0; i < lastMatrix.length; i++) {
            for (int j = 0; j < lastMatrix[0].length; j++) {

                if (Objects.equals(lastMatrix[i][j], '.')) {
                    newMatrix[i][j] = '.';
                } else if (ifEmptyAndToSeat(i, j)) {
                    newMatrix[i][j] = '#';
                } else if (occupiedAndRelease(i, j)) {
                    newMatrix[i][j] = 'L';
                } else {
                    newMatrix[i][j] = lastMatrix[i][j];
                }
            }
        }
        System.out.println("toto");
    }

    private void copyArray() {
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                lastMatrix[i][j] = newMatrix[i][j];
            }
        }
    }

    private boolean occupiedAndRelease(int i, int j) {
        if (Objects.equals(lastMatrix[i][j], '#')) {
            int occcupied = 0;
            int xMin = Math.max(i - 1, 0);
            int yMin = Math.max(j - 1, 0);
            int xMax = Math.min(lastMatrix.length, i + 2);
            int yMax = Math.min(lastMatrix[0].length, j + 2);
            for (int ii = xMin; ii < xMax; ii++) {
                for (int jj = yMin; jj < yMax; jj++) {
                    if (Objects.equals(lastMatrix[ii][jj], '#') && !(i == ii && j==jj)) {
                        occcupied = occcupied + 1;
                    }
                    if (3 < occcupied) {
                        return true;
                    }
                }

            }
            return false;
        }
        return false;
    }

    private boolean ifEmptyAndToSeat(int i, int j) {
        if (Objects.equals(lastMatrix[i][j], 'L')) {
            int occcupied = 0;
            int xMin = Math.max(i - 1, 0);
            int yMin = Math.max(j - 1, 0);
            int xMax = Math.min(lastMatrix.length, i + 2);
            int yMax = Math.min(lastMatrix[0].length, j + 2);
            for (int ii = xMin; ii < xMax; ii++) {
                for (int jj = yMin; jj < yMax; jj++) {
                    if (Objects.equals(lastMatrix[ii][jj], '#') && !(i == ii && j==jj)) {
                        occcupied = occcupied + 1;
                    }
                    if (0 < occcupied) {
                        return false;
                    }
                }

            }
            return true;
        }
        return false;
    }


    private void transformToMatrix() {
        int length = lines.get(0).length();
        lastMatrix = new char[lines.size()][length];
        newMatrix = new char[lines.size()][length];
        for (int i = 0; i < lines.size(); i++) {
            String lineToCheck = lines.get(i);
            for (int j = 0; j < length; j++) {
                lastMatrix[i][j] = lineToCheck.charAt(j);
            }
        }
    }
}
