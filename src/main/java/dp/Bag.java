package dp;

/**
 * 0-1 背包问题
 */
public class Bag {

    /**
     * 背包问题
     *
     * @param W   背包最大重量
     * @param N   物品数量
     * @param wt  物品重量数组
     * @param val 物品价值数组
     */
    public int knapsack(int W, int N, int[] wt, int[] val) {
        // dp[i][j]代表 只有前i个物品以及背包最大重量为j时 最大价值
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= W; j++) {
                if (j - wt[i - 1] < 0) {
                    // 余下重量不够放下该物品
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j],
                            dp[i - 1][j - wt[i - 1]] + val[i - 1]); // 没有放该物品时价值+该物品价值
                }
            }
        }
        return dp[N][W];
    }

    /**
     * LeetCode416 是否可以将这个数组分割成两个子集，使得两个子集的元素和相等
     * 转化为0-1背包问题
     *
     * @param nums 数组
     */
    public boolean canPartition(int[] nums) {
        final int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // 奇数不可能分为2个子集
        if ((sum & 1) == 1) {
            return false;
        }
        sum = sum / 2;
        // dp[i][j]代表前i个元素 包容量为j时 能否刚好装下
        boolean[][] dp = new boolean[n + 1][sum + 1];
        //dp[0][.] = false dp[.][0] = true
        // 没有元素容量不为0则不能
        // 有元素容量为0则为能
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j - nums[i - 1] < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }

    /**
     * LeetCode518 凑零钱2 硬币数目无限
     * 完全背包问题
     *
     * @param amount 总数量
     * @param coins  硬币种类数组
     * @return 多少种组合方法
     */
    public int change(int amount, int[] coins) {
        final int n = coins.length;
        // dp[i][j]代表前i种硬币凑amount元一共的种类数
        int[][] dp = new int[n + 1][amount + 1];
        //dp[.][0] = 1 dp[0][.] = 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[n][amount];
    }
}
