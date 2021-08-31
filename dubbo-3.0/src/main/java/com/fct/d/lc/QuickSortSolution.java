package com.fct.d.lc;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * QuickSortSolution
 *
 * @author fct
 * @version 2021-08-31 12:21
 */
public class QuickSortSolution {


  public static List<Integer> sort(List<Integer> nums) {
    if (nums == null || nums.size() <= 1) {
      return nums;
    }
    Integer base = nums.get(0);
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();
    for (int i = 1; i < nums.size(); i++) {
      Integer current = nums.get(i);
      if (current > base) {
        right.add(current);
      } else {
        left.add(current);
      }
    }
    List <Integer> result = sort(left);
    result.add(base);
    result.addAll(sort(right));
    return result;
  }

  public static void main(String[] args) {
    System.out.println(sort(Lists.newArrayList(1,7,2,3,6,5,0,10,9)));

    System.out.println(Arrays.toString(new QuickSortSolution().sortArray(new int[]{1, 7, 2, 3, 6, 5, 0, 10, 9})));
    for (int i = 1; i <= 12; i++) {
      System.out.print(new QuickSortSolution().fun(i) + " ");
    }
  }

  public int[] sortArray(int[] nums) {
    quickSort(nums, 0, nums.length);
    return nums;
  }

  public void quickSort(int[] nums, int left, int right) {
    if (right - left <= 1) {
      return;
    }
    // 基准值取第一个
    int base = nums[left];
    // 指针
    int lowIndex = left + 1;
    // 理出基准值小的并交换
    for (int i = left + 1;i < right;i++) {
      if (nums[i] < base) {
        swap(nums, i, lowIndex);
        lowIndex++;
      }
    }
    swap(nums, left, lowIndex - 1);
    // 递归
    quickSort(nums, left, lowIndex - 1);
    quickSort(nums, lowIndex, right);
  }

  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  //斐波那契数列
  public int fun(int n) {
    if (n == 1 || n == 2) {
      return 1;
    }
    return fun(n-1) + fun(n-2);
  }

  public int[] twoSum(int[] nums, int target) {
    int[] result = new int[2];
    Map<Integer,Integer> map = new HashMap<>();
    for (int i = 0;i<nums.length;i++) {
      map.put(nums[i], i);
    }
    for (int i = 0;i<nums.length;i++) {
      int value = target - nums[i];
      if (map.containsKey(value)) {
        result[0] = i;
        result[1] = map.get(value);
        return result;
      }
    }

    return result;
  }


}
