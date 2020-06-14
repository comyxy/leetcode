package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 买卖股票的最佳时期
 * 参考:https://labuladong.gitbook.io/algo/di-ling-zhang-bi-du-xi-lie/tuan-mie-gu-piao-wen-ti
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870
 * 对状态与选择进行枚举 选择：买入、卖出、无操作 状态：3个，天数、至多交易次数、股票持有状态（0未持有，1持有）
 * dp[i][k][0 or 1] 0<=i<=n-1, 1<=k<=K n为天数 K最多交易数 共 n*K*2 个状态
 * 状态转移：
 * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1]+prices[i]) = max(选择为无操作, 选择为卖出股票)
 * 今天我没有持有股票，有两种可能：要么是我昨天就没有持有，然后今天选择无操作，所以我今天还是没有持有；
 * 要么是我昨天持有股票，但是今天我卖出股票了，所以我今天没有持有股票了。
 * <p>
 * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0]-prices[i]) = max(选择为无操作， 选择为购入股票)
 * 今天我持有着股票，有两种可能：要么我昨天就持有着股票，然后今天选择无操作，所以我今天还持有着股票；
 * 要么我昨天本没有持有，但今天我选择购入，所以今天我就持有股票了。
 * 基础条件：
 */
public class BuyStock {
    /**
     * LeetCode 121
     *
     * @param prices 股票每日价格
     * @return 最大利润
     */
    public int maxProfit1(int[] prices) {
/*        // 找历史最低价格与最高价格
        final int length = prices.length;
        if(length <= 1){
            return 0;
        }
        // 股票最大收益
        int profit = 0;
        // 历史最低价格
        int minHistoryPrice = Integer.MAX_VALUE;
        for (int price : prices) {
            if(minHistoryPrice > price){
                minHistoryPrice = price;
            }
            if(price - minHistoryPrice > profit){
                profit = price - minHistoryPrice;
            }
        }
        return profit;
*/
/*        // 状态机方法
        final int length = prices.length;
        int[][] dp = new int[length][2];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[length - 1][0];
*/
        // 状态机方法的空间优化
        int dp_i0 = 0, dp_i1 = Integer.MIN_VALUE;
        for (int price : prices) {
            dp_i0 = Math.max(dp_i0, dp_i1 + price);
            dp_i1 = Math.max(dp_i1, -price);
        }
        return dp_i0;
    }

    /**
     * LeetCode122
     * 不限制交易次数 k不在是一个状态
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
/*
        // 状态机
        final int length = prices.length;
        int[][] dp = new int[length][2];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[length - 1][0];
*/
        // 空间优化
        int dp_i0 = 0, dp_i1 = Integer.MIN_VALUE;
        for (int price : prices) {
            int dp_t = dp_i0;
            dp_i0 = Math.max(dp_i0, dp_i1 + price);
            dp_i1 = Math.max(dp_i1, dp_t - price);
        }
        return dp_i0;
    }

    /**
     * LeetCode123
     * K=2 时
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1]+prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0]-prices[i])
     * ==>
     * dp[i][2][0] = max(dp[i-1][2][0], dp[i-1][2][1]+prices[i]);
     * dp[i][2][1] = max(dp[i-1][2][1], dp[i-1][1][0]-prices[i]);
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1]+prices[i]);
     * dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][0][0]-prices[i]);
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int dp_i20 = 0, dp_i21 = Integer.MIN_VALUE;
        int dp_i10 = 0, dp_i11 = Integer.MIN_VALUE;
        for (int price : prices) {
            dp_i20 = Math.max(dp_i20, dp_i21 + price);
            dp_i21 = Math.max(dp_i21, dp_i10 - price);
            dp_i10 = Math.max(dp_i10, dp_i11 + price);
            // dp[i-1][0][0] is 0 for any i
            dp_i11 = Math.max(dp_i11, 0 - price);
        }
        return dp_i20;
    }

    /**
     * LeetCode188
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1]+prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0]-prices[i])
     *
     * @param k      最多交易次数
     * @param prices 股价
     * @return 最大利润
     */
    public int maxProfit4(int k, int[] prices) {
/*
        // 状态空间
        final int length = prices.length;
        int[][][] dp = new int[length][k + 1][2];
        for (int i = 0; i < length; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = Integer.MIN_VALUE;
            for (int j = k; j >= 1; j--) {
                if (i == 0) {
                    // dp[0][j][0] is 0 and
                    // dp[0][j][1] = max(dp[-1][j][1],dp[-1][j][0]-prices[i])=max(-Inf,-prices[i])=-prices[i];
                    dp[i][j][0] = 0;
                    dp[i][j][1] = -prices[i];
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[length - 1][k][0];
*/
        final int length = prices.length;
        // k 大于等于 天数的一半时 k 对 profit 就没有影响
        if (k >= length / 2) {
            return maxProfit2(prices);
        }
        // 空间优化
        // dp_ik0 -> dp[i][k][0]
        // dp_ik1 -> dp[i][k][1]
        // dp[-1][k][0] = 0 and dp[-1][k][1] = -Inf
        int[] dp_ik0 = new int[k + 1];
        int[] dp_ik1 = new int[k + 1];
        Arrays.fill(dp_ik1, Integer.MIN_VALUE);
        for (int price : prices) {
            for (int j = k; j >= 1; j--) {
                dp_ik0[j] = Math.max(dp_ik0[j], dp_ik1[j] + price);
                dp_ik1[j] = Math.max(dp_ik1[j], dp_ik0[j - 1] - price);
            }
        }
        return dp_ik0[k];
    }

    /**
     * LeetCode309
     *
     * @param coolDown 冷冻期
     * @param prices   股价
     * @return
     */
    public int maxProfit5(int coolDown, int[] prices) {
        int dp_ik0 = 0, dp_ik1 = Integer.MIN_VALUE;
        Queue<Integer> cache_dp_ik0 = new LinkedList<Integer>();
        for (int i = 0; i < coolDown; i++) {
            cache_dp_ik0.offer(0);
        }
        for (int price : prices) {
            cache_dp_ik0.offer(dp_ik0);
            dp_ik0 = Math.max(dp_ik0, dp_ik1 + price);
            //noinspection ConstantConditions
            dp_ik1 = Math.max(dp_ik1, cache_dp_ik0.poll() - price);
        }

        return dp_ik0;
    }


    /**
     * LeetCode714
     *
     * @param fee    手续费
     * @param prices 股价
     * @return
     */
    public int maxProfit6(int fee, int[] prices) {
/*
        long dp_i0 = 0, dp_i1 = Integer.MIN_VALUE;
        for (int price : prices) {
            long dp_t = dp_i0;
            // 注意溢出 dp_i1=Integer.MIN_VALUE price < fee 就会溢出
            dp_i0 = Math.max(dp_i0, dp_i1 + price - fee);
            dp_i1 = Math.max(dp_i1, dp_t - price);
        }
        return (int) dp_i0;
*/
        int dp_i0 = 0, dp_i1 = Integer.MIN_VALUE;
        for (int price : prices) {
            int dp_t = dp_i0;
            dp_i0 = Math.max(dp_i0, dp_i1 + price);
            dp_i1 = Math.max(dp_i1, dp_t - price - fee);
        }
        return (int) dp_i0;
    }
}
