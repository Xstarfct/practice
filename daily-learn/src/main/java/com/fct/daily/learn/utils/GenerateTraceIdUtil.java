package com.fct.daily.learn.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分布式追踪ID生成器
 *
 * @author xstarfct
 * @version 2020-09-11 17:18
 */
public class GenerateTraceIdUtil {
    
    private static final AtomicInteger NUM = new AtomicInteger(0);
    
    private static final int MAX = 9999;
    
    private static final int SIZE = String.valueOf(MAX).length();
    
    private static final char[] BASE_CHARS1 = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    };
    
    private static final char[] BASE_CHARS2 = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };
    
    private static final String[] ALL_STRS = new String[BASE_CHARS1.length * BASE_CHARS2.length];
    
    private static final int RADIX = ALL_STRS.length;
    
    private static final String IP_CONVERT;
    
    private static String PID;
    
    private static final String ENCODE_PID;
    
    
    static {
        for (int i = 0; i < BASE_CHARS1.length; i++) {
            for (int j = 0; j < BASE_CHARS2.length; j++) {
                ALL_STRS[i * BASE_CHARS2.length + j] = BASE_CHARS1[i] + "" + BASE_CHARS2[j];
            }
        }
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException ignore) {
        
        }
        assert inetAddress != null;
        StringBuilder sb = new StringBuilder();
        
        Arrays.asList(inetAddress.getHostAddress().split("\\.")).forEach(e -> sb.append(ALL_STRS[Integer.parseInt(e)]));
        
        if (sb.length() == 0) {
            sb.append(ALL_STRS[new Random().nextInt(256)]).
                    append(ALL_STRS[new Random().nextInt(256)]);
        }
        IP_CONVERT = sb.toString();
        ENCODE_PID = encode(Integer.parseInt(getProcessId()));
    }
    
    private static String getProcessId() {
        String vmName = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = vmName.indexOf('@');
        if (indexOf > 0) {
            PID = vmName.substring(0, indexOf);
        }
        return PID;
    }
    
    private static String getNum() {
        while (NUM.compareAndSet(MAX, 0)) {
            return convertInt2String(0);
        }
        return convertInt2String(NUM.incrementAndGet());
    }
    
    private static String convertInt2String(int num) {
        int length = String.valueOf(num).length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (SIZE - length); i++) {
            sb.append("0");
        }
        sb.append(num);
        return sb.toString();
        
    }
    
    private static String encode(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("编码数字不能小于0");
        }
        StringBuilder sb = new StringBuilder();
        int mod;
        do {
            mod = number % RADIX;
            number = number / RADIX;
            sb.append(ALL_STRS[mod]);
        } while (number != 0);
        return reverse(sb);
    }
    
    private static String reverse(StringBuilder sb) {
        int length = sb.length() / 2;
        StringBuilder reverse = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String substring = sb.substring((length - 1 - i) * 2, (length - i) * 2);
            reverse.append(substring);
        }
        
        return reverse.toString();
    }
    
    private static int decode(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("解码字符串不能为空");
        }
        int result = 0;
        for (int i = 0; i < str.length() / 2; i++) {
            String substring = str.substring(i * 2, i * 2 + 2);
            double pow = findIndex(substring) * Math.pow(RADIX, str.length() / 2.0 - i - 1);
            result += (int) pow;
        }
        return result;
    }
    
    private static int findIndex(String s) {
        for (int i = 0; i < RADIX; i++) {
            if (s.equals(ALL_STRS[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("无法解码非指定字符");
    }
    
    public static String getTraceId() {
        return IP_CONVERT + ENCODE_PID + System.currentTimeMillis() + getNum();
    }
}
