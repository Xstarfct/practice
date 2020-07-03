package com.fct.dubbo.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConfigController
 *
 * @author xstarfct
 * @version 2020-07-03 4:44 下午
 */
@RestController
@RequestMapping("config")
@Slf4j
@NacosPropertySource(dataId = "dubbo-learn-provider.yml", autoRefreshed = true)
public class ConfigController {
    
    @NacosValue(value = "${test.age}", autoRefreshed = true)
    private Integer age;
    
    @NacosValue(value = "${test.name}", autoRefreshed = true)
    private String name;
    
    @GetMapping("/get/name")
    public ResponseEntity<List<String>> name() {
        return ResponseEntity.ok(Arrays.asList(name, age.toString()));
    }
}
