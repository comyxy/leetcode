package quiz;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @since 2021/8/27 11:27
 */
public class MeituanTest {

    public static void main(String[] args) {
//        q1();
//        q2();
//        q3();
        q4();
    }

    private static void q4() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int[] nums = new int[n];
        String[] s = in.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(s[i]);
        }
        SegmentTree segmentTree = new SegmentTree(nums);
        int m = Integer.parseInt(in.nextLine());
        for (int i = 0; i < m; i++) {
            s = in.nextLine().split(" ");
            int t = Integer.parseInt(s[0]);
            if (t == 2) {
                int idx = Integer.parseInt(s[1]);
                System.out.println(segmentTree.query(1, idx, idx));
            } else {
                int k = Integer.parseInt(s[1]);
                int x = Integer.parseInt(s[2]), y = Integer.parseInt(s[3]);
                k = Math.min(k, n - y + 1);
                segmentTree.modify(1, y, y + k - 1, x);
            }
        }
        in.close();
    }

    static class SegmentTree {
        int[] w;
        Node[] tr;

        SegmentTree(int[] nums) {
            int n = nums.length;
            w = new int[n + 1];
            System.arraycopy(nums, 0, w, 1, n);
            tr = new Node[n << 2];
            for (int i = 0; i < (n << 2); i++) {
                tr[i] = new Node();
            }
            buildSegmentTree(1, 1, n);
        }

        void buildSegmentTree(int u, int l, int r) {
            if (l == r) {
                tr[u].left = l;
                tr[u].right = r;
                tr[u].val = -1;
                tr[u].idx = 0;
                return;
            }
            tr[u].left = l;
            tr[u].right = r;
            int mid = (l + r) >> 1;
            buildSegmentTree(u << 1, l, mid);
            buildSegmentTree( (u << 1) + 1, mid + 1, r);
        }

        void modify(int u, int l, int r, int idx) {
            if (l <= tr[u].left && tr[u].right <= r) {
                int nidx = idx + (tr[u].left - l);
                tr[u].idx = nidx;
                tr[u].val = w[nidx];
                return;
            }
            int mid = (tr[u].left + tr[u].right) >> 1;
            pushDown(u);
            if (l <= mid) {
                modify(u << 1, l, r, idx);
            }
            if (r > mid) {
                modify((u << 1) + 1, l, r, idx);
            }
        }

        int query(int u, int l, int r) {
            if (tr[u].left == l && tr[u].right == r) {
                return tr[u].val;
            }
            pushDown(u);
            int mid = (tr[u].left + tr[u].right) >> 1;
            if (l <= mid) {
                return query(u << 1, l, r);
            } else {
                return query((u << 1) + 1, l, r);
            }
        }

        void pushDown(int u) {
            if (tr[u].idx > 0) {
                int mid = (tr[u].left + tr[u].right) >> 1;
                tr[u << 1].idx = tr[u].idx;
                tr[u << 1].val = w[tr[u].idx];
                int nidx = tr[u].idx + (mid - tr[u].left) + 1;
                tr[(u << 1) + 1].idx = nidx;
                tr[(u << 1) + 1].val = w[nidx];

                tr[u].idx = 0;
            }
        }
    }

    static class Node {
        // 线段树节点左边界
        int left;
        // 线段树节点右边界
        int right;
        // 修改后值
        int val;
        // 最近一次修改的w下标idx
        int idx;
    }

    static void q3() {
        Scanner in = new Scanner(System.in);
        String[] s = null;
        s = in.nextLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        int[][] mw = new int[n][3];
        for (int i = 0; i < n; i++) {
            s = in.nextLine().split(" ");
            mw[i][0] = Integer.parseInt(s[0]);
            mw[i][1] = Integer.parseInt(s[1]);
            mw[i][2] = i + 1;
        }
        int[] res = mostMoney(mw, m);
        for (int r : res) {
            System.out.print(r + " ");
        }
    }

    private static int[] mostMoney(int[][] mw, int m) {
        int[] res = new int[m];
        Arrays.sort(mw, (o1, o2) -> {
            int v1 = o1[0] + 2 * o1[1];
            int v2 = o2[0] + 2 * o2[1];
            if (v1 != v2) {
                return v2 - v1;
            } else {
                return o1[2] - o2[2];
            }
        });
        for (int i = 0; i < m; i++) {
            res[i] = mw[i][2];
        }
        Arrays.sort(res);
        return res;
    }

    static void q2() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        String[] w = in.nextLine().split(" ");
        String[] f = in.nextLine().split(" ");
        int[] nums = new int[n];
        int[] picks = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(w[i]);
            picks[i] = Integer.parseInt(f[i]);
        }
        /*--- 顺序移除 变为 逆序插入 (并查集) ---*/
        boolean[] visited = new boolean[n];
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        int[] res = new int[n];
        res[n - 1] = 0;
        int maxn = 0;
        for (int i = n - 1; i > 0; i--) {
            int cur = picks[i] - 1;
            visited[cur] = true;
            maxn = Math.max(maxn, nums[cur]);
            if (cur > 0 && visited[cur - 1]) {
                int p = find(cur - 1, parents);
                // union
                parents[p] = cur;
                nums[cur] += nums[p];
                maxn = Math.max(maxn, nums[cur]);
            }
            if (cur < n - 1 && visited[cur + 1]) {
                int p = find(cur + 1, parents);
                parents[p] = cur;
                nums[cur] += nums[p];
                maxn = Math.max(maxn, nums[cur]);
            }
            res[i - 1] = maxn;
        }
        for (int r : res) {
            System.out.println(r);
        }
    }

    /**
     * find
     */
    static int find(int x, int[] parents) {
        if (x == parents[x]) {
            return x;
        }
        parents[x] = find(parents[x], parents);
        return parents[x];
    }

    static void q1() {
        Scanner in = new Scanner(System.in);
        int t = Integer.parseInt(in.nextLine());
        for (int i = 0; i < t; i++) {
            String s = in.nextLine();
            boolean ok = check(s);
            if (ok) {
                System.out.println("Accept");
            } else {
                System.out.println("Wrong");
            }
        }
    }

    static boolean check(String s) {
        int n = s.length();
        int nc = 0, nn = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (i == 0 && !isAlpha(c)) {
                return false;
            }
            if (!isAlpha(c) && !isDigit(c)) {
                return false;
            }
            if (isAlpha(c)) {
                nc++;
            }
            if (isDigit(c)) {
                nn++;
            }
        }
        if (nc == 0 || nn == 0) {
            return false;
        }
        return true;
    }

    static boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}
