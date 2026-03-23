package com.fmolnar.code.sorting;

public class SumOfAllSubsetsSum {

    int subSets(int[] array) { //
        int sum = 0;
        int allSubsets = 1 << array.length;
        for (int i = 0; i < allSubsets; i++) {
            int actualSubSetSum = 0;

            // iterate all possible subsets
            for (int j = 0; j < array.length; j++) {
                if ((i & (1 << j)) != 0) {
                    actualSubSetSum += array[j];

                }
            }
            sum += actualSubSetSum;


        }
        return sum;
    }
}
