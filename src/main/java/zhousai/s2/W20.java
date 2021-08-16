package zhousai.s2;

import struct.Pair;

import java.util.*;

/**
 * @author comyxy
 * https://leetcode-cn.com/contest/weekly-contest-246
 */
public class W20 {
    public String largestOddNumber(String num) {
        for (int i = num.length() - 1; i >= 0; i--) {
            if ((num.charAt(i) - '0') % 2 == 1) {
                return num.substring(0, i + 1);
            }
        }
        return "";
    }

    public int numberOfRounds(String startTime, String finishTime) {
        String[] start = startTime.split(":");
        String[] finish = finishTime.split(":");
        int sh = Integer.parseInt(start[0]), sm = Integer.parseInt(start[1]);
        int fh = Integer.parseInt(finish[0]), fm = Integer.parseInt(finish[1]);
        int startStamp = sh * 60 + sm, finishStamp = fh * 60 + fm;
        if (finishStamp < startStamp) {
            finishStamp += 24 * 60;
        }
        int si = startStamp == 0 ? 0 : (startStamp - 1) / 15 + 1;
        int fi = (finishStamp) / 15;
        return fi - si;
    }

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int m = grid2.length, n = grid2[0].length;
        boolean[][] visited = new boolean[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] != 1 || visited[i][j]) {
                    continue;
                }
                Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
                boolean contains = true;
                queue.offer(new Pair<>(i, j));
                visited[i][j] = true;
                while (!queue.isEmpty()) {
                    Pair<Integer, Integer> poll = queue.poll();
                    int ci = poll.getKey(), cj = poll.getValue();
                    if (contains && grid1[ci][cj] != 1) {
                        contains = false;
                    }
                    for (int k = 0; k < 4; k++) {
                        int ni = ci + dirs[k][0], nj = cj + dirs[k][1];
                        if (!good(ni, nj, m, n, visited, grid2)) {
                            continue;
                        }
                        visited[ni][nj] = true;
                        queue.offer(new Pair<>(ni, nj));
                    }
                }
                if (contains) {
                    res++;
                }
            }
        }
        return res;
    }

    private boolean good(int x, int y, int m, int n, boolean[][] visited, int[][] grid) {
        if (x < 0 || x >= m || y < 0 || y >= n) {
            return false;
        }
        if (visited[x][y] || grid[x][y] != 1) {
            return false;
        }
        return true;
    }

    public int[] minDifference(int[] nums, int[][] queries) {
        int q = queries.length, n = nums.length;
        int[] res = new int[q];
        int[][] pre = new int[101][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= 100; j++) {
                pre[j][i] = pre[j][i - 1];
            }
            pre[nums[i - 1]][i] += 1;
        }
        List<Integer> tmp = new LinkedList<>();
        for (int k = 0; k < q; k++) {
            int[] query = queries[k];
            tmp.clear();
            for (int j = 1; j <= 100; j++) {
                if (pre[j][query[1] + 1] - pre[j][query[0]] > 0) {
                    tmp.add(j);
                }
            }
            if (tmp.size() == 1) {
                res[k] = -1;
            } else {
                res[k] = Integer.MAX_VALUE;
                for (int i = 0; i < tmp.size() - 1; i++) {
                    res[k] = Math.min(res[k], tmp.get(i + 1) - tmp.get(i));
                }
            }
        }
        return res;
    }
}
