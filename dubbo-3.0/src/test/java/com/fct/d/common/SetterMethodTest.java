package com.fct.d.common;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.fct.d.convert.SetterTestJMHConvert;
import com.fct.d.domain.SetterTestDest;
import com.fct.d.domain.SetterTestOrigin;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试set的几种便捷方法的性能差距： 1.反射 2.ASM 3.mapstruct
 *
 * @author fct
 * @version 2021-06-29 16:24
 */
@BenchmarkMode({Mode.Throughput})
@State(value = Scope.Thread)
@Timeout(time = 2, timeUnit = TimeUnit.MINUTES)
@Warmup(iterations = 3, time = 30, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.MINUTES)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(3)
public class SetterMethodTest {

  static SetterTestJMHConvert mapStruct = SetterTestJMHConvert.INSTANCE;

  public static List<SetterTestOrigin> origins;

  static {
    origins = new ArrayList<>(100000);
    for (int i = 0; i < 100000; i++) {
      SetterTestOrigin origin = new SetterTestOrigin();
      origin.setId(i);
      origin.setIdBoxing(i);
      origin.setContent(String.valueOf(i));
      origins.add(origin);
    }
  }

  public static void main(String[] args) throws RunnerException {

    Options opt =
        new OptionsBuilder()
            .include(SetterMethodTest.class.getSimpleName())
            .threads(3)
            .forks(1)
            .resultFormat(ResultFormatType.JSON)
            .build();
    new Runner(opt).run();
  }

  @Benchmark
  public static void reflectSetter() {
    List<SetterTestDest> res = new ArrayList<>();
    for (SetterTestOrigin origin : origins) {
      SetterTestDest dest = new SetterTestDest();
      BeanUtils.copyProperties(origin, dest);
      res.add(dest);
    }
  }

  @Benchmark
  public static void ASMSetter() {
    List<SetterTestDest> res = new ArrayList<>();
    FieldAccess originAccess = FieldAccess.get(SetterTestOrigin.class);
    FieldAccess targetAccess = FieldAccess.get(SetterTestDest.class);
    for (SetterTestOrigin origin : origins) {
      SetterTestDest cur = new SetterTestDest();
      for (String fieldName : originAccess.getFieldNames()) {
        targetAccess.set(cur, fieldName, originAccess.get(origin, fieldName));
      }
      res.add(cur);
    }
  }

  @Benchmark
  public static void mapStructSetter() {
    List<SetterTestDest> res = new ArrayList<>();
    res = mapStruct.toTargetList(origins);
  }
}
