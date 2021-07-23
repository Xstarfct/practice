package com.fct.dubbo.proxy.domain;

import com.fct.dubbo.proxy.support.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BaseResponseCode implements ResponseCode {
  SUCCESS("0", "SUCCESS", "处理成功"),
  SYSTEM_ERROR("1001001", "API_SYSTEM_ERROR", "网关处理请求发生异常，请联系管理员！"),
  FATAL_ERROR("-1", "API_FATAL_ERROR", "无效的服务请求，请检查！"),
  PRC_ERROR("-2", "API_PRC_ERROR", "远程调用异常：%s"),
  ;
  private final String codeNumber;
  private final String code;
  private final String message;
}
