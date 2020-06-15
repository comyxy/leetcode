package leetcode;

import java.util.Arrays;

/**
 * @date 2020/6/15
 */
public class Search {
    /**
     * 标准二分查找
     *
     * @param nums   升序的数组
     * @param target 查找对象目标
     * @return 目标所在数组中的索引 没有找到返回-1
     */
    public int binarySearch(int[] nums, int target) {
        // [left, right]
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                // [mid + 1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // [left, mid - 1]
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找一个递增数组中目标的第一个位置与最后一个位置
     * 使用两次二分查找
     *
     * @param nums   递增数组
     * @param target 搜索目标
     * @return [第一个位置, 最后一个位置] 如果没有找到 [-1,-1]
     */
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        Arrays.fill(res, -1);

        int left = 0, right = nums.length - 1;
        // [left, right]
        // 查找最左边的
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // [mid + 1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // [left, mid - 1]
                right = mid - 1;
            } else if (nums[mid] == target) {
                // [left, mid - 1]
                right = mid - 1;
            }
        }
        if (left >= nums.length || nums[left] != target) {

        } else {
            res[0] = left;
        }

        // 查找最右边的
        left = res[0] == -1 ? 0 : left;
        right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // [mid + 1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // [mid + 1, right]
                left = mid + 1;
            }
        }
        if (right < 0 || nums[right] != target) {

        } else {
            res[1] = right;
        }

        return res;
    }
}
