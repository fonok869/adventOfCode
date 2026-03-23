package com.fmolnar.code.leetcode.nails;

import java.util.Arrays;

public class Nails {
    public static int maxEqualNails(int[] nails, int k) {
        Arrays.sort(nails);

        int left = 0;
        long total = 0;
        int max = 1;

        for (int right = 0; right < nails.length; right++) {
            total += nails[right];

            while ((long) nails[right] * (right - left + 1) - total > k) {
                total -= nails[left];
                left++;
            }

            max = Math.max(max, right - left + 1);
        }

        return max;
    }
}
