package leetcode;

import java.util.function.Predicate;

/**
 * 2020/6/15
 *
 * @author comyxy
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
     * 左边界的二分查找
     *
     * @param nums   升序数组
     * @param target 查找值
     * @return 如果待查找的值在数组中存在 则返回最左侧值的索引
     * 如果不存在则返回-1
     */
    public int leftBinarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 如果left >= nums.length 表示没有查到而且target大于数组中所有值
        // 如果nums[left] != target 表示没有查到而且在中间位置
        if (left >= nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    /**
     * 右边界的二分查找
     *
     * @param nums   升序数组
     * @param target 查找值
     * @return 如果待查找的值在数组中存在 则返回最右侧值的索引
     * 如果不存在则返回-1
     */
    public int rightBinarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                left = mid + 1;
            }
        }
        // 如果right < 0 表示没有查到而且target小于数组中所有值
        // 如果nums[right] != target 表示没有查到而且在中间位置
        if (right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }

    /**
     * 在升序数组中找到第一个大于target元素的索引
     * [left, right)
     */
    public int upperBound(int[] nums, int left, int right, int target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * 在升序数组中找到第一个大于或者等于target元素的索引
     * [left, right)
     */
    public int lowerBound(int[] nums, int left, int right, int target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
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
        int left = leftBinarySearch(nums, target);
        if (left < 0) {
            return new int[]{-1, -1};
        }
        int right = rightBinarySearch(nums, target);
        return new int[]{left, right};
    }

    /**
     * LeetCode1011
     */
    public int shipWithinDays(int[] weights, int D) {
        int max = 0, sum = 0;
        for (int weight : weights) {
            max = Math.max(max, weight);
            sum += weight;
        }
        Predicate<Integer> canLoad = (size) -> {
            int day = 1, loaded = 0;
            for (int weight : weights) {
                if (loaded + weight > size) {
                    day++;
                    loaded = 0;
                }
                loaded += weight;
            }
            return day <= D;
        };
        int left = max, right = sum;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (canLoad.test(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    /**
     * Code329
     * 查找矩阵中的最长递增路径
     */
    public int longestIncreasingPath(int[][] matrix) {
        final int m = matrix.length;
        if (m == 0) {
            throw new IllegalArgumentException();
        }
        final int n = matrix[0].length;
        cache = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, search(matrix, i, j));
            }
        }
        return res;
    }

    private int[][] cache;

    enum Direction {
        NORTH(0, 1),
        SOUTH(0, -1),
        EAST(1, 0),
        WEST(-1, 0),
        ;

        int horizontal;
        int vertical;

        Direction(int i, int j) {
            horizontal = i;
            vertical = j;
        }
    }

    private int search(int[][] matrix, int i, int j) {
        if (cache[i][j] != 0) {
            return cache[i][j];
        }
        int res = 0;
        final int m = matrix.length, n = matrix[0].length;
        for (Direction d : Direction.values()) {
            int x = i + d.horizontal;
            int y = j + d.vertical;
            if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] > matrix[i][j]) {
                res = Math.max(res, search(matrix, x, y));
            }
        }
        return cache[i][j] = res + 1;
    }

    /**
     * LeetCode130
     */
    public void solve(char[][] board) {
        m = board.length;
        if (m == 0) {
            return;
        }
        n = board[0].length;

        // 找到与边界连接的O
        for (int i = 0; i < m; i++) {
            solveDfs(board, i, 0);
            solveDfs(board, i, n - 1);
        }
        for (int j = 1; j < n - 1; j++) {
            solveDfs(board, 0, j);
            solveDfs(board, m - 1, j);
        }

        //
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private int m;

    private int n;

    private void solveDfs(char[][] board, int x, int y) {
        if (x < 0 || x >= m || y < 0 || y >= n
                || board[x][y] != 'O') {
            return;
        }
        // 已遍历
        board[x][y] = 'A';
        solveDfs(board, x + 1, y);
        solveDfs(board, x - 1, y);
        solveDfs(board, x, y + 1);
        solveDfs(board, x, y - 1);
    }

    /**
     * https://leetcode-cn.com/problems/smallest-good-base/solution/zui-xiao-hao-jin-zhi-er-fen-shu-xue-fang-frrv/
     */
    public String smallestGoodBase(String n) {
        long num = Long.parseLong(n);
        for (int i = 63; i >= 2; i--) {
            // i位数
            // 计算进制数 k^0 + k^1 + ... + k^(mid) == num
            long lo = 2, hi = num - 1;
            while (lo <= hi) {
                long mid = (hi - lo) / 2 + lo;
                long sum = 0, maxVal = num / mid + 1;
                for (int j = 0; j < i; j++) {
                    if (sum < maxVal) {
                        sum = sum * mid + 1;
                    } else {
                        // overflow
                        sum = num + 1;
                        break;
                    }
                }
                if (sum == num) {
                    return String.valueOf(mid);
                } else if (sum > num) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
        }
        return "";
    }
}
