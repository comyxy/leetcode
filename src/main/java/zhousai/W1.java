package zhousai;

import java.util.ArrayList;
import java.util.List;

/** 2020/8/30 */
public class W1 {

  public boolean containsPattern(int[] arr, int m, int k) {
    final int n = arr.length;
    for (int i = 0; i < n - m + 1; i++) {
      List<Integer> mode = new ArrayList<>(m);
      for (int j = i; j < i + m; j++) {
        mode.add(arr[j]);
      }
      // l 代表 mode中位置
      int l = 0;
      int times = 1;
      for (int j = i + m; j < n; j++) {
        if (mode.get(l++) != arr[j]) {
          break;
        }
        if (l == m) {
          l = 0;
          times++;
        }
      }
      if (times >= k) {
        return true;
      }
    }
    return false;
  }


  public int getMaxLen(int[] nums) {
    return 0;
  }


  public static void main(String[] args) {
    W1 w1 = new W1();

    //    boolean b = w1.containsPattern(new int[]{1,2,4,4,4,4}, 1, 3);
    //    boolean b = w1.containsPattern(new int[]{1,2,1,2,1,3}, 2, 3);
    boolean b = w1.containsPattern(new int[] {2, 2, 2, 2}, 2, 3);
    //    boolean b = w1.containsPattern(new int[]{1,2,3,1,2}, 2, 2);
    //    boolean b = w1.containsPattern(new int[]{1,2,1,2,1,1,1,3}, 2, 2);
    System.out.println(b);

    //    int maxLen = w1.getMaxLen(new int[] {1, -2, -3, 4});
    int maxLen =
        w1.getMaxLen(
            new int[] {
              0, -19, 26, -24, -13, -2, 26, 10, 0, 4, 0, -26, -22, 9, 35, -11, -14, 0, -29
            });
    //    int maxLen = w1.getMaxLen(new int[] {-1,-2,-3,0,1});
    //    int maxLen = w1.getMaxLen(new int[] {-1,2});
    System.out.println(maxLen);
  }
}
