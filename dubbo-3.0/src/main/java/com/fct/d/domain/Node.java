package com.fct.d.domain;

import lombok.Data;

import java.util.List;

/**
 * Node
 *
 * @author fct
 * @version 2021-09-02 10:13
 */
@Data
public class Node {
  private Integer id;
  private String name;
  private Integer parentId;
  private List<Node> children;

  public Node(Integer id, String name, Integer parentId) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
  }
}
