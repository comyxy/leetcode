package zhousai.s3;

import java.util.*;

import static utils.EasyUtil.MOD;

/**
 * @since 2021/8/21 22:00
 * https://leetcode-cn.com/contest/biweekly-contest-59/
 */
public class W29 {

    public int minTimeToType(String word) {
        char c = 'a';
        int res = 0;
        for (int i = 0; i < word.length(); i++) {
            char t = word.charAt(i);
            if (t >= c) {
                res += Math.min(t - c, c + 26 - t);
            } else {
                res += Math.min(c - t, t + 26 - c);
            }
            res++;
            c = t;
        }
        return res;
    }

    public long maxMatrixSum(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        int minn = Integer.MAX_VALUE;
        boolean zero = false;
        long negCount = 0, sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zero = true;
                }
                if (matrix[i][j] < 0) {
                    negCount++;
                }
                minn = Math.min(minn, Math.abs(matrix[i][j]));
                sum += Math.abs(matrix[i][j]);
            }
        }
        if ((negCount & 1) == 1 && !zero) {
            sum -= 2L * minn;
        }
        return sum;
    }

    public int countPaths(int n, int[][] roads) {
        if (roads.length == 0) {
            return n == 1 ? 1 : 0;
        }
        Map<Integer, Map<Integer, Integer>> adjs = new HashMap<>();
        for (int[] road : roads) {
            int u = road[0], v = road[1], t = road[2];
            Map<Integer, Integer> t1 = adjs.computeIfAbsent(u, (i) -> new HashMap<>());
            t1.put(v, t);
            Map<Integer, Integer> t2 = adjs.computeIfAbsent(v, (i) -> new HashMap<>());
            t2.put(u, t);
        }
        long[] dis = new long[n];
        Arrays.fill(dis, Long.MAX_VALUE);
        long[] ways = new long[n];
        dis[0] = 0L;
        ways[0] = 1L;
        // dist and index
        PriorityQueue<long[]> pq = new PriorityQueue<>(((o1, o2) -> (int) (o1[0] - o2[0])));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] p = pq.poll();
            long t = p[0];
            int u = (int) p[1];
            if (t > dis[u]) {
                continue;
            }
            Map<Integer, Integer> adj = adjs.get(u);
            for (Map.Entry<Integer, Integer> vw : adj.entrySet()) {
                int v = vw.getKey(), w = vw.getValue();
                if (t + w < dis[v]) {
                    dis[v] = t + w;
                    ways[v] = 0;
                    pq.offer(new long[]{t + w, v});
                }
                if (t + w == dis[v]) {
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }
        return (int) ways[n-1];
    }
}
