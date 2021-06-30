package com.fct.d.common;

import com.fct.d.NoSpringBaseTest;
import com.fct.d.lc.RomanNumber;
import org.junit.Test;

/**
 * NumberTest[isPalindrome,]
 *
 * @author fct
 * @version 2021-06-23 17:22
 */
public class NumberTest extends NoSpringBaseTest {

  @Test
  public void testIsPalindrome() {
    System.out.println(isPalindrome(1221));
    System.out.println(isPalindrome(8));
    System.out.println(isPalindrome(88));
    System.out.println(isPalindrome(81));
    System.out.println(isPalindrome(181));
    System.out.println(isPalindrome(188888881));
  }

  public boolean isPalindrome(int x) {
    if (x < 0) {
      return false;
    }
    if (x < 10) {
      return true;
    }
    int a = 0, b = x;
    while (x > 0) {
      a = a * 10 + x % 10;
      x /= 10;
    }
    return a == b;
  }

  @Test
  public void test2() {
    int i = 1221 / 10;
    System.out.println(i % 10);
  }

  @Test
  public void printNumber() {
    for (int i = 0; i < 500; i++) {
      new Thread(() -> {}).run();
    }
  }

  @Test
  public void find2ArrayMinTest() {
    int[][] a = new int[][] {{1, 6, -3, 0}, {7, 5, 10, 33}, {2, 12, 9, 4}};
    System.out.println(find2ArrayMinTest(a));
  }

  static int find2ArrayMinTest(int[][] x) {
    int m = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        if (x[i][j] < m) {
          m = x[i][j];
        }
      }
    }
    return m;
  }

  @Test
  public void romanNumberTest() {
    System.out.println(new RomanNumber().romanToInt("CCVI"));
  }

}
