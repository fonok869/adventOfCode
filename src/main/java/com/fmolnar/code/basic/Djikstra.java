package com.fmolnar.code.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Djikstra {


    int[] csucsok;
    int[] dist;

    private int MAX_LINE = 3;
    private int ALL_CSUCSOK = MAX_LINE * MAX_LINE;
    private static final int ZERO = 0;

    public Djikstra() {

    }


    public void megold() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/2021/day15/input.txt");
        List<List<Integer>> matrix = new ArrayList<>();
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
        int xMax = matrix.get(0).size();
        int yMax = matrix.size();
        MAX_LINE = xMax;
        ALL_CSUCSOK = yMax * MAX_LINE;
         csucsok = new int [ALL_CSUCSOK];
         dist = new int [ALL_CSUCSOK];


        for(int i = 0; i<ALL_CSUCSOK; i++){
            int indexX = i % xMax;
            int indexY = (i-indexX) / yMax;
            csucsok[i] = matrix.get(indexY).get(indexX);
        }


        for (int i = 0; i < ALL_CSUCSOK; i++) {
            dist[i] = Integer.MAX_VALUE;
        }


//        int times = 1;
//        int[][] balOldalso = new int[times * yMax][times * xMax];
//        for (int x = 0; x < xMax; x++) {
//            for (int y = 0; y < yMax; y++) {
//                balOldalso[y][x] = matrix.get(y).get(x);
//                int value = balOldalso[y][x];
//                int newValue = value;
//                for (int i = 1; i < times; i++) {
//                    newValue = (newValue + 1) % 10 == 0 ? 1 : (newValue + 1) % 10;
//                    balOldalso[y + yMax * i][x] = newValue;
//                    balOldalso[y][x + xMax * i] = newValue;
//                }
//            }
//        }

        Set<Integer> indexesAlreadyCalculated = new HashSet<>();
        dist[0] = csucsok[0];

        int minDistance = Integer.MAX_VALUE;
        int distMinPoistion = 0;

        for(;;) {
            // min distance, ugy hogy nincs benne indexesAlreadyCalculated - ben
            int distMin = Integer.MAX_VALUE;
            for (int index = 0; index < dist.length; index++) {
                if (!indexesAlreadyCalculated.contains(index)) {
                    if (dist[index] < distMin) {
                        distMinPoistion = index;
                        distMin = dist[index];
                    }
                }
            }


            // megnezni a szomszedokat, ha olyan szomszed, ami mar volt egyszer szamitva, akkor ha kisebb akkor kicsereljuk az erteket, ha nagyobb akkor ugy hagyjuk
            // Baloldalso szomszed
            if (!(distMinPoistion % MAX_LINE == 0)) {
                // Csak akkoz szamitjuk ha nem balszelso
                int indexLeft = distMinPoistion - 1;
                int distanceIndexLeft = csucsok[indexLeft] + distMin;
                updateDistance(indexLeft, distanceIndexLeft);
            }

            // Felso szomszed
            if (!((distMinPoistion - MAX_LINE) <= MAX_LINE)) {
                // Csak akkor szamitjuk ki ha nem felso
                int indexUp = distMinPoistion - MAX_LINE;
                int distanceUp = csucsok[indexUp] + distMin;
                updateDistance(indexUp, distanceUp);
            }

            // Jobb oldalso szomszed
            if (!((distMinPoistion % MAX_LINE) == (MAX_LINE - 1))) {
                int indexRight = distMinPoistion + 1;
                int distanceRight = csucsok[indexRight] + distMin;
                updateDistance(indexRight, distanceRight);
            }

            // Also szomszed
            if (!(ALL_CSUCSOK <= (distMinPoistion + MAX_LINE))) {
                int indexDown = distMinPoistion + MAX_LINE;
                int distanceDown = csucsok[indexDown] + distMin;
                updateDistance(indexDown, distanceDown);
            }
            indexesAlreadyCalculated.add(distMinPoistion);

            if(distMinPoistion == (ALL_CSUCSOK - 1)){
                minDistance = distMin;
                break;
            }
        }

        System.out.println("Min distance: " + (minDistance - csucsok[0]));

    }

    private void updateDistance(int index, int distance) {
        if (distance < dist[index]) {
            dist[index] = distance;
        }
    }
}
