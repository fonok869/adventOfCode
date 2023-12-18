package com.fmolnar.code.year2023.day14;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day14/input.txt");

        char[][] matrix = new char[lines.size()][lines.get(0).length()];
        for (int j = 0; j < lines.size(); j++) {
            for (int i = 0; i < lines.get(0).length(); i++) {
                matrix[j][i] = lines.get(j).charAt(i);
            }
        }
        Point NORTH = new Point(-1, 0);
        Point WEST = new Point(0, -1);
        Point SOUTH = new Point(1, 0);
        Point EAST = new Point(0, 1);

        char[][] matrixFelfele = matrix;

        List<MatrixWrapper> matrixes = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            matrixFelfele = matrixNWLepes(matrixFelfele, NORTH);
            matrixFelfele = matrixNWLepes(matrixFelfele, WEST);
            matrixFelfele = matrixSELepes(matrixFelfele, SOUTH);
            matrixFelfele = matrixSELepes(matrixFelfele, EAST);
            matrixes.add(new MatrixWrapper(deepCopy(matrixFelfele)));
        }

        int iActual = 0;
        List<Integer> kulonbsegek = new ArrayList<>();
        alltogether:
        for (int i = 0; i < matrixes.size(); i++) {
            MatrixWrapper elso = matrixes.get(i);
            for (int j = i + 1; j < matrixes.size(); j++) {
                if (elso.equals(matrixes.get(j))) {
                    iActual = i;
                    kulonbsegek.add(j);
                }
                if (10 < kulonbsegek.size()) {
                    break alltogether;
                }
            }
        }

        long iActualLong = iActual + 1;
        long kulonbseg = kulonbsegek.get(1) - kulonbsegek.get(0);

        long multiplication = (1000000000L - iActualLong) / kulonbseg;
        long stepRestant = 1000000000L - (multiplication * kulonbseg);
        long restant = stepRestant - iActualLong;

        char[][] matrixLast = deepCopy(matrixes.get(iActual).matrixFelfele);

        for (int i = 1; i <= restant; i++) {
            matrixLast = matrixNWLepes(matrixLast, NORTH);
            matrixLast = matrixNWLepes(matrixLast, WEST);
            matrixLast = matrixSELepes(matrixLast, SOUTH);
            matrixLast = matrixSELepes(matrixLast, EAST);
        }


        System.out.println(calculate(matrixLast));
    }

    static char[][] deepCopy(char[][] matrixFelfele) {
        int maxY = matrixFelfele.length;
        int maxX = matrixFelfele[0].length;
        char[][] newCopy = new char[maxY][maxX];
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                newCopy[j][i] = matrixFelfele[j][i];
            }
        }
        return newCopy;
    }


    record MatrixWrapper(char[][] matrixFelfele) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MatrixWrapper that = (MatrixWrapper) o;
            int maxY = matrixFelfele.length;
            int maxX = matrixFelfele[0].length;
            for (int j = 0; j < maxY; j++) {
                for (int i = 0; i < maxX; i++) {
                    if (matrixFelfele[j][i] != (that.matrixFelfele[j][i])) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(matrixFelfele);
        }
    }

    ;

    private static int calculate(char[][] matrixFelfele) {
        int maxY = matrixFelfele.length;
        int maxX = matrixFelfele[0].length;
        int sum = 0;
        int lepesY = 1;
        for (int j = maxY - 1; 0 <= j; j--) {
            for (int i = maxX - 1; 0 <= i; i--) {
                if (matrixFelfele[j][i] == 'O' || matrixFelfele[j][i] == '0') {
                    sum += lepesY;
                }
            }
            lepesY++;
        }
        return sum;
    }

    private static void printMatrix(char[][] matrixFelfele) {
        int maxY = matrixFelfele.length;
        int maxX = matrixFelfele[0].length;
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                System.out.print(matrixFelfele[j][i]);
            }
            System.out.println();
        }
    }

    record Point(int y, int x) {
    }

    private static char[][] matrixSELepes(char[][] matrix, Point direction) {

        int maxY = matrix.length;
        int maxX = matrix[0].length;
        for (int j = maxY - 1; 0 <= j; j--) {
            for (int i = maxX - 1; 0 <= i; i--) {
                if ('O' == matrix[j][i]) {
                    // lepes
                    if (direction.y != 0) {
                        int lastJ = j;
                        for (int lepes = j + direction.y; 0 <= lepes && lepes < maxY; lepes = lepes + direction.y) {
                            if ('.' == matrix[lepes][i]) {
                                lastJ = lepes;
                            } else {
                                break;
                            }
                        }
                        if (lastJ != j) {
                            matrix[lastJ][i] = 'O';
                            matrix[j][i] = '.';
                        }
                    } else {
                        int lastI = i;
                        for (int lepes = i + direction.x; 0 <= lepes && lepes < maxY; lepes = lepes + direction.x) {
                            if ('.' == matrix[j][lepes]) {
                                lastI = lepes;
                            } else {
                                break;
                            }
                        }
                        if (lastI != i) {
                            matrix[j][lastI] = 'O';
                            matrix[j][i] = '.';
                        }

                    }
                }
            }
        }
        return matrix;
    }

    private static char[][] matrixNWLepes(char[][] matrix, Point direction) {

        int maxY = matrix.length;
        int maxX = matrix[0].length;
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                if ('O' == matrix[j][i]) {
                    // lepes
                    if (direction.y != 0) {
                        int lastJ = j;
                        for (int lepes = j + direction.y; 0 <= lepes && lepes < maxY; lepes = lepes + direction.y) {
                            if ('.' == matrix[lepes][i]) {
                                lastJ = lepes;
                            } else {
                                break;
                            }
                        }
                        if (lastJ != j) {
                            matrix[lastJ][i] = 'O';
                            matrix[j][i] = '.';
                        }
                    } else {
                        int lastI = i;
                        for (int lepes = i + direction.x; 0 <= lepes && lepes < maxY; lepes = lepes + direction.x) {
                            if ('.' == matrix[j][lepes]) {
                                lastI = lepes;
                            } else {
                                break;
                            }
                        }
                        if (lastI != i) {
                            matrix[j][lastI] = 'O';
                            matrix[j][i] = '.';
                        }

                    }
                }
            }
        }
        return matrix;
    }
}
