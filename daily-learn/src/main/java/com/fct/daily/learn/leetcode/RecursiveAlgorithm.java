package com.fct.daily.learn.leetcode;

import lombok.Data;

/**
 * <pre>
 * 递归算法：https://leetcode-cn.com/leetbook/read/recursion/45ici/
 * </pre>
 * <code>
 *     对于一个问题，如果存在递归解决方案，我们可以按照以下步骤来实施它。
 *     举个例子，我们将问题定义为有待实现的函数 F(X){F(X)}F(X)，其中 X{X}X 是函数的输入，同时也定义了问题的范围。
 *     然后，在函数 F(X){F(X)}F(X) 中，我们将会：
 *
 *     将问题逐步分解成较小的范围，例如 x0∈X{x_0} \in Xx0​∈X, x1∈X{x_1} \in Xx1​∈X, ..., xn∈X{x_n} \in Xxn​∈X；
 *     调用函数 F(x0){F(x_0)}F(x0​), F(x1)F(x_1)F(x1​), ..., F(xn)F(x_n)F(xn​) 递归地 解决 X{X}X 的这些子问题；
 *     最后，处理调用递归函数得到的结果来解决对应 X{X}X 的问题。
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/recursion/4oasv/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * </code>
 *
 * @author xstarfct
 * @version 2020-09-14 16:01
 */
public class RecursiveAlgorithm {
    //1、以相反的顺序打印字符串----------------------------------------------------------------------------------------------
    public static void printReverse(char [] str) {
        reverseHelper(0, str);
    }
    
    private static void reverseHelper(int index, char [] str) {
        if (str == null || index >= str.length) {
            return;
        }
        reverseHelper(index + 1, str);
        System.out.print(str[index]);
    }
    //end------------------------------------------------------------------------------------------------
    
    public static void main(String[] args) {
        //递归1：以相反的顺序打印字符串
        printReverse("welcome to kai ke la".toCharArray());
    }
    //end------------------------------------------------------------------------------------------------
    // 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
    // 给定 1->2->3->4, 你应该返回 2->1->4->3.
    // https://leetcode-cn.com/problems/swap-nodes-in-pairs/solution/liang-liang-jiao-huan-lian-biao-zhong-de-jie-di-19/
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    @Data
    private static class SinglyLinkedListNode {
        int val;
        SinglyLinkedListNode next;
        SinglyLinkedListNode(int x) { val = x; }
    }
    
    
    public SinglyLinkedListNode swapPairs(SinglyLinkedListNode head) {
        return swapPairsHelper(head);
    }
    
    private static SinglyLinkedListNode swapPairsHelper(SinglyLinkedListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        SinglyLinkedListNode secondNode = head.next;
        head.next = swapPairsHelper(secondNode);
        secondNode.next = head;
        
        return secondNode;
    }
    
    
    
}
