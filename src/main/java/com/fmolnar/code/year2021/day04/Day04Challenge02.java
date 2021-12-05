package com.fmolnar.code.year2021.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day04Challenge02 {

    List<Integer> numberToShoot = new ArrayList<>();
    private static final int size = 5;
    int[][] bingo = new int[size][size];
    Map<Integer, Integer> resultat = new HashMap<>();


    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day04/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counter = 0;
            int counterMatrix = 0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (counter == 0) {
                        String[] args = line.split(",");
                        Stream.of(args).forEach(s -> numberToShoot.add(Integer.valueOf(s)));
                    } else {
                        if (line.startsWith(" ")) {
                            line = line.substring(1);
                        }
                        String[] argsMatrix = line.replaceAll("  ", " ").split(" ");
                        for (int i = 0; i < size; i++) {
                            bingo[counterMatrix][i] = Integer.valueOf(argsMatrix[i]);
                        }
                        counterMatrix++;

                    }

                    counter++;
                } else if (counter != 0 && counterMatrix == 5) {
                    testBingo();
                    counterMatrix = 0;
                }
            }
            testBingo();
        }

        int max = resultat.keySet().stream().mapToInt(s->s).max().getAsInt();
        System.out.println("Winner (2): " + resultat.get(max));

        
    }

    private void testBingo() {
        List<Integer> bingosNumber = initBingoList();

        int[][] bingoShot = new int[size][size];

        int counter = 1;
        for (Integer actualBingo : numberToShoot) {
            if (bingosNumber.contains(actualBingo)) {
                addToMatrix(actualBingo, bingoShot);
            }
            if (testSucces(bingoShot, counter, actualBingo)) {
                break;
            }
            counter++;
        }

    }

    private boolean testSucces(int[][] bingoShot, int counter, int actualBingo) {
        // Vertical test
        int counterX = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bingoShot[i][j] != 0) {
                    counterX++;
                }
                if (counterX == 5) {
                    calculateScore(bingoShot, actualBingo, counter);
                    return true;
                }
            }
            counterX = 0;
        }

        // Horizontal test
        int counterY = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bingoShot[j][i] != 0) {
                    counterY++;
                }
                if (counterY == 5) {
                    calculateScore(bingoShot, actualBingo, counter);
                    return true;
                }
            }
            counterY = 0;
        }
        return false;

    }

    private void calculateScore(int[][] bingoShot, int actualBingo, int counter) {
        List<Integer> notUsed = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(bingoShot[i][j]!=bingo[i][j]){
                    notUsed.add(bingo[i][j]);
                }
            }
        }
        int sumNotUsed = notUsed.stream().mapToInt(s->s).sum();
        resultat.put(counter, (sumNotUsed * actualBingo));
    }

    private void addToMatrix(Integer actualBingo, int[][] bingoShot) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bingo[i][j] == actualBingo) {
                    bingoShot[i][j] = actualBingo;
                }
            }
        }

    }

    private List<Integer> initBingoList() {
        List<Integer> bingosNumber = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                bingosNumber.add(bingo[i][j]);
            }
        }
        return bingosNumber;
    }

}
