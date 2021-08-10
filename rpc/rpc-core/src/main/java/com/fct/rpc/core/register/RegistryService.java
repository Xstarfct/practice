package com.fct.rpc.core.register;

import com.fct.rpc.core.common.ServiceInfo;

import java.io.IOException;

/**
 * 服务注册发现
 *
 * @author fct
 * @date 2021-08-09 14:22
 */
public interface RegistryService {

  void register(ServiceInfo serviceInfo) throws Exception;

  void unRegister(ServiceInfo serviceInfo) throws Exception;

  void destroy() throws IOException;
}
