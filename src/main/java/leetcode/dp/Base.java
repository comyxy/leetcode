package leetcode.dp;

import java.util.Arrays;

/**
 * @date 2020/6/27
 */
public class Base {

    /**
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度
     *
     * @param nums 无序的整数数组
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        final int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // dp[i]代表前i个元素中的最长上升子序列
        int[] dp = new int[n];
        dp[0] = 1;
        int res = 1;
        for (int i = 1; i < n; i++) {
            // 找到索引i对应的nums[i]大于nums[j]的值
            int dpm = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dpm = Math.max(dpm, dp[j]);
                }
            }
            dp[i] = 1 + dpm;
            res = Math.max(dp[i], res);
        }
        return res;
    }

    /**
     * 找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和
     *
     * @param nums 整数数组
     * @return
     */
    public int maxSubArray(int[] nums) {
        final int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // dp[i]前i个元素中以第i个元素结尾的连续子数组的最大和
        int[] dp = new int[n];
        dp[0] = nums[0];
        int res = dp[0];
        for (int i = 1; i < n; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 0-1背包问题
     *
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

    /**
     * 是否可以将这个数组分割成两个子集，使得两个子集的元素和相等
     * 转化为0-1背包问题
     *
     * @param nums 数组
     * @return
     */
    public boolean canPartition(int[] nums) {
        final int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        }
        sum = sum / 2;
        // dp[i][j]代表前i个元素包容量为j时 能否刚好装下
        boolean[][] dp = new boolean[n + 1][sum + 1];
        //dp[0][.] = false dp[.][0] = true
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
     * 凑零钱2
     * 完全背包问题
     *
     * @param amount 总数量
     * @param coins  硬币种类数组
     * @return
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

    /*--------------------------------------------------------------------*/

    /**
     * 编辑距离
     * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     *
     * @param word1 单词1
     * @param word2 单词2
     * @return
     */
    public int minDistance(String word1, String word2) {
        char[] cs1 = word1.toCharArray();
        char[] cs2 = word2.toCharArray();
//        return minDistanceHelper(cs1, cs1.length - 1, cs2, cs2.length - 1);
        return minDistanceHelper(cs1, cs2);
    }

    /**
     * 自顶向下 无优化 动态规划
     * dp(i,j)
     * 代表cs1[0...i]与cs2[0...j]之间的编辑距离
     * base: 空字符串与cs2[0...j]之间的编辑距离为j+1 cs1[0...i]与空字符串之间的编辑距离为i+1
     *
     * @param cs1
     * @param i
     * @param cs2
     * @param j
     * @return
     */
    private int minDistanceHelper(char[] cs1, int i, char[] cs2, int j) {
        if (i == -1) {
            return j + 1;
        }
        if (j == -1) {
            return i + 1;
        }

        if (cs1[i] == cs2[j]) {
            return minDistanceHelper(cs1, i - 1, cs2, j - 1);
        } else {
            // 选择三种操作中步骤最少的
            return min(minDistanceHelper(cs1, i - 1, cs2, j - 1) + 1, // cs1替换操作
                    minDistanceHelper(cs1, i, cs2, j - 1) + 1, // cs1增加操作
                    minDistanceHelper(cs1, i - 1, cs2, j) + 1);  // cs1删除操作
        }
    }

    /**
     * 自底向上动态规划
     *
     * @param cs1
     * @param cs2
     * @return
     */
    private int minDistanceHelper(char[] cs1, char[] cs2) {
        final int l1 = cs1.length, l2 = cs2.length;
        // dp[i][j]代表cs1前i个与cs2前j个字符之间的编辑距离 i=0或者j=0代表空字符
        int[][] dp = new int[l1 + 1][l2 + 1];
        // base
        for (int i = 0; i <= l1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= l2; j++) {
            dp[0][j] = j;
        }

        // choose
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                // 索引对应关系减一
                if (cs1[i - 1] == cs2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i - 1][j] + 1, //cs1删除
                            dp[i][j - 1] + 1, //cs1增加
                            dp[i - 1][j - 1] + 1); // cs1替换
                }
            }
        }
        return dp[l1][l2];
    }

    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public int minDistanceWithTrace(String word1, String word2) {
        char[] cs1 = word1.toCharArray();
        char[] cs2 = word2.toCharArray();
        final int l1 = cs1.length, l2 = cs2.length;
        // dp[i][j]代表cs1前i个与cs2前j个字符之间的编辑距离 i=0或者j=0代表空字符
        Node[][] dp = new Node[l1 + 1][l2 + 1];
        for (int m = 0; m <= l1; m++) {
            for (int n = 0; n <= l2; n++) {
                dp[m][n] = new Node();
            }
        }
        // base
        for (int i = 0; i <= l1; i++) {
            dp[i][0].val = i;
        }
        for (int j = 0; j <= l2; j++) {
            dp[0][j].val = j;
        }

        // choose
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                // 索引对应关系减一
                if (cs1[i - 1] == cs2[j - 1]) {
                    dp[i][j].val = dp[i - 1][j - 1].val;
                    dp[i][j].choice = Choice.SKIP;
                    dp[i][j].prev = dp[i - 1][j - 1];
                } else {
                    dp[i][j].val = min(dp[i - 1][j].val + 1, //cs1删除
                            dp[i][j - 1].val + 1, //cs1增加
                            dp[i - 1][j - 1].val + 1); // cs1替换
                    if (dp[i][j].val == dp[i - 1][j].val + 1) {
                        dp[i][j].choice = Choice.DELETE;
                        dp[i][j].prev = dp[i - 1][j];
                    } else if (dp[i][j].val == dp[i][j - 1].val + 1) {
                        dp[i][j].choice = Choice.INSERT;
                        dp[i][j].prev = dp[i][j - 1];
                    } else {
                        dp[i][j].choice = Choice.REPLACE;
                        dp[i][j].prev = dp[i - 1][j - 1];
                    }
                }
            }
        }
        //print out
        Node t = dp[l1][l2];
        while (t != null) {
            System.out.println(t);
            t = t.prev;
        }
        return dp[l1][l2].val;
    }

    private static class Node {
        int val;
        Choice choice;
        Node prev;

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", choice=" + choice +
                    '}';
        }
    }

    private enum Choice {
        SKIP, INSERT, DELETE, REPLACE;
    }

    /**
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] cs1 = text1.toCharArray();
        char[] cs2 = text2.toCharArray();
        return longestCommonSubsequenceHelper(cs1, cs1.length - 1, cs2, cs2.length - 1);
    }

    private int longestCommonSubsequenceHelper(char[] cs1, int i, char[] cs2, int j) {
        // dp[i][j] 字符串cs1前i个字符 与 字符串cs2前j个字符 的最大公共子序列
        // 两个字符串从后往前遍历
        // base
        if (i == -1) {
            return 0;
        }
        if (j == -1) {
            return 0;
        }

        if (cs1[i] == cs2[j]) {
            return longestCommonSubsequenceHelper(cs1, i - 1, cs2, j - 1) + 1;
        } else {
            return Math.max(
                    longestCommonSubsequenceHelper(cs1, i - 1, cs2, j),
                    longestCommonSubsequenceHelper(cs1, i, cs2, j - 1)
            );
        }
    }

    public int longestCommonSubsequence2(String text1, String text2) {
        char[] cs1 = text1.toCharArray();
        char[] cs2 = text2.toCharArray();
        final int l1 = cs1.length, l2 = cs2.length;
        // dp[i][j] 字符串cs1前i个字符 与 字符串cs2前j个字符 的最大公共子序列
        int[][] dp = new int[l1 + 1][l2 + 1];
        // base
        for (int i = 0; i <= l1; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= l2; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (cs1[i - 1] == cs2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[l1][l2];
    }

    /**
     * 给定一个字符串 s ，找到其中最长的回文子序列，并返回该序列的长度
     *
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {
        char[] cs = s.toCharArray();
        final int n = cs.length;
        // dp[i][j] 代表 [i,j] 第i个字符到第j个字符字符串所代表的最长回文串序列
        int[][] dp = new int[n][n];
        // base
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        // 注意遍历顺序 从状态转移方程中得到
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                // 状态转移
                if (cs[i] == cs[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }
        return dp[0][n - 1];
    }
}
