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
}
