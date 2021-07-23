package com.fct.dubbo.proxy.dao;

import com.fct.dubbo.proxy.Config;

import java.util.List;

public class ServiceMapping {

    List<Config.Mapping> mappings;

    public List<Config.Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<Config.Mapping> mappings) {
        this.mappings = mappings;
    }
}
