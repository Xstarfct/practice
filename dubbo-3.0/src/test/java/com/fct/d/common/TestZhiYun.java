package com.fct.d.common;

import org.apache.dubbo.common.utils.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * TestZhiYun
 * <p>新华智云在线变成题目</p>
 *
 * @author fct
 * @version 2021-09-01 11:01
 */
public class TestZhiYun {

  public static String removeDuplicate(String str) {
    if (StringUtils.isBlank(str)) {
      return "";
    }
    StringBuilder result = new StringBuilder();
    Map<Character, Boolean> map = new HashMap();
    for (char s : str.toCharArray()) {
      if(!map.containsKey(s)) {
        result.append(s);
        map.put(s, true);
      }
    }
    return result.toString();
  }

  //题目2:将字符串倒序。例如：12asdf变成fdsa21

  public static String reserve(String str) {
    if (StringUtils.isBlank(str)) {
      return "";
    }
    char[] chars = str.toCharArray();
    char[] r = new char[chars.length];
    for (int i = 0;i < chars.length;i++) {
      r[chars.length-1-i] = chars[i];
    }
    return String.copyValueOf(r);
  }

  public static void main(String[] args) throws InterruptedException {
    //
    System.out.println(removeDuplicate("trrerew1231bcc124"));
    System.out.println(reserve("123abc"));
    CountDownLatch cdl = new CountDownLatch(100);
    cdl.countDown();
    cdl.await();

    Timer t1 = new Timer();
    Timer t2 = new Timer();

    IntStream.range(0, 3).forEach(i-> new Thread(()-> t1.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        write();
      }
    }, 1000*60, 1000*60)).start());

    IntStream.range(0, 10).forEach(i-> new Thread(()-> t2.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        read();
      }
    },0, 100)).start());

    TimeUnit.MINUTES.sleep(100);

  }

//题目3:编程模拟实现并发读写，写线程和读线程共享一个char[] buffer，
//初始化值buffer="hello1"，写线程有3个，读线程有10个。
//写线程每隔1分钟，随机生成一个长度为10的字符串，并更新buffer的内容；
//每个读线程每100毫秒读取一次buffer当前的内容，并且打印出来。
//要求尽量考虑程序整体的性能最优，同时不允许出现脏读幻读

  private static volatile char[] buffer = "hello1".toCharArray();

  private final static String str = "abcdefghijklmnopqrstuvwxyz";

  private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public static void read() {
    lock.readLock().lock();
    try {
      System.out.println(Thread.currentThread() + "_read:" + Arrays.toString(buffer));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.readLock().unlock();
    }
  }

  public static void write() {
    lock.writeLock().lock();
    try {
      Random random = new Random();
      char[] b = new char[10];
      for (int i = 0; i < 10; i++) {
        b[i] = str.charAt(random.nextInt(26));
      }
      buffer = b;
      System.out.println(Thread.currentThread() + "_write:" + Arrays.toString(buffer));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Test
  public void aStopB() throws Exception {
    Thread a =
        new Thread(
            () -> {
              System.out.println("a线程执行");
              try {
                Thread.sleep(1000 * 60);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    Thread b =
        new Thread(
            () -> {
              System.out.println("b线程执行");
              try {
                Thread.sleep(1000 * 10);
                System.out.println("让a线程停止");
//                a.interrupt();
                a.stop();

              } catch (Exception e) {
                e.printStackTrace();
              }
            });

    a.start();
    b.start();
    try {
      while (true) {
        Thread.sleep(1000);
        System.out.println("a.isInterrupted()=" +a.isInterrupted() + ",a.isAlive()=" + a.isAlive());
        if (a.isInterrupted() || !a.isAlive()) {
          System.out.println("a interrupted or isNotAlive  ----- 结束");
          break;
        }
      }
    } catch (Exception ignore) {
    }
    a.join();
    b.join();
  }







}
