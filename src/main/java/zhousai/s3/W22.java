package zhousai.s3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @date 2021/7/4 10:31
 * @see <a href="https://leetcode-cn.com/contest/weekly-contest-248">248</a>
 */
public class W22 {
    public int[] buildArray(int[] nums) {
        final int size = nums.length;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = nums[nums[i]];
        }
        return res;
    }

    public int eliminateMaximum(int[] dist, int[] speed) {
        final int n = dist.length;

        int[] arriveTime = new int[n];
        for (int i = 0; i < n; i++) {
            arriveTime[i] = (dist[i] - 1) / speed[i] + 1;
        }
        Arrays.sort(arriveTime);
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (arriveTime[i] >= i + 1) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }

    private static final int MOD = 1000000007;

    public int countGoodNumbers(long n) {
        long p1 = n / 2;
        long p2 = n % 2 == 1 ? p1 + 1 : p1;
        long res = qPower(5, p2) * qPower(4, p1) % MOD;
        return (int) res;
    }

    private long qPower(long x, long p) {
        long res = 1;
        while (p > 0) {
            if ((p & 1) == 1) {
                res = res * x % MOD;
            }
            p >>= 1;
            x = x * x % MOD;
        }
        return res;
    }


    public int longestCommonSubpath(int n, int[][] paths) {
        Arrays.sort(paths, Comparator.comparingInt(o -> o.length));
        final int m = paths.length;
        List<int[]> tmp = new ArrayList<>();
        tmp.add(paths[0]);
        for (int i = 1; i < m; i++) {
            tmp = lcs(tmp, paths[i]);
        }
        if(tmp.size() == 0) {
            return 0;
        }
        return tmp.get(0).length;
    }

    private List<int[]> lcs(List<int[]> tmp, int[] path) {
        int maxLen = 0;
        List<int[]> rep = new ArrayList<>();
        if(tmp.size() == 0) {
            return rep;
        }
        int m = tmp.get(0).length, n = path.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int[] tm : tmp) {
            List<int[]> se = new ArrayList<>();
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 0;
                    } else if (tm[i - 1] == path[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        if (dp[i][j] >= maxLen) {
                            if (dp[i][j] > maxLen) {
                                se.clear();
                                maxLen = dp[i][j];
                            }
                            se.add(new int[]{i - 1, j - 1});
                        }
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
            for (int i = 0; i <= m; i++) {
                Arrays.fill(dp[i], 0);
            }
            for (int[] s : se) {
                rep.add(Arrays.copyOfRange(tm, s[0] - maxLen + 1, s[0] + 1));
            }
        }
        return rep;
    }
}
