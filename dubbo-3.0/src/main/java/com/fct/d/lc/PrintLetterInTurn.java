package com.fct.d.lc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程分别打印 A，B，C，要求这三个线程一起运行，打印 n 次，输出形如“ABCABCABC....”的字符串
 * <see>
 *   参考链接：https://mp.weixin.qq.com/s/RROuyxtZqkd_7Uk33N81nQ
 * </see>
 *
 * @author fct
 * @date 2021-07-12 15:17
 */
public class PrintLetterInTurn {

  private final int times; // 控制打印次数
  private int state; // 当前状态值：保证三个线程之间交替打印
  private final Lock lock = new ReentrantLock();

  private final Object LOCK_OBJECT = new Object();

  public PrintLetterInTurn(int times) {
    this.times = times;
  }

  /*lock*/
  public void printLetter(String name, int targetNum) {
    for (int i = 0; i < times; ) {
      lock.lock();
      if (state % 3 == targetNum) {
        state++;
        i++;
        System.out.print(name);
      }
      lock.unlock();
    }
  }

  /*synchronized and wait and notify*/
  public void printLetter2(String name, int targetNum) throws Exception {
    for (int i = 0; i < times; ) {
      synchronized (LOCK_OBJECT) {
        while (state % 3 != targetNum) {
          try {
            LOCK_OBJECT.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        state++;
        i++;
        System.out.print(name);
        LOCK_OBJECT.notifyAll();
      }
    }
  }
}
