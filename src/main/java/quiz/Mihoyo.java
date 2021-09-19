package quiz;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @since 2021/9/11 18:59
 */
public class Mihoyo {

    private final static int N = 10;

    private static int m = 100;

    private final static int P = 1000;

    private final static Random random = new Random();

    private final Map<Integer, Integer> id2count = new HashMap<>();

    public int lottery(int id) {
        int res = 0;
        synchronized (this) {
            int count = id2count.getOrDefault(id, 0);
            if (count >= N) {
                res = 1;
                return res;
            } else {
                id2count.put(id, count + 1);
            }
            if (m <= 0) {
                res = 1;
                return res;
            } else {
                m--;
            }
            if (random.nextInt(P) != 0) {
                res = 1;
                return res;
            }
        }
        return res;
    }


    public static void main(String[] args) {
//        q1();
//        q2();
//        q3();
        ExecutorService es = Executors.newFixedThreadPool(10);
        Mihoyo mihoyo = new Mihoyo();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            es.submit(() -> {
                int r = mihoyo.lottery(finalI % 10);
                System.out.println(r);
            });
        }
    }

    private static void q3() {
        Scanner in = new Scanner(System.in);
        String[] cs = in.nextLine().split(" ");
        int r1 = Integer.parseInt(cs[0]), c1 = Integer.parseInt(cs[1]);
        cs = in.nextLine().split(" ");
        int r2 = Integer.parseInt(cs[0]), c2 = Integer.parseInt(cs[1]);
        int res = q3(r1, c1, r2, c2);
        System.out.println(res);
    }

    private static int q3(int r1, int c1, int r2, int c2) {
        final int m = 10, n = 9;
        final int[][] dirs = {{-3, -2}, {-3, 2}, {-2, 3}, {2, 3}, {3, 2}, {3, -2}, {2, -3}, {-2, -3}};
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r1, c1});
        visited[r1][c1] = true;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] p = queue.poll();
                assert p != null;
                int x = p[0], y = p[1];
                if (x == r2 && y == c2) {
                    return level;
                }
                for (int k = 0; k < 8; k++) {
                    int nx = x + dirs[k][0], ny = y + dirs[k][1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                        queue.offer(new int[]{nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }
            level++;
        }
        return -1;
    }


    private static void q1() {
        Scanner in = new Scanner(System.in);
        int t = Integer.parseInt(in.nextLine());
        for (int i = 0; i < t; i++) {
            String s = in.nextLine().trim();
            int res = q1(s);
            System.out.println(res);
        }
    }

    private static int q1(String s) {
        char[] cs = s.toCharArray();
        int n = cs.length;
        Deque<Character> st = new LinkedList<>();
        int res = 0;
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (c == '[' || c == '{') {
                st.push(c);
            } else if (c == ']') {
                char p = st.peek();
                if (p == '[') {
                    st.pop();
                } else if (p == '{') {
                    res++;
                    st.pop();
                }
            } else if (c == '}') {
                char p = st.peek();
                if (p == '[') {
                    st.pop();
                    res++;
                } else if (p == '{') {
                    st.pop();
                }
            }
        }
        return res;
    }

    private static void q2() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for (int i = 0; i < n; i++) {
            int num = in.nextInt();
            int res = q2(num);
            System.out.println(res);
        }
    }

    private static int q2(int num) {
        int n = num / 3;
        if (n < 3) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < n; i++) {
            int t = n - i;
            int j = i, k = n - 2;
            while (j <= k) {
                if (j + k == t) {
                    if (i == j && j == k) {
                        res += 1;
                    } else if (i == j || j == k) {
                        res += 3;
                    } else {
                        res += 6;
                    }
                    j++;
                    k--;
                } else if (j + k > t) {
                    k--;
                } else {
                    j++;
                }
            }

        }
        return res;
    }
}
