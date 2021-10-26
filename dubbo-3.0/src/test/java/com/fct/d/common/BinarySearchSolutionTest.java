package com.fct.d.common;

import com.fct.d.lc.BinarySearchSolution;
import org.junit.Test;

/**
 * @author fanchuantao
 * @date 2021/10/26
 */
public class BinarySearchSolutionTest {

    private static final BinarySearchSolution solution = new BinarySearchSolution();

    @Test
    public void searchTest() {
        int[] nums = {1, 2, 4, 5, 7, 9, 12, 16, 45, 59, 100};
        System.out.println(solution.search(nums, 7));
        System.out.println(solution.search(nums, 1));
        System.out.println(solution.search(nums, 0));
        System.out.println(solution.search(nums, 59));
        System.out.println(solution.search(nums, 100));
    }

    @Test
    public void searchInsertTest() {
        int[] nums = {1, 2, 4, 5, 7, 9, 12, 16, 45, 59, 100};
        System.out.println(solution.searchInsert(nums, 7));
        System.out.println(solution.search(nums, 1));
        System.out.println(solution.search(nums, 0));
        System.out.println(solution.search(nums, 58));
        System.out.println(solution.search(nums, 101));
    }

}
