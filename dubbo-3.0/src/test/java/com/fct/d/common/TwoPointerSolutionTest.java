package com.fct.d.common;

import com.fct.d.lc.TwoPointerSolution;
import org.junit.Test;

/**
 * TwoPointerSolutionTest
 *
 * @author haoyu
 * @version 1.0
 * @date 2021/10/09 11:15
 */
public class TwoPointerSolutionTest {

  private final TwoPointerSolution twoPointerSolution = new TwoPointerSolution();

  @Test
  public void maxAreaTest() {
    System.out.println(twoPointerSolution.maxArea(new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7}));
    System.out.println(twoPointerSolution.maxArea(new int[] {1, 8, 6, 2, 5, 4, 8, 3, 10}));
    System.out.println(twoPointerSolution.maxArea(new int[] {9, 8, 6, 2, 5, 4, 8, 3, 10}));
  }
}
