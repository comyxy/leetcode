package dp;

/** 2020/7/8 */
public class Robot {

  /** LeetCode62 */
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (i == 0 && j == 0) {
          dp[i][j] = 1;
        } else if (i == 0) {
          dp[i][j] = dp[i][j - 1];
        } else if (j == 0) {
          dp[i][j] = dp[i - 1][j];
        } else {
          dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
        }
      }
    }
    return dp[m - 1][n - 1];
  }

  /** LeetCode63 */
  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    final int m = obstacleGrid.length;
    if (m == 0) {
      throw new RuntimeException();
    }
    final int n = obstacleGrid[0].length;

    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int ob = obstacleGrid[i][j];
        if (ob == 1) {
          dp[i][j] = 0;
        } else {
          if (i == 0 && j == 0) {
            dp[i][j] = 1;
          } else if (i == 0) {
            dp[i][j] = dp[i][j - 1];
          } else if (j == 0) {
            dp[i][j] = dp[i - 1][j];
          } else {
            dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
          }
        }
      }
    }
    return dp[m - 1][n - 1];
  }

  /** LeetCode64 */
  public int minPathSum(int[][] grid) {
    final int m = grid.length;
    if (m == 0) {
      throw new RuntimeException();
    }
    final int n = grid[0].length;

    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (i == 0 && j == 0) {
          dp[i][j] = grid[i][j];
        } else if (i == 0) {
          dp[i][j] = dp[i][j - 1] + grid[i][j];
        } else if (j == 0) {
          dp[i][j] = dp[i - 1][j] + grid[i][j];
        } else {
          dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + grid[i][j];
        }
      }
    }
    return dp[m - 1][n - 1];
  }
}
