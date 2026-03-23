package com.fmolnar.code.sorting;

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatingCharacter {
    // Problem: Given a string, find the first non-repeating character.
    char notRepeating(String s) {
        Map<Character, Integer> histo = new HashMap<>(); // space O(n)
        for (int i = 0; i < s.length(); i++) { // O(n) time
            histo.compute(s.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }

        for (int j = 0; j < s.length(); j++) { // O(n) time
            if (histo.get(s.charAt(j)) == 1) {
                return s.charAt(j);
            }
        }

        // alltogether O(2*n) time --> O(n) time space O(n)
        return '\0';
    }
}
