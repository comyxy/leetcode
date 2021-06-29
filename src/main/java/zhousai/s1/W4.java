package zhousai.s1;

import java.util.*;

/** 2020/9/20 */
public class W4 {
  public String reorderSpaces(String text) {
    int space = 0;
    List<String> words = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == ' ') {
        space++;
        if (sb.length() != 0) {
          words.add(new String(sb));
          sb.delete(0, sb.length());
        }
      } else {
        sb.append(text.charAt(i));
      }
      if (i == text.length() - 1 && text.charAt(i) != ' ') {
        words.add(new String(sb));
      }
    }
    sb = new StringBuilder();
    if (words.size() == 1) {
      sb.append(words.get(0));
      for (int i = 0; i < space; i++) {
        sb.append(' ');
      }
      return sb.toString();
    }
    int t = space / (words.size() - 1);
    int r = space % (words.size() - 1);
    for (int i = 0; i < words.size() - 1; i++) {
      sb.append(words.get(i));
      for (int j = 0; j < t; j++) {
        sb.append(' ');
      }
    }
    sb.append(words.get(words.size() - 1));
    for (int i = 0; i < r; i++) {
      sb.append(' ');
    }
    return sb.toString();
  }

  private int ans = 1;

  /** 回溯 分割字符串 */
  public int maxUniqueSplit(String s) {
    Set<String> set = new HashSet<>();
    dfs(0, set, s);
    return ans;
  }

  private void dfs(int index, Set<String> set, String s) {
    if (index == s.length()) {
      ans = Math.max(ans, set.size());
      return;
    }
    for (int i = index; i < s.length(); i++) {
      String t = s.substring(index, i + 1);
      if (set.contains(t)) {
        continue;
      }
      set.add(t);
      dfs(i + 1, set, s);
      set.remove(t);
    }
  }

  private static final int MOD = 1000000007;

  /** 注意溢出 */
  public int maxProductPath(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    long[][] min = new long[n][m];
    long[][] max = new long[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (i == 0 && j == 0) {
          min[i][j] = max[i][j] = grid[i][j];
        } else if (i == 0) {
          min[i][j] = max[i][j] = min[i][j - 1] * grid[i][j];
        } else if (j == 0) {
          min[i][j] = max[i][j] = min[i - 1][j] * grid[i][j];
        } else {

          max[i][j] = Math.max(max[i - 1][j] * grid[i][j], max[i][j - 1] * grid[i][j]);
          max[i][j] = Math.max(max[i][j], min[i][j - 1] * grid[i][j]);
          max[i][j] = Math.max(max[i][j], min[i - 1][j] * grid[i][j]);

          min[i][j] = Math.min(min[i - 1][j] * grid[i][j], min[i][j - 1] * grid[i][j]);
          min[i][j] = Math.min(min[i][j], max[i][j - 1] * grid[i][j]);
          min[i][j] = Math.min(min[i][j], max[i - 1][j] * grid[i][j]);
        }
      }
    }
    return max[n - 1][m - 1] >= 0 ? (int) (max[n - 1][m - 1] % MOD) : -1;
  }

  public int connectTwoGroups(List<List<Integer>> cost) {
    int m = cost.size();
    int n = cost.get(0).size();
    int[][] costMatrix = new int[m][1 << n];
    // 获得每一行每2^n种情况下的代价和
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < (1 << n); j++) {
        int sum = 0;
        int x = j, k = 0;
        while( x > 0){
          if((x & 1) > 0){
            sum+=cost.get(i).get(k);
          }
          k++;
          x = x>>1;
        }
//        for (int k = 0; k < n; k++) {
//          if ((j & (1 << k)) > 0) {
//            sum += cost.get(i).get(k);
//          }
//        }
        costMatrix[i][j] = sum;
      }
    }
    // dp(i,j) 从第0行到第i行 选取情况为j的最小代价
    int[][] dp = new int[m][1 << n];
    for (int i = 0; i < m; i++) {
      Arrays.fill(dp[i], Integer.MAX_VALUE);
    }
    dp[0] = costMatrix[0];
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < (1 << n); j++) {
        for (int k = 1; k < (1 << n); k++) {
          dp[i][j | k] = Math.min(dp[i][j | k], dp[i - 1][k] + costMatrix[i][j]);
        }
      }
    }
    return dp[m - 1][(1 << n) - 1];
  }

  public static void main(String[] args) {
    W4 w4 = new W4();
    //    String reorderSpaces = w4.reorderSpaces("  hello");
    //    System.out.println(reorderSpaces);

    //    int maxUniqueSplit = w4.maxUniqueSplit("addbsd");
    //    System.out.println(maxUniqueSplit);
    //
    //    int[][] grids = {
    //      {1, -2, 1},
    //      {1, -2, 1},
    //      {3, -4, 1}
    //    };
    //    int maxProductPath = w4.maxProductPath(grids);
    //    System.out.println(maxProductPath);

    List<List<Integer>> costs = new ArrayList<>();
    List<Integer> t = new ArrayList<>();
    t.add(1);
    t.add(3);
    t.add(5);
    costs.add(new ArrayList<>(t));
    t.clear();
    t.add(4);
    t.add(1);
    t.add(1);
    costs.add(new ArrayList<>(t));
    t.clear();
    t.add(1);
    t.add(5);
    t.add(3);
    costs.add(new ArrayList<>(t));

    int connectTwoGroups = w4.connectTwoGroups(costs);
    System.out.println(connectTwoGroups);
  }
}
