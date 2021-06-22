package com.fct.daily.learn.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fct.daily.learn.utils.HttpProxyUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * TODO
 *
 * @author xstarfct
 * @version 2021-04-20 17:59
 */
public class HttpClientTest {
    
    /**
     * kkl_pre_gateway
     */
    private static final String PRE_URL = "http://kapimng.kaike.la/test/interface";
    
    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(20);
    
    /**
     *
     */
    @Test
    public void name() throws Exception {
        JSONObject jsonObject = JSON.parseObject(
                "{\"pid\":2,\"interfaceShortUrl\":\"cpcoreprod.LiveLessonPrivateFacade\",\"methodName\":\"listByLessonIdsFromLayerCache\",\"envId\":1,\"params\":{\"lessonIds\":[24692,24693,24694,24695,24696,24697,24698,24699,24700,24701,24702,24703,24704,24705,24706,24737,24738,24739,24740,24741,24742,24743,24744,24745,24746,24747,24748,24749,24750,24751,33307,33308,33309,33310,33311,33312,33313,33314,33315,33316,33317,33318,33319,33320,33321,33202,33203,33204,33205,33206,33207,33208,33209,33210,33211,33212,33213,33214,33215,33216,31291,31292,31293,31294,31295,31296,31297,31298,31299,31300,31301,31302,31025,31026,31027,31028,31029,31030,31031,31032,31033,31034,31035,31036]},\"loginToken\":\"\"}");
        IntStream.range(1, 10000).parallel().forEach(i->{
            try {
                SERVICE.submit(()->{
                    try {
                        HttpProxyUtils.executePostMethod(PRE_URL, jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        TimeUnit.MINUTES.sleep(10);
        
        
    }
}
