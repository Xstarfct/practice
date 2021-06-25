package com.fct.d.struct;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树节点
 *
 * @author fct
 * @version 2021-06-25 9:47
 */
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

  public int val;
  public TreeNode left;
  public TreeNode right;

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    inorder(root, list);
    return list;
  }

  /*基于递归的中序遍历*/
  public void inorder(TreeNode root, List<Integer> list) {
    if (root == null) {
      return;
    }
    inorder(root.left, list);
    list.add(root.val);
    inorder(root.right, list);
  }

  /*基于栈的数据结构*/
  public List<Integer> inorderTraversal4Stack(TreeNode root) {
    // 栈 先进后出
    // 前序遍历，出栈顺序：根左右; 入栈顺序：右左根
    // 中序遍历，出栈顺序：左根右; 入栈顺序：右根左
    // 后序遍历，出栈顺序：左右根; 入栈顺序：根右左
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    // root为空且stack为空，遍历结束
    while (root != null || !stack.isEmpty()) {
      // 先根后左入栈
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      // 此时root==null，说明上一步的root没有左子树
      // 1. 执行左出栈。因为此时root==null，导致root.right一定为null
      // 2. 执行下一次外层while代码块，根出栈。此时root.right可能存在
      // 3a. 若root.right存在，右入栈，再出栈
      // 3b. 若root.right不存在，重复步骤2
      root = stack.pop();
      result.add(root.val);
      root = root.right;
    }
    return result;
  }

  /**
   * 前言 这道题中的平衡二叉树的定义是：二叉树的每个节点的左右子树的高度差的绝对值不超过
   * 11，则二叉树是平衡二叉树。根据定义，一棵二叉树是平衡二叉树，当且仅当其所有子树也都是平衡二叉树，因此可以使用递归的方式判断二叉树是不是平衡二叉树，递归的顺序可以是自顶向下或者自底向上。
   *
   * <p>方法一：自顶向下的递归 定义函数 \texttt{height}height，用于计算二叉树中的任意一个节点 pp 的高度：
   *
   * <p>\texttt{height}(p) = \begin{cases} 0 & p \text{ 是空节点}\\
   * \max(\texttt{height}(p.\textit{left}), \texttt{height}(p.\textit{right}))+1 & p \text{ 是非空节点}
   * \end{cases} height(p)={ 0 max(height(p.left),height(p.right))+1 ​
   *
   * <p>p 是空节点 p 是非空节点 ​
   *
   * <p>有了计算节点高度的函数，即可判断二叉树是否平衡。具体做法类似于二叉树的前序遍历，即对于当前遍历到的节点，首先计算左右子树的高度，如果左右子树的高度差是否不超过
   * 11，再分别递归地遍历左右子节点，并判断左子树和右子树是否平衡。这是一个自顶向下的递归的过程。
   *
   * <p>作者：LeetCode-Solution
   * 链接：https://leetcode-cn.com/problems/balanced-binary-tree/solution/ping-heng-er-cha-shu-by-leetcode-solution/
   * 来源：力扣（LeetCode） 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
   *
   * @param root
   * @return
   */
  public static boolean isBalanced(TreeNode root) {
    return height(root) >= 0;
  }

  private static int height(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int leftHeight = height(root.left);
    int rightHeight = height(root.right);
    if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
      return -1;
    }
    return Math.max(leftHeight, rightHeight) + 1;
  }

}
