package com.fmolnar.code.sorting;

public class TwoArraysMerge {

    // Problem: Given two sorted arrays, merge them into a single sorted array.
    int[] merging(int[] merge1, int[] merge2) {
        int m1 = 0;
        int m2 = 0;
        int m = 0;
        int[] sum = new int[merge1.length + merge2.length];
        while (m1 < merge1.length && m2 < merge2.length) {
            if (merge1[m1] < merge2[m2]) {
                sum[m++] = merge1[m1++];
            } else {
                sum[m++] = merge2[m2++];
            }
        }


        if (merge2.length <= m2) {
            for (int i = m1; i < merge1.length; i++) {
                sum[m++] = merge1[i];
            }
        } else {
            for (int i = m2; i < merge2.length; i++) {
                sum[m++] = merge2[i];
            }
        }

        return sum; // O(m1 + m2) time et O(m1+m2) space
    }
}
