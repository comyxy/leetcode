package leetcode.math;


/**
 * @date 2020/7/7
 */
public class Algebra {

    /**
     * LeetCode172
     * 给定一个整数 n，返回 n! 结果尾数中零的数量
     * 2 和 5 的因子
     * @param n 整数
     * @return
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while(n > 0){
            n /= 5;
            count += n;
        }
        return count;
    }

    /**
     * LeetCode16.11 跳水板
     * @param shorter
     * @param longer
     * @param k
     * @return
     */
    public int[] divingBoard(int shorter, int longer, int k) {
        if(k == 0){
            return new int[0];
        }
        if(shorter == longer){
            return new int[]{shorter * k};
        }

        int[] res = new int[k + 1];
        for (int i = 0; i <= k; i++) {
            res[i] = shorter * (k - i) + longer * i;
        }
        return res;
    }
}
