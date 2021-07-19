package com.fct.d.lc;

/**
 * FindMiddleNumber
 *
 * @author fct
 * @date 2021-07-16 14:15
 */
public class FindMiddleNumber {

  /** 给定一个有序的数组，查找 value 是否在数组中，不存在返回 -1。 注意：题目保证数组不为空，且 n 大于等于 1 ，以下问题默认相同 */
  int binarySearch(int[] array, int value) {
    int left = 0;
    int right = array.length - 1;
    // 循环条件，适时而变
    while (left <= right) {
      // 防止溢出，移位也更高效。同时，每次循环都需要更新。
      int middle = left + ((right - left) >> 1);
      if (array[middle] > value) {
        right = middle - 1;
      } else if (array[middle] < value) {
        left = middle + 1;
      } else {
        return middle;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(
        new FindMiddleNumber().binarySearch(new int[] {1, 2, 5, 8, 9, 10, 12, 13, 16}, 10));
  }
}
