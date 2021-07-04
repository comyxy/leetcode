package leetcode;

import java.util.*;
import java.util.stream.Collectors;

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
}
