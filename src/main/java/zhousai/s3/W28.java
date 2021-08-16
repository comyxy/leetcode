package zhousai.s3;

import struct.UnionFind;

import java.math.BigInteger;
import java.util.*;

/**
 * @since 2021/8/15 10:30
 * https://leetcode-cn.com/contest/weekly-contest-254
 */
public class W28 {
    public int numOfStrings(String[] patterns, String word) {
        int res = 0;
        for (String pattern : patterns) {
            if (word.contains(pattern)) {
                res++;
            }
        }
        return res;
    }

    public int[] rearrangeArray(int[] nums) {
        final int n = nums.length;
        Arrays.sort(nums);
        int[] res = new int[n];
        int i = 0, j = n - 1;
        for (int k = 0; k < n; k++) {
            if ((k & 1) == 0) {
                res[k] = nums[i++];
            } else {
                res[k] = nums[j--];
            }
        }
        return res;
    }

    public static final BigInteger MOD = BigInteger.valueOf(1000000007L);

    public int minNonZeroProduct(int p) {
        long m = (1L << p) - 1;
        BigInteger n = BigInteger.valueOf(m >> 1);
        BigInteger l = BigInteger.valueOf(m & ~1L);
        BigInteger v = qPower(l, n);
        BigInteger res = BigInteger.valueOf(m).multiply(v).mod(MOD);
        return res.intValue();
    }

    private BigInteger qPower(BigInteger x, BigInteger p) {
        BigInteger res = BigInteger.valueOf(1);
        while (p.longValue() > 0) {
            if ((p.longValue() & 1) == 1) {
                res = res.multiply(x).mod(MOD);
            }
            p = p.divide(BigInteger.valueOf(2));
            x = x.multiply(x).mod(MOD);
        }
        return res;
    }

    public int latestDayToCross(int row, int col, int[][] cells) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int lo = 0, hi = row * col;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            boolean[][] mat = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                Arrays.fill(mat[i], true);
            }
            boolean[][] vis = new boolean[row][col];
            for (int i = 0; i < mid; i++) {
                mat[cells[i][0] - 1][cells[i][1] - 1] = false;
            }
            Queue<int[]> queue = new LinkedList<>();
            for (int j = 0; j < col; j++) {
                if (mat[0][j]) {
                    queue.offer(new int[]{0, j});
                    vis[0][j] = true;
                }
            }
            boolean can = false;
            while (!queue.isEmpty()) {
                int[] p = queue.poll();
                int r = p[0], c = p[1];
                if (r == row - 1) {
                    // last row
                    can = true;
                    break;
                }
                for (int k = 0; k < 4; k++) {
                    int nr = r + directions[k][0], nc = c + directions[k][1];
                    if (nr >= 0 && nr < row && nc >= 0 && nc < col &&
                            mat[nr][nc] && !vis[nr][nc]) {
                        vis[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            if (can) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return hi;
    }

    public int latestDayToCrossV2(int row, int col, int[][] cells) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        UnionFind uf = new UnionFind(row * col + 2);
        int start = row * col, end = row * col + 1;
        boolean[][] exist = new boolean[row][col];
        for (int i = row * col - 1; i >= 0; i--) {
            int r = cells[i][0] - 1, c = cells[i][1] - 1;
            if (r == 0) {
                // first row
                uf.union(start, c);
            }
            if (r == row - 1) {
                // last row
                uf.union(end, r * col + c);
            }
            exist[r][c] = true;
            for (int k = 0; k < 4; k++) {
                int nr = r + directions[k][0], nc = c + directions[k][1];
                if (nr >= 0 && nr < row && nc >= 0 && nc < col && exist[nr][nc]) {
                    uf.union(r * col + c, nr * col + nc);
                }
            }
            if (uf.find(start) == uf.find(end)) {
                return i;
            }
        }
        return -1;
    }
}
