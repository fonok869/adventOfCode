package com.fmolnar.code.year2021.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day03Challenge02 {

    private List<String> lines = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day03/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }

        int[][] matrixOriginal = transformLinesToMatrix();

        int[][] matrixOxygen = matrixOriginal;
        for (int i = 0; i < matrixOriginal.length; i++) {
            matrixOxygen = matrixToCalculate(i, matrixOxygen, true);
            if (matrixOxygen[0].length == 1) {
                break;
            }
        }

        int[][] co2Matrix = matrixOriginal;
        for (int i = 0; i < matrixOriginal.length; i++) {
            co2Matrix = matrixToCalculate(i, co2Matrix, false);
            if (co2Matrix[0].length == 1) {
                break;
            }
        }

        String co2 = "";
        String oxyGen = "";
        for (int i = 0; i < co2Matrix.length; i++) {
            co2 = co2 + co2Matrix[i][0];
            oxyGen = oxyGen + matrixOxygen[i][0];
        }
        System.out.println("Result (2): " + Integer.parseInt(oxyGen, 2) * Integer.parseInt(co2, 2));
    }


    private int[][] matrixToCalculate(int level, int[][] matrixToTreat, boolean isOxygen) {
        int sum = 0;
        for (int j = 0; j < matrixToTreat[0].length; j++) {
            if (matrixToTreat[level][j] == 1) {
                sum += matrixToTreat[level][j];
            }
        }
        // Oxygen determine the most common value
        int ujkezedet;
        if (2 * sum < matrixToTreat[0].length) {
            ujkezedet = isOxygen ? 0 : 1;
        } else {
            ujkezedet = isOxygen ? 1 : 0;
        }

        // ujMatrix
        int ujHossz = ujkezedet == 1 ? sum : matrixToTreat[0].length - sum;

        int[][] newMatrix = createReducedMatrix(ujHossz, ujkezedet, matrixToTreat, level);
        return newMatrix;
    }


    private int[][] createReducedMatrix(int ujHossz, int ujkezedet, int[][] matrixToTreat, int level) {
        int counter = 0;
        int[][] newMatrix = new int[matrixToTreat.length][ujHossz];
        for (int j = 0; j < matrixToTreat[0].length; j++) {
            if (matrixToTreat[level][j] == ujkezedet) {
                for (int i = 0; i < matrixToTreat.length; i++) {
                    int cucc = matrixToTreat[i][j];
                    newMatrix[i][counter] = cucc;
                }
                counter++;
            }

        }
        return newMatrix;
    }


    private int[][] transformLinesToMatrix() {
        int[][] matrix = new int[lines.get(0).length()][lines.size()];
        for (int i = 0; i < lines.get(0).length(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                matrix[i][j] = lines.get(j).charAt(i) == '1' ? 1 : 0;
            }
        }
        return matrix;
    }

}
