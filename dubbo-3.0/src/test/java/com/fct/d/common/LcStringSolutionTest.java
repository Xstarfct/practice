package com.fct.d.common;

import com.fct.d.lc.LcStringSolution;
import org.junit.Test;

/**
 * leetcode有关字符串相关的测试
 *
 * @author haoyu
 * @version 1.0
 * @date 2021/09/30 15:53
 */
public class LcStringSolutionTest {

  private static final LcStringSolution lcStringSolution = new LcStringSolution();

  @Test
  public void longestCommonPrefixTest() {
    System.out.println(lcStringSolution.longestCommonPrefix("abcde", "cdefgh"));
  }

  @Test
  public void myAotiTest() {
    System.out.println(lcStringSolution.myAtoi("123"));
    System.out.println(lcStringSolution.myAtoi("-123a"));
    System.out.println(lcStringSolution.myAtoi("-12a3a"));
    System.out.println(lcStringSolution.myAtoi("-12a3a"));
    System.out.println(lcStringSolution.myAtoi("-a12a3a"));
    System.out.println(lcStringSolution.myAtoi("-a12"));
  }
}
