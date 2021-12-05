package com.fmolnar.code.year2020.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day17Challenge02 {

    private static final char HASHTAG = '#';
    private static final char POINT = '.';

    public Day17Challenge02() {
        coordinatesAround = new ArrayList<>();

        for(int i=-1; i<2;i++){
            for(int j=-1; j<2; j++){
                for(int k=-1; k<2;k++){
                    for(int m=-1; m<2; m++){
                        if(!(i==0 && j==0 && k==0 && m==0)){
                            coordinatesAround.add(new Coordinate(i,j,k,m));
                        }
                    }
                }
            }
        }
    }

    List<Coordinate> coordinatesAround;
    char[][][][] lastXYZW;
    char[][][][] newXYZW;
    private List<String> lines = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day17/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }
        transformToXYZW();

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
        for (int i = 0; i < newXYZW.length; i++) {
            for (int j = 0; j < newXYZW[0].length; j++) {
                for (int k = 0; k < newXYZW[0][0].length; k++) {
                    for (int m = 0; m < newXYZW[0][0][0].length; m++) {
                        if (Objects.equals(newXYZW[i][j][k][m], HASHTAG)) {
                            occupied = occupied + 1;
                        }
                    }
                }
            }
        }
        return occupied;
    }

    private void copyArray() {
        for (int i = 0; i < newXYZW.length; i++) {
            for (int j = 0; j < newXYZW[0].length; j++) {
                for (int k = 0; k < newXYZW[0][0].length; k++) {
                    for (int m = 0; m < newXYZW[0][0][0].length; m++) {
                        lastXYZW[i][j][k][m] = newXYZW[i][j][k][m];
                    }
                }
            }
        }
    }

    private void checkStep() {
        for (int ii = 0; ii < lastXYZW.length; ii++) {
            for (int jj = 0; jj < lastXYZW[0].length; jj++) {
                for (int kk = 0; kk < lastXYZW[0][0].length; kk++) {
                    for (int mm = 0; mm < lastXYZW[0][0][0].length; mm++) {
                        caulculateNewNeighour(ii, jj, kk, mm);
                    }
                }
            }
        }
    }

    private void caulculateNewNeighour(int ii, int jj, int kk, int mm) {
        List<Character> characters = new ArrayList<>();
        for (Coordinate coordinate : coordinatesAround) {
            characters.add(getCubeAtCoordinateFromPoint(ii, jj, kk, mm,  coordinate));
        }
        newXYZW[ii][jj][kk][mm] = newValueOfCube(ii, jj, kk, mm, characters);
    }

    private char newValueOfCube(int ii, int jj, int kk, int mm, List<Character> characters) {
        char actual = lastXYZW[ii][jj][kk][mm];
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

    private Character getCubeAtCoordinateFromPoint(int ii, int jj, int kk, int mm, Coordinate coordinate) {
        int x = ii + coordinate.x;
        int y = jj + coordinate.y;
        int z = kk + coordinate.z;
        int w = mm + coordinate.w;
        if (lastXYZW.length <= x || x < 0 || y < 0 || lastXYZW[0].length <= y ||
                lastXYZW[0][0].length <= z || z < 0 || lastXYZW[0][0][0].length <= w || w < 0) {
            return null;
        }
        return lastXYZW[x][y][z][w];
    }

    private void transformToXYZW() {
        int max = Math.max(lines.get(0).length(), lines.size());
        int dimMoy = 8;
        int dim = max + 2*dimMoy;
        int dimHalf = dim/2;
        lastXYZW = new char[dim][dim][dim][dim];
        newXYZW = new char[dim][dim][dim][dim];
        for (int i = 0; i < lines.size(); i++) {
            String lineToCheck = lines.get(i);
            for (int j = 0; j < lineToCheck.length(); j++) {
                lastXYZW[dimHalf+i][dimHalf+j][dimHalf][dimHalf]= lineToCheck.charAt(j);
            }
        }
    }

    public class Coordinate {
        public Coordinate(int x, int y, int z, int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        public int x;
        public int y;
        public int z;
        public int w;
    }

}
