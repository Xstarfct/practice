package com.fct.d.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ShowMeBug
 * <p>https://www.it610.com/article/4816994.htm</p>
 * <p>https://blog.csdn.net/qq_41844287/article/details/93859931?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-11.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-11.control</p>
 *
 * @author fct
 * @version 2021-09-01 15:35
 */
public class ShowMeBug {

  @Data
  static class Node {
    int id;
    int parentId;
    String name;

    public Node(int id, int parentId, String name) {
      this.id = id;
      this.parentId = parentId;
      this.name = name;
    }
  }

  private static StringBuilder buffer = new StringBuilder();

  public static String buildTree(List<Node> nodes) {
    for (Node node : nodes) {
      if (node.getParentId() == 0) {
        buffer.append(node.getName());
        build(node, nodes);
      }
    }
    buffer.append("\n");
    return buffer.toString();
  }

  private static void build(Node node, List<Node> nodes) {
    List<Node> children = getChildren(node, nodes);
    if (!children.isEmpty()) {
      buffer.append("\n");
      for (Node child : children) {
        buffer.append("  " + child.getName());
        build(child, nodes);
      }
      buffer.append("\n");
    }
  }

  private static List<Node> getChildren(Node node, List<Node> nodes) {
    List<Node> children = new ArrayList<>();
    Integer id = node.getId();
    for (Node child : nodes) {
      if (id.equals(child.getParentId())) {
        children.add(child);
      }
    }
    return children;
  }

  /* 分析题目，怎么才能打印出这种效果 ？
      1. 先看打印顺序，使用什么算法能打印出这种顺序？
        —— 使用树的广度优先遍历
      2. 前缀空格怎么实现
        —— 可以利用递归每次 + 分隔符
  */
  public static void main(String[] args) {
    List<Node> nodeList =
        Arrays.asList(
            new Node(1, 0, "AA"),
            new Node(2, 1, "BB"),
            new Node(3, 1, "CC"),
            new Node(4, 3, "DD"),
            new Node(5, 3, "EE"),
            new Node(6, 2, "FF"),
            new Node(7, 2, "GG"),
            new Node(8, 4, "HH"),
            new Node(9, 5, "II"),
            new Node(10, 0, "JJ"),
            new Node(11, 10, "KK"),
            new Node(12, 10, "LL"),
            new Node(13, 12, "MM"),
            new Node(14, 13, "NN"),
            new Node(15, 14, "OO"));
    print(nodeList);
    System.out.println(buildTree(nodeList));
  }

  public static void print(List<Node> nodeList) {
    // todo
    Node p = new Node(0, 0, "");

    Map<Integer, List<Node>> parentIdNodeMap =
        nodeList.stream().collect(Collectors.groupingBy(Node::getParentId));
  }
}
