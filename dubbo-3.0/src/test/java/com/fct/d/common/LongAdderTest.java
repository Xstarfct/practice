package com.fct.d.common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder
 *
 * @author fct
 * @date 2021-07-19 10:38
 */
public class LongAdderTest {

  static final ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("demo-test-%s").build();

  final AtomicLong atomicLong = new AtomicLong(0L);
  final LongAdder longAdder = new LongAdder();
//  final LongAccumulator longAccumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);

  // 访问的线程总数
  public static final int THREAD_COUNT = 100;
  // 循环的总次数
  public static final int LOOP_COUNT = 1000000;

  private static final ExecutorService POOL =
      new ThreadPoolExecutor(
          50,
          100,
          0L,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(1024),
          tf,
          new ThreadPoolExecutor.AbortPolicy());
  private static final CompletionService<Long> COMPLETION_SERVICE =
      new ExecutorCompletionService<>(POOL);

  /**
   * 耗时：1928 耗时：255
   */
  @Test
  public void test() throws Exception {
    runTest(
        () -> {
          for (int i = 0; i < LOOP_COUNT; i++) {
            atomicLong.getAndIncrement();
          }
        });
    //
    runTest(
        () -> {
          for (int i = 0; i < LOOP_COUNT; i++) {
            longAdder.increment();
          }
        });
//    Random random = new Random();
//    runTest(
//        () -> {
//          for (int i = 0; i < LOOP_COUNT; i++) {
//            longAccumulator.accumulate(random.nextLong()); // 比较value和上一次的比较值，然后存储较大者
//          }
//        });

    System.out.println(atomicLong.get());
    System.out.println(longAdder.longValue());
//    System.out.println(longAccumulator.get());
    POOL.shutdown();
  }

  private void runTest(Runnable runnable) throws InterruptedException, ExecutionException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < THREAD_COUNT; i++) {
      COMPLETION_SERVICE.submit(runnable, 1L);
    }
    for (int i = 0; i < THREAD_COUNT; i++) {
      COMPLETION_SERVICE.take().get();
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - start));
  }

  @Test
  public void test2() throws Exception {
    //
    System.out.println(2>>1);
    System.out.println(2<<1);
    System.out.println(2|1);
    System.out.println(2&1);
  }
}
