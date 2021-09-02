package com.fct.d.lc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程，按顺序交替打印数字1-100，如：线程1打印1，线程2打印2，线程3打印3，线程1打印4...
 *
 * @author xstarfct
 * @version 2020-04-27 8:27 下午
 */
public class PrintNumber {

  final static ReentrantLock lock       = new ReentrantLock();
  final static Condition     condition1 = lock.newCondition();
  final static Condition     condition2 = lock.newCondition();
  final static Condition     condition3 = lock.newCondition();

  public static void main(String[] args) {

    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 1; i < 101; i++) {
          if (i % 3 == 1) {
            System.out.println(Thread.currentThread().getName() + "打印" +i);
            try {
              condition2.signal();
              condition1.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      } finally {
        lock.unlock();
      }
    }, "线程1").start();

    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 1; i < 101; i++) {
          if (i % 3 == 2) {
            System.out.println(Thread.currentThread().getName() + "打印" +i);
            try {
              condition3.signal();
              condition2.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      } finally {
        lock.unlock();
      }
    }, "线程2").start();

    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 1; i < 101; i++) {
          if (i % 3 == 0) {
            System.out.println(Thread.currentThread().getName() + "打印" +i);
            try {
              condition1.signal();
              condition3.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      } finally {
        lock.unlock();
      }
    }, "线程3").start();
  }
}
