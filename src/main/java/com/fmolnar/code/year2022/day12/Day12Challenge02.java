package com.fmolnar.code.year2022.day12;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12Challenge02 {

    int xMax = 0;
    int yMax = 0;
    private int ALL_CSUCSOK = 0;
    int[] csucsok;
    int[] dist;


    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day12/input.txt");

        yMax = lines.size();
        xMax = lines.get(0).length();
        ALL_CSUCSOK = xMax * yMax;
        csucsok = new int[ALL_CSUCSOK];
        dist = new int[ALL_CSUCSOK];
        List<Integer> starters = new ArrayList<>();
        int iStart = -1;
        int iEnd = -1;

        for (int i = 0; i < ALL_CSUCSOK; i++) {
            int indexX = i % xMax;
            int indexY = (i - indexX) / xMax;
            char letter = lines.get(indexY).charAt(indexX);
            if (letter == 'S') {
                csucsok[i] = Integer.valueOf('a') - Integer.valueOf('a');
                starters.add(i);
            } else if (letter == 'E') {
                iEnd = i;
                csucsok[i] = Integer.valueOf('z') - Integer.valueOf('a');
            } else {
                csucsok[i] = Integer.valueOf(lines.get(indexY).charAt(indexX)) - Integer.valueOf('a');
                if ('a' == lines.get(indexY).charAt(indexX)) {
                    starters.add(i);
                }
            }
        }

        iStart = iEnd;

        for (int i = 0; i < ALL_CSUCSOK; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        Set<Integer> indexesAlreadyCalculated = new HashSet<>();
        dist[iStart] = 0;

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

            // Baloldalso szomszed
            if (!(distMinPoistion % xMax == 0)) {
                // Csak akkoz szamitjuk ha nem balszelso
                int indexLeft = distMinPoistion - 1;
                // nem nohet 1 nel tobbel
                int diff = csucsok[indexLeft] - csucsok[distMinPoistion];
                if (-1 <= diff) {
                    int distanceIndexLeft = 1 + dist[distMinPoistion];
                    updateDistance(indexLeft, distanceIndexLeft);
                }
            }

            // Felso szomszed
            if (!((distMinPoistion - xMax) < 0)) {
                // Csak akkor szamitjuk ki ha nem felso
                int indexUp = distMinPoistion - xMax;
                int diff = csucsok[indexUp] - csucsok[distMinPoistion];
                if (-1 <= diff) {
                    int distanceUp = 1 + dist[distMinPoistion];
                    updateDistance(indexUp, distanceUp);
                }
            }

            // Jobb oldalso szomszed
            if (!((distMinPoistion % xMax) == (xMax - 1))) {
                int indexRight = distMinPoistion + 1;
                int diff = csucsok[indexRight] - csucsok[distMinPoistion];
                if (-1 <= diff) {
                    int distanceRight = 1 + dist[distMinPoistion];
                    updateDistance(indexRight, distanceRight);
                }
            }

            // Also szomszed
            if (!(ALL_CSUCSOK <= (distMinPoistion + xMax))) {
                int indexDown = distMinPoistion + xMax;
                int diff = csucsok[indexDown] - csucsok[distMinPoistion];
                if (-1 <= diff) {
                    int distanceDown = 1 + dist[distMinPoistion];
                    updateDistance(indexDown, distanceDown);
                }
            }

            indexesAlreadyCalculated.add(distMinPoistion);

            if (indexesAlreadyCalculated.size() == 3200) {
                break;
            }
        }

        List<Integer> distancesMin = new ArrayList<>();

        for (Integer start : starters) {
            distancesMin.add(dist[start]);
        }
        System.out.println("Min distance: " + (distancesMin.stream().mapToInt(s -> s).min().getAsInt()));
    }

    private void updateDistance(int index, int distance) {
        if (distance < dist[index]) {
            dist[index] = distance;
        }
    }
}
