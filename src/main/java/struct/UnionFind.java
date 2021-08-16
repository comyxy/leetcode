package struct;

/**
 * @author comyxy<br>
 * 并查集 处理连通性问题
 */
public class UnionFind {
    private final int[] parent;
    private final int[] rank;
    private int count;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * 路径压缩
     */
    public int find(int x) {
        if (x == parent[x]) {
            return x;
        }
        parent[x] = find(parent[x]);
        return parent[x];
    }

    /**
     * 按秩合并
     */
    public void union(int x, int y) {
        int p = find(x), q = find(y);
        if (p == q) {
            return;
        }
        if (rank[p] < rank[q]) {
            parent[p] = q;
            rank[q] += rank[p];
        } else {
            parent[q] = p;
            rank[p] += rank[q];
        }
        count--;
    }

    public int size() {
        return this.count;
    }
}
