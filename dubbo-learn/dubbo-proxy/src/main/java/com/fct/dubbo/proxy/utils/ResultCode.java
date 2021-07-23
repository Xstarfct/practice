package com.fct.dubbo.proxy.utils;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
  OK(0, "success"),

  TIMEOUT(1, "timeout"),

  BIZ_ERROR(2, "BIZ_ERROR"),

  NETWORK_ERROR(3, "NETWORK_ERROR"),

  SERIALIZATION(4, "SERIALIZATION");

  private final int code;
  private final String message;

  public static void main(String[] args) {
    System.out.println(JSON.toJSONString(ResultCode.TIMEOUT));
  }
}
