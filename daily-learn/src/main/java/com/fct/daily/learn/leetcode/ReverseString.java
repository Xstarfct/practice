package com.fct.daily.learn.leetcode;

import com.alibaba.fastjson.JSON;

/**
 * <pre>
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnhbqj/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * </pre>
 *
 * @author xstarfct
 * @version 2020-09-14 14:59
 */
public class ReverseString {
    
    public static void reverseString(char[] s) {
        int length = s.length;
        for (int index = 0; index < (length % 2 == 0 ? length / 2 : length / 2 + 1); index++) {
            char start = s[index];
            s[index] = s[length - index - 1];
            s[length - index - 1] = start;
        }
    }
    
    public static void reverseString2(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char tmp = s[left];
            s[left++] = s[right];
            s[right--] = tmp;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(5 / 2);
        System.out.println(4 / 2);
        char[] s = "hello".toCharArray();
        reverseString(s);
        System.out.println(JSON.toJSONString(s));
    
        char[] s2 = "hellom".toCharArray();
        reverseString(s2);
        System.out.println(JSON.toJSONString(s2));
    }
    
}
