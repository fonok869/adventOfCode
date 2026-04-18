package com.fmolnar.code.sorting;

public class PermutationString {

    public static void permute(String str, int l, int r) {
        char[] chars = str.toCharArray();

        permuteHelper(chars, l, r);
    }

    private static void permuteHelper(char[] chars, int l, int r) {
        if (l == r) {
            System.out.println(new String(chars));
        } else {
            for (int i = l; i <= r; i++) {
                swap(chars, l, i);
                permuteHelper(chars, l + 1, r);
                swap(chars, l, i); // backtrack
            }
        }
    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        String s = "ABC";
        permute(s, 0, s.length() - 1);
    }
}
