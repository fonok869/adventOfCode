package com.fmolnar.code.year2022.day12;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12Challenge01 {

    int xMax = 0;
    int yMax = 0;
    private int ALL_CSUCSOK = 0;
    int[] csucsok;
    int[] dist;


    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day12/input.txt");

        yMax = lines.size();
        xMax = lines.get(0).length();
        ALL_CSUCSOK = xMax * yMax;
        csucsok = new int[ALL_CSUCSOK];
        dist = new int[ALL_CSUCSOK];
        int iStart = -1;
        int iEnd = -1;

        for (int i = 0; i < ALL_CSUCSOK; i++) {
            int indexX = i % xMax;
            int indexY = (i - indexX) / xMax;
            char letter = lines.get(indexY).charAt(indexX);
            if (letter == 'S') {
                iStart = i;
                csucsok[i] = Integer.valueOf('a') - Integer.valueOf('a');
            } else if (letter == 'E') {
                iEnd = i;
                csucsok[i] = Integer.valueOf('z') - Integer.valueOf('a');
                ;
            } else {
                csucsok[i] = Integer.valueOf(lines.get(indexY).charAt(indexX)) - Integer.valueOf('a');
            }
        }

        for (int i = 0; i < ALL_CSUCSOK; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        Set<Integer> indexesAlreadyCalculated = new HashSet<>();
        dist[iStart] = 0;

        int minDistance = Integer.MAX_VALUE;
        int distMinPoistion = iStart;

        for (; ; ) {
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
            if (!(distMinPoistion % xMax == 0)) {
                // Csak akkoz szamitjuk ha nem balszelso
                int indexLeft = distMinPoistion - 1;
                // nem nohet - 1 nel tobbel
                int diff = csucsok[indexLeft] - csucsok[distMinPoistion];
                if ((diff <= 1)) {
                    updateDistance(indexLeft, 1 + dist[distMinPoistion]);
                }
            }

            // Felso szomszed
            if (!((distMinPoistion - xMax) < 0)) {
                // Csak akkor szamitjuk ki ha nem felso
                int indexUp = distMinPoistion - xMax;
                int diff = csucsok[indexUp] - csucsok[distMinPoistion];
                if ((diff <= 1)) {
                    updateDistance(indexUp, 1 + dist[distMinPoistion]);
                }
            }

            // Jobb oldalso szomszed
            if (!((distMinPoistion % xMax) == (xMax - 1))) {
                int indexRight = distMinPoistion + 1;
                int diff = csucsok[indexRight] - csucsok[distMinPoistion];
                if (diff <= 1) {
                    updateDistance(indexRight, 1 + dist[distMinPoistion]);
                }
            }

            // Also szomszed
            if (!(ALL_CSUCSOK <= (distMinPoistion + xMax))) {
                int indexDown = distMinPoistion + xMax;
                int diff = csucsok[indexDown] - csucsok[distMinPoistion];
                if (diff <= 1) {
                    updateDistance(indexDown, 1 + dist[distMinPoistion]);
                }
            }

            indexesAlreadyCalculated.add(distMinPoistion);

            if (distMinPoistion == iEnd) {
                minDistance = distMin;
                break;
            }
        }

        System.out.println("Day12Challenge01: " + minDistance);
    }

    private void updateDistance(int index, int distance) {
        if (distance < dist[index]) {
            dist[index] = distance;
        }
    }
}
