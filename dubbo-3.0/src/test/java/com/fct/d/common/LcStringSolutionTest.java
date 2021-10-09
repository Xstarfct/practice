package com.fct.d.common;

import com.fct.d.lc.LcStringSolution;
import org.junit.Assert;
import org.junit.Test;

/**
 * leetcode有关字符串相关的测试
 *
 * @author fct
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
    Assert.assertEquals(123, lcStringSolution.myAtoi("123"));
    Assert.assertEquals(-123, lcStringSolution.myAtoi("-123a"));
    Assert.assertEquals(-12, lcStringSolution.myAtoi("-12a3a"));
    Assert.assertEquals(-12, lcStringSolution.myAtoi("-12a3a"));
    Assert.assertEquals(0, lcStringSolution.myAtoi("-a12a3a"));
    Assert.assertEquals(0, lcStringSolution.myAtoi("-a12"));
  }

  @Test
  public void countStrTest() {
    Assert.assertEquals(3, lcStringSolution.countSegments("abc, a ,b"));
  }
}
