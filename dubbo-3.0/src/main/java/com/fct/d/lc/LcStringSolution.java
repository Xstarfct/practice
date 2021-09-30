package com.fct.d.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * Lc中有关字符串相关的题目
 *
 * @author haoyu
 * @version 1.0
 * @date 2021/09/26 14:17
 */
public class LcStringSolution {

  /**
   * 二维状态方程
   *
   * <pre> 最长公共子串
   *     https://leetcode-cn.com/problems/longest-common-subsequence/
   * </pre>
   */
  public int longestCommonPrefix(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      char c1 = text1.charAt(i - 1);
      for (int j = 1; j <= n; j++) {
        char c2 = text2.charAt(j - 1);
        if (c1 == c2) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }
    return dp[m][n];
  }

  /**
   * 字符串转数字
   *
   * <pre>
   * 函数myAtoi(string s) 的算法如下：
   *
   * 读入字符串并丢弃无用的前导空格
   * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
   * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
   * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
   * 如果整数数超过 32 位有符号整数范围 [−231, 231− 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231− 1 的整数应该被固定为 231− 1 。
   * 返回整数作为最终结果
   *
   * 来源：力扣（Leetcode）
   * 链接：https://leetcode-cn.com/problems/string-to-integer-atoi
   * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
   * </pre>
   *
   * @param str 待转字符串
   * @return 使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）
   */
  public int myAtoi(String str) {
    Automaton automaton = new Automaton();
    int length = str.length();
    for (int i = 0; i < length; ++i) {
      automaton.get(str.charAt(i));
    }
    return (int) (automaton.sign * automaton.ans);
  }

  static class Automaton {
    public int sign = 1;
    public long ans = 0;
    private String state = "start";
    //又穷自动状态机
    private final Map<String, String[]> table = new HashMap<String, String[]>() {{
      put("start", new String[]{"start", "signed", "in_number", "end"});
      put("signed", new String[]{"end", "end", "in_number", "end"});
      put("in_number", new String[]{"end", "end", "in_number", "end"});
      put("end", new String[]{"end", "end", "end", "end"});
    }};

    public void get(char c) {
      state = table.get(state)[get_col(c)];
      if ("in_number".equals(state)) {
        ans = ans * 10 + c - '0';
        ans = sign == 1 ? Math.min(ans, Integer.MAX_VALUE) : Math.min(ans, -(long) Integer.MIN_VALUE);
      } else if ("signed".equals(state)) {
        sign = c == '+' ? 1 : -1;
      }
    }

    private int get_col(char c) {
      if (c == ' ') {
        return 0;
      }
      if (c == '+' || c == '-') {
        return 1;
      }
      if (Character.isDigit(c)) {
        return 2;
      }
      return 3;
    }
  }

}
