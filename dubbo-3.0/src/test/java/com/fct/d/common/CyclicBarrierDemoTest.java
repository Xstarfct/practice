package com.fct.d.common;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierDemoTest
 *
 * @author fct
 * @version 2021-07-05 14:47
 */
public class CyclicBarrierDemoTest {
  static class TaskThread extends Thread {

    CyclicBarrier barrier;

    public TaskThread(CyclicBarrier barrier) {
      this.barrier = barrier;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
        System.out.println(getName() + " 到达栅栏 A");
        barrier.await();
        System.out.println(getName() + " 冲破栅栏 A");

        Thread.sleep(2000);
        System.out.println(getName() + " 到达栅栏 B");
        barrier.await();
        System.out.println(getName() + " 冲破栅栏 B");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    int threadNum = 5;
    CyclicBarrier barrier =
        new CyclicBarrier(
            threadNum, () -> System.out.println(Thread.currentThread().getName() + " 完成最后任务"));

    for (int i = 0; i < threadNum; i++) {
      new TaskThread(barrier).start();
    }
    System.out.println("ending......");
  }
}
