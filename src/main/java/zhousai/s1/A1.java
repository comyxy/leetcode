package zhousai.s1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 2020/9/12
 */
public class A1 {
    private static final int MOD = 1000000007;

    public int calculate(String s) {
        int x = 1, y = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                x = 2 * x + y;
            } else {
                y = 2 * y + x;
            }
        }
        return x + y;
    }

    public int breakfastNumber(int[] staple, int[] drinks, int x) {
        long ret = 0;
        Arrays.sort(staple);
        Arrays.sort(drinks);

        int left = drinks.length - 1;
        for (int i = 0; i < staple.length; i++) {
            while (left >= 0 && staple[i] + drinks[left] > x) {
                left--;
            }
            ret += left + 1;
        }
        return (int) (ret % MOD);
    }

    public int minimumOperations(String leaves) {
        final int n = leaves.length();
        // dp(i,0) 0到i全为r的最小代价
        // dp(i,1) 0到i先r后y的最小代价
        // dp(i,2) 0到i先r后y再为r的最小代价
        int[][] dp = new int[n][3];
        for (int i = 0; i < n; i++) {
            if (i <= 0) {
                dp[i][0] = leaves.charAt(i) == 'y' ? 1 : 0;
            } else {
                dp[i][0] = dp[i - 1][0] + (leaves.charAt(i) == 'y' ? 1 : 0);
            }

            if (i <= 0) {
                dp[i][1] = dp[i][0];
            } else {
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + (leaves.charAt(i) == 'r' ? 1 : 0);
            }

            if (i <= 1) {
                dp[i][2] = dp[i][1];
            } else {
                dp[i][2] = Math.min(dp[i - 1][1], dp[i - 1][2]) + (leaves.charAt(i) == 'y' ? 1 : 0);
            }
        }
        return dp[n - 1][2];
    }

    private Map<Long, Long> map = new HashMap<>();
    private int inc;
    private int dec;
    private int[] jump;
    private int[] cost;

    public int busRapidTransit(int target, int inc, int dec, int[] jump, int[] cost) {
        this.inc = inc;
        this.dec = dec;
        this.jump = jump;
        this.cost = cost;
        return (int) (dfs(target) % MOD);
    }

    private long dfs(long target) {
        if (target == 0) {
            return 0;
        }
        if (target == 1) {
            return inc;
        }
        if (map.containsKey(target)) {
            return map.get(target);
        }
        // 初始全为步行
        long s = (long) target * inc;
        for (int i = 0; i < jump.length; i++) {
            // 向上取整
            long ceil = (target + jump[i] - 1) / jump[i] * jump[i];
            s = Math.min(s, dfs(ceil / jump[i]) + cost[i] + (ceil - target) * dec);
            s = Math.min(s, dfs(target / jump[i]) + cost[i] + (target % jump[i]) * inc);
        }
        map.put(target, s);
        return s;
    }

    public static void main(String[] args) {
        A1 a1 = new A1();
        //    int ab = a1.calculate("AB");
        //    System.out.println(ab);

        //    int[] staple = {10, 20, 5};
        //    int[] drinks = {5, 5, 2};
        //    int x = 15;
        //    int breakfastNumber = a1.breakfastNumber(staple, drinks, x);
        //    System.out.println(breakfastNumber);

        int minimumOperations = a1.minimumOperations("ryyrryyrryyrryyr");
        System.out.println(minimumOperations);

        int[] jump = {3, 6, 8, 11, 5, 10, 4};
        int[] cost = {4, 7, 6, 3, 7, 6, 4};
        int busRapidTransit = a1.busRapidTransit(612, 4, 5, jump, cost);
        System.out.println(busRapidTransit);
    }
}
