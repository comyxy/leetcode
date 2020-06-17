package leetcode.dp;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * 扔鸡蛋
 * 状态有 当前拥有的鸡蛋数量K 需要测试的楼层数N
 * 选择为 在某层仍鸡蛋
 * 状态转移 第i层扔鸡蛋
 * 鸡蛋碎了，鸡蛋个数K减1，需要测试的楼层从[1,N]变为[i+1,N]
 * 鸡蛋没碎，鸡蛋个数K不变，需要测试的楼层从[1,N]变为[1,i-1]
 *
 * @date 2020/6/17
 */
public class EggDrop {
    private Map<Pair<Integer, Integer>, Integer> memo = new HashMap<>();

    public int superEggDrop(int K, int N) {
        return helper(K, N);
    }

    private int helper(int k, int n) {
        // 只有1个鸡蛋 需要n次
        if (k == 1) {
            return n;
        }
        // 楼层0 需要0次
        if (n == 0) {
            return 0;
        }
        Pair<Integer, Integer> key = new Pair<>(k, n);
        // 备忘录
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int result = Integer.MAX_VALUE;
        // 线性搜索
//        for (int i = 1; i <= n; i++) {
//            // 选择每个楼层中最少的
//            result = Math.min(result,
//                    // 碎没碎决定于最坏情况
//                    Math.max(helper(k - 1, i - 1), helper(k, n - i)) + 1);
//        }
        // 二分搜索 原因是dp[k-1,i-1]保持k不变时随i递增 dp[k,n-i]保持k不变时随i递减
        int left = 1, right = n;
        // [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int broken = helper(k - 1, mid - 1);
            int unbroken = helper(k, n - mid);
            if (broken > unbroken) {
                right = mid - 1;
                result = Math.min(result, broken + 1);
            } else {
                left = mid + 1;
                result = Math.min(result, unbroken + 1);
            }
        }

        memo.put(key, result);
        return result;
    }

    /*------------------------*/

    private int[][] arrays;

    public int superEggDrop2(int K, int N) {
        arrays = new int[K + 1][N + 1];
        return helper2(K, N);
    }

    private int helper2(int k, int n) {
        // 只有1个鸡蛋 需要n次
        if (k == 1) {
            return n;
        }
        // 楼层0 需要0次
        if (n == 0) {
            return 0;
        }
        // 备忘录
        if (arrays[k][n] != 0) {
            return arrays[k][n];
        }

        int result = Integer.MAX_VALUE;
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int broken = helper2(k - 1, mid - 1);
            int unbroken = helper2(k, n - mid);
            if (broken > unbroken) {
                right = mid - 1;
                result = Math.min(result, broken + 1);
            } else {
                left = mid + 1;
                result = Math.min(result, unbroken + 1);
            }
        }

        arrays[k][n] = result;
        return result;
    }

    /*----决策单调性----*/

    public int superEggDrop3(int K, int N) {
        // dp[i] 代表 dp[1,i]
        int[] dp = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            dp[i] = i;
        }
        // 2个鸡蛋到K个鸡蛋
        for (int k = 2; k <= K; k++) {
            // dp2[i] 代表 dp2[k,i]
            int[] dp2 = new int[N + 1];
            int i = 1;
            for (int n = 1; n <= N; n++) {
                // 决策单调性 k不变时 n从1到N x可以递增来查找每个n对应的dp[k,n]
                // dp[i]代表dp[k-1,i] dp2[i]代表dp[k,i]     dp[k-1,i-1] dp[k,n-i]
                while (i < n && Math.max(dp2[n - i], dp[i - 1]) > Math.max(dp2[n - i - 1], dp[i])) {
                    i++;
                }

                dp2[n] = Math.max(dp2[n - i], dp[i - 1]) + 1;
            }
            dp = dp2;
        }
        return dp[N];
    }
}
