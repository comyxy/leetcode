package zhousai;

import java.util.*;

public class W17 {
    public boolean isSumEqual(String firstWord, String secondWord, String targetWord) {
        int first = getNum(firstWord);
        int second = getNum(secondWord);
        int target = getNum(targetWord);
        return first + second == target;
    }

    private int getNum(String word) {
        int num = 0;
        for (int i = 0; i < word.length(); i++) {
            num = 10 * num + word.charAt(i) - 'a';
        }
        return num;
    }

    public String maxValue(String n, int x) {
        char first = n.charAt(0);
        StringBuilder sb = new StringBuilder();
        boolean inserted = false;
        if (first == '-') {
            sb.append('-');
            for (int i = 1; i < n.length(); i++) {
                if (!inserted && n.charAt(i) - '0' > x) {
                    inserted = true;
                    sb.append((char) ('0' + x));
                }
                sb.append(n.charAt(i));
            }
            if (!inserted) {
                sb.append((char) ('0' + x));
            }
        } else {
            for (int i = 0; i < n.length(); i++) {
                if (!inserted && n.charAt(i) - '0' < x) {
                    inserted = true;
                    sb.append((char) ('0' + x));
                }
                sb.append(n.charAt(i));
            }
            if (!inserted) {
                sb.append((char) ('0' + x));
            }
        }
        return sb.toString();
    }

    static class Server {
        int idx;
        int weight;
        int time;

        public Server(int idx, int weight, int time) {
            this.idx = idx;
            this.weight = weight;
            this.time = time;
        }
    }

    public int[] assignTasks(int[] servers, int[] tasks) {
        PriorityQueue<Server> busy = new PriorityQueue<>(Comparator.comparingInt(o -> o.time));
        PriorityQueue<Server> idle = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight != o2.weight) {
                return o1.weight - o2.weight;
            }
            return o1.idx - o2.idx;
        });

        for (int i = 0; i < servers.length; i++) {
            idle.add(new Server(i, servers[i], 0));
        }
        int[] res = new int[tasks.length];
        int ts = 0;
        for (int i = 0; i < tasks.length; i++) {
            ts = Math.max(ts, i);
            while (!busy.isEmpty() && busy.peek().time <= ts) {
                idle.add(busy.poll());
            }
            if (idle.isEmpty()) {
                assert busy.peek() != null;
                // 更新时刻
                ts = busy.peek().time;
                while (!busy.isEmpty() && busy.peek().time <= ts) {
                    idle.add(busy.poll());
                }
            }
            // ts时刻被选出来的server
            Server select = idle.poll();
            assert select != null;
            res[i] = select.idx;
            select.time = ts + tasks[i];
            busy.add(select);
        }
        return res;
    }

    private static final double EPS = 1e-8;

    public int minSkips(int[] dist, int speed, int hoursBefore) {
        int n = dist.length;
        // dp(i,j)=t 到第i个位置一共停顿j次所需时间t
        double[][] dp = new double[n][n];
        double ts = 0.0;
        // 完全不停顿情况下
        for (int i = 0; i < n; i++) {
            ts += dist[i] / (double) speed;
            dp[i][0] = ts;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= i; j++) {
                double x = i == j ? Double.MAX_VALUE : dp[i - 1][j];
                double y = Math.ceil(dp[i - 1][j - 1] - EPS);
                dp[i][j] = Math.min(x, y) + dist[i] / (double) speed;
            }
        }
        for (int j = n - 1; j >= 0; j--) {
            if (dp[n - 1][j] < hoursBefore + EPS) {
                return n - 1 - j;
            }
        }
        return -1;
    }

    public int minSkips2(int[] dist, int speed, int hoursBefore) {
        int n = dist.length;
        // dp(i,j)=t 到第i个位置一共停顿j次所需时间t
        long[][] dp = new long[n][n];
        long ts = 0;
        // 完全不停顿情况下
        for (int i = 0; i < n; i++) {
            ts += dist[i];
            dp[i][0] = ts;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= i; j++) {
                long x = i == j ? Long.MAX_VALUE : dp[i - 1][j];
                long y = ((dp[i - 1][j - 1] - 1) / speed + 1) * speed;
                dp[i][j] = Math.min(x, y) + dist[i];
            }
        }
        for (int j = n - 1; j >= 0; j--) {
            if (dp[n - 1][j] <= hoursBefore * (long) speed) {
                return n - 1 - j;
            }
        }
        return -1;
    }
}
