package com.fct.d.common;

import com.fct.d.NoSpringBaseTest;
import org.junit.Test;

/**
 * SortTest
 *
 * <pre>
 *     https://sort.hust.cc/6.quicksort
 * </pre>
 *
 * @author fct
 * @version 2021-06-23 9:51
 */
public class SortTest extends NoSpringBaseTest {

  @Test
  public void testSort() {
    int[] nums = new int[] {10, 9, 6, 5, 8, 7, 11, 200, 87, 34, 29};
    bubbleSort(nums);
  }

  private void swap(int[] nums, int j) {
    int temp = nums[j];
    nums[j] = nums[j + 1];
    nums[j + 1] = temp;
  }

  public void bubbleSort(int[] nums) {
    int length = nums.length;
    for (int i = 0; i < length - 1; i++) {
      for (int j = 0; j < length - 1; j++) {
        if (nums[j] > nums[j + 1]) {
          swap(nums, j);
        }
      }
    }
    printJson(nums);
  }
}
