package dp;

import struct.TreeNode;

import java.util.*;

import static utils.EasyUtil.MOD;

/**
 * 2020/6/27 动态规划
 */
public class Base {

    /*
    线性动态规划
    dp[n] := [0..n]上的问题
    dp[n] = f(dp[n-1], ..., dp[0])
    */

    /**
     * LIS 最小上升子序列的长度
     */
    public int lengthOfLIS(int[] nums) {
        // f[i] 从0开始以i结尾的最小上升子序列的长度
        // f[i] = max(f[j]) + 1, j<i and nums[i]>nums[j]

        // 1.递归
//        int n = nums.length;
//        int[] cache = new int[n];
//        return lengthOfLIS(nums, n - 1, cache);

        // 2.迭代
        int n = nums.length;
        int[] f = new int[n];
        Arrays.fill(f, 1);
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            res = Math.max(res, f[i]);
        }
        return res;
    }

    private int lengthOfLIS(int[] nums, int i, int[] cache) {
        if (cache[i] != 0) {
            return cache[i];
        }
        int res = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                res = Math.max(res, lengthOfLIS(nums, j, cache) + 1);
            }
        }
        return res;
    }

    /**
     * 最长递增子序列的个数
     */
    public int findNumberOfLIS(int[] nums) {
        final int n = nums.length;
        // lengths 从0开始以i结尾的最长递增子序列长度
        // counts 从0开始以i结尾长度为最长递增子序列长度的个数
        int[] lengths = new int[n];
        int[] counts = new int[n];
        Arrays.fill(lengths, 1);
        Arrays.fill(counts, 1);
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
            maxLength = Math.max(maxLength, lengths[i]);
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (lengths[i] == maxLength) {
                count += counts[i];
            }
        }
        return count;
    }

    /**
     * 俄罗斯套娃信封问题
     */
    public int maxEnvelopes(int[][] envelopes) {
        final int n = envelopes.length;
        // [[5,4],[6,4],[6,7],[2,3]] --> [[2,3],[5,4],[6,7],[6,4]]
        // --> 3, 4, 7, 4
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            }
            return o2[1] - o1[1];
        });
        // dp[i] 从0开始以i结束能够构成的最多信封数
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 最大子序和
     */
    public int maxSubArray(int[] nums) {
        // f[i] 从0开始以i结尾的最大子序和
        // f[i] = nums[i] + max(f[j], 0), j=i-1
        final int n = nums.length;
        int[] dp = new int[n];
        int res = dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = nums[i] + Math.max(dp[i - 1], 0);
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 乘积最大子数组
     */
    public int maxProduct(int[] nums) {
        // mm[i] 从0开始以i结尾的最大乘积
        // nn[i]] 从0开始以i结尾的最小乘积
        final int n = nums.length;
        int[] mm = new int[n];
        int[] nn = new int[n];
        mm[0] = nn[0] = nums[0];
        int res = mm[0];
        for (int i = 1; i < n; i++) {
            mm[i] = Math.max(nums[i], Math.max(mm[i - 1] * nums[i], nn[i - 1] * nums[i]));
            nn[i] = Math.min(nums[i], Math.min(mm[i - 1] * nums[i], nn[i - 1] * nums[i]));
            res = Math.max(res, mm[i]);
        }
        return res;
    }

    /**
     * 环形子数组的最大和
     */
    public int maxSubarraySumCircular(int[] nums) {
        // 分2类 A=[0, 1, 2, 3, 4, 5, 6]
        // 1类是没有跨越边界 [2,3,4] 直接使用最大子序和解决
        // 2类是跨越了边界 [0,1] + [5,6]
        int res = maxSubArray(nums);
        // 后缀和数组
        final int n = nums.length;
        int[] rightsums = new int[n];
        rightsums[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightsums[i] = rightsums[i + 1] + nums[i];
        }
        // 右侧最大值数组
        int[] maxright = new int[n];
        maxright[n - 1] = rightsums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxright[i] = Math.max(maxright[i + 1], rightsums[i]);
        }
        int leftsum = 0;
        for (int i = 0; i < n - 2; i++) {
            leftsum += nums[i];
            res = Math.max(res, leftsum + maxright[i + 1]);
        }
        return res;
    }

    /**
     * 最大子矩阵
     */
    public int[] getMaxMatrix(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        int sx = 0, sy = 0;
        int sum = Integer.MIN_VALUE;
        int[] res = new int[4];
        for (int i = 0; i < m; i++) {
            // 矩阵从上往下扫描
            // i区间上边界 j区间下边界 [i,j]
            int[] sub = new int[n];
            for (int j = i; j < m; j++) {
                int dp = 0;
                for (int k = 0; k < n; k++) {
                    sub[k] += matrix[j][k];
                    if (dp > 0) {
                        dp += sub[k];
                    } else {
                        // 重新开始左边界
                        dp = sub[k];
                        sx = i;
                        sy = k;
                    }

                    if (dp > sum) {
                        sum = dp;
                        res[0] = sx;
                        res[1] = sy;
                        res[2] = j;
                        res[3] = k;
                    }
                }
            }
        }
        return res;
    }


    /**
     * 矩形区域不超过 K 的最大数值和
     */
    public int maxSumSubmatrix(int[][] matrix, int k) {
        final int m = matrix.length, n = matrix[0].length;
        int res = Integer.MIN_VALUE;
        // 行[i,j]区间
        for (int i = 0; i < m; i++) {
            int[] sub = new int[n];
            for (int j = i; j < m; j++) {
                for (int l = 0; l < n; l++) {
                    sub[l] += matrix[j][l];
                }
                res = Math.max(res, maxSumSubArray(sub, k));
            }
        }
        return res;
    }

    /**
     * 数组不超过 K 的最大数值和
     */
    private int maxSumSubArray(int[] nums, int k) {
        // 1.暴力
//        final int n = nums.length;
//        int res = Integer.MIN_VALUE;
//        for (int i = 0; i < n; i++) {
//            int sum = 0;
//            for (int j = i; j < n; j++) {
//                sum += nums[j];
//                if (sum > res && sum <= k) {
//                    res = sum;
//                }
//                if (res == k) {
//                    return res;
//                }
//            }
//        }
//        return res;
        // 2.二分查找
        // [l, r) nums[l] + nums[l+1] + ... + nums[r-1] <= k -->
        // Sr - Sl <= k --> Sl >= Sr - k 找到最小的Sl使左边式子成立
        int res = Integer.MIN_VALUE;
        TreeSet<Integer> set = new TreeSet<>();
        set.add(0);
        int sum = 0;
        for (int num : nums) {
            sum += num;
            Integer sl = set.ceiling(sum - k);
            if (sl != null) {
                res = Math.max(res, sum - sl);
            }
            set.add(sum);
        }
        return res;
    }

    /**
     * 打家劫舍
     */
    public int rob(int[] nums) {
        final int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        // 从0开始到刚刚好偷第i家
        // f[i] = max(f[i-1], f[i-2]+nums[i])
        return robRange(nums, 0, n - 1);
    }

    /**
     * [start, end]
     */
    private int robRange(int[] nums, int start, int end) {
        int f0 = nums[start], f1 = Math.max(nums[start], nums[start + 1]);
        for (int i = start + 2; i <= end; i++) {
            int tmp = f1;
            f1 = Math.max(f1, f0 + nums[i]);
            f0 = tmp;
        }
        return f1;
    }

    /**
     * 打家劫舍 II
     */
    public int robII(int[] nums) {
        final int n = nums.length;
        if (n == 1) {
            return nums[0];
        } else if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(robRange(nums, 0, n - 2), robRange(nums, 1, n - 1));
    }

    /**
     * 打家劫舍 III
     */
    public int robIII(TreeNode root) {
        int[] res = robIIIHelper(root);
        return Math.max(res[0], res[1]);
    }

    private int[] robIIIHelper(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        // 后序遍历 递归写法
        int[] left = robIIIHelper(node.left);
        int[] right = robIIIHelper(node.right);

        // 状态转移
        int[] res = new int[2];
        // 该节点不偷
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        // 该节点偷
        res[1] = node.val + left[0] + right[0];
        return res;
    }

    /**
     * 删除与获得点数
     * 转化为rob问题 获得了nums[x]就不能获得nums[x]-1与nums[x]+1
     */
    public int deleteAndEarn(int[] nums) {
        final int n = nums.length;
        assert n > 0;
        int max = Arrays.stream(nums).max().getAsInt();
        int[] sums = new int[max + 1];
        for (int num : nums) {
            sums[num] += num;
        }
        return robRange(sums, 0, max);
    }

    /**
     * 3n 块披萨
     * 转化为3n块里取n个互不相邻的数和的最大值
     */
    public int maxSizeSlices(int[] slices) {
        final int n = slices.length;
        return Math.max(maxSizeSlicesHelper(Arrays.copyOfRange(slices, 0, n - 1)),
                maxSizeSlicesHelper(Arrays.copyOfRange(slices, 1, n)));
    }

    /**
     * [start, end]
     */
    private int maxSizeSlicesHelper(int[] slices) {
        // f[i][j] 从0开始以i结尾的元素中一共选择了j个情况下的和的最大值
        // f[i][j] = max(f[i-1][j], f[i-2][j-1]+slice[i-1])
        final int n = slices.length;
        final int m = (n + 1) / 3;
        int[][] f = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i == 1) {
                    f[i][j] = slices[i - 1];
                } else {
                    f[i][j] = Math.max(f[i - 1][j], f[i - 2][j - 1] + slices[i - 1]);
                }
            }
        }
        return f[n][m];
    }

    /**
     * 最长的斐波那契子序列的长度
     */
    public int lenLongestFibSubseq(int[] arr) {
        final int n = arr.length;
        Map<Integer, Integer> v2i = new HashMap<>();
        for (int i = 0; i < n; i++) {
            v2i.put(arr[i], i);
        }
        int res = 0;
        int[][] f = new int[n][n];
        // f[i][j] = f[j][k] + 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = arr[i] - arr[j];
                if (v2i.containsKey(tmp) && v2i.get(tmp) < j) {
                    f[i][j] = f[j][v2i.get(tmp)] + 1;
                } else {
                    f[i][j] = 2;
                }
                res = Math.max(res, f[i][j]);
            }
        }
        return res == 2 ? 0 : res;
    }

    /**
     * 最长等差数列
     */
    public int longestArithSeqLength(int[] nums) {
        final int n = nums.length;
        int[][] f = new int[n][n];
        Map<Integer, Integer> v2i = new HashMap<>();
        int res = 0;
        // 迭代顺序
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = 2 * nums[i] - nums[j];
                if (v2i.containsKey(tmp) && v2i.get(tmp) < i) {
                    f[i][j] = f[v2i.get(tmp)][i] + 1;
                } else {
                    f[i][j] = 2;
                }
                res = Math.max(res, f[i][j]);
            }
            // 存在重复元素 只能在迭代时添加
            // 选择的是离j最近的
            v2i.put(nums[i], i);
        }
        return res;
    }

    /**
     * 形成字符串的最短路径
     */
    public int shortestWay(String source, String target) {
        final int m = source.length(), n = target.length();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            set.add(source.charAt(i));
        }
        for (int i = 0; i < n; i++) {
            if (!set.contains(target.charAt(i))) {
                return -1;
            }
        }
        int[] f = new int[n];
        StringBuilder sb = new StringBuilder();
        // f[i] = f[i-1] if source contains tmp
        // f[i] = f[i-1] + 1 if source not contains tmp
        for (int i = 0; i < n; i++) {
            char c = target.charAt(i);
            sb.append(c);
            if (i == 0) {
                f[i] = 1;
            } else {
                if (isSubsequence(sb.toString(), source)) {
                    f[i] = f[i - 1];
                } else {
                    f[i] = f[i - 1] + 1;
                    sb.delete(0, sb.length() - 1);
                }
            }
        }
        return f[n - 1];
    }

    /**
     * 最大整除子集
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        final int n = nums.length;
        Arrays.sort(nums);
        // dp得到最大子集长度与子集中的最大数
        int[] f = new int[n];
        int maxV = 0, maxS = 0;
        Arrays.fill(f, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums [j] == 0) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            if (f[i] > maxS) {
                maxS = f[i];
                maxV = nums[i];
            }
        }

        // 得到最大整除子集
        List<Integer> res = new ArrayList<>();
        for (int i = n - 1; i >= 0 && maxV > 0; i--) {
            if (f[i] == maxS && maxV % nums[i] == 0) {
                res.add(nums[i]);
                maxS--;
                maxV = nums[i];
            }
        }
        return res;
    }

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
                    int count = dp[i - 1][j][k];
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

    /**
     * https://leetcode-cn.com/problems/student-attendance-record-ii/
     */
    public int checkRecord(int n) {
        // (长度 缺席A次数 迟到L次数) --> 出现次数
        int[][][] dp = new int[n + 1][2][3];
        dp[0][0][0] = 1;
        for (int i = 1; i <= n; i++) {
            // 到达P结尾 dp(i,0,0) dp(i,1,0)
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[i][j][0] = (dp[i][j][0] + dp[i - 1][j][k]) % MOD;
                }
            }
            // 缺席A结尾 dp(i,1,0)
            for (int k = 0; k < 3; k++) {
                dp[i][1][0] = (dp[i][1][0] + dp[i - 1][0][k]) % MOD;
            }
            // 迟到L结尾 dp(i,0,1) dp(i,0,2) dp(i,1,1) dp(i,1,2)
            for (int j = 0; j < 2; j++) {
                for (int k = 1; k < 3; k++) {
                    dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j][k - 1]) % MOD;
                }
            }
        }
        int res = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 3; k++) {
                res = (res + dp[n][j][k]) % MOD;
            }
        }
        return res;
    }

    /**
     * https://leetcode-cn.com/problems/interleaving-string/solution/jiao-cuo-zi-fu-chuan-by-leetcode-solution/
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        final int n1 = s1.length(), n2 = s2.length(), n3 = s3.length();
        if (n1 + n2 != n3) {
            return false;
        }
        // dp(i,j) s1前i个与s2前j个能否恰好组成s3的前i+j个
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];
        for (int i = 0; i <= n1; i++) {
            for (int j = 0; j <= n2; j++) {
                int k = i + j;
                if (i == 0 && j == 0) {
                    dp[i][j] = true;
                } else {
                    if (i > 0) {
                        dp[i][j] = dp[i][j] || (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(k - 1));
                    }
                    if (j > 0) {
                        dp[i][j] = dp[i][j] || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(k - 1));
                    }
                }
            }
        }
        return dp[n1][n2];
    }
}
