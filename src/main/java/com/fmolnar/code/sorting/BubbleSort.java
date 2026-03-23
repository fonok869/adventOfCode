package com.fmolnar.code.sorting;

public class BubbleSort {

    int[] bubbleSort(int[] array) { // O(n*n) time // space O(1)
        for (int i = array.length - 1; 0 <= i; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int old = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = old;
                }
            }
        }
        return array;
    }
}
