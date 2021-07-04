package struct;

/**
 * 包含next右侧指针的二叉树节点
 * 2020/7/8
 * @author comyxy
 * */
public class NextNode {
  public int val;
  public NextNode left;
  public NextNode right;
  public NextNode next;

  public NextNode() {}

  public NextNode(int val){
    this.val = val;
  }

  public NextNode(int val, NextNode left, NextNode right, NextNode next) {
    this.val = val;
    this.left = left;
    this.right = right;
    this.next = next;
  }
}
