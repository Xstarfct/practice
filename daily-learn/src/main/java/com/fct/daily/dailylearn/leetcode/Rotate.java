package com.fct.daily.dailylearn.leetcode;

import com.alibaba.fastjson.JSON;

/**
 * <pre>旋转数组
 *     给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 * </pre>
 *
 * @author xstarfct
 * @version 2020-09-14 14:20
 */
public class Rotate {
    
    /**
     * [1,2,3,4,5,6,7], k=3
     * <pre>index
     *     0,1,2,3,4,5,6
     *     4,5,6,0,1,2,3
     * </pre>
     * <p>
     * [5,6,7,1,2,3,4]
     */
    public static void rotate(int[] nums, int k) {
        int length = nums.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i + k < nums.length ? i + k : (i + k) % nums.length] = nums[i];
        }
        System.arraycopy(result, 0, nums, 0, length);
    }
    
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println(JSON.toJSONString(nums));
        int[] nums2 = new int[]{-100, -19, 199, 9};
        rotate(nums2, 2);
        System.out.println(JSON.toJSONString(nums2));
    }
}
