package quiz;

import struct.ListNode;

import java.util.Scanner;

/**
* @since 2021/8/23 14:44
*/public class Shopee {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int target = Integer.parseInt(in.nextLine());
        int[] nums = {1,2,3,3,3,3,4};
        int left, right;
        left = leftSearch(nums, 3);
        right = rightSearch(nums, 3);
        System.out.println(left + " " + right);
        left = leftSearch(nums, 1);
        right = rightSearch(nums, 1);
        System.out.println(left + " " + right);
    }

    private static int leftSearch(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (nums[mid] >= target) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        if (lo >= nums.length || nums[lo] != target) {
            return -1;
        }
        return lo;
    }

    private static int rightSearch(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (nums[mid] > target) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        if (hi < 0 || nums[hi] != target) {
            return -1;
        }
        return hi;
    }

    public ListNode revertLinkList(ListNode head, int k) {
        // write code here
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        while (head != null) {
            ListNode tail = pre;
            for (int i = 0; i < k; i++) {
                if (tail.next == null) {
                    return dummy.next;
                }
                tail = tail.next;
            }
            ListNode nextHead = tail.next;
            tail.next = null;
            ListNode[] reversed = reverse(head, tail);
            head = reversed[0];
            tail = reversed[1];

            pre.next = head;
            tail.next = nextHead;

            head = nextHead;
            pre = tail;
        }
        return dummy.next;
    }

    private ListNode[] reverse(ListNode head, ListNode tail) {
        ListNode cur = head, t = null, pre = null;
        while (cur != null) {
            t = cur.next;
            cur.next = pre;
            pre = cur;
            cur = t;
        }
        return new ListNode[]{tail, head};
    }

    private final int[][] directions = {{-1,0}, {1,0}, {0,1}, {0,-1}};

    public int numberOfIslands(int[][] grid) {
        // write code here
        final int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfs(i, j, grid, visited);
                    res++;
                }
            }
        }
        return res;
    }

    private void dfs(int i, int j, int[][] grid, boolean[][] visited) {
        final int m = grid.length, n = grid[0].length;
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int[] d = directions[k];
            int ni = d[0] + i, nj = d[1] + j;
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && !visited[ni][nj] && grid[i][j] == 1) {
                dfs(ni, nj, grid, visited);
            }
        }
    }
}
