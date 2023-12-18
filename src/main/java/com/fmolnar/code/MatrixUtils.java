package com.fmolnar.code;

public final class MatrixUtils {

    public static char[][] deepCopy(char[][] matrix) {
        int maxY = matrix.length;
        int maxX = matrix[0].length;
        char[][] newCopy = new char[maxY][maxX];
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                newCopy[j][i] = matrix[j][i];
            }
        }
        return newCopy;
    }

    public static void printMatrix(char[][] matrix) {
        int maxY = matrix.length;
        int maxX = matrix[0].length;
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                System.out.print(matrix[j][i]);
            }
            System.out.println();
        }
    }


}
