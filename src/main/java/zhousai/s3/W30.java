package zhousai.s3;

import java.util.*;

import static utils.EasyUtil.MOD;

/**
 * @since 2021/8/29 10:29
 * https://leetcode-cn.com/contest/weekly-contest-256
 */
public class W30 {
    public int minimumDifference(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (i + k - 1 < n) {
                res = Math.min(res, nums[i + k - 1] - nums[i]);
            }
        }
        return res;
    }

    public String kthLargestNumber(String[] nums, int k) {
        PriorityQueue<String> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.length() != o2.length()) {
                return o1.length() - o2.length();
            }
            for (int i = 0; i < o1.length(); i++) {
                if (o1.charAt(i) != o2.charAt(i)) {
                    return o1.charAt(i) - o2.charAt(i);
                }
            }
            return 0;
        });
        for (String num : nums) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }


    /**
     *  位运算: 求i的子集 j=i then (j-1)&i
     */
    public int minSessions(int[] tasks, int sessionTime) {
        // NP hard
        // 状态压缩dp
        int n = tasks.length;
        int[] sum = new int[1 << n];
        for (int i = 1; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    sum[i] = sum[i ^ (1 << j)] + tasks[j];
                    break;
                }
            }
        }
        int[] dp = new int[1 << n];
        Arrays.fill(dp, 1000000000);
        dp[0] = 0;
        for (int i = 1; i < (1 << n); i++) {
            for (int j = i; j > 0; j = (j - 1) & i) {
                // 枚举子集 j = (j - 1) & i
                if (sum[j] > sessionTime) {
                    continue;
                }
                // 由于sum[j]<=sessionTime表示j可以满足一次session
                // i^j表示集合i减去集合j得到的集合
                dp[i] = Math.min(dp[i], dp[i ^ j] + 1);
            }
        }
        return dp[(1 << n) - 1];
    }

    /**
     * https://leetcode-cn.com/problems/number-of-unique-good-subsequences/solution/dong-tai-gui-hua-jing-dian-ti-mu-de-bian-n4h3/
     */
    public int numberOfUniqueGoodSubsequences(String binary) {
        int dp0 = 0, dp1 = 0;
        int extraZero = 0;
        int n = binary.length();
        for (int i = n - 1; i >= 0; i--) {
            int c = binary.charAt(i) - '0';
            if (c == 0) {
                extraZero = 1;
                dp0 = (dp0 + dp1 + 1) % MOD;
            }
            else {
                dp1 = (dp0 + dp1 + 1) % MOD;
            }
        }
        return (dp1 + extraZero) % MOD;
    }
}
