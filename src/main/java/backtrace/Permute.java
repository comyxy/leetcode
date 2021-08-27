package backtrace;

import java.util.*;

import static utils.EasyUtil.swap;

/**
 * @since 2020/6/12
 * 排列问题
 */
public class Permute {

    public List<List<Integer>> permuteV2(int[] nums) {
        final int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        permuteHelperV2(nums, 0, res);
        return res;
    }

    private void permuteHelperV2(int[] nums, int depth, List<List<Integer>> res) {
        if (depth >= nums.length) {
            List<Integer> tmp = new ArrayList<>(nums.length);
            for (int num : nums) {
                tmp.add(num);
            }
            res.add(tmp);
            return;
        }
        for (int i = depth; i < nums.length; i++) {
            swap(nums, i, depth);
            permuteHelperV2(nums, depth + 1, res);
            swap(nums, i, depth);
        }
    }

    /**
     * 全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        final int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        List<Integer> tmp = new LinkedList<>();
        boolean[] used = new boolean[n];
        Arrays.sort(nums);
        permuteHelper(nums, used, 0, tmp, res);
        return res;
    }

    private void permuteHelper(int[] nums, boolean[] used, int depth, List<Integer> tmp, List<List<Integer>> res) {
        if (depth >= nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                // 已经使用过
                continue;
            }
            used[i] = true;
            tmp.add(nums[i]);
            permuteHelper(nums, used, depth + 1, tmp, res);
            tmp.remove(tmp.size() - 1);
            used[i] = false;
        }
    }

    /**
     * 全排列 不重复
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        final int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        List<Integer> tmp = new LinkedList<>();
        boolean[] used = new boolean[n];
        Arrays.sort(nums);
        permuteUniqueHelper(nums, used, 0, tmp, res);
        return res;
    }

    /**
     *
     * @param nums 原始数组
     * @param used 是否已经用过数组
     * @param depth 递归深度
     * @param tmp 每条路径记录
     * @param res 结果
     */
    private void permuteUniqueHelper(int[] nums, boolean[] used, int depth, List<Integer> tmp, List<List<Integer>> res) {
        if (depth >= nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                // 已经使用过
                continue;
            }
            if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) {
                // i-1和i处大小相等 而且i-1没有用过 这时使用i就会有重复
                continue;
            }
            used[i] = true;
            tmp.add(nums[i]);
            permuteUniqueHelper(nums, used, depth + 1, tmp, res);
            tmp.remove(tmp.size() - 1);
            used[i] = false;
        }
    }

}
