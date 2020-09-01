package leetcode;

import java.util.Arrays;

/**
 * 2020/6/15
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


    /**
     * LeetCode329
     * 查找矩阵中的最长递增路径
     *
     * @param matrix
     * @return
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


    enum Direction {
        NORTH(0, 1), SOUTH(0, -1), EAST(1, 0), WEST(-1, 0);

        int horizontal;
        int vertical;

        Direction(int i, int j) {
            horizontal = i;
            vertical = j;
        }
    }

    /**
     * LeetCode130
     *
     * @param board
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
                if(board[i][j] == 'A'){
                    board[i][j] = 'O';
                }else if(board[i][j] == 'O'){
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
}
