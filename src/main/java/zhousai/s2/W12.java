package zhousai.s2;

import struct.Pair;

import java.util.*;

/** 2020/11/15 */
public class W12 {

  static class OrderedStream {

    private String[] strings;
    private int ptr;

    public OrderedStream(int n) {
      strings = new String[n + 1];
      ptr = 1;
    }

    public List<String> insert(int id, String value) {
      List<String> ans = new ArrayList<>();
      strings[id] = value;
      while (ptr < strings.length) {
        if (strings[ptr] == null) {
          break;
        }
        ans.add(strings[ptr]);
        ptr++;
      }
      return ans;
    }
  }

  public boolean closeStrings(String word1, String word2) {
    int n = word1.length();
    if (n != word2.length()) {
      return false;
    }
    Map<Character, Integer> times = new HashMap<>();
    Map<Character, Integer> times2 = new HashMap<>();
    Map<Integer, Integer> timeToCat = new HashMap<>();
    Map<Integer, Integer> timeToCat2 = new HashMap<>();
    for (int i = 0; i < n; i++) {
      char c = word1.charAt(i);
      times.put(c, times.getOrDefault(c, 0) + 1);
    }
    for (int i = 0; i < n; i++) {
      char c = word2.charAt(i);
      times2.put(c, times2.getOrDefault(c, 0) + 1);
    }
    if (!times.keySet().equals(times2.keySet())) {
      return false;
    }

    for (Integer time : times.values()) {
      timeToCat.put(time, timeToCat.getOrDefault(time, 0) + 1);
    }
    for (Integer time : times2.values()) {
      timeToCat2.put(time, timeToCat2.getOrDefault(time, 0) + 1);
    }
    for (Integer integer : timeToCat.keySet()) {
      if (!timeToCat.get(integer).equals(timeToCat2.get(integer))) {
        return false;
      }
    }
    return true;
  }

  public int minOperations(int[] nums, int x) {
    int n = nums.length;
    long[] preSum = new long[n + 1];
    long[] postSum = new long[n + 1];
    for (int i = 0; i < n; i++) {
      preSum[i + 1] = preSum[i] + nums[i];
      postSum[i + 1] = postSum[i] + nums[n - i - 1];
    }
    int ans = Integer.MAX_VALUE;
    for (int i = 0; i <= n; i++) {
      long ci = preSum[i];
      if (ci > x) {
        break;
      }
      int j = Arrays.binarySearch(postSum, 0, n - i + 1, x - ci);
      if (j < 0) {
        continue;
      }
      ans = Math.min(ans, i + j);
    }
    return ans == Integer.MAX_VALUE ? -1 : ans;
  }

  /**
   * https://leetcode-cn.com/problems/maximize-grid-happiness/solution/zui-da-hua-wang-ge-xing-fu-gan-by-zerotrac2/
   */
  public int getMaxGridHappiness(int m, int n, int introvertsCount, int extrovertsCount) {
    this.m = m;
    this.n = n;
    this.n3 = (int) Math.pow(3, n);

    // 预处理
    for (int mask = 0; mask < n3; mask++) {
      for (int mask_tmp = mask, i = 0; i < n; i++) {
        mask_span[mask][i] = mask_tmp % 3;
        mask_tmp = mask_tmp / 3;
      }
      nx_inner[mask] = wx_inner[mask] = score_inner[mask] = 0;
      for (int i = 0; i < n; i++) {
        if (mask_span[mask][i] != 0) {
          // 个人分数
          if (mask_span[mask][i] == 1) {
            nx_inner[mask]++;
            score_inner[mask] += 120;
          } else if (mask_span[mask][i] == 2) {
            wx_inner[mask]++;
            score_inner[mask] += 40;
          }
          // 行内分数
          if (i - 1 >= 0) {
            score_inner[mask] += calc(mask_span[mask][i], mask_span[mask][i - 1]);
          }
        }
      }
    }
    // 行外分数
    for (int mask0 = 0; mask0 < n3; mask0++) {
      for (int mask1 = 0; mask1 < n3; mask1++) {
        score_outer[mask0][mask1] = 0;
        for (int i = 0; i < n; i++) {
          score_outer[mask0][mask1] += calc(mask_span[mask0][i], mask_span[mask1][i]);
        }
      }
    }

    for (int[][][] d1 : dp) {
      for (int[][] d2 : d1) {
        for (int[] d3 : d2) {
          Arrays.fill(d3, -1);
        }
      }
    }

    return dfs(0, 0, introvertsCount, extrovertsCount);
  }

  private int[][] mask_span = new int[729][6];
  private int[] nx_inner = new int[729];
  private int[] wx_inner = new int[729];
  private int[] score_inner = new int[729];
  private int[][] score_outer = new int[729][729];
  private int[][][][] dp = new int[729][6][7][7];
  private int m, n, n3;

  private int dfs(int mask_last, int row, int nx, int wx) {
    if (row == m || nx + wx == 0) {
      return 0;
    }
    if (dp[mask_last][row][nx][wx] != -1) {
      return dp[mask_last][row][nx][wx];
    }
    int best = 0;
    for (int mask = 0; mask < n3; mask++) {
      if (nx_inner[mask] > nx || wx_inner[mask] > wx) {
        continue;
      }
      int score = score_inner[mask] + score_outer[mask][mask_last];
      best = Math.max(best, score + dfs(mask, row + 1, nx - nx_inner[mask], wx - wx_inner[mask]));
    }

    return dp[mask_last][row][nx][wx] = best;
  }

  private int calc(int x, int y) {
    if (x == 0 || y == 0) {
      return 0;
    }
    if (x == 1 && y == 1) {
      return -60;
    }
    if (x == 2 && y == 2) {
      return 40;
    }
    return -10;
  }

  /**
   * LeetCode391 完美矩形
   */
  public boolean isRectangleCover(int[][] rectangles) {
    // 完美矩形的四个端点
    int x2 = Integer.MIN_VALUE, y2 = Integer.MIN_VALUE;
    int x1 = Integer.MAX_VALUE, y1 = Integer.MAX_VALUE;
    // 实际面积
    int actual_area = 0;
    // 每个小矩形对应的 四个端点
    Set<Pair<Integer, Integer>> set = new HashSet<>();
    for (int[] rec : rectangles) {
      int tx1 = rec[0], ty1 = rec[1];
      int tx2 = rec[2], ty2 = rec[3];
      // 每个小矩形对应的 四个端点
      Pair<Integer, Integer> p1 = new Pair<>(tx1, ty1);
      Pair<Integer, Integer> p2 = new Pair<>(tx1, ty2);
      Pair<Integer, Integer> p3 = new Pair<>(tx2, ty1);
      Pair<Integer, Integer> p4 = new Pair<>(tx2, ty2);
      // 确定小矩形组成大矩形的端点
      for (Pair<Integer, Integer> point : Arrays.asList(p1, p2, p3, p4)) {
        if (set.contains(point)) {
          set.remove(point);
        } else {
          set.add(point);
        }
      }

      actual_area += (tx2 - tx1) * (ty2 - ty1);

      x1 = Math.min(x1, tx1);
      y1 = Math.min(y1, ty1);
      x2 = Math.max(x2, tx2);
      y2 = Math.max(y2, ty2);
    }

    int expect_area = (x2 - x1) * (y2 - y1);
    if (actual_area != expect_area) return false;
    // 完美矩形只能有4个端点
    if (set.size() != 4) return false;
    // 两种方式得到的端点必须相同
    if (!set.contains(new Pair<>(x1, y1))) return false;
    if (!set.contains(new Pair<>(x1, y2))) return false;
    if (!set.contains(new Pair<>(x2, y1))) return false;
    if (!set.contains(new Pair<>(x2, y2))) return false;

    return true;
  }

  public static void main(String[] args) {
    W12 w = new W12();

    //    boolean b = w.closeStrings("uau", "ssx");
    //    System.out.println(b);
    int[] nums = {1, 1};
    int i = w.minOperations(nums, 3);
    System.out.println(i);
  }
}
