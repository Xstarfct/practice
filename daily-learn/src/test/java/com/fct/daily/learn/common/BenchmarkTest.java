package com.fct.daily.learn.common;

import com.fct.daily.learn.utils.GenerateTraceIdUtil;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * 性能压力测试
 *
 * @author xstarfct
 * @version 2020-09-11 17:26
 */
public class BenchmarkTest {
    
    @Rule
    public ContiPerfRule rule = new ContiPerfRule();
    
    /**
     * 30个线程 执行10000000, 生成测试报告 ./target/contiperf-report/index.html
     * <pre> 测试结果
     *     com.fct.daily.dailylearn.common.BenchmarkTest.test
     *     samples: 100000000
     *     max:     352
     *     average: 0.00345048
     *     median:  0
     * </pre>
     */
    //
    @Test
    @PerfTest(invocations = 100000000, threads = 30)
    public void test() {
        GenerateTraceIdUtil.getTraceId();
    }
}
