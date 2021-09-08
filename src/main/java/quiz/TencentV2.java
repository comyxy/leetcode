package quiz;

import struct.ListNode;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @since 2021/9/5 19:29
 */
public class TencentV2 {
    public static void main(String[] args) {
//        q2();
//        q3();
        q4();
    }

    private static void q4() {
        Scanner in = new Scanner(System.in);
        String[] cs = in.nextLine().trim().split(" ");
        int n = Integer.parseInt(cs[0]);
        int l = Integer.parseInt(cs[1]);
        int r = Integer.parseInt(cs[2]);
        int res = countOne(n, l, r);
        System.out.println(res);
    }

    private static int countOne(int n, int l, int r) {
        int i = 1, count = 1;
        StringBuilder sb = new StringBuilder();
        while (n > 1) {
            int t = n % 2;
            sb.append(t);
            count += i * 2;
            i *= 2;
            n = n >> 1;
        }
        sb.append(n);
        int j = sb.length() - 1;
        char[] cs = new char[count];
        cs[0] = sb.charAt(j);
        int k = 1;
        while (i > 1) {
            cs[k] = sb.charAt(--j);
            System.arraycopy(cs, 0, cs, k + 1, k);
            k += k;
            k++;
            i = i >> 1;
        }
        int res = 0;
        for (int x = l; x <= r; x++) {
            if(cs[x - 1] == '1') {
                res++;
            }
        }
        return res;
    }

    private static void q3() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine().trim());
        String str = in.nextLine().trim();
        int res = maxValue(str, n);
        System.out.println(res);
    }

    private static int maxValue(String str, int n) {
        int[] dp = new int[n + 1];
        int[] val = new int[n + 1];
        dp[1] = 1;
        val[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = i - 1; j >= 1; j--) {
                val[i] = Math.max(val[i], str.charAt(i - 1) != str.charAt(j - 1) ? 1 : val[j] + 1);
                dp[i] = Math.max(dp[i], dp[j] + (str.charAt(i - 1) != str.charAt(j - 1) ? 1 : val[j] + 1));
            }
        }
        return dp[n];
    }

    private static void q2() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine().trim());
        int[] a = new int[n];
        int[] b = new int[n];
        String[] cs = in.nextLine().trim().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(cs[i]);
        }
        cs = in.nextLine().trim().split(" ");
        for (int i = 0; i < n; i++) {
            b[i] = Integer.parseInt(cs[i]);
        }
        int res = maxWinRound(a, b);
        System.out.println(res);
    }

    private static int maxWinRound(int[] a, int[] b) {
        int[] cache = new int[100001];
        for (int i = 1; i <= 100000; i++) {
            for (int j = 1; j * j <= i; j++) {
                if (i % j == 0) {
                    cache[i]++;
                    if (i / j != j) {
                        cache[i]++;
                    }
                }
            }
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            a[i] = cache[a[i]];
            b[i] = cache[b[i]];
        }
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0, j = 0;
        int res = 0;
        while (j < n) {
            int vb = b[j];
            while (i < n && a[i] <= vb) {
                i++;
            }
            if (i >= n) {
                break;
            }
            res++;
            i++;
            j++;
        }
        return res;
    }

    public ListNode q1(ListNode[] a) {
        // write code here
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (true) {
            boolean end = true;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == null) {
                    continue;
                }
                cur.next = a[i];
                a[i] = a[i].next;
                end = false;
                cur = cur.next;
            }
            if (end) break;
        }
        return dummy.next;
    }
}
