package com.fmolnar.code.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day17Challenge01 {

    private static final char HASHTAG = '#';
    private static final char POINT = '.';

    public Day17Challenge01() {
        coordinatesAround = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (!(i == 0 && j == 0 && k == 0)) {
                        coordinatesAround.add(new Coordinate(i, j, k));
                    }
                }
            }
        }


    }

    List<Coordinate> coordinatesAround;

    char[][][] lastXYZ;
    char[][][] newXYZ;

    private List<String> lines = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day17/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }
        transformToPointsXYZ();

        for (int i = 0; i < 6; i++) {
            if (i != 0) {
                copyArray();
            }
            checkStep();
        }

        System.out.println("Occupied seat: " + calculateOccupiedSeat());
    }

    private int calculateOccupiedSeat() {
        int occupied = 0;
        for (int i = 0; i < newXYZ.length; i++) {
            for (int j = 0; j < newXYZ[0].length; j++) {
                for (int k = 0; k < newXYZ[0][0].length; k++) {
                    if (Objects.equals(newXYZ[i][j][k], HASHTAG)) {
                        occupied = occupied + 1;
                    }
                }
            }
        }
        return occupied;
    }

    private void copyArray() {
        for (int i = 0; i < newXYZ.length; i++) {
            for (int j = 0; j < newXYZ[0].length; j++) {
                for (int k = 0; k < newXYZ[0][0].length; k++) {
                    lastXYZ[i][j][k] = newXYZ[i][j][k];
                }
            }
        }
    }

    private void checkStep() {
        for (int ii = 0; ii < lastXYZ.length; ii++) {
            for (int jj = 0; jj < lastXYZ[0].length; jj++) {
                for (int kk = 0; kk < lastXYZ[0][0].length; kk++) {
                    caulculateNewNeighbour(ii, jj, kk);
                }
            }
        }
    }

    private void caulculateNewNeighbour(int ii, int jj, int kk) {
        List<Character> characters = new ArrayList<>();
        for (Coordinate coordinate : coordinatesAround) {
            characters.add(getCubeAtCoordinateFromPoint(ii, jj, kk, coordinate));
        }
        newXYZ[ii][jj][kk] = newValueOfCube(ii, jj, kk, characters);
    }

    private char newValueOfCube(int ii, int jj, int kk, List<Character> characters) {
        char actual = lastXYZ[ii][jj][kk];
        long count = characters.stream().filter(Objects::nonNull).filter(s -> HASHTAG == s).count();
        if (actual == HASHTAG) {
            if (2 <= count && count <= 3) {
                return HASHTAG;
            }
            return POINT;
        } else {
            if (count == 3) {
                return HASHTAG;
            }
            return POINT;
        }

    }

    private Character getCubeAtCoordinateFromPoint(int ii, int jj, int kk, Coordinate coordinate) {
        int x = ii + coordinate.x;
        int y = jj + coordinate.y;
        int z = kk + coordinate.z;
        if (lastXYZ.length <= x || x < 0 || y < 0 || lastXYZ[0].length <= y ||
                lastXYZ[0][0].length <= z || z < 0) {
            return null;
        }
        return lastXYZ[x][y][z];
    }

    private void transformToPointsXYZ() {
        int max = Math.max(lines.get(0).length(), lines.size());
        int dimMoy = 10;
        int dim = max + 2 * dimMoy;
        lastXYZ = new char[dim][dim][dim];
        newXYZ = new char[dim][dim][dim];
        for (int i = 0; i < lines.size(); i++) {
            String lineToCheck = lines.get(i);
            for (int j = 0; j < lineToCheck.length(); j++) {
                lastXYZ[i + dimMoy][j + dimMoy][dim / 2] = lineToCheck.charAt(j);
            }
        }
    }

    public class Coordinate {
        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int x;
        public int y;
        public int z;
    }


}
