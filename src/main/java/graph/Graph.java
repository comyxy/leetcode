package graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author comyxy
 */
public class Graph {
    private final int V;
    private int E;
    private List<Integer>[] adj;


    public Graph(int V) {
        this.V = V;
        this.adj = (List<Integer>[])new ArrayList[V];
        for(int v=0;v<V;v++){
            adj[v] = new ArrayList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public static class DepthFirstSearch {
        private boolean[] marked;
        private int count;

        public DepthFirstSearch(Graph G, int s) {
            marked = new boolean[G.V()];
            dfs(G, s);
        }

        private void dfs(Graph G, int v) {
            marked[v] = true;
            count++;
            for(int w : G.adj(v)) {
                if (!marked[w]) {
                    dfs(G, w);
                }
            }
        }

        public boolean marked(int w) {
            return marked[w];
        }

        public int count() {
            return count;
        }
    }

    public static class BreadthFirstSearch{
        private boolean[] marked;
        private int count;

        public BreadthFirstSearch(Graph G, int s) {
            marked = new boolean[G.V()];
            bfs(G, s);
        }

        private void bfs(Graph G, int s) {
            Deque<Integer> queue = new LinkedList<>();
            marked[s] = true;
            queue.offer(s);
            while(!queue.isEmpty()) {
                count++;
                int v = queue.remove();
                for(int w : G.adj(v)) {
                    if(!marked[w]) {
                        marked[w] = true;
                        queue.offer(w);
                    }
                }
            }
        }
    }
}
