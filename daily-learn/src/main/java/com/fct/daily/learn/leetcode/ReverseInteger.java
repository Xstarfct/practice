package com.fct.daily.learn.leetcode;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 123 输出: 321  示例 2:
 * <p>
 * 输入: -123 输出: -321 示例 3:
 * <p>
 * 输入: 120 输出: 21 注意:
 * <p>
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 * <p>
 * 作者：力扣 (LeetCode) 链接：https://leetcode-cn.com/problems/reverse-integer/ 来源：力扣（LeetCode） 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * @author xstarfct
 * @version 2020-09-25 11:52
 */
public class ReverseInteger {
    
    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            result = result * 10 + pop;
        }
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(reverse(Integer.MAX_VALUE / 10));
    }
}
