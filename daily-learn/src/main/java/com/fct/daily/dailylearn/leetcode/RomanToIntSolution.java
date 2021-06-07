package com.fct.daily.dailylearn.leetcode;

/**
 * RomanToIntSolution
 *
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/roman-to-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 示例 1:
 *
 * 输入: "III"
 * 输出: 3
 * 示例 2:
 *
 * 输入: "IV"
 * 输出: 4
 * 示例 3:
 *
 * 输入: "IX"
 * 输出: 9
 * 示例 4:
 *
 * 输入: "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 * 示例 5:
 *
 * 输入: "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 *  
 *
 * 提示：
 *
 * 1 <= s.length <= 15
 * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
 * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
 * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
 * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
 * 关于罗马数字的详尽书写规则，可以参考
 * https://zh.wikipedia.org/wiki/%E7%BD%97%E9%A9%AC%E6%95%B0%E5%AD%97
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/roman-to-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xstarfct
 * @version 2021-05-13 11:46
 */
public class RomanToIntSolution {
    
    /**
     * 右加左减：
     * 在较大的罗马数字的右边记上较小的罗马数字，表示大数字加小数字。
     * 在较大的罗马数字的左边记上较小的罗马数字，表示大数字减小数字。
     * 左减的数字有限制，仅限于Ⅰ、Ⅹ、Ⅽ。比如45是ⅩⅬⅤ，不用ⅤⅬ。
     * 但是，左减时不可跨越一个位值。比如，99是ⅩⅭⅨ（{\displaystyle [100-10]+[10-1]}[100-10]+[10-1]），不用ⅠⅭ（{\displaystyle 100-1}100-1）。（等同于阿拉伯数字每位数字分别表示。）
     * 左减数字必须为一位，比如8是Ⅷ，不用ⅡⅩ。
     * 右加数字不连续超过三位，比如14是ⅪⅤ，不用ⅪⅢ。（见下方“数码限制”一项。）
     */
    public int romanToInt(String s) {
        int sum = 0;
        int pre = getValue(s.charAt(0));
        if (s.length() == 1) {
            return pre;
        }
        for(int i = 1;i < s.length(); i ++) {
            int current = getValue(s.charAt(i));
            if(pre < current) {
                sum -= pre;
            } else {
                sum += pre;
            }
            pre = current;
        }
        sum += pre;
        return sum;
    }
    
    
    /**
     *  根据字符获取对应的数字
     * @param c
     * @return
     */
    public int getValue(char c){
        switch (c){
            case 'I':return 1;
            case 'V':return 5;
            case 'X':return 10;
            case 'L':return 50;
            case 'C':return 100;
            case 'D':return 500;
            case 'M':return 1000;
        }
        return 0;
    }
    
    public static void main(String[] args) {
        int i = new RomanToIntSolution().romanToInt("MCMXCIV");
        System.out.println(i);
    }
    
    
}
