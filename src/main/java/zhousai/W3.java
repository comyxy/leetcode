package zhousai;

import javafx.util.Pair;

import java.util.*;

/** 2020/9/13 */
public class W3 {
  public int numSpecial(int[][] mat) {
    int ret = 0;
    final int n = mat.length;
    final int m = mat[0].length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (mat[i][j] == 1) {
          if (check(i, j, mat)) {
            ret++;
          }
        }
      }
    }
    return ret;
  }

  private boolean check(int i, int j, int[][] mat) {
    final int n = mat.length;
    final int m = mat[0].length;
    for (int k = 0; k < n; k++) {
      if (k == i) {
        continue;
      }
      if (mat[k][j] != 0) {
        return false;
      }
    }
    for (int k = 0; k < m; k++) {
      if (k == j) {
        continue;
      }
      if (mat[i][k] != 0) {
        return false;
      }
    }
    return true;
  }

  private int[][] preferences;
  private Set<Integer> set;

  public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
    this.preferences = preferences;
    this.set = new HashSet<>();
    for (int i = 0; i < pairs.length; i++) {
      for (int j = i + 1; j < pairs.length; j++) {
        getUnhappy(pairs[i], pairs[j]);
        getUnhappy(pairs[j], pairs[i]);
      }
    }
    return set.size();
  }

  private void getUnhappy(int[] p1, int[] p2) {
    int c1 = p1[0], c2 = p1[1], d1 = p2[0], d2 = p2[1];
    boolean f = compare(preferences[c1], c2, d1);
    if (f) {
      f = compare(preferences[d1], d2, c1);
    }
    boolean g = compare(preferences[c1], c2, d2);
    if (g) {
      g = compare(preferences[d2], d1, c1);
    }
    if (f || g) {
      set.add(c1);
    }

    f = compare(preferences[c2], c1, d1);
    if (f) {
      f = compare(preferences[d1], d2, c2);
    }
    g = compare(preferences[c2], c1, d2);
    if (g) {
      g = compare(preferences[d2], d1, c2);
    }
    if (f || g) {
      set.add(c2);
    }
  }

  private boolean compare(int[] preference, int c2, int d1) {
    int iC2 = -1, iD1 = -1;
    for (int i = 0; i < preference.length; i++) {
      if (preference[i] == c2) {
        iC2 = i;
      }
      if (preference[i] == d1) {
        iD1 = i;
      }
      if (iC2 != -1 && iD1 != -1) {
        break;
      }
    }
    return iC2 - iD1 > 0;
  }

  private Set<Integer> visited;
  private Set<Integer> not;

  public int minCostConnectPoints(int[][] points) {
    final int n = points.length;
    this.visited = new HashSet<>();
    this.not = new HashSet<>();
    for (int i = 1; i < n; i++) {
      not.add(i);
    }
    visited.add(0);
    int ret = 0;
    while (!not.isEmpty()) {
      int dis = Integer.MAX_VALUE;
      int wait = -1;
      for (Integer v : visited) {
        int[] start = points[v];
        for (Integer nv : not) {
          int[] end = points[nv];
          int newDis = Math.abs(start[0] - end[0]) + Math.abs(start[1] - end[1]);
          if (newDis < dis) {
            dis = newDis;
            wait = nv;
          }
        }
      }
      ret += dis;
      visited.add(wait);
      not.remove(wait);
    }
    return ret;
  }

  private int[] pre;

  public int minCostConnectPoints2(int[][] points) {
    List<Pair<Integer, Pair<Integer, Integer>>> edges = new ArrayList<>();
    final int n = points.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        edges.add(
            new Pair<>(
                Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]),
                new Pair<>(i, j)));
      }
    }
    edges.sort(Comparator.comparingInt(Pair::getKey));
    // 并查集
    pre = new int[n];
    for (int i = 0; i < n; i++) {
      pre[i] = i;
    }
    int ret = 0;
    for (int i = 0; i < edges.size(); i++) {
      int dis = edges.get(i).getKey();
      int x = find(edges.get(i).getValue().getKey());
      int y = find(edges.get(i).getValue().getValue());
      if (x != y) {
        pre[x] = y;
        ret += dis;
      }
    }
    return ret;
  }

  private int find(int x) {
    return x == pre[x] ? x : find(pre[x]);
  }

  public boolean isTransformable(String s, String t) {
    // 队列列表 记录s每个数字出现的位置索引
    List<Queue<Integer>> numberIndexQueueList = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      numberIndexQueueList.add(new LinkedList<>());
    }
    for (int i = 0; i < s.length(); i++) {
      numberIndexQueueList.get(s.charAt(i) - '0').offer(i);
    }
    for (int i = 0; i < t.length(); i++) {
      // 对于t的第一个数
      int digit = t.charAt(i) - '0';
      // s中不存在直接返回false
      if (numberIndexQueueList.get(digit).isEmpty()) {
        return false;
      }
      // 从0到digit-1的数字在s中出现的位置不能比digit在t中出现的位置在前面
      for (int j = 0; j < digit; j++) {
        if (!numberIndexQueueList.get(j).isEmpty()
            && numberIndexQueueList.get(j).peek() < numberIndexQueueList.get(digit).peek()) {
          return false;
        }
      }
      // 从0到digit-1的数字在s中出现的位置都比digit在t中的位置大
      // digit总可以被移动到s的当前第一位
      numberIndexQueueList.get(digit).poll();
    }
    return true;
  }

  public static void main(String[] args) {
    W3 w3 = new W3();

    //    int[][] mat = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    //    int numSpecial = w3.numSpecial(mat);
    //    System.out.println(numSpecial);

    //    int[][] preference = {{1, 3, 2}, {2, 3, 0}, {1, 3, 0}, {0, 2, 1}};
    //    int[][] pair = {{1, 3}, {0, 2}};
    //    int unhappyFriends = w3.unhappyFriends(4, preference, pair);
    //    System.out.println(unhappyFriends);

    //    int[][] points = {{3, 12}, {-2, 5}, {-4, 1}};
    //    int minCostConnectPoints = w3.minCostConnectPoints2(points);
    //    System.out.println(minCostConnectPoints);

    boolean isTrans = w3.isTransformable("84532", "34852");
    System.out.println(isTrans);
  }
}
