package com.fct.dubbo.proxy.domain;

import com.fct.dubbo.proxy.support.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * ApiProxyResult
 *
 * @author fct
 * @date 2021-07-23 17:48
 */
@Data
@AllArgsConstructor
public class ApiProxyResult implements Serializable {
  private boolean success;
  private String code;
  private String message;
  private String sign; /* TODO 需要做鉴权*/
  private Object data;
  private boolean serResult;

  public static ApiProxyResult success() {
    return new ApiProxyResult(
        true,
        BaseResponseCode.SUCCESS.getCodeNumber(),
        BaseResponseCode.SUCCESS.getMessage(),
        null,
        null,
        false);
  }

  public static ApiProxyResult success(Object data) {
    return new ApiProxyResult(
        true,
        BaseResponseCode.SUCCESS.getCodeNumber(),
        BaseResponseCode.SUCCESS.getMessage(),
        null,
        data,
        true);
  }

  public static ApiProxyResult fail(ResponseCode responseCode) {
    return new ApiProxyResult(
        false, responseCode.getCodeNumber(), responseCode.getMessage(), null, null, false);
  }

  public static ApiProxyResult formatErrMsg(ResponseCode responseCode, Object... args) {
    return new ApiProxyResult(
        false,
        responseCode.getCodeNumber(),
        String.format(responseCode.getMessage(), args),
        null,
        null,
        false);
  }

  @SuppressWarnings("rawtypes")
  public static ApiProxyResult markResult(Object obj) {
    if (obj == null) {
      return ApiProxyResult.success();
    } else if (obj instanceof ApiProxyResult) {
      return (ApiProxyResult) obj;
    }
    Map resultMap = (Map) obj;
    final Object success = Optional.ofNullable(resultMap.remove("success")).orElse(Boolean.FALSE);
    final Object code =
        Optional.ofNullable(resultMap.get("nCode"))
            .map(
                o -> {
                  resultMap.remove("code");
                  return o;
                })
            .orElse(BaseResponseCode.SUCCESS.getCodeNumber());
    final Object message =
        Optional.ofNullable(resultMap.remove("message"))
            .orElse(BaseResponseCode.SUCCESS.getMessage());
    resultMap.remove("class");
    // TODO 生成签名
    //    String signString = JSONObject.toJSONString(resultMap);
    //    String sign = MD5.getStrUpper(signString);

    return new ApiProxyResult(
        (boolean) success, String.valueOf(code), (String) message, "", resultMap, true);
  }
}
