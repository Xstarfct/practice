package com.fct.daily.dailylearn.leetcode;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角
 *
 * @author xstarfct
 * @version 2020-09-14 16:37
 */
public class Triangle {
    
    
    public static List<List<Integer>> triangle(int num) {
        List<List<Integer>> triangle = new ArrayList<>(num);
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);
        if (num <= 1) {
            return triangle;
        }
        for (int i = 1; i < num; i++) {
            List<Integer> pre = triangle.get(i - 1);
            List<Integer> current = new ArrayList<>();
            current.add(1);
            for (int j = 1; j < i; j++) {
                current.add(j, pre.get(j - 1) + pre.get(j));
            }
            current.add(1);
            triangle.add(current);
        }
        return triangle;
    }
    
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(triangle(2)));
    }
    
    
}
