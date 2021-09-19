package quiz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @since 2021/9/14 18:43
 */
public class Horn {

    public static void main(String[] args) {
        Horn horn = new Horn();
//        int[] a = new int[]{5, 4, 3, 2, 1};
//        int result = horn.increaseInterval(a);
//        System.out.println(result);
//        int result = horn.step(2);
//        System.out.println(result);
//        result = horn.step(7);
//        System.out.println(result);
        int[] a = {1, 2, 3, 4};
        int[] b = {0, 1, 2, 0, 1, 2};
        int[] c = {0, 1, 1, 1, 1, 1};
        int[] d = {1, 2, 0, 2, 3, 0};
//        int[] a = {1, 2, 3};
//        int[] b = {0, 0, 2};
//        int[] c = {0, 0, 1};
//        int[] d = {1, 2, 0};
        int[] result = horn.work(a, b, c, d);
        for (int r : result) {
            System.out.print(r + " ");
        }
    }

    public int increaseInterval(int[] a) {
        final int n = a.length;
        int res = 1;
        int pre = a[0];
        int tmp = 0;
        for (int i = 1; i < n; i++) {
            int cur = a[i];
            tmp += cur;
            if (tmp > pre) {
                pre = tmp;
                tmp = 0;
                res++;
            }
        }
        return res;
    }

    public int step(int n) {
        // f[i][j] = f[i][j-1]
        // f[i][j] = f[i][j-1] + f[i-1][j]
        int mod = 10000;
        /*
        int[][] f = new int[n + 1][2 * n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 2 * i; j++) {
                if (j == 0) {
                    f[i][j] = 1;
                } else if (j <= 2 * i - 2) {
                    f[i][j] = f[i][j - 1] + f[i - 1][j];
                } else {
                    f[i][j] = f[i][j - 1];
                }
                if (f[i][j] >= mod) {
                    f[i][j] = (f[i][j] % mod);
                }
            }
        }
        return f[n][2 * n];
        */
        int[] f = new int[2 * n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 2 * n; j++) {
                if (j == 0) {
                    f[j] = 1;
                } else if (j <= 2 * i - 2) {
                    f[j] = f[j - 1] + f[j];
                } else {
                    f[j] = f[j - 1];
                }
                if (f[j] >= mod) {
                    f[j] = (f[j] % mod);
                }
            }
        }
        return f[2 * n];
    }

    public int[] work(int[] a, int[] t, int[] c, int[] d) {
        List<Integer> list = new ArrayList<>();
        final int m = a.length;
        final int n = t.length;
        UF uf = new UF(a);
        for (int i = 0; i < n; i++) {
            if (t[i] == 0) {
                uf.union(c[i], d[i]);
            } else if (t[i] == 1) {
                uf.add(c[i], d[i]);
            } else if (t[i] == 2) {
                list.add(uf.query(c[i]));
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    static class UF {
        int[] parents;
        int[] vs;
        int[] ranks;
        int[] tags;

        UF(int[] a) {
            int n = a.length;
            parents = new int[n];
            vs = new int[n];
            ranks = new int[n];
            tags = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
                vs[i] = a[i];
                ranks[i] = 1;
            }
        }

        void add(int x, int a) {
            int px = find(x);
            tags[px] += a;
        }

        int query(int x) {
            int px = find(x);
            return vs[x] + tags[px];
        }

        int find(int x) {
            if (parents[x] == x) {
                return parents[x];
            }
            parents[x] = find(parents[x]);
            return parents[x];
        }

        void push(int px) {
            if (tags[px] == 0) {
                return;
            }
            for (int i = 0; i < vs.length; i++) {
                if (find(i) == px) {
                    vs[i] += tags[px];
                }
            }
            tags[px] = 0;
        }

        void union(int x, int y) {
            int px = find(x), py = find(y);
            push(px);
            push(py);
            if (px == py) {
                return;
            }
            if (ranks[px] < ranks[py]) {
                ranks[px] += ranks[py];
                parents[py] = px;
            } else {
                ranks[py] += ranks[px];
                parents[px] = py;
            }
        }
    }
}
