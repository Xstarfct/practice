package com.fct.d.lc;

/**
 * 二分查找的一些题目
 *
 * @author fanchuantao
 */
public class BinarySearchSolution {

    /**
     * https://leetcode-cn.com/problems/binary-search/
     */
    public int search(int[] nums, int target) {
        if (nums.length <= 0) {
            return -1;
        }
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (r - l) / 2 + l;
            int num = nums[mid];
            if (num == target) {
                return mid;
            }
            if (num > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public int firstBadVersion(int n) {
        int left = 1, right = n;
        // 循环直至区间左右端点相同
        while (left < right) {
            // 防止计算时溢出
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                // 答案在区间 [left, mid] 中
                right = mid;
            } else {
                // 答案在区间 [mid+1, right] 中
                left = mid + 1;
            }
        }
        // 此时有 left == right，区间缩为一个点，即为答案
        return left;
    }

    private boolean isBadVersion(int mid) {
        return false;
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 请必须使用时间复杂度为 O(log n) 的算法
     *
     * 链接：https://leetcode-cn.com/problems/search-insert-position
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int num = nums[mid];
            if (num == target) {
                return mid;
            }
            if (num > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

}
