package com.fmolnar.code.leetcode.removeduplicate;

import java.util.Stack;

public class Solution {
    public String removeDuplicates(String s) {
        int index = 0;
        while (true) {
            // left
            if (0 < index && s.charAt(index) == s.charAt(index - 1)) {
                s = s.substring(0, Math.max(index - 1, 0)) +
                        s.substring(Math.min(s.length(), index + 1));
                index--;
            } else if (index < s.length() - 1 && s.charAt(index) == s.charAt(index + 1)) {
                s = s.substring(0, Math.max(index, 0)) +
                        s.substring(Math.min(s.length(), index + 2));
            } else {
                index++;
            }
            if (s.length() <= index) {
                break;
            }
        }
        return s;
    }

    public String removeDuplicates2(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character actualChar = s.charAt(i);
            if (!stack.isEmpty() && stack.peek() == actualChar) {
                stack.pop();
            } else {
                stack.push(actualChar);
            }
        }
        StringBuilder sb = new StringBuilder();
        stack.forEach(sb::append);
        return sb.toString();
    }
}
