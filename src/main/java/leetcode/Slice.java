package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author comyxy
 */
public class Slice {
    /**
     * 414 第三大的数
     */
    public int thirdMax(int[] nums) {
        long lo = Long.MIN_VALUE;
        long mid = Long.MIN_VALUE;
        long hi = Long.MIN_VALUE;

        for (int t : nums) {
            if (t == lo || t == mid || t == hi) {
                continue;
            }
            if (t > hi) {
                lo = mid;
                mid = hi;
                hi = t;
            } else if (t > mid) {
                lo = mid;
                mid = t;
            } else if (t > lo) {
                lo = t;
            }
        }
        return lo == Long.MIN_VALUE ? (int) (hi) : (int) (lo);
    }


    /**
     * 368. 最大整除子集
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] size = new int[n];
        int[] trace = new int[n];
        for (int r = 0; r < n; r++) {
            int s = 1, p = r;
            for (int l = 0; l < r; l++) {
                if (nums[r] % nums[l] == 0) {
                    if (size[l] + 1 > s) {
                        s = size[l] + 1;
                        p = l;
                    }
                }
            }
            size[r] = s;
            trace[r] = p;
        }

        int idx = 0, val = size[0];
        for (int i = 1; i < n; i++) {
            if (size[i] > val) {
                idx = i;
                val = size[i];
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < val; i++) {
            res.add(nums[idx]);
            idx = trace[idx];
        }
        return res;
    }

    /**
     * 377. 组合总和 Ⅳ
     */
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int val = 1; val <= target; val++) {
            for (int num : nums) {
                if(val >= num) {
                    dp[val] += dp[val-num];
                }
            }
        }
        return dp[target];
    }
}
