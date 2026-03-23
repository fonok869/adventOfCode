package com.fmolnar.code.sorting;

public class LinearSearch {


    //Problem: Given an array of integers and a target value,
    // find the index of the target value in the array. If the target is not present, return -1
    int getPosition(int array[], int target) {
        // O(1) space
        for (int index = 0; index < array.length; index++) { // O(n) time
            if (array[index] == target) {
                return index;
            }
        }
        return -1;
    }
}
