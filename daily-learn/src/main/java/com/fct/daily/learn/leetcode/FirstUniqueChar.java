package com.fct.daily.learn.leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第一个只出现一次的字符
 *
 * <pre>
 *     在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。
 * </pre>
 *
 * @author xstarfct
 * @version 2021-04-09 20:20
 */
public class FirstUniqueChar {
    
    
    public static void main1(String[] args) {
        String str = "abaccdeff";
        Map<Character, Integer> map = new LinkedHashMap<>();//有序
        for (char c : str.toCharArray()) {
            Integer value = map.get(c);
            if (value == null) {
                map.put(c, 1);
            } else {
                map.replace(c, value + 1);
            }
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
                return;
            }
        }
        System.out.println(' ');
    }
    
    public static void main(String[] args) {
        isPalindrome2(100);
        isPalindrome2(12321);
    }
    
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x < 10) {
            return true;
        }
        String s = String.valueOf(x);
        int length = s.length();
        
        int m = length % 2 == 1 ? (length+1) / 2 : length / 2;
    
        String s1 = s.substring(0, m);
        String s2 = s.substring(m, length -1);
        
        
    
        return false;
    }
    
    
    public static boolean isPalindrome2(int x) {
        // 特殊情况：
        // 如上所述，当 x < 0 时，x 不是回文数。
        // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
        // 则其第一位数字也应该是 0
        // 只有 0 满足这一属性
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        System.out.println(revertedNumber);
        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == revertedNumber || x == revertedNumber / 10;
    }
    
    
    
    
    
    
}
