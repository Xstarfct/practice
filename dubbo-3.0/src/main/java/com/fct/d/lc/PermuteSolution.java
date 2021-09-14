package com.fct.d.lc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * PermuteSolution,全排列
 *
 * <pre>
 *   https://leetcode-cn.com/problems/permutations/solution/quan-pai-lie-by-leetcode-solution-2/
 * </pre>
 *
 * @author fct
 * @version 2021-09-14 16:00
 */
public class PermuteSolution {

  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int len = nums.length;
    if (len == 0) {
      return result;
    }
    /*深搜的状态变量*/
    Deque<Integer> path = new ArrayDeque<>(); /*当前选的数字*/
    boolean[] used = new boolean[len]; /*当前遍历路径节点是否使用的标记*/
    /*遍历的深度depth*/
    dfs(nums, 0, path, used, result);
    return result;
  }

  /**
   * 深搜遍历过程
   *
   * @param nums 待排列的数字
   * @param depth 深度
   * @param path 选择路径的队列
   * @param used 是否使用过
   * @param result 结果集
   */
  private void dfs(int[] nums, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> result) {
    int length = nums.length;
    if (depth == length) {
      result.add(new ArrayList<>(path));
      return;
    }
    for (int i = 0; i < length; i++) {
      if (used[i]) {
        continue;
      }
      // 入栈
      path.addLast(nums[i]);
      used[i] = true;
      dfs(nums, depth + 1, path, used, result);
      // 回溯
      path.removeLast();
      used[i] = false;
    }
  }
}
