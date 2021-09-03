package com.fct.d.common;

import com.fct.d.NoSpringBaseTest;
import com.fct.d.struct.ListNode;
import com.fct.d.struct.MySingleLinkedList;
import org.junit.Test;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author fct
 * @version 2021-06-23 10:46
 */
public class MergeListNodeTest extends NoSpringBaseTest {

  @Test
  public void mergeTest1() {
    ListNode l1 = MySingleLinkedList.initListNode(new int[]{1, 4, 8, 10}).head;
    ListNode l2 = MySingleLinkedList.initListNode(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 11, 13}).head;
    printJson(mergeTwoLists(l1, l2));
  }

  @Test
  public void initListNode() {
    int[] nums = new int[] {10, 9, 6, 5, 8, 7, 11, 200, 87, 34, 29};
    MySingleLinkedList a = MySingleLinkedList.initListNode(nums);
    printJson(a);
    a.add(99);
    printJson(a);
  }

  // 递归方法
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) {
      return l2;
    }
    if (l2 == null) {
      return l1;
    }
    if (l1.val < l2.val) {
      l1.next = mergeTwoLists(l1.next, l2);
      return l1;
    }
    l2.next = mergeTwoLists(l1, l2.next);
    return l2;
  }

  @Test
  public void mergeTest2() {
    ListNode l1 = MySingleLinkedList.initListNode(new int[]{1, 4, 8, 10}).head;
    System.out.println("l1 = " + l1);
    ListNode l2 = MySingleLinkedList.initListNode(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 11, 13}).head;
    System.out.println("l2 = " + l2);
    System.out.println("mergeTwoLists2:[l1 + l2] = " + mergeTwoLists2(l1, l2));
  }

  //迭代
  public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0), p = dummy;
    while (l1 != null && l2 != null) {
      if (l1.val <= l2.val) {
        p.next = l1;
        l1 = l1.next;
      } else {
        p.next = l2;
        l2 = l2.next;
      }
      p = p.next;
    }
    // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
    p.next = l1 == null ? l2 : l1;
    return dummy.next;
  }

  @Test
  public void addTwoNumbersTest() {
    ListNode l1 = MySingleLinkedList.initListNode(new int[] {1, 4, 8, 9}).head;
    System.out.println("l1 = " + l1);
    ListNode l2 = MySingleLinkedList.initListNode(new int[] {2, 3, 4, 5}).head;
    System.out.println("l2 = " + l2);
    System.out.println("addTwoNumbers:[l1 + l2] = " + addTwoNumbers(l1, l2));
  }

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(), cur = dummy;

    int total, s = 0;
    while (l1 != null || l2 != null || s > 0) {
      total = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 :l2.val) + s;
      cur.next = new ListNode(total % 10);
      s = total / 10;
      l1 = l1 == null ? null : l1.next;
      l2 = l2 == null ? null : l2.next;
      cur = cur.next;
    }
    return dummy.next;
  }

  /**
   * <pre>
   *   https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
   * </pre>
   * 删除倒数第n个节点
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(), p1 = dummy, p2 = dummy;
    dummy.next = head;

    // 双指针定位出左右两个之间的距离为n
    for (int i = 0; i < n; i++) {
      if (p2 == null) {
        return dummy.next;
      }
      p2 = p2.next;
    }
    while (p2.next != null) {
      p1 = p1.next;
      p2 = p2.next;
    }
    if (p1 != null && p1.next != null) {
      p1.next = p1.next.next;
    }
    return dummy.next;
  }

  @Test
  public void removeNthFromEndTest() {
    ListNode l1 = MySingleLinkedList.initListNode(new int[] {1, 4, 8, 9}).head;
    System.out.println(removeNthFromEnd(l1, 2));
  }


}
