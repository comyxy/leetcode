package graph;

/**
 * @author comyxy
 */
public class UnionFind {
    private final int[] parent;
    private final int[] rank;
    private int count;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        for(int i=0;i<n;i++){
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        while(x != parent[x]) {
            x = parent[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int p = find(x), q = find(y);
        if(p == q){
            return;
        }
        if(rank[p] < rank[q]) {
            parent[p] = q;
            rank[q] += rank[p];
        }else{
            parent[q] = p;
            rank[p] += rank[q];
        }
        count--;
    }
}
