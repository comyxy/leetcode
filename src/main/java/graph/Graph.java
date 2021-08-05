package graph;

import struct.GraphNode;

import java.util.*;

/**
 * @author comyxy
 */
public class Graph {
    private int V;
    private int E;
    private List<Integer>[] adj;

    public Graph() {

    }


    public Graph(int V) {
        this.V = V;
        this.adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
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
            for (int w : G.adj(v)) {
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

    public static class BreadthFirstSearch {
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
            while (!queue.isEmpty()) {
                count++;
                int v = queue.remove();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        marked[w] = true;
                        queue.offer(w);
                    }
                }
            }
        }
    }

    /**
     * LeetCode207
     *
     * @param numCourses    课程数
     * @param prerequisites 限制矩阵 [[0,1]] 代表 想要学习课程 0 需要先完成课程 1
     * @return 能否学完课程
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        final int m = prerequisites.length;
        if (m == 0) {
            return true;
        }
        // 每一个节点的出度
        int[] out = new int[numCourses];
        for (int i = 0; i < m; i++) {
            // 对于每一个限制条件 被限制的节点出度加1
            int limited = prerequisites[i][0];
            out[limited]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (out[i] == 0) {
                queue.offer(i);
                out[i]--;
            }
        }

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (int i = 0; i < m; i++) {
                int limited = prerequisites[i][0];
                int pre = prerequisites[i][1];
                // 先修课程已经修过
                if (pre == poll) {
                    out[limited]--;
                }
                if (out[limited] == 0) {
                    queue.offer(limited);
                    out[limited]--;
                }
            }
        }

        // 检查出度
        for (int i = 0; i < numCourses; i++) {
            if (out[i] > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 有向图的变
     */
    private List<List<Integer>> edges;
    /**
     * visited 0 尚未探索 1 正在探索 尚未回溯 2 已经探索 回溯完成
     */
    private int[] visited;
    /**
     * 能否学完
     */
    private boolean valid = true;

    /**
     * LeetCode207 dfs
     *
     * @param numCourses    课程数
     * @param prerequisites 限制矩阵 [[0,1]] 代表 想要学习课程 0 需要先完成课程 1
     * @return 能否学完课程
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        visited = new int[numCourses];
        for (int[] info : prerequisites) {
            // edge 1:2,3 代表 1->2 1->3 即1是2,3的先修课程
            edges.get(info[1]).add(info[0]);
        }
        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                canFinish2dfs(i);
            }
        }
        return valid;
    }

    private void canFinish2dfs(int id) {
        // 探索中
        visited[id] = 1;
        List<Integer> nextIds = edges.get(id);
        for (int next : nextIds) {
            if (visited[next] == 0) {
                // 探索下一个
                canFinish2dfs(next);
                if (!valid) {
                    // 搜索过程中到了尚未回溯的节点 说明有环
                    return;
                }
            } else if (visited[next] == 1) {
                // 搜索过程中到了尚未回溯的节点 说明有环
                valid = false;
                return;
            }
        }
        // 回溯
        visited[id] = 2;
    }

    /**
     * LeetCode207 bfs
     *
     * @param numCourses    课程数
     * @param prerequisites 限制矩阵 [[0,1]] 代表 想要学习课程 0 需要先完成课程 1
     * @return 能否学完课程
     */
    public boolean canFinish3(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        int[] indeg = new int[numCourses];
        for (int[] info : prerequisites) {
            // edge 1:2,3 代表 1->2 1->3 即1是2,3的先修课程
            edges.get(info[1]).add(info[0]);
            indeg[info[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }
        // 记录访问过的节点
        int visited = 0;
        while (!queue.isEmpty()) {
            visited++;
            int u = queue.poll();
            for (int next : edges.get(u)) {
                indeg[next]--;
                if (indeg[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return visited == numCourses;
    }


    /**
     * LeetCode1192 查找关键连接 无向图的桥
     *
     * @param n           节点数目
     * @param connections 连接表
     * @return 桥
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        dfn = new int[n];
        low = new int[n];
        for (int i = 0; i < n; i++) {
            dfn[i] = -1;
            low[i] = -1;
        }
        es = new HashMap<>();
        for (List<Integer> connection : connections) {
            int l = connection.get(0), r = connection.get(1);
            if (es.containsKey(l)) {
                es.get(l).add(r);
            } else {
                List<Integer> t = new ArrayList();
                t.add(r);
                es.put(l, t);
            }
            r = connection.get(0);
            l = connection.get(1);
            if (es.containsKey(l)) {
                es.get(l).add(r);
            } else {
                List<Integer> t = new ArrayList();
                t.add(r);
                es.put(l, t);
            }
        }
        ret = new ArrayList<>();
        v = new boolean[n];
        tot = 1;
        tarjan(0, -1);
        return ret;
    }

    private void tarjan(int node, int parent) {
        tot++;
        low[node] = dfn[node] = tot;
        v[node] = true;

        for (int edge : es.get(node)) {
            if (!v[edge]) {
                tarjan(edge, node);
                low[node] = Math.min(low[node], low[edge]);
                if (low[edge] > dfn[node]) {
                    ret.add(Arrays.asList(node, edge));
                }
            } else if (edge != parent) {
                low[node] = Math.min(low[node], dfn[edge]);
            }
        }
    }

    private int[] dfn; // 时间戳
    private int[] low; // 回溯时间
    private Map<Integer, List<Integer>> es; // 无向图的边
    private List<List<Integer>> ret;
    private boolean[] v; // 是否已经访问
    private int tot; // 时间戳计数

    /**
     * LeetCode1568 陆地分离的最少天数
     *
     * @param grid 网格
     * @return 陆地分离的最少天数
     */
    public int minDays(int[][] grid) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        // n行 m列
        int n = grid.length, m = grid[0].length;
        UnionFind uf = new UnionFind(n * m);
        TarjanGeDian tarjanGeDian = new TarjanGeDian(n * m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                for (int k = 0; k < 4; k++) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (x >= 0 && x < n && y >= 0 && y < m && grid[x][y] == 1) {
                        uf.union(i * m + j, x * m + y);
                        tarjanGeDian.setEdges(i * m + j, x * m + y);
                    }
                }
            }
        }
        // Union-Find计算连通分量数目
        int cycle = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && uf.find(i * m + j) == (i * m + j)) {
                    cycle++;
                }
            }
        }
        // 连通分量不为1 已经分割开
        if (cycle != 1) {
            return 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    tarjanGeDian.tarjan(i * m + j, -1);
                    break;
                }
            }
        }
        return tarjanGeDian.haveGeDian ? 1 : 2;
    }

    class TarjanGeDian {
        private boolean haveGeDian = false;
        private int n;
        private int tot;
        private int[] dfn;
        private int[] low;
        private Map<Integer, Set<Integer>> edges;

        public TarjanGeDian(int n) {
            this.n = n;
            this.tot = 0;
            this.dfn = new int[n];
            this.low = new int[n];
            this.edges = new HashMap<>();
        }

        public void setEdges(int x, int y) {
            if (edges.containsKey(x)) {
                edges.get(x).add(y);
            } else {
                Set<Integer> arr = new HashSet<>();
                arr.add(y);
                edges.put(x, arr);
            }

            if (edges.containsKey(y)) {
                edges.get(y).add(x);
            } else {
                Set<Integer> arr = new HashSet<>();
                arr.add(x);
                edges.put(y, arr);
            }
        }

        public void tarjan(int x, int p) {
            dfn[x] = low[x] = ++tot;
            int nChild = 0;
            for (int y : edges.get(x)) {
                if (dfn[y] == 0) {
                    nChild++;
                    tarjan(y, x);
                    low[x] = Math.min(low[x], low[y]);
                    if (p == -1 && nChild > 1) {
                        haveGeDian = true;
                    } else if (p != -1 && low[y] >= dfn[x]) {
                        haveGeDian = true;
                    }
                } else if (y != p) {
                    low[x] = Math.min(low[x], dfn[y]);
                }
            }
        }
    }

    public int[] restoreArray(int[][] adjacentPairs) {
        final int n = adjacentPairs.length + 1;
        Map<Integer, List<Integer>> adjs = new HashMap<>();
        for (int[] pair : adjacentPairs) {
            int u = pair[0], v = pair[1];
            List<Integer> u2v = adjs.getOrDefault(u, new ArrayList<>());
            u2v.add(v);
            adjs.put(u, u2v);
            List<Integer> v2u = adjs.getOrDefault(v, new ArrayList<>());
            v2u.add(u);
            adjs.put(v, v2u);
        }
        int cur = 0;
        for (Map.Entry<Integer, List<Integer>> entry : adjs.entrySet()) {
            if(entry.getValue().size() == 1) {
                cur = entry.getKey();
                break;
            }
        }
        Set<Integer> visited= new HashSet<>();
        int[] res = new int[n];
        res[0] = cur;
        visited.add(cur);
        for (int i = 1; i < n; i++) {
            List<Integer> nexts = adjs.get(cur);
            for (Integer next : nexts) {
                if(visited.contains(next)) {
                    continue;
                }
                cur = next;
                res[i] = cur;
                visited.add(cur);
            }
        }

        return res;
    }


    /**
     * https://leetcode-cn.com/problems/find-eventual-safe-states
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        final int n = graph.length;
        int[] colors = new int[n];
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (isSafe(i, colors, graph)) {
                res.add(i);
            }
        }
        return res;
    }

    private boolean isSafe(int u, int[] colors, int[][] graph) {
        if (colors[u] > 0) {
            return colors[u] == 2;
        }
        // 0 -> 1
        colors[u] = 1;
        for (int v : graph[u]) {
            if (!isSafe(v, colors, graph)) {
                return false;
            }
        }
        // 1 -> 2
        colors[u] = 2;
        return true;
    }
}
