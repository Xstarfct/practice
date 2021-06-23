package com.fct.d.struct;

import java.io.Serializable;

/**
 * ListNode-单链表结点
 *
 * @author fct
 * @version 2021-06-23 10:44
 */
public class ListNode implements Serializable {

  private static final long serialVersionUID = 7181862618731711648L;
  public int val;
  public ListNode next;

  public ListNode() {}

  public ListNode(int val) {
    this.val = val;
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }
}
