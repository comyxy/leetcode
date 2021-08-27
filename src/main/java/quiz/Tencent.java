package quiz;

import struct.ListNode;

import java.util.*;

/**
 * @since 2021/8/22 19:43
 */
public class Tencent {
    private static final int MOD = 1000000007;

    public static void main(String[] args) {
        q4();
//        q3();
//        q2();
//        q1();
    }

    /**
     * 长度为n的字符串s，找到长度为k的字典序最大子序列
     */
    private static void q4() {
        Scanner in = new Scanner(System.in);
        String[] s = in.nextLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int k = Integer.parseInt(s[1]);
        String word = in.nextLine();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // 剩余字符能够构成k个字符 sb最后一个字符小于当前子符c
            while (sb.length() > 0 && (sb.length() - 1 + (n - i)) >= k
                    && sb.charAt(sb.length() - 1) < c) {
                sb.deleteCharAt(sb.length() - 1);
            }

            if (sb.length() < k) {
                sb.append(c);
            }
        }
        System.out.println(sb);
    }

    /**
     * 小船无数，载重w，n个人，每条船可以有2种方式
     * 2个人，载重为偶数
     * 1个人
     * 奇偶分组
     */
    private static void q3() {
        Scanner in = new Scanner(System.in);
        int t = Integer.parseInt(in.nextLine());
        for (int i = 0; i < t; i++) {
            String[] s1 = in.nextLine().split(" ");
            int n = Integer.parseInt(s1[0]);
            int w = Integer.parseInt(s1[1]);
            int[] nums = new int[n];
            String[] s = in.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                nums[j] = Integer.parseInt(s[j]);
            }

            Arrays.sort(nums);
            List<Integer> a = new ArrayList<>();
            List<Integer> b = new ArrayList<>();
            for (int num : nums) {
                if ((num & 1) == 0) {
                    a.add(num);
                } else {
                    b.add(num);
                }
            }
            int na = group(a, w);
            int nb = group(b, w);
            System.out.println(na + nb);
        }
    }

    private static int group(List<Integer> a, int w) {
        int n = a.size();
        int lo = 0, hi = n - 1;
        int res = 0;
        while (lo < hi) {
            if (a.get(lo) + a.get(hi) > w) {
                hi--;
            } else {
                lo++;
                hi--;
                res++;
            }
        }
        return n - res;
    }

    private static void q1() {
        Tencent tencent = new Tencent();
        ListNode n1 = new ListNode(0);
        ListNode n2 = new ListNode(1);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(11);
        ListNode n7 = new ListNode(6);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        ListNode[] solve = tencent.solve(5, n1);
        System.out.println(solve);
    }

    /**
     * n个魔法球，破坏魔法球可以获得能量x，并且给其他魔法球增加能量x
     */
    private static void q2() {
        Scanner in = new Scanner(System.in);

        int t = Integer.parseInt(in.nextLine());
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(in.nextLine());
            int[] nums = new int[n];
            String[] s = in.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                nums[j] = Integer.parseInt(s[j]);
            }
            Arrays.sort(nums);
            long sum = 0L;
            for (int j = n - 1; j >= 0; j--) {
                // 加上上一次累计值 到现在总共移除了多少就需要增加多少
                nums[j] = (int) ((nums[j] + sum) % MOD);
                sum = (sum + nums[j]) % MOD;
            }
            System.out.println(sum);
        }
    }

    /**
     * 链表分组
     */
    public ListNode[] solve(int m, ListNode a) {

        ListNode[] list = new ListNode[m];
        ListNode[] tmp = new ListNode[m];
        for (int i = 0; i < m; i++) {
            list[i] = new ListNode(0);
            tmp[i] = list[i];
        }

        while (a != null) {
            int v = a.val % m;
            ListNode t = tmp[v];
            t.next = a;
            a = a.next;
            t = t.next;
            t.next = null;
            tmp[v] = t;
        }
        for (int i = 0; i < m; i++) {
            list[i] = list[i].next;
        }
        return list;
    }
}
