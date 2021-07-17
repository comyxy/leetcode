package zhousai.s3;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/contest/weekly-contest-247
 */
public class W21 {
    public int maxProductDifference(int[] nums) {
        final int n = nums.length;
        Arrays.sort(nums);
        return nums[n-1] * nums[n-2] - nums[0] * nums[1];
    }

    public int[][] rotateGrid(int[][] grid, int k) {
        final int m = grid.length, n = grid[0].length;
        int x = m, y = n;
        for (int q = 0; q < Math.min(m, n) / 2; q++) {
            rotateCircle(grid, x, y, k, q);
            x -= 2;
            y -= 2;
        }
        return grid;
    }

    private void rotateCircle(int[][] grid, int x, int y, int k, int q) {
        int len = 2*x + 2*y - 4;
        k = k % len;
        for (int i = 0; i < k; i++) {
            rotateOnce(grid, x, y, q);
        }
    }

    private void rotateOnce(int[][] grid, int x, int y, int q) {
        int tmp = grid[q][q];
        int di = 0, dj = 0;
        while(dj < y - 1) {
            grid[q+di][q+dj] = grid[q+di][q+dj+1];
            dj++;
        }
        while(di < x - 1) {
            grid[q+di][q+dj] = grid[q+di+1][q+dj];
            di++;
        }
        while(dj > 0) {
            grid[q+di][q+dj] = grid[q+di][q+dj-1];
            dj--;
        }
        while(di > 1) {
            grid[q+di][q+dj] = grid[q+di-1][q+dj];
            di--;
        }
        grid[q+di][q+dj] = tmp;
    }

    /**
     * 超时
     */
    public long wonderfulSubstrings(String word) {
        final int n = word.length();
        long res = 0;
        for (int i = 0; i < n; i++) {
            int[] tmp = new int[10];
            int chance = 2;
            for (int j = i + 1; j <= n ; j++) {
                int idx = word.charAt(j - 1) - 'a';
                tmp[idx]++;
                if(tmp[idx] % 2 == 1) {
                    chance--;
                }else {
                    chance++;
                }

                if(chance > 0) {
                    res++;
                }
            }
        }
        return res;
    }

    public long wonderfulSubstrings2(String word){
        final int n = word.length();
        int[] counts = new int[1024];
        // 空串+1
        counts[0] = 1;
        // 前缀和 一个长度为10的二进制
        int cur = 0;
        long res = 0;
        for (int i = 0; i < n; i++) {
            int c = word.charAt(i) - 'a';
            // 计算前缀和
            cur ^= (1 << c);
            // 加上全是偶数次的
            res += counts[cur];
            // 对于存在一次奇数次
            for (int j = 0; j < 10; j++) {
                res += counts[cur ^ (1 << j)];
            }

            counts[cur]++;
        }
        return res;
    }

    private static final long MOD = 1000000007;

    @SuppressWarnings("unchecked")
    public int waysToBuildRooms(int[] prevRoom) {
        final int n = prevRoom.length;
        factors = new long[2*n+1];
        factors[0] = factors[1] = 1;
        for (int i = 2; i <= 2*n; i++) {
            factors[i] = factors[i-1] * i % MOD;
        }
        List<Integer>[] la = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            la[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            la[prevRoom[i]].add(i);
        }
        Pair<Long, Long> ret = dfs(la, 0);
        return (int)(long)ret.getKey();
    }

    private Pair<Long, Long> dfs(List<Integer>[] la, int node) {
        if(la[node].size() == 0) {
            return new Pair<Long, Long>(1L, 1L);
        }
        long ways = 0, counts = 0;
        for (Integer next : la[node]) {
            Pair<Long, Long> ret = dfs(la, next);
            Long subWays = ret.getKey(), subCounts = ret.getValue();
            if (ways == 0) {
                ways = subWays;
                counts = subCounts;
            } else {
                ways = (ways * subWays % MOD) * C((int) (counts + subCounts), (int)(long) subCounts) % MOD;
                counts += subCounts;
            }
        }
        return new Pair<>(ways, counts + 1);
    }

    private long[] factors;

    private long C(int n, int k) {
        return (factors[n] * inv(factors[n-k], MOD)) % MOD
                * inv(factors[k], MOD) % MOD;
    }

    /**
     * 快速幂
     * @param x 底数
     * @param p 指数
     * @param mod 求余
     */
    public static long qPower(long x, long p, long mod) {
        long res = 1;
        while(p > 0) {
            if((p & 1) == 1) {
                res = res * x % mod;
            }
            p >>= 1;
            x = x * x % mod;
        }
        return res;
    }

    /**
     * 逆元
     * 费马小定理 a^(p-1) = 1 (mod p)
     * @param mod 为素数
     */
    public static long inv(long x, long mod) {
        return qPower(x, mod-2, mod);
    }

}
