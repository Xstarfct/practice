package com.fct.d.struct;

/**
 * MySingleList-单链表
 *
 * @author fct
 * @version 2021-06-23 13:37
 */
public class MySingleLinkedList {

  public volatile int size;

  public ListNode head;

  public static MySingleLinkedList initListNode(int[] arr) {
    MySingleLinkedList list = new MySingleLinkedList();
    for (int j : arr) {
      list.add(j);
    }
    return list;
  }

  /** 最尾部插入 */
  public synchronized void add(int data) {
    ListNode p = this.head;
    if (p == null) {
      this.head = new ListNode(data);
      size++;
      return;
    }
    for (int i = 0; i < this.size - 1; i++) {
      p = p.next;
    }
    ListNode newNode = new ListNode(data);
    newNode.next = p.next;
    p.next = newNode;
    size++;
  }
}
