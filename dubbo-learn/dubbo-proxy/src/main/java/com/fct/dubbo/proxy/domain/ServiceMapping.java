package com.fct.dubbo.proxy.domain;

import com.fct.dubbo.proxy.Config;
import lombok.Data;

import java.util.List;

@Data
public class ServiceMapping {

  List<Config.Mapping> mappings;

  String env;

  Integer threadCount;
}
