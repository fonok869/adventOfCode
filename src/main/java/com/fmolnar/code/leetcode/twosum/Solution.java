package com.fmolnar.code.leetcode.twosum;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> position = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            int actual = nums[index];
            int diff = target - actual;
            if (position.containsKey(diff)) {
                return new int[]{position.get(diff), index};
            }
            position.put(actual, index);
        }
        return new int[2];
    }
}
