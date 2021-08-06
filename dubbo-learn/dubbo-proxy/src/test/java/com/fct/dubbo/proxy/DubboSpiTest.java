package com.fct.dubbo.proxy;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author fct
 * @date 2021-07-30 17:59
 */
public class DubboSpiTest {

  @Test
  public void sayHello() throws Exception {
    URL url = URL.valueOf("dubbo://localhost/test");
    Protocol adaptiveProtocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
    Class<?> type = null;
    adaptiveProtocol.refer(type, url);
  }

}
