package zhousai;

import java.util.*;

/** 2020/8/30 */
public class W1 {

  public boolean containsPattern(int[] arr, int m, int k) {
    final int n = arr.length;
    for (int i = 0; i < n - m + 1; i++) {
      List<Integer> mode = new ArrayList<>(m);
      for (int j = i; j < i + m; j++) {
        mode.add(arr[j]);
      }
      // l 代表 mode中位置
      int l = 0;
      int times = 1;
      for (int j = i + m; j < n; j++) {
        if (mode.get(l++) != arr[j]) {
          break;
        }
        if (l == m) {
          l = 0;
          times++;
        }
      }
      if (times >= k) {
        return true;
      }
    }
    return false;
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
    UF uf = new UF(n * m);
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

  class UF {
    private int n;
    private int[] pre;
    private int[] sz;

    public UF(int n) {
      this.n = n;
      this.pre = new int[n];
      this.sz = new int[n];
      for (int i = 0; i < n; i++) {
        sz[i] = 1;
        pre[i] = i;
      }
    }

    public int find(int x) {
      return x == pre[x] ? x : find(pre[x]);
    }

    public void union(int x, int y) {
      x = find(x);
      y = find(y);
      if (x == y) {
        return;
      }
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

  }
}
