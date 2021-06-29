package zhousai.s1;

import struct.TreeNode;

import java.util.*;

/** 2020/10/4 */
public class W6 {

  public int specialArray(int[] nums) {
    Arrays.sort(nums);
    for (int i = 0; i <= nums[nums.length - 1]; i++) {
      for (int j = 0; j < nums.length; j++) {
        if (nums[j] >= i) {
          if (i == nums.length - j) {
            return i;
          } else {
            break;
          }
        }
      }
    }
    return -1;
  }

  public boolean isEvenOddTree(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    int level = 0;
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      TreeNode pre = null;
      for (int i = 0; i < size; i++) {
        TreeNode cur = queue.poll();
        if ((level & 1) == 0) {
          if ((cur.val & 1) != 1) {
            return false;
          }
          if (pre != null) {
            if (cur.val <= pre.val) {
              return false;
            }
          }
        } else {
          if ((cur.val & 1) != 0) {
            return false;
          }
          if (pre != null) {
            if (cur.val >= pre.val) {
              return false;
            }
          }
        }
        if (cur.left != null) {
          queue.offer(cur.left);
        }
        if (cur.right != null) {
          queue.offer(cur.right);
        }
        pre = cur;
      }
      level++;
    }
    return true;
  }

  public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
    List<Double> angles = new ArrayList<>();
    int ans = 0;
    for (List<Integer> point : points) {
      if(point.get(0)
              .equals(location.get(0)) && point.get(1).equals(location.get(1))){
        ans++;
        continue;
      }
      double tmp = Math.atan2(point.get(1) - location.get(1), point.get(0) - location.get(0));
      angles.add(tmp);
    }
    angles.sort(Double::compareTo);
    double a = angle / 180.0 * Math.PI;
    int n = angles.size();
    for (int i = 0; i < n; i++) {
      angles.add(angles.get(i) + 2 * Math.PI);
    }
    int max = 0;
    for (int i = 0, j = 0; i < n; i++) {
      while(j < 2 * n && angles.get(j) - angles.get(i) <= a){
        j++;
      }
      max = Math.max(max, j - i);
    }
    return max + ans;
  }

  /**
   * https://en.wikipedia.org/wiki/Gray_code
   */
  public int minimumOneBitOperations(int n) {
    int mask = n;
    while(mask > 0){
      mask >>= 1;
      n ^= mask;
    }
    return n;
  }

  public static void main(String[] args) {
    //
    W6 w6 = new W6();
//    int[] nums = {2, 2};
//    int specialArray = w6.specialArray(nums);
//    System.out.println(specialArray);
//
//    TreeNode root = new TreeNode(2);
//    boolean evenOddTree = w6.isEvenOddTree(root);
//    System.out.println(evenOddTree);

  }
}
