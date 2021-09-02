package com.fct.d;

import com.fct.d.domain.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TreeUtils
 *
 * @author fct
 * @version 2021-09-02 10:14
 */
public class TreeUtil {

  /**
   * 遍历树结构
   *
   * @param root              节点树结构
   * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
   * @param behavior          遍历到的节点行为
   * @param <T>               树节点对象
   */
  public static <T> void traversing(List<T> root, Function<T, List<T>> loadChildrenNodes, Consumer<T> behavior) {
    Stack<T> stack = new Stack<>();
    root.forEach(stack::push);
    while (!stack.isEmpty()) {
      T o = stack.pop();
      behavior.accept(o);
      List<T> children = loadChildrenNodes.apply(o);
      if (children != null && children.size() > 0) {
        children.forEach(stack::push);
      }
    }
  }

  /**
   * 平铺树结构
   *
   * @param root              节点树结构
   * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
   * @param <T>               树节点对象
   * @return 平铺结构
   */
  public static <T> List<T> tileTree(List<T> root, Function<T, List<T>> loadChildrenNodes) {
    List<T> list = new ArrayList<>();
    traversing(root, loadChildrenNodes, list::add);
    return list;
  }

  /**
   * 打印树信息
   *
   * @param list              树结构列表
   * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
   * @param <T>               树节点对象
   */
  public static <T> void printTree(List<T> list, Function<T, List<T>> loadChildrenNodes) {
    System.out.println("---------- Tree Nodes Print ----------");
    traversing(list, loadChildrenNodes, System.out::println);
    System.out.println("---------- Tree Nodes Print ----------");
  }

  /**
   * 聚合树结构
   *
   * @param list          节点列表结构
   * @param loadKey       节点唯一key读取 接收一个节点 返回节点的唯一key
   * @param loadParentKey 节点父节点key读取 接收一个节点 返回节点的父节点key
   * @param write         节点子项写入函数 接收待写入节点与节点子项 负责将子节点写入
   * @param <T>           节点对象
   * @param <R>           节点唯一key对象
   * @return 树结构
   */
  public static <T, R> List<T> polymerizationTree(List<T> list, Function<T, R> loadKey, Function<T, R> loadParentKey, BiConsumer<T, List<T>> write) {
    List<T> root = list.stream().filter(o -> loadParentKey.apply(o) == null).collect(Collectors.toList());
    Stack<T> stack = new Stack<>();
    root.forEach(stack::push);
    while (!stack.isEmpty()) {
      T o = stack.pop();
      R key = loadKey.apply(o);
      List<T> children = list.stream()
          .filter(k -> key.equals(loadParentKey.apply(k)))
          .collect(Collectors.toList());
      write.accept(o, children);
      if (children.size() > 0) {
        children.forEach(stack::push);
      }
    }
    return root;
  }

  public static void main(String[] args) {
    List<Node> listNodes = new ArrayList<>();
    listNodes.add(new Node(1, "根节点1", null));
    listNodes.add(new Node(2, "根节点2", null));
    listNodes.add(new Node(3, "根节点3", null));
    listNodes.add(new Node(4, "1-1", 1));
    listNodes.add(new Node(5, "1-2", 1));
    listNodes.add(new Node(6, "2-1", 2));
    listNodes.add(new Node(7, "3-1", 3));
    listNodes.add(new Node(8, "1-1-1", 4));
    listNodes.add(new Node(9, "1-1-2", 4));
    printTree(listNodes, Node::getChildren);
    // 聚合
    List<Node> treeNodes = polymerizationTree(listNodes, Node::getId, Node::getParentId, Node::setChildren);
    printTree(treeNodes, Node::getChildren);
    // 平铺
    listNodes = tileTree(treeNodes, Node::getChildren);
    printTree(listNodes, Node::getChildren);
  }
}
