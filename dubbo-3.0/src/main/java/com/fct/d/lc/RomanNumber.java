package com.fct.d.lc;

/**
 * RomanNumber
 *
 * @author fct
 * @version 2021-06-28 10:46
 */
public class RomanNumber {

  public int romanToInt(String s) {
    int result = 0;
    String romanNumber =
        s.replaceAll("IV", "a")
            .replaceAll("IX", "b")
            .replaceAll("XL", "c")
            .replaceAll("XC", "d")
            .replaceAll("CD", "e")
            .replaceAll("CM", "f");

    for (char c : romanNumber.toCharArray()) {
      result += romanMap(c);
    }
    return result;
  }

  /**
   * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4
   * 。同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
   *
   * <p>
   *   I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
   *   X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
   *   C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
   *
   * <p>来源：力扣（LeetCode） 链接：https://leetcode-cn.com/problems/roman-to-integer
   * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
   *
   * @param c
   * @return
   */
  int romanMap(char c) {
    switch (c) {
      case 'I':
        return 1;
      case 'V':
        return 5;
      case 'X':
        return 10;
      case 'L':
        return 50;
      case 'C':
        return 100;
      case 'D':
        return 500;
      case 'M':
        return 1000;
      case 'a':/*VI->4*/
        return 4;
      case 'b':/*XI->9*/
        return 9;
      case 'c':/*LX->40*/
        return 40;
      case 'd':/*CX->40*/
        return 90;
      case 'e':/*DC->400*/
        return 400;
      case 'f':/*MC->900*/
        return 900;
      default:
        return 0;
    }
  }
}
