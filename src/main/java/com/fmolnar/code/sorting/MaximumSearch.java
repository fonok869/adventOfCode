package com.fmolnar.code.sorting;

public class MaximumSearch {

    // Problem: Given an array of integers, find the maximum element in the array.
    int maximum(int[] array) {
        int maxActual = array[0]; // O(1) Space
        for (int index = 0; index < array.length; index++) { // O(n) time
            if (maxActual < array[index]) {
                maxActual = array[index];
            }
        }
        return maxActual;
    }
}
