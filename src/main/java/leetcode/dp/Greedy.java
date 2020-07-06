package leetcode.dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @date 2020/7/6
 */
public class Greedy {
    /**
     * LeetCode 435
     * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠
     *
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        final int n = intervals.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int[] dp = new int[n];
        // base
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (intervals[i][0] >= intervals[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return n - Arrays.stream(dp)
                .max()
                .getAsInt();
    }

    /**
     * 贪心
     *
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals2(int[][] intervals) {
        final int n = intervals.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int count = 1, t_end = intervals[0][1];
        for (int i = 1; i < n; i++) {
            if(intervals[i][0] >= t_end){
                count++;
                t_end = intervals[i][1];
            }
        }
        return n - count;
    }

    /**
     * LeetCode452
     *
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        final int n = points.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int count = 1, t_end = points[0][1];
        for (int i = 1; i < n; i++) {
            if(points[i][0] > t_end){
                count++;
                t_end = points[i][1];
            }
        }
        return count;
    }
}
