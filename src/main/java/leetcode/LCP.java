package leetcode;

import java.util.*;

/** 2020/7/29 */
public class LCP {

  /**
   * LCP13
   *
   * @param maze 迷宫
   * @return 最小步数
   */
  public int minimalSteps(String[] maze) {
    m = maze.length;
    if (m == 0) {
      throw new IllegalArgumentException();
    }
    n = maze[0].length();
    // 机关
    List<int[]> buttons = new ArrayList<>();
    // 石头
    List<int[]> stones = new ArrayList<>();
    // 起点 终点
    int sx = -1, sy = -1, tx = -1, ty = -1;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (maze[i].charAt(j) == 'M') {
          buttons.add(new int[] {i, j});
        }
        if (maze[i].charAt(j) == 'O') {
          stones.add(new int[] {i, j});
        }
        if (maze[i].charAt(j) == 'S') {
          sx = i;
          sy = j;
        }
        if (maze[i].charAt(j) == 'T') {
          tx = i;
          ty = j;
        }
      }
    }
    // 机关数
    int numberOfButtons = buttons.size();
    // 石头堆数
    int numberOfStones = stones.size();
    // 从起点开始到每个点的距离
    int[][] startDist = bfs(sx, sy, maze);

    // 没有机关
    if (numberOfButtons == 0) {
      return startDist[tx][ty];
    }

    // dist记录从某个机关到其他机关 或者 起点重点 的最短距离
    // dist[i][numberOfButtons] 第i个机关到起点
    // dist[i][numberOfButtons + 1] 第i个机关到终点
    int[][] dist = new int[numberOfButtons][numberOfButtons + 2];
    for (int i = 0; i < numberOfButtons; i++) {
      Arrays.fill(dist[i], -1);
    }
    // dd记录某个机关到其他所有位置的最短记录
    int[][][] dd = new int[numberOfButtons][][];
    for (int i = 0; i < numberOfButtons; i++) {
      int[][] d = bfs(buttons.get(i)[0], buttons.get(i)[1], maze);
      dd[i] = d;
      // 从机关到终点不用拿石头
      dist[i][numberOfButtons + 1] = d[tx][ty];
    }

    for (int i = 0; i < numberOfButtons; i++) {

      // 得到从起点到第一次开机关的距离
      int tmp = -1;
      for (int[] stone : stones) {
        int mx = stone[0], my = stone[1];
        // 第i个机关位置可以到达位置(mx,my) 以及 可以从起点到达位置(mx.my)
        if (dd[i][mx][my] != -1 && startDist[mx][my] != -1) {
          if (tmp == -1 || tmp > dd[i][mx][my] + startDist[mx][my]) {
            tmp = dd[i][mx][my] + startDist[mx][my];
          }
        }
      }
      dist[i][numberOfButtons] = tmp;

      // 各个机关之间的距离
      for (int j = i + 1; j < numberOfButtons; j++) {
        int mn = -1;
        for (int[] stone : stones) {
          int mx = stone[0], my = stone[1];
          // 第i个机关位置可以到达位置(mx,my) 以及 可以从第j个机关到达位置(mx.my)
          if (dd[i][mx][my] != -1 && dd[j][mx][my] != -1) {
            if (mn == -1 || mn > dd[i][mx][my] + dd[j][mx][my]) {
              mn = dd[i][mx][my] + dd[j][mx][my];
            }
          }
        }
        dist[i][j] = mn;
        dist[j][i] = mn;
      }
    }

    // 无法从起点到达一个机关或者无法从一个机关达到终点
    for (int i = 0; i < numberOfButtons; i++) {
      if (dist[i][numberOfButtons] == -1 || dist[i][numberOfButtons + 1] == -1) {
        return -1;
      }
    }

    // dp(mask,i) 其中mask是掩码 二进制位为1代表机关已经开启
    // dp(mask,i) 表示在第i个机关处 触发状态为mask 最小步数
    int[][] dp = new int[1 << numberOfButtons][numberOfButtons];
    for (int i = 0; i < (1 << numberOfButtons); i++) {
      Arrays.fill(dp[i], -1);
    }
    // 起点到第一个机关处
    for (int i = 0; i < numberOfButtons; i++) {
      dp[1 << i][i] = dist[i][numberOfButtons];
    }
    // 机关之间
    for (int mask = 0; mask < (1 << numberOfButtons); mask++) {
      for (int i = 0; i < numberOfButtons; i++) {
        // mask 合法 即 dp(mask,i)是存在的 第i个机关已经开启
        if ((mask & (1 << i)) != 0) {
          for (int j = 0; j < numberOfButtons; j++) {
            // 第j个机关还没开启还没计算
            if ((mask & (1 << j)) == 0) {
              int next = mask | (1 << j);
              if (dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j]) {
                dp[next][j] = dp[mask][i] + dist[i][j];
              }
            }
          }
        }
      }
    }
    // 终点
    int res = -1;
    int finalMask = (1 << numberOfButtons) - 1;
    for (int i = 0; i < numberOfButtons; i++) {
      if (res == -1 || res > dp[finalMask][i] + dist[i][numberOfButtons + 1]) {
        res = dp[finalMask][i] + dist[i][numberOfButtons + 1];
      }
    }
    return res;
  }

  private static final int[] DX = {1, -1, 0, 0};
  private static final int[] DY = {0, 0, -1, 1};
  private int m;
  private int n;

  /**
   * bfs
   *
   * @param x x
   * @param y y
   * @param maze 迷宫
   * @return 位置到每个位置的最少步数
   */
  private int[][] bfs(int x, int y, String[] maze) {
    int[][] res = new int[m][n];
    for (int i = 0; i < m; i++) {
      Arrays.fill(res[i], -1);
    }
    res[x][y] = 0;
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[] {x, y});
    while (!queue.isEmpty()) {
      int[] p = queue.poll();
      int cx = p[0], cy = p[1];
      for (int k = 0; k < 4; k++) {
        int nx = cx + DX[k], ny = cy + DY[k];
        if (isBound(nx, ny) && maze[nx].charAt(ny) != '#' && res[nx][ny] == -1) {
          res[nx][ny] = res[cx][cy] + 1;
          queue.offer(new int[] {nx, ny});
        }
      }
    }
    return res;
  }

  private boolean isBound(int x, int y) {
    return x >= 0 && x < m && y >= 0 && y < n;
  }

  public static void main(String[] args) {
    LCP lcp = new LCP();
    String[] maze2 = {"MM#", "OO#", "OST", "#M#", "M.M", "MMM", "##O"};
    int result = lcp.minimalSteps(maze2);
    System.out.println(result);
  }
}
