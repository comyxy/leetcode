package zhousai;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 2020/11/29
 */
public class W14 {
    public int maximumWealth(int[][] accounts) {
        int ans = 0;
        for (int[] account : accounts) {
            ans = Math.max(ans, Arrays.stream(account)
                    .sum());
        }
        return ans;
    }

    public int[] mostCompetitive(int[] nums, int k) {
        /*
        int[] ans = new int[k];
        int[] tmp = new int[nums.length];
        helper(ans, nums, 0, 0, k, tmp);
        return ans;
         */
        // 单调栈
        int n = nums.length;
        Deque<Integer> st = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && nums[i] < st.peekLast()
                    && k - st.size() < n - i) {
                st.pollLast();
            }
            if (st.size() <= k) {
                st.offerLast(nums[i]);
            }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = st.pollFirst();
        }
        return ans;
    }

    /**
     * 超时
     */
    private void helper(int[] ans, int[] nums, int i, int j, int k, int[] tmp) {
        if (i >= ans.length) {
            return;
        }
        System.arraycopy(nums, j, tmp, j, nums.length - j);
        Arrays.sort(tmp, j, nums.length);
        int pos = -1;
        for (int l = j; l < tmp.length; l++) {
            int pickOne = tmp[l];
            for (int m = j; m < nums.length; m++) {
                if (pickOne == nums[m] && k <= nums.length - m) {
                    pos = m;
                    break;
                }
            }
            if (pos != -1) {
                break;
            }
        }
        if (pos == -1) {
            return;
        }
        ans[i] = nums[pos];
        helper(ans, nums, i + 1, pos + 1, k - 1, tmp);
    }

    /**
     * https://leetcode-cn.com/problems/minimum-moves-to-make-array-complementary/solution/shi-shu-zu-hu-bu-de-zui-shao-cao-zuo-ci-shu-by-zer/
     */
    public int minMoves(int[] nums, int limit) {
        int n = nums.length;
        Map<Integer, Integer> freqs = new HashMap<>();
        // 差分数组
        int[] d = new int[2 * (limit + 1)];
        for (int i = 0; i < n / 2; i++) {
            int t = nums[i] + nums[n - i - 1];
            freqs.put(t, freqs.getOrDefault(t, 0) + 1);
            // l左边界 1 + Math.min(nums[i], nums[n - i - 1])
            // r右边界 limit + Math.max(nums[i], nums[n - i - 1])
            // 代表贡献为0或者1的区间
            // 进入l累计加1 离开r累计减一
            d[1 + Math.min(nums[i], nums[n - i - 1])]++;
            d[limit + Math.max(nums[i], nums[n - i - 1]) + 1]--;
        }
        int ans = 2 * n;
        int c01 = 0;
        for (int k = 1; k <= 2 * limit; k++) {
            c01 += d[k];
            int c0 = freqs.getOrDefault(k, 0);
            int c1 = c01 - c0;
            int c2 = n / 2 - c01;

            ans = Math.min(ans, c1 + c2 * 2);
        }
        return ans;
    }


    public static void main(String[] args) {
        W14 w = new W14();

        int[] nums = {71, 18, 52, 29, 55, 73, 24, 42, 66, 8, 80, 2};
        int k = 3;
        int[] mostCompetitive = w.mostCompetitive(nums, k);
        for (int i : mostCompetitive) {
            System.out.println(i);
        }
    }
}
