package com.fmolnar.code.leetcode.matrixmax;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public int largestSubmatrix(int[][] matrix) {
        int[][] matrixConsec = new int[matrix.length][matrix[0].length];
        for (int x = 0; x < matrix[0].length; x++) {


            int sum = 1;
            for (int y = 0; y < matrix.length; y++) {
                if (matrix[y][x] == 1) {
                    matrixConsec[y][x] = sum++;
                } else {
                    matrixConsec[y][x] = 0;
                    sum = 1;
                }
            }
        }
        Set<Integer> maxs = new HashSet<>();
        for (int y = 0; y < matrix.length; y++) {
            List<Integer> integs = new ArrayList<>();
            for (int x = 0; x < matrix[0].length; x++) {
                integs.add(matrixConsec[y][x]);
            }
            List<Integer> list = integs.stream().sorted(Comparator.reverseOrder()).toList();
            for (int i = 0; i < list.size(); i++) {
                maxs.add((i + 1) * list.get(i));
            }
        }

        return maxs.stream().mapToInt(s -> s).max().getAsInt();

    }
}
