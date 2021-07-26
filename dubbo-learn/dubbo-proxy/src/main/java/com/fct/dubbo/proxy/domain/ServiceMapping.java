package com.fct.dubbo.proxy.domain;

import com.fct.dubbo.proxy.ProxyConfig;
import lombok.Data;

import java.util.List;

@Data
public class ServiceMapping {

  List<ProxyConfig.Mapping> mappings;

  String env;

  Integer threadCount;
}
