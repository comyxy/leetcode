package leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 312 戳气球
 *
 * @date 2020/6/29
 */
public class BurstBalloon {


    private int res = 0;

    /**
     * 回溯穷举
     *
     * @param nums
     * @return
     */
    public int maxCoins(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        backtrace(list, 0);
        return res;
    }

    private void backtrace(List<Integer> list, int score) {
        if (list.size() == 0) {
            res = Math.max(res, score);
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            int point = list.get(i);
            if (i > 0) {
                point *= list.get(i - 1);
            }
            if (i < list.size() - 1) {
                point *= list.get(i + 1);
            }
            //删除
            int tmp = list.remove(i);
            backtrace(list, score + point);
            //撤销
            list.add(i, tmp);
        }
    }

    /**
     * 动态规划
     *
     * @param nums
     * @return
     */
    public int maxCoins2(int[] nums) {
        final int n = nums.length;
        //数组左侧与右侧各补充1个1
        int[] balloons = new int[n + 2];
        balloons[0] = balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }
        //dp[i][j] 代表 开区间(i,j)内戳气球的最大分数 i<j
        int[][] dp = new int[n + 2][n + 2];
        //base i=j+1时 dp[i][j] = 0
        //结果 dp[0][n+2] (0,n+2)
        //注意i j遍历顺序
        for (int i = n; i >= 0; i--) {
            for (int j = i + 1; j <= n + 1; j++) {
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + balloons[i] * balloons[k] * balloons[j]);
                }
            }
        }
        return dp[0][n + 1];
    }

}
