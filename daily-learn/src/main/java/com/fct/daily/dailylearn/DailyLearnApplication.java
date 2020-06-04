package com.fct.daily.dailylearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DailyLearnApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DailyLearnApplication.class, args);
    }
    
}
