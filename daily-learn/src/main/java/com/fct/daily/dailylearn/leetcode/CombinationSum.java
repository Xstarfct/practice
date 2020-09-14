package com.fct.daily.dailylearn.leetcode;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *     所有数字（包括 target）都是正整数。
 *     解集不能包含重复的组合。
 * 示例：
 *  输入：candidates = [2,3,6,7], target = 7,
 *  所求解集为：
 * [
 *   [7],
 *   [2,2,3]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * </pre>
 *
 * @author xstarfct
 * @version 2020-09-14 15:31
 */
public class CombinationSum {
    
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, target, result, combine, 0);
        return result;
    }
    
    private static void dfs(int[] candidates, int target, List<List<Integer>> result, List<Integer> combine, int index) {
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(combine));
            return;
        }
        // 直接跳过
        dfs(candidates, target, result, combine, index + 1);
        // 选择当前数
        if (target - candidates[index] >= 0) {
            combine.add(candidates[index]);
            dfs(candidates, target - candidates[index], result, combine, index);
            combine.remove(combine.size() - 1);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(combinationSum(new int[]{2, 3, 4, 7}, 7)));
    }
    
}
