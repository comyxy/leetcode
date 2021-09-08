package dp;

/**
 * 背包问题
 */
public class Bag {

    /**
     * 01背包问题
     * f[i][v]代表 只有前i个物品以及背包最大费用为v时 最大价值
     * f[i][v] = max{f[i-1][v], f[i-1][v-c[i]]+w[i]} 物品
     * 空间压缩下 f[v] = max{f[v], f[v-c[i]]+w[i]} v: V->0
     *
     * @param N 物品数量
     * @param V 背包最大重量
     * @param c 物品费用数组
     * @param w 物品价值数组
     */
    public int zeroOnePack(int N, int V, int[] c, int[] w) {

        int[] f = new int[V + 1];
        for (int i = 1; i <= N; i++) {
            for (int v = V; v >= c[i - 1]; v--) {
                f[v] = Math.max(f[v], f[v - c[i - 1]] + w[i - 1]);
            }
        }
        return f[V];
    }

    /**
     * 完全背包问题
     * f[i][v]代表 只有前i个物品以及背包最大费用为v时 最大价值
     * f[i][v] = max{f[i-1][v], f[i][v-c[i]]+w[i]} 物品可以多次取
     * 空间压缩下 f[v] = max{f[v], f[v-c[i]]+w[i]} v: c[i]->V
     *
     * @param N 物品数量
     * @param V 背包最大重量
     * @param c 物品费用数组
     * @param w 物品价值数组
     */
    public int completePack(int N, int V, int[] c, int[] w) {
        int[] f = new int[V + 1];
        for (int i = 1; i <= N; i++) {
            for (int v = c[i - 1]; v <= V; v++) {
                f[v] = Math.max(f[v], f[v - c[i - 1]] + w[i - 1]);
            }
        }
        return f[V];
    }

    /**
     * 多重背包问题
     * 物品有数量限制
     *
     * @param N 物品数量
     * @param V 背包最大重量
     * @param c 物品费用数组
     * @param w 物品价值数组
     * @param a 物品数量
     */
    public int multiplePack(int N, int V, int[] c, int[] w, int[] a) {
        int[] f = new int[V + 1];
        for (int i = 1; i <= N; i++) {
            int cost = c[i - 1], weight = w[i - 1], amount = a[i - 1];
            if (cost * amount >= V) {
                // 此件物品无论怎样都取不尽
                // completePack
                for (int v = cost; v <= V; v++) {
                    f[v] = Math.max(f[v], f[v - cost] + weight);
                }
            } else {
                // 将amount按位分解 比如9 = (1 + 2 + 3) + 2
                int k = 1;
                while (k < amount) {
                    for (int v = V; v >= k * cost; v--) {
                        f[v] = Math.max(f[v], f[v - k * cost] + k * weight);
                    }
                    amount -= k;
                    k *= 2;
                }
                for (int v = V; v >= amount * cost; v--) {
                    f[v] = Math.max(f[v], f[v - amount * cost] + amount * weight);
                }
            }
        }
        return f[V];
    }

    /**
     * 二维代价背包问题 代价不止一种
     * 给定一个二进制字符串数组strs和两个整数m和n
     * 找出并返回strs的最大子集的大小 子集种最多有m个0和n个1
     * f[i][u][v] = max(f[i-1][u][v], f[i-1][u-c0[i]][v-c1[i]]
     */
    public int findMaxForm(String[] strs, int m, int n) {
        final int len = strs.length;
        int[][] costs = new int[len][2];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < strs[i].length(); j++) {
                if (strs[i].charAt(j) == '0') {
                    costs[i][0]++;
                } else {
                    costs[i][1]++;
                }
            }
        }
        int[][] f = new int[m + 1][n + 1];
        for (int i = 1; i <= len; i++) {
            int c0 = costs[i-1][0], c1 = costs[i-1][1];
            for (int u = m; u >= c0; u--) {
                for (int v = n; v >= c1; v--) {
                    f[u][v] = Math.max(f[u][v], f[u - c0][v - c1] + 1);
                }
            }
        }
        return f[m][n];
    }


    /**
     * LeetCode416 是否可以将这个数组分割成两个子集 使得两个子集的元素和相等
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
        int half = sum / 2;
        // dp[i][j]代表前i个元素 包容量为j时 能否刚好装下
        // dp[i][j] = dp[i-1][j] | dp[i-1][j-nums[i]]
        // 空间压缩 dp[j]=dp[j] | dp[j-nums[i]]
        boolean[] dp = new boolean[half + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = half; j >= nums[i - 1]; j--) {
                dp[j] = dp[j] | dp[j - nums[i - 1]];
            }
        }
        return dp[half];
    }

    /**
     * LeetCode518 凑零钱2 硬币数目无限
     * 完全背包问题
     * f[i][j]代表前i种硬币凑j元一共的种类数
     * f[i][j] = f[i-1][j] + f[i][j-c[i]]
     * 空间压缩 f[j] = f[j] + f[j-c[i]] j: c[i]->amount, f[0]=1
     *
     * @param amount 总数量
     * @param coins  硬币种类数组
     * @return 多少种组合方法
     */
    public int change(int amount, int[] coins) {
        final int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = coins[i - 1]; j <= amount; j++) {
                dp[j] = dp[j] + dp[j - coins[i - 1]];
            }
        }
        return dp[amount];
    }
}
