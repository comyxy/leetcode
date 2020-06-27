package leetcode.dp;

/**
 * 0-1 背包问题
 *
 * @date 2020/6/27
 */
public class Bag {

    /**
     * @param W   背包最大重量
     * @param N   物品数量
     * @param wt  物品重量数组
     * @param val 物品价值数组
     * @return
     */
    public int knapsack(int W, int N, int[] wt, int[] val) {
        // dp[i][j]代表 只有前i个物品以及背包最大重量为j时 最大价值
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= W; j++) {
                if (j - wt[i - 1] < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - wt[i - 1]] + val[i - 1]);
                }
            }
        }
        return dp[N][W];
    }
}
