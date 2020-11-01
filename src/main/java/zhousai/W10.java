package zhousai;

import java.util.Arrays;
import java.util.PriorityQueue;

/** 2020/11/1 */
public class W10 {
  public boolean canFormArray(int[] arr, int[][] pieces) {
    int m = arr.length, n = pieces.length;
    int i = 0;
    while (i < m) {
      int a = arr[i];
      boolean found = false;
      for (int j = 0; j < n; j++) {
        // 寻找pieces中开头相同的数组
        int b = pieces[j][0];
        if (a == b) {
          // 遍历第j个数组
          i++;
          found = true;
          for (int k = 1; k < pieces[j].length; k++) {
            if (arr[i++] != pieces[j][k]) {
              return false;
            }
          }
          break;
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }

  public int countVowelStrings(int n) {
    // d[i][j] 长度为i的字符串最后一个字母为第j个元音字母
    int[] d = new int[5];
    Arrays.fill(d, 1);
    for (int j = 1; j < n; j++) {
      int[] td = new int[5];
      td[0] = d[0];
      td[1] = d[0] + d[1];
      td[2] = d[0] + d[1] + d[2];
      td[3] = d[0] + d[1] + d[2] + d[3];
      td[4] = d[0] + d[1] + d[2] + d[3] + d[4];
      d = td;
    }
    return Arrays.stream(d).sum();
  }

  public int furthestBuilding(int[] heights, int bricks, int ladders) {
    int n = heights.length;
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int numOfBrick = 0;
    for (int i = 0; i < n - 1; i++) {
      int delta = heights[i + 1] - heights[i];
      if (delta <= 0) {
        continue;
      }
      pq.offer(delta);
      if(pq.size() > ladders){
        // 先用梯子 梯子不够时
        int smallest = pq.poll();
        numOfBrick += smallest;
        if(numOfBrick > bricks){
          return i;
        }
      }
    }
    return n - 1;
  }

  public String kthSmallestPath(int[] destination, int k) {
    int m = destination[0], n = destination[1];
    // 计算组合数
    int[][] c = new int[m + n + 1][m + n + 1];
    for (int i = 0; i <= n + m; i++) {
      c[i][0] = 1;
    }
    for (int i = 1; i <= n + m; i++) {
      for (int j = 1; j <= i; j++) {
        c[i][j] = c[i - 1][j] + c[i - 1][j - 1];
      }
    }
    int v = m, h = n;
    int order = 1;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= m + n; i++) {
      if (v == 0) {
        sb.append("H");
        continue;
      }
      if (h == 0) {
        sb.append("V");
        continue;
      }
      // H开头的字符串的数目
      long comb = c[v + h - 1][h - 1];
      if (order + comb > k) {
        // 选择H 字典序不会增加
        sb.append("H");
        h--;
      } else {
        // 选择V 字典序增加comb
        sb.append("V");
        v--;
        order += comb;
      }
    }
    return sb.toString();
  }
}
