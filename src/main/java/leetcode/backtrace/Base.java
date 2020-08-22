package leetcode.backtrace;

import leetcode.node.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** 2020/7/8 */
public class Base {

  /**
   * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树
   *
   * @return 所有二叉搜索树根节点组成的列表
   */
  public List<TreeNode> generateTrees(int n) {
    if (n == 0) {
      return new LinkedList<>();
    }
    return generateTrees(1, n);
  }

  private List<TreeNode> generateTrees(int start, int end) {
    List<TreeNode> res = new LinkedList<>();
    if (start > end) {
      res.add(null);
      return res;
    }
    for (int i = start; i <= end; i++) {
      // [start, i-1]范围内节点组成的二叉搜索树
      List<TreeNode> leftChild = generateTrees(start, i - 1);
      // [i+1, end]范围内节点组成的二叉搜索树
      List<TreeNode> rightChild = generateTrees(i + 1, end);
      for (TreeNode leftNode : leftChild) {
        for (TreeNode rightNode : rightChild) {
          TreeNode newNode = new TreeNode(i);
          newNode.left = leftNode;
          newNode.right = rightNode;
          res.add(newNode);
        }
      }
    }
    return res;
  }

  /**
   * LeetCode93 复原IP地址
   *
   */
  public List<String> restoreIpAddresses(String s) {
    segments = new int[4];
    ipRes = new ArrayList<>();
    restoreIpAddressesDfs(s, 0, 0);
    return ipRes;
  }

  private int[] segments;
  private List<String> ipRes;

  /**
   * @param s 字符串
   * @param segId ip地址分段id 0 1 2 3
   * @param segStart ip地址字符位置
   */
  private void restoreIpAddressesDfs(String s, int segId, int segStart) {
    // 4段ip地址同时遍历完字符串
    if (segId == 4) {
      if (segStart == s.length()) {
        StringBuilder ipAddr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
          ipAddr.append(segments[i]);
          if (i != 4 - 1) {
            ipAddr.append(".");
          }
        }
        ipRes.add(ipAddr.toString());
      }
      return;
    }

    // 没有找到4段ip地址就已经遍历完字符串
    if (segStart == s.length()) {
      return;
    }

    // 某一段第一个字符为0
    if (s.charAt(segStart) == '0') {
      segments[segId] = 0;
      restoreIpAddressesDfs(s, segId + 1, segStart + 1);
    }

    // 一般情况下
    int addr = 0;
    for (int segEnd = segStart; segEnd < s.length(); segEnd++) {
      addr = addr * 10 + (s.charAt(segEnd) - '0');
      if (addr > 0 && addr <= 255) {
        segments[segId] = addr;
        restoreIpAddressesDfs(s, segId + 1, segEnd + 1);
      } else {
        break;
      }
    }
  }

  /**
   * LeetCode 24点游戏
   * @param nums 4张数字牌
   * @return 能否凑成24点
   */
  public boolean judgePoint24(int[] nums) {
    List<Double> intToDoubleList = new ArrayList<>(nums.length);
    for (int num : nums) {
      intToDoubleList.add((double)num);
    }
    return judgePoint24BackTrace(intToDoubleList);
  }

  private static final int TWENTY_FOUR = 24;
  private static final double EPSILON = 1e-6;

  private boolean judgePoint24BackTrace(List<Double> list) {
    final int size = list.size();
    // 没有数字直接返回false
    if(size == 0){
      return false;
    }else if(size == 1){
      // 只剩最后一个数字 与24比较判断
      return Math.abs(list.get(0) - TWENTY_FOUR) < EPSILON;
    }
    // 否则选择两个数字
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if(i != j){
          // 不能取位置相同的数字
          List<Double> backtraceList = new ArrayList<>(size - 1);
          for (int k = 0; k < size; k++) {
            if(k != i && k != j){
              // 把没有选中的加入用于回溯的列表
              backtraceList.add(list.get(k));
            }
            // 选择4中操作 0 1 2 3 加乘减除
            for (int l = 0; l < 4; l++) {
              if(l <= 1 && i > j){
                // 加乘具有交换律 跳过已经计算过的
                continue;
              }
              if(k == 0){
                backtraceList.add(list.get(i) + list.get(j));
              }else if(k == 1){
                backtraceList.add(list.get(i) * list.get(j));
              }else if(k == 2){
                backtraceList.add(list.get(i) - list.get(j));
              }else{
                // 除法注意不能除0
                if(Math.abs(list.get(j)) < EPSILON){
                  continue;
                }else {
                  backtraceList.add(list.get(i) / list.get(j));
                }
              }

              // 递归处理
              boolean r = judgePoint24BackTrace(backtraceList);
              // 满足24
              if(r){
                return true;
              }
              // 不满足24 回溯到添加前
              backtraceList.remove(backtraceList.size() - 1);
            }
          }
        }
      }
    }
    return false;
  }
}
