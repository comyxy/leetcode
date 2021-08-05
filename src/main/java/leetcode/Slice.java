package leetcode;

import java.util.*;
import java.util.stream.Collectors;

import static utils.EasyUtil.*;

/**
 * @author comyxy
 */
public class Slice {

    /**
     * LeetCode1 两数之和
     *
     * @param nums   整数数组
     * @param target 目标值
     * @return 两个数和为目标的索引
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> pair = new HashMap<>();
        final int length = nums.length;
        int[] res = new int[2];
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            if (!pair.containsKey(num)) {
                pair.put(target - num, i);
            } else {
                res[0] = pair.get(num);
                res[1] = i;
                break;
            }
        }
        return res;
    }

    /**
     * 三数之和
     * 三元组内元素可以相同 但是三元组间不能相等
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        final int n = nums.length;
        // first < second < third   (index)
        // 第一重循环
        for (int first = 0; first < n; first++) {
            // 重复则直接跳过
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            int target = -nums[first];
            // 三重循环位置
            int third = n - 1;
            // 第二重循环
            for (int second = first + 1; second < n; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }

                // 第三重循环
                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }

                // 第二个数与第三个数重合 排除
                if (second == third) {
                    break;
                }

                if (nums[second] + nums[third] == target) {
                    res.add(new ArrayList<>(Arrays.asList(nums[first], nums[second], nums[third])));
                }
            }
        }
        return res;
    }

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
                if (val >= num) {
                    dp[val] += dp[val - num];
                }
            }
        }
        return dp[target];
    }

    private static class Employee {
        int id;
        int importance;
        List<Integer> subordinates;
    }

    /**
     * 690
     */
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = employees.stream().
                collect(Collectors.toMap(employee -> employee.id, employee -> employee));
        return getImportanceDfs(map, id);
    }

    private int getImportanceDfs(Map<Integer, Employee> map, int id) {
        Employee employee = map.get(id);
        int importance = employee.importance;
        for (Integer subordinate : employee.subordinates) {
            importance += getImportanceDfs(map, subordinate);
        }
        return importance;
    }

    /**
     * 554
     */
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> map = new HashMap<>();
        for (List<Integer> w : wall) {
            int cur = 0;
            for (int i = 0; i < w.size() - 1; i++) {
                cur += w.get(i);
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            }
        }
        // 最多穿过每一层
        int res = wall.size();
        for (Integer val : map.values()) {
            int d = wall.size() - val;
            res = Math.min(res, d);
        }
        return res;
    }

    /**
     * https://leetcode-cn.com/problems/minimize-maximum-pair-sum-in-array/
     */
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        final int n = nums.length;
        int res = 0;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            res = Math.max(nums[i] + nums[j], res);
        }
        return res;
    }

    /**
     * https://leetcode-cn.com/problems/check-if-all-the-integers-in-a-range-are-covered/
     * 差分数组
     */
    public boolean isCovered(int[][] ranges, int left, int right) {
        int[] diff = new int[52];
        for (int[] range : ranges) {
            diff[range[0]]++;
            diff[range[1] + 1]--;
        }
        int cur = 0;
        for (int i = 1; i <= 50; i++) {
            cur += diff[i];
            if (i >= left && i <= right && cur < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode-cn.com/problems/longest-common-subsequence/solution/zui-chang-gong-gong-zi-xu-lie-by-leetcod-y7u0/
     * LCS O(m*n)
     */
    public int longestCommonSubsequence(String text1, String text2) {
        final int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    /**
     * https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
     * dp O(n*n)
     */
    public int lengthOfLIS(int[] nums) {
        final int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        int maxn = 1;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                maxn = Math.max(dp[i], maxn);
            }
        }
        return maxn;
    }

    /**
     * greedy + binary search
     */
    public int lengthOfLISV2(int[] nums) {
        final int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] d = new int[n];
        d[0] = nums[0];
        int len = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > d[len - 1]) {
                d[len++] = nums[i];
            } else {
                // 查找左边界的二分
                int lo = 0, hi = len - 1;
                while (lo <= hi) {
                    int mid = (lo + hi) >> 1;
                    if (d[mid] >= nums[i]) {
                        hi = mid - 1;
                    } else {
                        lo = mid + 1;
                    }
                }
                int pos = 0;
                if (lo >= len) {
                    throw new IllegalStateException();
                } else {
                    pos = lo;
                }
                d[pos] = nums[i];
            }
        }
        return len;
    }

    /**
     * https://leetcode-cn.com/problems/minimum-operations-to-make-a-subsequence/
     */
    public int minOperations(int[] target, int[] arr) {
        final int n = target.length, m = arr.length;
        Map<Integer, Integer> value2idx = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            value2idx.put(target[i], i);
        }
        List<Integer> nA = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (value2idx.containsKey(arr[i])) {
                nA.add(value2idx.get(arr[i]));
            }
        }
        //
        final int l = nA.size();
        if (l == 0) {
            return n;
        }
        int[] d = new int[l];
        d[0] = nA.get(0);
        int len = 1;
        for (int i = 1; i < l; i++) {
            if (nA.get(i) > d[len - 1]) {
                d[len++] = nA.get(i);
            } else {
                int lo = 0, hi = len - 1;
                while (lo <= hi) {
                    int mid = (lo + hi) >>> 1;
                    if (d[mid] >= nA.get(i)) {
                        hi = mid - 1;
                    } else {
                        lo = mid + 1;
                    }
                }
                d[lo] = nA.get(i);
            }
        }
        return n - len;
    }

    /**
     * https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/
     */
    public int findUnsortedSubarray(int[] nums) {
        if (isSorted(nums)) {
            return 0;
        }
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        int left = 0;
        while (nums[left] == sorted[left]) {
            left++;
        }
        int right = nums.length - 1;
        while (nums[right] == sorted[right]) {
            right--;
        }
        return right - left + 1;
    }

    public int findUnsortedSubarrayV2(int[] nums) {
        final int n = nums.length;
        int minn = Integer.MAX_VALUE, left = -1;
        int maxn = Integer.MIN_VALUE, right = -1;
        for (int i = 0; i < n; i++) {
            if (nums[n - i - 1] <= minn) {
                minn = nums[n - i - 1];
            } else {
                left = n - i - 1;
            }
            if (nums[i] >= maxn) {
                maxn = nums[i];
            } else {
                right = i;
            }
        }
        return left == -1 ? 0 : right - left + 1;
    }
}
