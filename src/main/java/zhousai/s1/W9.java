package zhousai.s1;

import struct.Pair;

import java.util.*;

/**
 * 2020/10/25
 */
public class W9 {

    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int pre = releaseTimes[0];
        char m = keysPressed.charAt(0);
        int max = releaseTimes[0];
        for (int i = 1; i < keysPressed.length(); i++) {
            char c = keysPressed.charAt(i);
            int cur = releaseTimes[i];
            if (max < cur - pre) {
                max = cur - pre;
                m = c;
            } else if (max == cur - pre) {
                if (m < c) {
                    m = c;
                }
            }
            pre = cur;
        }
        return m;
    }

    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        int n = l.length;
        List<Boolean> ans = new ArrayList<>(n);
        int left = -1, right = -1;
        for (int i = 0; i < n; i++) {
            left = l[i];
            right = r[i];
            boolean toAdd = check(nums, left, right);
            ans.add(toAdd);
        }
        return ans;
    }

    private boolean check(int[] nums, int left, int right) {
        int[] tmp = Arrays.copyOfRange(nums, left, right + 1);
        Arrays.sort(tmp);
        int skip = -1;
        for (int j = 0; j < right - left; j++) {
            if (skip == -1) {
                skip = tmp[j + 1] - tmp[j];
            } else {
                if (skip != tmp[j + 1] - tmp[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int m;
    private int n;
    private boolean[][] visited;
    private final int[][] ds = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
    private Queue<Pair<Integer, Integer>> queue;

    public int minimumEffortPath(int[][] heights) {
        m = heights.length;
        n = heights[0].length;
        visited = new boolean[m][n];
        queue = new LinkedList<>();
        int left = 0, right = 1000001;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (check(heights, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean check(int[][] heights, int dis) {
        for (int i = 0; i < m; i++) {
            Arrays.fill(visited[i], false);
        }
        visited[0][0] = true;
        queue.offer(new Pair<>(0, 0));
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> p = queue.poll();
            int i = p.getKey(), j = p.getValue();
            for (int[] d : ds) {
                int ni = i + d[0], nj = j + d[1];
                if (ni < 0 || ni >= m || nj < 0 || nj >= n) {
                    continue;
                }
                if (visited[ni][nj] || Math.abs(heights[ni][nj] - heights[i][j]) > dis) {
                    continue;
                }
                queue.offer(new Pair<>(ni, nj));
                visited[ni][nj] = true;
            }
        }
        return visited[m - 1][n - 1];
    }

    public static void main(String[] args) {
        W9 w9 = new W9();

        //    int[] releaseTimes = {9,29,49,50};
        //    String key = "cbcd";
        //    char slowestKey = w9.slowestKey(releaseTimes, key);
        //    System.out.println(slowestKey);

        //    int[] nums = {4,6,5,9,3,7};
        //    int[] l = {0,0,2};
        //    int[] r = {2,3,5};
        //    List<Boolean> checkArithmeticSubarrays = w9.checkArithmeticSubarrays(nums, l, r);
        //    System.out.println(checkArithmeticSubarrays);

        int[][] heights = {{1, 2, 2}, {3, 8, 2}, {5, 3, 5}};
        int minimumEffortPath = w9.minimumEffortPath(heights);
        System.out.println(minimumEffortPath);
    }
}
