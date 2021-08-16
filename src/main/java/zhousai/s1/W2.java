package zhousai.s1;

import java.util.*;

/**
 * 2020/9/6
 */
public class W2 {

    public String modifyString(String s) {
        final int n = s.length();
        char[] cs = s.toCharArray();
        int left = -1, right = -1;
        for (int i = 0; i < n; i++) {
            if (cs[i] == '?' && left == -1) {
                left = i;
            }
            if (cs[i] != '?' && left != -1) {
                right = i - 1;
            }
            if (left != -1 && cs[i] == '?' && i == n - 1) {
                right = n - 1;
            }

            if (left != -1 && right != -1) {
                int len = right - left + 1;
                System.out.println(len);

                // change value here
                if (left == 0 && right == n - 1) {
                    cs[left] = 'a';
                    for (int j = left + 1; j <= right; j++) {
                        cs[j] = (char) (cs[j - 1] + 1);
                    }
                } else if (left == 0 && right != n - 1) {
                    for (int j = right; j >= left; j--) {
                        if (cs[j + 1] == 'z') {
                            cs[j] = 'a';
                        } else cs[j] = (char) (cs[j + 1] + 1);
                    }
                } else if (right == n - 1 && left != 0) {
                    for (int j = left; j <= right; j++) {
                        if (cs[j - 1] == 'z') {
                            cs[j] = 'a';
                        } else cs[j] = (char) (cs[j - 1] + 1);
                    }
                } else {
                    for (int j = left; j <= right; j++) {
                        if (cs[j - 1] == 'z') {
                            cs[j] = 'a';
                        } else cs[j] = (char) (cs[j - 1] + 1);

                        if (j == right && cs[j] == cs[j + 1]) {
                            cs[j] += 1;
                        }
                    }
                }
                left = right = -1;
            }
        }
        return new String(cs);
    }

    public int numTriplets(int[] nums1, int[] nums2) {
        int ret = 0;
        ret += getRet(nums1, nums2);
        ret += getRet(nums2, nums1);
        return ret;
    }

    private int getRet(int[] nums1, int[] nums2) {
        int ret = 0;
        Map<Integer, Integer> m = new HashMap<>();
        for (int num1 : nums1) {
            if (m.containsKey(num1)) {
                m.put(num1, m.get(num1) + 1);
            } else {
                m.put(num1, 1);
            }
        }

        for (Map.Entry<Integer, Integer> longIntegerEntry : m.entrySet()) {
            long s = longIntegerEntry.getKey();
            s = s * s;
            for (int i = 0; i < nums2.length; i++) {
                for (int j = i + 1; j < nums2.length; j++) {
                    long x = nums2[i] * (long) nums2[j];
                    if (x == s) {
                        ret += longIntegerEntry.getValue();
                    }
                }
            }
        }
        return ret;
    }

    public int minCost(String s, int[] cost) {
        int pre = 0, ret = 0;
        for (int i = 1; i < cost.length; i++) {
            if (s.charAt(i) == s.charAt(pre)) {
                if (cost[i] > cost[pre]) {
                    ret += cost[pre];
                    pre = i;
                } else {
                    ret += cost[i];
                }
            } else {
                pre = i;
            }
        }
        return ret;
    }

    public int maxNumEdgesToRemove(int n, int[][] edges) {
        UF ufAlice = new UF(n);
        UF ufBob = new UF(n);
        int red = 0;
        for (int[] edge : edges) {
            if (edge[0] == 3) {
                if (ufAlice.isConnected(edge[1] - 1, edge[2] - 1)
                        && ufBob.isConnected(edge[1] - 1, edge[2] - 1)) {
                    red++;
                } else {
                    ufAlice.union(edge[1] - 1, edge[2] - 1);
                    ufBob.union(edge[1] - 1, edge[2] - 1);
                }
            }
        }
        for (int[] edge : edges) {
            if (edge[0] == 1) {
                // only Alice
                if (ufAlice.isConnected(edge[1] - 1, edge[2] - 1)) {
                    red++;
                } else {
                    ufAlice.union(edge[1] - 1, edge[2] - 1);
                }
            } else if (edge[0] == 2) {
                if (ufBob.isConnected(edge[1] - 1, edge[2] - 1)) {
                    red++;
                } else {
                    ufBob.union(edge[1] - 1, edge[2] - 1);
                }
            }
        }
        if (ufAlice.count != 1 || ufBob.count != 1) {
            return -1;
        }
        return red;
    }

    class UF {
        // 父节点
        private int[] pre;
        // 秩
        private int[] sz;
        // 分量个数
        private int count;

        public UF(int size) {
            this.pre = new int[size];
            this.sz = new int[size];
            for (int i = 0; i < size; i++) {
                sz[i] = 1;
                pre[i] = i;
            }
            this.count = size;
        }

        public int find(int x) {
            return x == pre[x] ? x : find(pre[x]);
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }

        public void union(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            count--;
            if (sz[x] > sz[y]) {
                pre[y] = pre[x];
                sz[x] += sz[y];
            } else {
                pre[x] = pre[y];
                sz[y] += sz[x];
            }
        }
    }

    public static void main(String[] args) {
        W2 w2 = new W2();
        //    String s = w2.modifyString("?av??x???");
        //    System.out.println(s);
        //    int[] nums1 = {7, 4};
        //    int[] nums2 = {5, 2, 8};
        //    int[] nums1 = {43024, 99908};
        //    int[] nums2 = {1864};
        //    int[] cost = {1, 2, 3, 4, 5};
        //    int result = w2.minCost("abaac", cost);
        //    System.out.println(result);
        int[][] edges = {{3, 1, 2}, {3, 2, 3}, {1, 1, 3}, {1, 2, 4}, {1, 1, 2}, {2, 3, 4}};
        int result = w2.maxNumEdgesToRemove(4, edges);
        System.out.println(result);
    }
}
