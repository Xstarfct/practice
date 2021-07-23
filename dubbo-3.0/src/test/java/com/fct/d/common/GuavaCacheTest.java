package com.fct.d.common;

import com.fct.d.NoSpringBaseTest;
import com.fct.d.struct.MySingleLinkedList;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.dubbo.common.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * GuavaCacheTest
 *
 * @author fct
 * @date 2021-07-21 15:57
 */
public class GuavaCacheTest extends NoSpringBaseTest {

  private static final LoadingCache<String, Optional<MySingleLinkedList>> cache =
      CacheBuilder.newBuilder()
          .expireAfterWrite(1, TimeUnit.HOURS)
          .maximumSize(10000L)
          .build(
              new CacheLoader<String, Optional<MySingleLinkedList>>() {
                @Override
                public Optional<MySingleLinkedList> load(@NotNull String s) {
                  if (StringUtils.isBlank(s)) {
                    return Optional.empty();
                  }
                  char[] chars = s.toCharArray();
                  int[] list = new int[s.length()];
                  for (int i = 0; i < chars.length; i++) {
                    list[i] = chars[i];
                  }
                  return Optional.of(MySingleLinkedList.initListNode(list));
                }
              });

  private final ExecutorService service =
      Executors.newFixedThreadPool(10, new ThreadFactoryBuilder().setNameFormat("test-%s").build());

  @Test
  public void test() throws Exception {
    IntStream.range(1, 10000)
        .parallel()
        .forEach(
            i ->
                service.submit(
                    () -> {
                      try {
                        printJson(
                            ImmutableMap.of(
                                "c",
                                cache.get(String.valueOf(i)),
                                "t",
                                Thread.currentThread().getName()));
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }));
    for (int i = 0; i < 10; i++) {
      TimeUnit.SECONDS.sleep(1);
      System.out.println("\n\n i=" + i + ",size=" + cache.size());
    }

    TimeUnit.MINUTES.sleep(60);
  }
}
