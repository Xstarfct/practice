package com.fct.daily.learn.utils;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列生成器
 * <pre>
 * 结构图：
 *  1位，标志位           41位，毫秒时间戳                           10位，服务器序号   9位， 自增序列
 *    ___  ______________________________________________       ____________ __________
 *    | |  |                                            |       |          | |        |
 *     0 - 0000000000 0000000000 0000000000 0000000000 0 - 000 - 0000000000 - 000000000
 *                                                        |   |
 *                                                     3位，运行环境序号
 * </pre>
 * <p>
 * 特性：
 * <pre>
 *      <li>可持续使用69年</li>
 *      <li>支持“DEBUG”、“DEV”、“TEST”、“PRE”、“PROD”五种运行环境</li>
 *      <li>每种环境支持1024个服务器</li>
 *      <li>当前设计有效TPS 50w,最大可支持1500W+TPS</li>
 *      <li>一行获取，零侵入，零配置，开箱即用</li>
 *      <li>全局唯一</li>
 *      <li>单调递增，适用于平衡二叉树（B树、红黑树）数据结构存储</li>
 *      <li>信息安全，不携带mac信息，不连续递增</li>
 *      <li>一旦启动，即脱离任何外部依赖，不受外部环境影响</li>
 *      <li>心跳上传本地时间</li>
 *      <li>支持注册服务器(目前是ZK)重连</li>
 * </pre>
 * <p>
 * <a style='color:red;font-weight:800;'>注意：服务器时间回调5秒以上即停止服务，直到当前时间大于最后一次序列生成时间。如果不任意调整时间，不会自动发生时间回调的情况。</a>
 *
 * @author kunpeng.Liu
 * @version $Id: Sahara.java, v 0.1 2017年6月8日 下午9:08:30 kunpeng.Liu Exp $
 */
public class Sahara {
    
    protected static final Logger LOG = LoggerFactory.getLogger(Sahara.class);
    
    public static Sahara instance = new Sahara();
    
    /**
     * 服务器序号
     */
    private long workerId;
    /**
     * 运行模式序号
     */
    private long runModeId;
    /**
     * 自增序列
     */
    private long sequence = 0L;
    /**
     * 序列启用时间戳： 1991-05-12 00:00:00 000
     */
    private long twepoch = 673977600000L;
    
    /**
     * 运行环境位数
     */
    private long runModeIdBits = 3L;
    /**
     * 服务器序号位数
     */
    private long workerIdBits = 10L;
    /**
     * 序列所占位数
     */
    private long sequenceBits = 9L;
    /**
     * 相同环境支持最大服务器数 -- 1024
     */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 支持环境数 -- 7
     */
    private long maxRunModeId = -1L ^ (-1L << runModeIdBits);
    
    /**
     * 服务器序号偏移量
     */
    private long workerIdShift = sequenceBits;
    /**
     * 运行环境偏移量
     */
    private long runModeIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳偏移量
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + runModeIdBits;
    
    /**
     * 序列掩码
     */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    
    private long lastTimestamp = -1L;
    
    private Random random = new Random();
    
    /**
     * 时间回调容忍  5s
     */
    private final static long CALL_BACK_TOLERANCE = 1000L;
    
    private Sahara() {
    }
    
    ;
    
    public void initial(long workerId, long runModeId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("服务器序号workId必须在%d到0之间", maxWorkerId));
        }
        if (runModeId > maxRunModeId || runModeId < 0) {
            throw new IllegalArgumentException(String.format("环境序号runModeId必须在%d到0之间", maxRunModeId));
        }
        this.workerId = workerId;
        this.runModeId = runModeId;
        LOG.info(String.format("sahara初始化完成，环境序号： %d, 服务器序号： %d", runModeId, workerId));
    }
    
    /**
     * 获取一个18长度的long型序列
     * <p>
     * 系统时间如果发生回调，那么生成的序列号可能会重复的风险，所以这里发现时间回调超过容忍范围，则会报异常
     *
     * @return
     */
    public synchronized long getSand() {
        //当前时间
        long timestamp = timeGen();
        
        //系统时间如果发生回调，那么生成的序列号可能会重复的风险，所以这里发现时间回调sleep一段时间，避免异常
        if (timestamp < lastTimestamp) {
            LOG.warn(String.format("-------- [SAND][警告] -------- \n 检测到时间被回调!  上次调用时间：%d\n-------- [SAND][警告] --------", lastTimestamp));
            //时间回调超过容忍范围，则会报异常
            if (timestamp < (lastTimestamp - CALL_BACK_TOLERANCE)) {
                throw new RuntimeException(
                        String.format("-------- [SAND][警告] -------- \n 时间回调超过容忍范围! 生成ID失败： %d\n-------- [SAND][警告] --------", lastTimestamp));
            }
            LOG.warn(String.format("-------- [SAND][警告] -------- \n 等待时间恢复, 上次调用时间： %d\n-------- [SAND][警告] --------", lastTimestamp));
            timestamp = sleepToNextMillisecond();
            LOG.warn(String.format("-------- [SAND][警告] -------- \n 时间已恢复, 上次调用时间： %d\n-------- [SAND][警告] --------", lastTimestamp));
        }
        
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = idleToNextMillisecond();
            }
        } else {
            sequence = 0;
        }
        
        lastTimestamp = timestamp;
        
        return ((timestamp - twepoch) << timestampLeftShift) | (runModeId << runModeIdShift) | (workerId << workerIdShift) | sequence;
    }
    
    
    public String getHexSand() {
        return Long.toHexString(getSand());
    }
    
    public String getDuoSand() {
        return Long.toString(getSand(), 32);
    }
    
    private long sleepToNextMillisecond() {
        long timestamp = timeGen();
        try {
            long sleepTime = lastTimestamp - timestamp;
            if (sleepTime > 0) {
                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            LOG.error("线程睡眠中断", e);
        }
        timestamp = timeGen();
        return timestamp;
    }
    
    private long idleToNextMillisecond() {
        long timestamp;
        while (true) {
            timestamp = timeGen();
            if (lastTimestamp < timestamp) {
                break;
            }
        }
        return timestamp;
    }
    
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
