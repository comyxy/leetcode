package quiz;

import java.util.Scanner;

/**
 * @since 2021/9/1 18:48
 */
public class Xiaomi {
    public static void main(String[] args) {
//        q1();
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int res = josphy(n);
        System.out.println(res);
    }

    private static int josphy(int n) {
        int res = 0;
        for (int i = 2; i <= n; i++) {
            res = (res + 3) % i;
        }
        return res + 1;
    }

    private static void q1() {
        Scanner in = new Scanner(System.in);
        String[] cs = in.nextLine().split(",");
        int m = Integer.parseInt(cs[0].substring(2));
        int n = Integer.parseInt(cs[1].substring(2));
        cs = in.nextLine().split(",");
        int[] nums1 = new int[m];
        for (int i = 0; i < m; i++) {
            nums1[i] = Integer.parseInt(cs[i]);
        }
        cs = in.nextLine().split(",");
        int[] nums2 = new int[n];
        for (int i = 0; i < n; i++) {
            nums2[i] = Integer.parseInt(cs[i]);
        }
        int[] res = merge(nums1, nums2);
        for(int r:res) {
            System.out.print(r + " ");
        }
    }

    private static int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] res = new int[m+n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                res[k] = nums1[i];
                i++;
            } else {
                res[k] = nums2[j];
                j++;
            }
            k++;
        }
        while (i < m) {
            res[k++] = nums1[i++];
        }
        while (j < n) {
            res[k++] = nums2[j++];
        }
        return res;
    }
}
