package com.fmolnar.code.sorting;

public class BinarySearch {

    // Problem: Given a sorted array of integers and a target value,
    // find the index of the target value using binary search. If the target is not present, return -1
    int binarySearch(int[] array, int target) {
        int left = 0; // O(1) space
        int right = array.length - 1; // O(1) space
        while (left <= right) { // O(log(n)) time
            int middle = (left + right) / 2;
            if (array[middle] == target) {
                return middle;
            }
            if (target < array[middle]) {
                right = middle;
            } else {
                left = middle;
            }
        }
        return -1;
    }
}
