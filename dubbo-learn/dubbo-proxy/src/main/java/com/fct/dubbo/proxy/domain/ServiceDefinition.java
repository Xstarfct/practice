package com.fct.dubbo.proxy.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class ServiceDefinition {

  private String application;
  private String serviceID;
  private String methodName;
  private Object[] paramValues;
  private String[] paramTypes;
  private Map<String, String> attachments;
}
