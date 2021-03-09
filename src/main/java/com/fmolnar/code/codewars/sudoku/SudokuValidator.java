package com.fmolnar.code.codewars.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SudokuValidator {

    private static int NINE = 9;
    public static boolean check(int[][] sudoku) {

        if(sudoku.length != NINE || sudoku[0].length != NINE)
            return false;

        for (int i = 0; i < NINE; i++) {
            int sum = 0;
            Set<Integer> sets = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
            for (int j = 0; j < NINE; j++) {
                int value = sudoku[i][j];
                sum += value;
                if(sets.contains(value)){
                    sets.remove(value);
                } else {
                    return false;
                }
                if(sudoku[i][j] == 0){
                    return false;
                }
            }
            if (sum != 45) {
                return false;
            }
        }

        for (int i = 0; i < NINE; i++) {
            int sum = 0;
            Set<Integer> sets = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
            for (int j = 0; j < NINE; j++) {
                int value = sudoku[i][j];
                sum += value;
                if(sets.contains(value)){
                    sets.remove(value);
                } else {
                    return false;
                }
                if(sudoku[i][j] == 0){
                    return false;
                }
            }
            if (sum != 45) {
                return false;
            }
        }
        boolean square = true;
        for(int i = 0; i<NINE; i=i+3){
            for(int j=0; j<NINE; j = j+3){
                square = square && checkSquare(i,j,sudoku);
                if(!square){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkSquare(int i, int j, int[][] sudoku){
        int sum = 0;
        Set<Integer> sets = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        for(int ii=i; ii<i+3; ii++){
            for(int jj=j; jj<j+3; jj++){
                int value = sudoku[ii][jj];
                sum += value;
                if(sets.contains(value)){
                    sets.remove(value);
                } else {
                    return false;
                }
            }
        }
        if (sum != 45) {
            return false;
        }
        return true;
    }
}
