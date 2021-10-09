package com.fct.d.lc;

/**
 * 使用双指针套路解题
 *
 * @author fct
 * @version 1.0
 * @date 2021/10/09 10:49
 */
public class TwoPointerSolution {

  /**
   * https://leetcode-cn.com/problems/container-with-most-water/
   *
   * @param height 给出的数组
   * @return 返回所能盛水的最大容器的面积
   */
  public int maxArea(int[] height) {
    // 临界情况快速返回
    if (height.length <= 1) {
      return 0;
    }
    // 定义返回结果、左右双指针
    int result = 0, left = 0, right = height.length - 1;
    while (left < right) {
      result = Math.max(result, Math.min(height[left], height[right]) * (right - left));
      if (height[left] < height[right]) {
        left++;
      } else {
        right--;
      }
    }
    return result;
  }

}
