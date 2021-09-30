package com.fct.d.common;

import com.fct.d.NoSpringBaseTest;
import com.fct.d.lc.PermuteSolution;
import org.junit.Test;

/**
 * PermuteSolutionTest
 *
 * @author fct
 * @version 2021-09-14 16:04
 */
public class PermuteSolutionTest extends NoSpringBaseTest {

  @Test
  public void testPermute1() {
    printJson(new PermuteSolution().permute(new int[]{1,2,3}));
    System.out.println(Math.addExact(1,3));

  }
}
