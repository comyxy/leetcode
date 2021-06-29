package zhousai.s1;

import struct.ListNode;

import java.util.*;

/** 2020/10/11 */
public class W7 {

  public int maxDepth(String s) {
    char[] cs = s.toCharArray();
    int max = 0, ans = 0;
    for (char c : cs) {
      if (c == '(') {
        ans++;
        max = Math.max(max, ans);
      } else if (c == ')') {
        ans--;
      }
    }
    return max;
  }

  public int maximalNetworkRank(int n, int[][] roads) {
    List<List<Integer>> edges = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      edges.add(new ArrayList<>());
    }
    for (int[] road : roads) {
      int u = road[0], v = road[1];
      edges.get(u).add(v);
      edges.get(v).add(u);
    }
    int max = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int ans = edges.get(i).size() + edges.get(j).size();
        for (Integer in : edges.get(i)) {
          if (in == j) {
            ans--;
          }
        }
        max = Math.max(max, ans);
      }
    }
    return max;
  }

  public boolean checkPalindromeFormation(String a, String b) {
    char[] cs1 = a.toCharArray();
    char[] cs2 = b.toCharArray();
    return check(cs1, cs2) || check(cs2, cs1);
  }

  private boolean check(char[] cs1, char[] cs2) {
    int size = cs1.length;
    for (int i = 0, j = size - 1; i < j; i++, j--) {
      if (cs1[i] != cs2[j]) {
        return isPalindrome(cs1, i, j) || isPalindrome(cs2, i, j);
      }
    }
    return true;
  }

  private boolean isPalindrome(char[] cs, int l, int r) {
    for (int i = l, j = r; i < j; i++, j--) {
      if (cs[i] != cs[j]) {
        return false;
      }
    }
    return true;
  }

  static final int MAX = 16;
  List<List<Integer>> graph = new ArrayList<>();
  int link;
  int dis;
  int start;

  public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
    for (int i = 0; i <= n; i++) {
      graph.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
      int u = edge[0], v = edge[1];
      graph.get(u).add(v);
      graph.get(v).add(u);
    }
    int[] ans = new int[n - 1];
    for (int i = 1; i < (1 << n); i++) {
      BitSet bs = BitSet.valueOf(new long[] {i});
      int nodeNumber = bs.cardinality();
      if (nodeNumber <= 1) {
        continue;
      }
      start = 0;
      for (int j = 0; j < n; j++) {
        if (bs.get(j)) {
          start = j + 1;
          break;
        }
      }
      link = 0;
      dis = -1;
      bfs(bs);
      if (link != nodeNumber) {
        continue;
      }
      link = 0;
      dis = -1;
      bfs(bs);
      ans[dis - 1]++;
    }
    return ans;
  }

  private void bfs(BitSet bs) {
    boolean[] visited = new boolean[MAX];
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    visited[start] = true;
    int last = start;
    while (!queue.isEmpty()) {
      int size = queue.size();
      link += size;
      dis++;
      for (int i = 0; i < size; i++) {
        int cur = queue.poll();
        last = cur;
        for (int next : graph.get(cur)) {
          if (!visited[next] && bs.get(next - 1)) {
            visited[next] = true;
            queue.offer(next);
          }
        }
      }
    }
    // update
    start = last;
  }

  private ListNode merge(ListNode lo, ListNode hi) {
    ListNode dummy = new ListNode(0);
    ListNode p = dummy;
    while (lo != null && hi != null) {
      if (lo.val < hi.val) {
        p.next = lo;
        p = p.next;
        lo = lo.next;
      } else {
        p.next = hi;
        p = p.next;
        hi = hi.next;
      }
    }
    p.next = lo != null ? lo : hi;
    return dummy.next;
  }

  private ListNode cut(ListNode head, int size){
    ListNode p = head;
    while(--size > 0 && p != null){
      p = p.next;
    }
    if(p == null){
      return null;
    }
    ListNode n = p.next;
    p.next = null;
    return n;
  }

  public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    int length = 0;
    ListNode p = head;
    while (p != null) {
      p = p.next;
      length++;
    }
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    for (int sz = 1; sz < length; sz = sz + sz) {
      ListNode cur = dummy.next;
      ListNode tail = dummy;
      while(cur != null){
        ListNode left = cur;
        ListNode right = cut(left, sz);
        cur = cut(right, sz);
        tail.next = merge(left, right);
        while(tail.next != null){
          tail = tail.next;
        }
      }
    }
    return dummy.next;
  }

  public static void main(String[] args) {
    W7 w7 = new W7();
    ListNode l1 = new ListNode(3);
    ListNode l2 = new ListNode(4);
    ListNode l3 = new ListNode(10);
    ListNode l4 = new ListNode(2);
    ListNode l5 = new ListNode(7);
    ListNode l6 = new ListNode(9);
    l1.next = l2;
    l2.next = l3;
    l3.next = l4;
    l4.next = l5;
    l5.next = l6;
    ListNode merge = w7.sortList(l1);
    while (merge != null) {
      System.out.println(merge.val);
      merge = merge.next;
    }
  }
}
