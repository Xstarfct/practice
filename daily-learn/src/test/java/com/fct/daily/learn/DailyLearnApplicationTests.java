package com.fct.daily.learn;

import javax.annotation.Resource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DailyLearnApplicationTests {
    
    @Resource
    protected ApplicationContext applicationContext;
}
