package com.fct.daily.learn.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
 * </pre>
 *
 * @author xstarfct
 * @version 2020-09-14 14:21
 */
public class ContainsDuplicate {
    
    /**
     * 暴力破解法
     */
    public static boolean containsDuplicate1(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1; j < nums.length; j++) {
                if (nums[i] == nums[j] && i != j) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 使用hashset
     */
    public static boolean containsDuplicate2(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        int i = 0;
        while (i < nums.length) {
            int e = nums[i++];
            if (set.contains(e)) {
                return true;
            }
            set.add(e);
        }
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println(containsDuplicate1(new int[]{1, 2, 3, 1}));
        System.out.println(containsDuplicate2(new int[]{1, 2, 3, 1}));
    }
}
