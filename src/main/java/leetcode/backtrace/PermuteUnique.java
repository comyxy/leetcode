package leetcode.backtrace;

import java.util.*;

/**
 * @date 2020/6/12
 */
public class PermuteUnique {
    public List<List<Integer>> permuteUnique(int[] nums) {
        final int length = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (length == 0) {
            return res;
        }
        Arrays.sort(nums);
        // 记录nums[i]是否已经被使用
        boolean[] used = new boolean[length];
        Deque<Integer> temp = new ArrayDeque<>();
        backtrace(nums, length, 0, used, temp, res);
        return res;
    }

    private void backtrace(int[] nums, int length, int depth, boolean[] used,
                           Deque<Integer> temp, List<List<Integer>> res) {
        if (depth == length) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < length; i++) {
            if (used[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            temp.addLast(nums[i]);
            used[i] = true;
            backtrace(nums, length, depth + 1, used, temp, res);
            used[i] = false;
            temp.removeLast();
        }
    }
}
