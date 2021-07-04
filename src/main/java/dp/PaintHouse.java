package dp;

import java.util.Arrays;

/**
 * @author comyxy
 */
public class PaintHouse {
    private static final int INF = Integer.MAX_VALUE / 2;

    /**
     * 1473
     */
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        // dp(i,j,k) 0-i个房子都已经刷过 且第i个房子是第j种颜色 属于第k个街区 最小值
        int[][][] dp = new int[m][n][target];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        for (int i = 0; i < m; i++) {
            houses[i]--;
        }

        // 状态转移
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 第i个房子已经被刷过了 但是不是第j种颜色 这种情况下dp(i,j,k)为INF表示不可能
                if(houses[i] != -1 && houses[i] != j) {
                    continue;
                }
                for (int k = 0; k < target; k++) {
                    // 上一个房子i-1 的颜色为j0
                    for (int j0 = 0; j0 < n; j0++) {
                        if(j == j0) {
                            // 与上个房子颜色相同 则同属于一个街区k
                            if(i > 0) {
                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i-1][j0][k]);
                            }else if(k == 0) {
                                // 对于dp(0,j,0)
                                dp[i][j][k] = 0;
                            }
                        }else {
                            // 与上个房子颜色不同 从第1个位置和第1种颜色开始考虑
                            if(i > 0 && k > 0) {
                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i-1][j0][k-1]);
                            }
                        }
                    }
                    // 对于需要刷的房子 计算cost
                    if(dp[i][j][k] != INF && houses[i] == -1) {
                        dp[i][j][k] += cost[i][j];
                    }
                }
            }
        }

        int res = INF;
        for (int j = 0; j < n; j++) {
            res = Math.min(res, dp[m-1][j][target-1]);
        }
        return res == INF ? -1 : res;
    }
}
