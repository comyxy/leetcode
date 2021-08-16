package dp;

import static utils.EasyUtil.*;

import java.util.Arrays;
import java.util.List;

/**
 * 2020/6/27 动态规划
 */
public class Base {

    /**
     * LeetCode300 给定一个无序的整数数组，找到其中最长上升子序列的长度 不要求子序列连续
     *
     * @param nums 无序的整数数组
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
     * LeetCode53 找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和
     *
     * @param nums 整数数组
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
                // 以i-1结尾的连续子数组和大于0则继续添加
                dp[i] = dp[i - 1] + nums[i];
            } else {
                // 小于0则重新开始
                dp[i] = nums[i];
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /*--------------------------------------------------------------------*/

    /**
     * LeetCode72 编辑距离 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 插入一个字符 删除一个字符 替换一个字符
     *
     * @param word1 单词1
     * @param word2 单词2
     * @return word1到word2的最少操作数
     */
    public int minDistance(String word1, String word2) {
        char[] cs1 = word1.toCharArray();
        char[] cs2 = word2.toCharArray();
        int result1 = minDistanceHelper(cs1, cs1.length - 1, cs2, cs2.length - 1);
        int result2 = minDistanceHelper(cs1, cs2);
        assert result1 == result2;
        return result2;
    }

    /**
     * 自顶向下 无优化 动态规划 dp(i,j) 代表cs1[0...i]与cs2[0...j]之间的编辑距离 base: 空字符串与cs2[0...j]之间的编辑距离为j+1
     * cs1[0...i]与空字符串之间的编辑距离为i+1
     *
     * @param cs1 字符数组1
     * @param i   cs1索引i
     * @param cs2 字符数组2
     * @param j   cs2索引j
     * @return 编辑距离
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
            return min(
                    minDistanceHelper(cs1, i - 1, cs2, j - 1) + 1, // cs1替换操作
                    minDistanceHelper(cs1, i, cs2, j - 1) + 1, // cs1增加操作
                    minDistanceHelper(cs1, i - 1, cs2, j) + 1); // cs1删除操作
        }
    }

    /**
     * 自底向上动态规划
     *
     * @param cs1 字符数组src
     * @param cs2 字符数组dest
     * @return 编辑距离
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
                    dp[i][j] =
                            min(
                                    dp[i - 1][j] + 1, // cs1删除
                                    dp[i][j - 1] + 1, // cs1增加
                                    dp[i - 1][j - 1] + 1); // cs1替换
                }
            }
        }
        return dp[l1][l2];
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * 记录变化过程的编辑距离
     *
     * @param word1 字符串src
     * @param word2 字符串dest
     * @return 编辑距离
     */
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
                    dp[i][j].val =
                            min(
                                    dp[i - 1][j].val + 1, // cs1删除
                                    dp[i][j - 1].val + 1, // cs1增加
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
        // print out
        Node t = dp[l1][l2];
        while (t != null) {
            if (t.choice != null) {
                System.out.print(t.choice + "<--");
            }
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
            return "Node{" + "val=" + val + ", choice=" + choice + '}';
        }
    }

    private enum Choice {
        SKIP,
        INSERT,
        DELETE,
        REPLACE
    }

    /*----------------------------------------------------------------*/

    /**
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度
     *
     * @param text1 字符串1
     * @param text2 字符串2
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
                    longestCommonSubsequenceHelper(cs1, i, cs2, j - 1));
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
     * @param s 字符串
     * @return 最长的回文子序列长度
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

    /**
     * LeetCode 877 石子游戏 只能从石子堆首尾拿石子
     *
     * @param piles 石子堆 每堆石子数目
     * @return 先手是否能赢
     */
    public boolean stoneGame(int[] piles) {
        final int n = piles.length;
        // dp[i][j] 代表索引第i个石子到第j个石子 [i,j]
        StoneGameResult[][] dp = new StoneGameResult[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                dp[i][j] = new StoneGameResult();
            }
        }
        // base 只有一个石子堆时
        for (int i = 0; i < n; i++) {
            dp[i][i].first = piles[i];
            dp[i][i].second = 0;
        }
        // i从大到小 j从小到大 遍历
    /*        for (int i = n - 2; i >= 0; i--) {
        for (int j = i + 1; j < n; j++) {
            int leftValue = dp[i + 1][j].second + piles[i];
            int rightValue = dp[i][j - 1].second + piles[j];
            if (leftValue > rightValue) {
                dp[i][j].first = leftValue;
                dp[i][j].second = dp[i + 1][j].first;
            } else {
                dp[i][j].first = rightValue;
                dp[i][j].second = dp[i][j - 1].first;
            }
        }
    }*/
        // 斜着遍历
        for (int k = 1; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                int j = i + k;
                // 先后手关系的转化
                int leftValue = dp[i + 1][j].second + piles[i];
                int rightValue = dp[i][j - 1].second + piles[j];
                if (leftValue > rightValue) {
                    dp[i][j].first = leftValue;
                    dp[i][j].second = dp[i + 1][j].first;
                } else {
                    dp[i][j].first = rightValue;
                    dp[i][j].second = dp[i][j - 1].first;
                }
            }
        }
        return dp[0][n - 1].first > dp[0][n - 1].second;
    }

    private static class StoneGameResult {
        int first;
        int second;
    }

    /**
     * LeetCode292 2个人搬石子 一次可以拿1 2 3 谁拿走最后一个石子谁赢
     *
     * @param n 石头数目
     * @return 先手是否能赢
     */
    public boolean canWinNim(int n) {
        boolean[] dp = new boolean[n + 1];
        dp[0] = false;
        dp[1] = dp[2] = dp[3] = true;
        for (int i = 4; i <= n; i++) {
            dp[i] = !(dp[i - 1] && dp[i - 2] && dp[i - 3]);
        }
        return dp[n];
    }

    /**
     * LeetCode10 简单的正则表达式匹配 匹配. *
     *
     * @param s 字符串s
     * @param p 规则p
     * @return s能否匹配规则p
     */
    public boolean isMatch(String s, String p) {
        char[] text = s.toCharArray();
        char[] pattern = p.toCharArray();
        isMatchDP = new Boolean[text.length + 1][pattern.length + 1];
        return isMatchHelper(text, 0, pattern, 0);
    }

    private Boolean[][] isMatchDP;

    private boolean isMatchHelper(char[] text, int i, char[] pattern, int j) {
        if (isMatchDP[i][j] != null) {
            return isMatchDP[i][j];
        }
        boolean res;
        if (j == pattern.length) {
            res = i == text.length;
        } else {
            boolean firstMatch = i != text.length && (text[i] == pattern[j] || pattern[j] == '.');
            // 处理*
            if (pattern.length - j >= 2 && pattern[j + 1] == '*') {
                res =
                        isMatchHelper(text, i, pattern, j + 2)
                                || (firstMatch && isMatchHelper(text, i + 1, pattern, j));
            } else {
                res = firstMatch && isMatchHelper(text, i + 1, pattern, j + 1);
            }
        }
        isMatchDP[i][j] = res;
        return res;
    }

    public boolean isMatch2(String s, String p) {
        char[] text = s.toCharArray();
        char[] pattern = p.toCharArray();
        final int l1 = text.length, l2 = pattern.length;
        // dp[i][j] text的第i个字符到结尾[i,...] 与 pattern的第j个字符到结尾[j,...] 能否匹配
        Boolean[][] dp = new Boolean[l1 + 1][l2 + 1];
        // base
        dp[l1][l2] = true;
        for (int i = 0; i < l1; i++) {
            dp[i][l2] = false;
        }
        //
        for (int i = l1; i >= 0; i--) {
            for (int j = l2 - 1; j >= 0; j--) {
                boolean firstMatch = i != l1 && (text[i] == pattern[j] || pattern[j] == '.');
                if (l2 - j >= 2 && pattern[j + 1] == '*') {
                    dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
                } else {
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    /**
     * LeetCode651 四键键盘
     *
     * @param n 操作次数
     * @return A出现最多次数
     */
    public int maxOf4KeyBoard(int n) {
        return maxOf4KeyBoardHelper(n, 0, 0);
    }

    private int maxOf4KeyBoardHelper(int opTime, int displayLength, int cacheLength) {
        if (opTime <= 0) {
            return displayLength;
        }

        return max(
                maxOf4KeyBoardHelper(opTime - 1, displayLength + 1, cacheLength),
                maxOf4KeyBoardHelper(opTime - 1, displayLength + cacheLength, cacheLength),
                maxOf4KeyBoardHelper(opTime - 2, displayLength, displayLength));
    }

    private static int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public int maxOf4KeyBoard2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int j = 2; j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j - 2] * (i - j + 1));
            }
        }
        return dp[n];
    }

    /**
     * LeetCode120 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
     *
     * @param triangle 三角形节点
     * @return 最小路径和
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        final int n = triangle.size();
        int[] dp = new int[n];
        // base
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + triangle.get(i).get(j);
            }
            dp[0] = dp[0] + triangle.get(i).get(0);
        }
        return Arrays.stream(dp).min().getAsInt();
    }

    /**
     * LeetCode410 分割数组，使每个分割的最大值最小
     *
     * @param nums 数组
     * @param m    分割成多少份
     */
    public int splitArray(int[] nums, int m) {
        final int n = nums.length;
        // dp[i][j]代表前i个数分成j组后最大连续数组和的最小值
        int[][] dp = new int[n + 1][m + 1];
        // sumOfBefore[i]代表前i个数之和
        int[] sumOfBefore = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sumOfBefore[i] = nums[i - 1] + sumOfBefore[i - 1];
        }
        for (int[] d : dp) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        // base 0个数分成0组 结果为0
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                for (int k = 0; k < i; k++) {
                    // dp(i,j)= max[dp(k,j-1),sum(k+1,i)] 对于k从0到i-1 [0,i-1]从中取最小的
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], sumOfBefore[i] - sumOfBefore[k]));
                }
            }
        }
        return dp[n][m];
    }

    /**
     * LeetCode392 判断子序列
     *
     * @param s 序列s
     * @param t 序列t
     * @return s是否为t的子序列
     */
    public boolean isSubsequence(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        final int m = cs.length, n = ct.length;
        // 预处理
        // dp(i,j)第i个数（包括第i个数）后字符j第一次出现的位置
        int[][] dp = new int[n + 1][26];
        // base
        for (int j = 0; j < 26; j++) {
            dp[n][j] = n;
        }
        // iter
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (ct[i] - 'a' == j) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        for (int i = 0, start = 0; i < m; i++) {
            if (dp[start][cs[i] - 'a'] == n) {
                // dp(start,字符)不存在 start位置后没有该字符了
                return false;
            }
            // 更新start位置到
            start = dp[start][cs[i] - 'a'] + 1;
        }
        return true;
    }

    /**
     * LeetCode343 整数拆分
     */
    public int integerBreak(int n) {
        // dp(i)代表整数i拆分后的最大乘积
        int[] dp = new int[n + 1];
        // base 拆分为正整数
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }

    /**
     * 数学优化的动态规划
     */
    public int integerBreak2(int n) {
        // dp(i)代表整数i拆分后的最大乘积
        int[] dp = new int[n + 1];
        // base 拆分为正整数
        dp[0] = 0;
        dp[1] = 0;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            // j取2或3最优
            dp[i] = Math.max(Math.max(2 * (i - 2), 2 * dp[i - 2]), Math.max(3 * (i - 3), 3 * dp[i - 3]));
        }
        return dp[n];
    }

    /**
     * LeetCode564 移除箱子
     */
    public int removeBoxes(int[] boxes) {
        final int n = boxes.length;
        // dp(i,j,k) [i,j]区间加上区间右边等于aj的k个元素组成序列的最大积分
        int[][][] dp = new int[n][n][n];
        return removeBoxesHelper(boxes, dp, 0, n - 1, 0);
    }

    private int removeBoxesHelper(int[] boxes, int[][][] dp, int l, int r, int k) {
        // 退出条件1
        if (l > r) {
            return 0;
        }
        // 已经计算过
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        // 压缩区间右边界
        while (r > l && boxes[r] == boxes[r - 1]) {
            // 右边界-1 等于ar的元素个数+1
            r--;
            k++;
        }
        // 第一种策略 移除右边界及右侧
        dp[l][r][k] = removeBoxesHelper(boxes, dp, l, r - 1, 0) + (k + 1) * (k + 1);
        // 第二种策略 移除区间中其他数凑更大相连区间
        for (int i = l; i < r; i++) {
            if (boxes[i] == boxes[r]) {
                // 移除[i+1, r-1]
                dp[l][r][k] =
                        Math.max(
                                dp[l][r][k],
                                removeBoxesHelper(boxes, dp, l, i, k + 1)
                                        + removeBoxesHelper(boxes, dp, i + 1, r - 1, 0));
            }
        }
        return dp[l][r][k];
    }

    /**
     * https://leetcode-cn.com/problems/out-of-boundary-paths/solution/chu-jie-de-lu-jing-shu-by-leetcode-solut-l9dw/
     */
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[][][] dp = new int[1 + maxMove][m][n];
        int res = 0;
        dp[0][startRow][startColumn] = 1;
        for (int i = 1; i <= maxMove; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    int count = dp[i-1][j][k];
                    if (count == 0) {
                        continue;
                    }
                    for (int[] d : directions) {
                        int nj = j + d[0], nk = k + d[1];
                        if (nj >= 0 && nj < m && nk >= 0 && nk < n) {
                            dp[i][nj][nk] = (dp[i][nj][nk] + count) % MOD;
                        } else {
                            res = (res + count) % MOD;
                        }
                    }
                }
            }
        }
        return res;
    }
}
