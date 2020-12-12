package com.fmolnar.code.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day11ChallengeBeauty02 {

    public Day11ChallengeBeauty02() {
        model= new ArrayList<>();
        model.add(new Coordinate(0,1));
        model.add(new Coordinate(0,-1));
        model.add(new Coordinate(1,0));
        model.add(new Coordinate(-1,0));
        model.add(new Coordinate(1,1));
        model.add(new Coordinate(-1,1));
        model.add(new Coordinate(-1,-1));
        model.add(new Coordinate(1,-1));
    }

    List<Coordinate> model;

    List<String> lines = new ArrayList<>();

    char[][] lastMatrix;
    char[][] newMatrix;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day11/input.txt");
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

    private void checkStep() {
        for(int i=0; i<lastMatrix.length; i++){
            for(int j=0; j<lastMatrix[0].length; j++){
                calculateToto(i, j);
            }
        }
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

    private void calculateToto(int i, int j) {
        List<Character> characters = new ArrayList<>();
        for(Coordinate coordinate : model){
            characters.add(szamol(i, j, coordinate));
        }
        newMatrix[i][j] = elozoSzerep(i,j,characters);
    }

    private char elozoSzerep(int i, int j,List<Character> characters) {
        char actual = lastMatrix[i][j];
        long count = characters.stream().filter(Objects::nonNull).filter(s->'#' == s).count();
        if(actual == '.'){
            return '.';
        }
        if(actual == '#'){
            if(4<count){
                return 'L';
            }
        }
        if(actual == 'L'){
            if(0 == count){
                return '#';
            }
            else{
                return 'L';
            }
        }
        return actual;
    }

    private Character szamol(int ii, int jj, Coordinate coordinate) {
        for(int level =1; level<100; level++){
            int x = ii + coordinate.x * level;
            int y = jj + coordinate.y * level;
            if(lastMatrix.length <= x || x<0 || y<0 ||lastMatrix[0].length <= y){
                return null;
            }else if (lastMatrix[x][y]=='.'){
                continue;
            } else{
                return lastMatrix[x][y];
            }
        }
        return null;
    }

    public class Coordinate{
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x;
        public int y;
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

    private void copyArray() {
        System.out.println("---------------------------------");
        for (int i = 0; i < newMatrix.length; i++) {
            String line = "";
            for (int j = 0; j < newMatrix[0].length; j++) {
                lastMatrix[i][j] = newMatrix[i][j];
                line = line + newMatrix[i][j] ;
            }
            System.out.println(line);
        }
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
