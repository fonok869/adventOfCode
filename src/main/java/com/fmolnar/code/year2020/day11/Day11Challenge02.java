package com.fmolnar.code.year2020.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day11Challenge02 {

    public Day11Challenge02() {
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
            System.out.println("Counter: " + counter);
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
        System.out.println("Toto");
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

    private boolean occupiedAndRelease(int i, int j) {
        boolean xLeft = true;
        boolean xRight = true;
        boolean yDown = true;
        boolean yUp = true;
        boolean upLeft = true;
        boolean upRight = true;
        boolean downRight = true;
        boolean downLeft = true;
        int occcupied = 0;
        if (Objects.equals(lastMatrix[i][j], '#')) {
            int limitX = Math.max(i, Math.abs(lastMatrix[0].length-i));
            int limitY =  Math.max(j, Math.abs(lastMatrix.length-j));
            int limitOverall = Math.max(limitX,limitY);
            for(int level=1; level<limitOverall; level++){
                int xMin = i - level;
                int yMin = j - level;
                int xMax =  i + level;
                int yMax = j + level;
// 1
                if(xLeft && (j != yMin) && isOccupied(i,yMin)){
                    occcupied += 1;
                    xLeft = false;
                }
// 2
                if(xRight && (j != yMax) && isOccupied(i,yMax)){
                    occcupied += 1;
                    xRight = false;
                }
//3
                if(yDown && (i != xMax)  && isOccupied(xMax, j)){
                    occcupied += 1;
                    yDown = false;
                }

                if(yUp && (i != xMin) && isOccupied(xMin, j)){
                    occcupied += 1;
                    yUp = false;
                }

                if(upLeft && (i != xMin && j!=yMin) && isOccupied(xMin, yMin)){
                    occcupied += 1;
                    upLeft = false;
                }

                if(upRight && (i != xMin && j!=yMax) && isOccupied(xMin, yMax)){
                    occcupied += 1;
                    upRight = false;
                }

                if(downLeft && (i != xMax && j!=yMin) && isOccupied(xMax, yMin)){
                    occcupied += 1;
                    downLeft = false;
                }

                if(downRight && (i != xMax && j!=yMax) && isOccupied(xMax, yMax)){
                    occcupied += 1;
                    downRight = false;
                }

                //----------------------------
                if (4 < occcupied) {
                    return true;
                }
                //------------------------------------------------
                //1
                if(xLeft && (j != yMin) && isFree(i,yMin)){
                    xLeft = false;
                }

                //2
                if(xRight && (j != yMax) && isFree(i,yMax)){
                    xRight = false;
                }

                // yDown && (i != xMax)  && isOccupied(xMax, j)
                if(yDown && (i != xMax)  && isFree(xMax, j)){
                    yDown = false;
                }

                // if(yUp && (i != xMin) && isOccupied(xMin, j)){
                if(yUp && (i != xMin) && isFree(xMin, j)){
                    yUp = false;
                }

                // if(upLeft && (i != xMin && j!=yMin) && isOccupied(xMin, yMin)){
                if(upLeft && (i != xMin && j!=yMin) && isFree(xMin, yMin)){
                    upLeft = false;
                }

                //  if(upRight && (i != xMin && j!=yMax) && isOccupied(xMin, yMax)){
                if(upRight && (i != xMin && j!=yMax) && isFree(xMin, yMax)){
                    upRight = false;
                }

                // if(downLeft && (i != xMax && j!=yMin) && isOccupied(xMax, yMin)){
                if(downLeft && (i != xMax && j!=yMin) && isFree(xMax, yMin)){
                    downLeft = false;
                }

                // if(downRight && (i != xMax && j!=yMax) && isOccupied(xMax, yMax)){
                if(downRight && (i != xMax && j!=yMax) && isFree(xMax, yMax)){
                    downRight = false;
                }


            }
            return false;
        }
        return false;
    }

    private boolean isOccupied(int i, int j) {
        if((lastMatrix.length <= i || i<0)|| (j<0 ||lastMatrix[0].length <= j)){
            return false;
        }
        if (Objects.equals(lastMatrix[i][j], '#')) {
            return true;
        }
        return false;
    }

    private boolean ifEmptyAndToSeat(int i, int j) {

        boolean xLeft = true;
        boolean xRight = true;
        boolean yDown = true;
        boolean yUp = true;
        boolean upLeft = true;
        boolean upRight = true;
        boolean downRight = true;
        boolean downLeft = true;
        int occcupied = 0;
        if (Objects.equals(lastMatrix[i][j], 'L')) {
            int limitX = Math.max(i, Math.abs(lastMatrix[0].length-i));
            int limitY =  Math.max(j, Math.abs(lastMatrix.length-j));
            int limitOverall = Math.max(limitX,limitY);
            for(int level=1; level<limitOverall; level++){
                int xMin = i - level;
                int yMin = j - level;
                int xMax = i + level;
                int yMax = j + level;

                if(xLeft && (j != yMin) && isOccupied(i,yMin)){
                    occcupied += 1;
                    xLeft = false;
                }

                if(xRight && (j != yMax) && isOccupied(i,yMax)){
                    occcupied += 1;
                    xRight = false;
                }

                if(yDown && (i != xMax)  && isOccupied(xMax, j)){
                    occcupied += 1;
                    yDown = false;
                }

                if(yUp && (i != xMin) && isOccupied(xMin, j)){
                    occcupied += 1;
                    yUp = false;
                }

                if(upLeft && (i != xMin && j!=yMin) && isOccupied(xMin, yMin)){
                    occcupied += 1;
                    upLeft = false;
                }

                if(upRight && (i != xMin && j!=yMax) && isOccupied(xMin, yMax)){
                    occcupied += 1;
                    upRight = false;
                }

                if(downLeft && (i != xMax && j!=yMin) && isOccupied(xMax, yMin)){
                    occcupied += 1;
                    downLeft = false;
                }

                if(downRight && (i != xMax && j!=yMax) && isOccupied(xMax, yMax)){
                    occcupied += 1;
                    downRight = false;
                }

                if (0 < occcupied) {
                    return false;
                }
                //------------------------------------------------
                //1
                if(xLeft && (j != yMin) && isFree(i,yMin)){
                    xLeft = false;
                }

                //2
                if(xRight && (j != yMax) && isFree(i,yMax)){
                    xRight = false;
                }

                if(yDown && (i != xMax)  && isFree(xMax, j)){
                    yDown = false;
                }

                if(yUp && (i != xMin) && isFree(xMin, j)){
                    yUp = false;
                }

                if(upLeft && (i != xMin && j!=yMin) && isFree(xMin, yMin)){
                    upLeft = false;
                }

                if(upRight && (i != xMin && j!=yMax) && isFree(xMin, yMax)){
                    upRight = false;
                }

                if(downLeft && (i != xMax && j!=yMin) && isFree(xMax, yMin)){
                    downLeft = false;
                }

                if(downRight && (i != xMax && j!=yMax) && isFree(xMax, yMax)){
                    downRight = false;
                }


            }
            return true;
        }
        return false;
    }

    private boolean isFree(int i, int j) {
        if((lastMatrix.length <= i || i<0)|| (j<0 ||lastMatrix[0].length <= j)){
            return false;
        }
        if (Objects.equals(lastMatrix[i][j], 'L')) {
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
