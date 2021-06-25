package com.fct.d.common;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * StackDemo
 *
 * @author fct
 * @version 2021-06-25 10:22
 */
public class StackDemo {

  static void showPush(Stack<Integer> st, int a) {
    st.push(a);
    System.out.println("push(" + a + ")");
    System.out.println("stack: " + st  + "\n");
  }

  static void showPop(Stack<Integer> st) {
    System.out.print("pop -> ");
    Integer a = st.pop();
    System.out.println(a);
    System.out.println("stack: " + st  + "\n");
  }

  public static void main(String[] args) {
    Stack<Integer> st = new Stack<>();
    System.out.println("stack: " + st + "\n");
    showPush(st, 1);
    showPush(st, 9);
    showPush(st, 7);
    showPop(st);
    showPop(st);
    showPop(st);
    try {
      showPop(st);
    } catch (EmptyStackException e) {
      System.out.println("empty stack");
    }
  }
}
