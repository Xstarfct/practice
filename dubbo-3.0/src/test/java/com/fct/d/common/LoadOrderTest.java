package com.fct.d.common;

/**
 * 父子类加载顺序测试
 *
 * <p>https://juejin.cn/post/6991707135117967373?utm_source=gold_browser_extension
 *
 * @author fct
 * @date 2021-08-02 15:32
 */
public class LoadOrderTest {

  static class A {
    static Hi hi = new Hi("A");

    Hi hi2 = new Hi("A2");

    // 静态代码块
    static {
      System.out.println("A static");
    }

    // 非静态代码块
    {
      System.out.println("A non static");
    }

    public A() {
      System.out.println("A init");
    }
  }

  // B 是 A 的子类
  static class B extends A {
    static Hi hi = new Hi("B");

    Hi hi2 = new Hi("B2");

    // 静态代码块
    static {
      System.out.println("B static");
    }

    // 非静态代码块
    {
      System.out.println("B non static");
    }

    public B() {
      System.out.println("B init");
    }
  }

  static class Hi {
    public Hi(String str) {
      System.out.println("Hi " + str);
    }
  }

  public static void main(String[] args) {
    System.out.println("[First] new B：");
    B b = new B();
    System.out.println("[First] end B：" + b.hi2);
    System.out.println("-------------------------------------------------------");
    System.out.println("[Second] new B：");
    b = new B();
    System.out.println("[Second] end B：" + b.hi2);
  }
}
