package com.fct.d;

import com.alibaba.fastjson.JSON;

/**
 * BaseTest
 *
 * @author fct
 * @version 2021-06-23 9:49
 */
public class NoSpringBaseTest {

    protected void printJson(Object obj) {
        System.out.println(JSON.toJSONString(obj));
    }

}
