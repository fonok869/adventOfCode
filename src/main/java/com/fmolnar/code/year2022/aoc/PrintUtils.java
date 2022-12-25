package com.fmolnar.code.year2022.aoc;

public class PrintUtils {

    public static void showMatrix(int[][] matrix) {
        for (int y = 0; y < matrix[0].length; y++) {
            System.out.println();
            for (int x = 0; x < matrix.length; x++) {
                System.out.print(matrix[x][y]);
            }
        }
        System.out.println();
        System.out.println("---------------------------------------------");
    }

    public static void showMatrixT(int[][] matrix) {
        for (int y = 0; y < matrix.length; y++) {
            System.out.println();
            for (int x = 0; x < matrix[0].length; x++) {
                System.out.print(matrix[y][x]);
            }
        }
        System.out.println();
        System.out.println("---------------------------------------------");
    }
}
