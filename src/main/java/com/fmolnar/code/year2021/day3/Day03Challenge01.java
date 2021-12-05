package com.fmolnar.code.year2021.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day03Challenge01 {

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
        calculateRates();
    }

    private int[][] calculateRates() {
        String gammaAll = "";
        String epsilonAll = "";
        int[][] matrix = new int[lines.get(0).length()][lines.size()];
        for (int i = 0; i < lines.get(0).length(); i++) {
            int sum = 0;
            for (int j = 0; j < lines.size(); j++) {
                matrix[i][j] = lines.get(j).charAt(i) == '1' ? 1 : 0;
                sum += matrix[i][j];
            }
            String gammaRateActual = (2 * sum) > (lines.size()) ? "1" : "0";
            String epsilonRateActual = gammaRateActual.equals("1") ? "0" : "1";
            gammaAll = gammaAll + gammaRateActual;
            epsilonAll = epsilonAll + epsilonRateActual;
        }
        System.out.println("Result : " + (Integer.parseInt(gammaAll,2)* Integer.parseInt(epsilonAll,2)));
        return matrix;
    }

}
