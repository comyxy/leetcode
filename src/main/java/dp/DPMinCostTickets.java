package dp;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode983 最低票价
 * dp(i)来表示从第 i天开始到一年的结束需要花的钱
 * 从后往前倒着进行动态规划
 */
public class DPMinCostTickets {
    Set<Integer> holidays;
    Integer[] memo;
    int[] costs;

    public int mincostTickets(int[] days, int[] costs) {
        this.memo = new Integer[366];
        this.holidays = new HashSet<>();
        for (int d : days) {
            holidays.add(d);
        }
        this.costs = costs;
        return dp(1);
    }

    private int dp(int i) {
        if (i > 365) {
            return 0;
        }
        if (memo[i] != null) {
            return memo[i];
        }
        if (holidays.contains(i)) {
            memo[i] = Math.min(dp(i + 1) + costs[0], dp(i + 7) + costs[1]);
            memo[i] = Math.min(memo[i], dp(i + 30) + costs[2]);
        } else {
            memo[i] = dp(i + 1);
        }
        return memo[i];
    }
}
