package zhousai.tt;


import javafx.util.Pair;

import java.util.*;

/**
 * @author comyxy
 */
public class TT {

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static void t1() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        Map<Node, List<Pair<Node, Integer>>> transfer = new HashMap<>();
        for (int i = 0; i < k; i++) {

            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();

            Node from = new Node(x, y);
            Node to = new Node(u, v);
            // 记录允许的五元组
            List<Pair<Node, Integer>> t = transfer.getOrDefault(from, new ArrayList<>());
            t.add(new Pair<>(to, w));
            transfer.put(from, t);
        }

        // bfs queue中每个元素是 (Node, 已经消耗的cost)
        Queue<Pair<Node, Integer>> queue = new LinkedList<>();
        int res = Integer.MAX_VALUE;
        queue.offer(new Pair<>(new Node(1, 1), 0));
        while (!queue.isEmpty()) {
            Pair<Node, Integer> poll = queue.poll();
            Node t = poll.getKey();
            Integer acc = poll.getValue();
            // 达到终点
            if (t.x == n && t.y == m) {
                res = Math.min(res, acc);
                continue;
            }
            // 死路
            if (!transfer.containsKey(t)) {
                continue;
            }
            // 将所有可能的转移加入queue
            List<Pair<Node, Integer>> next = transfer.get(t);
            for (Pair<Node, Integer> nodeIntegerPair : next) {
                queue.offer(new Pair<>(nodeIntegerPair.getKey(), nodeIntegerPair.getValue() + acc));
            }
        }
        // 返回最小值或者-1
        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }

    private static void t12() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        Map<Node, List<Pair<Node, Integer>>> reverse = new HashMap<>();
        for (int i = 0; i < k; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();

            Node from = new Node(x, y);
            Node to = new Node(u, v);
            // 记录允许的五元组
            List<Pair<Node, Integer>> t = reverse.getOrDefault(to, new ArrayList<>());
            t.add(new Pair<>(from, w));
            reverse.put(to, t);
        }
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Node t = new Node(i+1, j+1);
                if(!reverse.containsKey(t)){
                    continue;
                }
                for (Pair<Node, Integer> nodeIntegerPair : reverse.get(t)) {
                    Node key = nodeIntegerPair.getKey();
                    Integer value = nodeIntegerPair.getValue();
                    dp[i][j] = Math.min(dp[i][j], dp[key.x-1][key.y-1] + value);
                }
            }
        }
        System.out.println(dp[n-1][m-1] == Integer.MAX_VALUE ? -1 : dp[n-1][m-1]);
    }

    static class GS {
        int x;
        long val;
        int n;

        public GS(int x, long val, int n) {
            this.x = x;
            this.val = val;
            this.n = n;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            GS gs = (GS) o;
            return x == gs.x && val == gs.val && n == gs.n;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, val, n);
        }

        @Override
        public String toString() {
            return "GS{" +
                    "x=" + x +
                    ", val=" + val +
                    ", n=" + n +
                    '}';
        }
    }

    private static void t3() {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int x = scanner.nextInt();
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int n = scanner.nextInt();

            Queue<GS> queue = new LinkedList<>();
            long res = 0;
            queue.offer(new GS(x, 0, n));
            while (!queue.isEmpty()) {
                GS poll = queue.poll();
                if (poll.n == 0) {
                    if(poll.val > res) {
                        res = poll.val;
                    }
                    continue;
                }
                // practice it
                queue.offer(new GS(Math.max(0, poll.x - a), poll.val + poll.x, poll.n - 1));
                // not
                queue.offer(new GS(poll.x + b, poll.val, poll.n - 1));
            }
            System.out.println(res);
        }
    }

    static class State {
        /**
         * 位置
         */
        int x, y;
        /**
         * 武器使用次数
         */
        int shoot;

        public State(int x, int y, int shoot) {
            this.x = x;
            this.y = y;
            this.shoot = shoot;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return x == state.x && y == state.y && shoot == state.shoot;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, shoot);
        }

        @Override
        public String toString() {
            return "State{" +
                    "x=" + x +
                    ", y=" + y +
                    ", shoot=" + shoot +
                    '}';
        }
    }

    private static void t4() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();
        String[] matrix = new String[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = scanner.nextLine();
        }
        Queue<Pair<State, Integer>> queue = new LinkedList<>();
        Map<State, Integer> visited = new HashMap<>();
        queue.offer(new Pair<>(new State(1, 1, 1), 0));
        while (!queue.isEmpty()) {
            Pair<State, Integer> poll = queue.poll();
            State sta = poll.getKey();
            Integer acc = poll.getValue();
            if (visited.containsKey(sta)) {
                Integer prev = visited.get(sta);
                if (acc < prev) {
                    visited.put(sta, acc);
                } else {
                    // 该节点此时不需要拓展
                    continue;
                }
            } else {
                visited.put(sta, acc);
            }
            // expand
            int x = sta.x, y = sta.y, sh = sta.shoot;
            if (x > 1) {
                if (matrix[x - 1 - 1].charAt(y - 1) == '*') {
                    // 向左扩展遇见障碍
                    if (sh > 0) {
                        // 武器使用次数大于0
                        queue.offer(new Pair<>(new State(x - 1, y, 0), acc + 1));
                    }
                } else {
                    queue.offer(new Pair<>(new State(x - 1, y, sh), acc + 1));
                }
            }
            if (x < n) {
                if (matrix[x - 1 + 1].charAt(y - 1) == '*') {
                    if (sh > 0) {
                        queue.offer(new Pair<>(new State(x + 1, y, 0), acc + 1));
                    }
                } else {
                    queue.offer(new Pair<>(new State(x + 1, y, sh), acc + 1));
                }
            }
            if (y > 1) {
                if (matrix[x - 1].charAt(y - 1 - 1) == '*') {
                    if (sh > 0) {
                        queue.offer(new Pair<>(new State(x, y - 1, 0), acc + 1));
                    }
                } else {
                    queue.offer(new Pair<>(new State(x, y - 1, sh), acc + 1));
                }
            }
            if (y < m) {
                if (matrix[x - 1].charAt(y - 1 + 1) == '*') {
                    if (sh > 0) {
                        queue.offer(new Pair<>(new State(x, y + 1, 0), acc + 1));
                    }
                } else {
                    queue.offer(new Pair<>(new State(x, y + 1, sh), acc + 1));
                }
            }
        }
        State f1 = new State(n, m, 1);
        State f0 = new State(n, m, 0);
        if (visited.containsKey(f1) && visited.containsKey(f0)) {
            System.out.println(Math.min(visited.get(f1), visited.get(f0)));
        } else if (visited.containsKey(f1)) {
            System.out.println(visited.get(f1));
        } else if (visited.containsKey(f0)) {
            System.out.println(visited.get(f0));
        } else {
            System.out.println(-1);
        }
    }

    public static void main(String[] args) {
        t1();
        t12();
//        t3();
//        t4();

    }
}
