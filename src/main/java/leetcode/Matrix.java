package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * @author comyxy
 */
public class Matrix {
    /**
     * 363 矩形区域不超过 K 的最大数值和
     */
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int res = Integer.MIN_VALUE;
        int m = matrix.length, n = matrix[0].length;
        for (int l = 0; l < m; l++) {
            int[] sum = new int[n];
            for (int r = l; r < m; r++) {
                // [l, r)
                for (int col = 0; col < n; col++) {
                    sum[col] += matrix[r][col];
                }
                TreeSet<Integer> set = new TreeSet<>();
                int val = 0;
                set.add(0);
                for (int s : sum) {
                    val += s;
                    Integer ceiling = set.ceiling(val - k);
                    if(ceiling != null) {
                        res = Math.max(res, val - ceiling);
                    }
                    set.add(val);
                }
            }
        }
        return res;
    }
}
