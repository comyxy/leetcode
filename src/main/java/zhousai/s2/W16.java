package zhousai.s2;

import java.util.*;

/**
 * @author comyxy
 */
public class W16 {

    private int ans1;
    private int n1;

    public int subsetXORSum(int[] nums) {
        n1 = nums.length;
        ans1 = 0;
        dfs(nums, 0, 0);
        return ans1;
    }

    private void dfs(int[] nums, int val, int idx) {
        if (idx == n1) {
            ans1 += val;
            return;
        }
        dfs(nums, val, idx + 1);
        dfs(nums, val ^ nums[idx], idx + 1);
    }

    public int minSwaps(String s) {
        int len = s.length();
        int zero = 0, one = 0;
        int start = 1;
        int res1 = -1, res2 = -1;
        res1 = getRes(s, len, zero, one, start, res1);
        zero = one = start = 0;
        res2 = getRes(s, len, zero, one, start, res2);
        if (res1 != -1 && res2 != -1) {
            return Math.min(res1, res2);
        } else if (res1 != -1) {
            return res1;
        } else if (res2 != -1) {
            return res2;
        }
        return -1;
    }

    private int getRes(String s, int len, int zero, int one, int start, int res1) {
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '1') {
                if (start == 0) {
                    one++;
                }
            } else {
                if (start == 1) {
                    zero++;
                }
            }
            start = start == 0 ? 1 : 0;
        }
        if (zero == one) {
            res1 = zero;
        }
        return res1;
    }

    class FindSumPairs {
        private Map<Integer, List<Integer>> map;
        private int[] nums1, nums2;

        public FindSumPairs(int[] nums1, int[] nums2) {
            this.nums1 = nums1;
            this.nums2 = nums2;
            this.map = new HashMap<>();
            for (int i = 0; i < nums2.length; i++) {
                List<Integer> idxs = map.getOrDefault(nums2[i], new ArrayList<>());
                idxs.add(i);
                map.put(nums2[i], idxs);
            }
        }

        public void add(int index, int val) {
            int b = nums2[index];
            nums2[index] += val;
            List<Integer> idx1 = map.get(b);
            idx1.remove((Integer) index);
            map.put(b, idx1);
            b += val;
            List<Integer> idx2 = map.getOrDefault(b, new ArrayList<>());
            idx2.add(index);
            map.put(b, idx2);
        }

        public int count(int tot) {
            int res = 0;
            for (int i = 0; i < nums1.length; i++) {
                int v1 = nums1[i];
                int v2 = tot - v1;
                if (map.containsKey(v2)) {
                    res += map.get(v2).size();
                }
            }
            return res;
        }
    }

    private static final int MOD = 1000000007;
    public int rearrangeSticks(int n, int k) {
        int[][] dp = new int[n+1][k+1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = (dp[i-1][j-1] + (int)(((i-1)*(long)dp[i-1][j]) % MOD)) % MOD;
            }
        }
        return dp[n][k];
    }


    public static void main(String[] args) {
        W16 w = new W16();
        System.out.println(w.rearrangeSticks(3, 2));
        System.out.println(w.rearrangeSticks(5, 5));
        System.out.println(w.rearrangeSticks(20, 11));
    }
}
