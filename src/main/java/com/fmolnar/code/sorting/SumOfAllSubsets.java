package com.fmolnar.code.sorting;

public class SumOfAllSubsets {

    int subSets(int[] array) { // O(n*2^n)
        int totalSum = 0;
        int numOfSubsets = 1 << array.length; // 2^n subsets
        System.out.println(numOfSubsets);

        for (int i = 0; i < numOfSubsets; i++) { // O(2^n) time
            int subsetSum = 0;

//            Loop over j:
//
//            j = 0: (3 & 1) != 0 → true → include arr[0]
//            j = 1: (3 & 2) != 0 → true → include arr[1]
//            j = 2: (3 & 4) != 0 → false → skip
//
//👉          Subset = {1, 2}
            for (int j = 0; j < array.length; j++) { // O(n) time
                if ((i & (1 << j)) != 0) {
                    subsetSum += array[j];
                }
            }

            totalSum += subsetSum;
        }

        return totalSum;
    }
}
