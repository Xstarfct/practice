package com.fct.practice;

import javax.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class PracticeApplicationTests {
    
    @Resource
    protected ApplicationContext applicationContext;
    
}
