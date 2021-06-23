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
  public void mergeTest() {}

  @Test
  public void test() {
    int[] nums = new int[] {10, 9, 6, 5, 8, 7, 11, 200, 87, 34, 29};
    MySingleLinkedList a = MySingleLinkedList.initListNode(nums);
    printJson(a);
    a.add(99);
    printJson(a);

    printJson(
        mergeTwoLists(
            MySingleLinkedList.initListNode(new int[] {1, 4, 8, 10}).head,
            MySingleLinkedList.initListNode(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 11, 13}).head));
  }

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
}
