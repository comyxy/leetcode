package math;


import java.util.Objects;
import java.util.PriorityQueue;

/**
 * @author comyxy
 * @date 2020/7/7
 */
public class Algebra {

    /**
     * 172
     * 给定一个整数 n，返回 n! 结果尾数中零的数量
     * 2 和 5 的因子
     *
     * @param n 整数
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    /**
     * 16.11 跳水板
     */
    public int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0) {
            return new int[0];
        }
        if (shorter == longer) {
            return new int[]{shorter * k};
        }

        int[] res = new int[k + 1];
        for (int i = 0; i <= k; i++) {
            res[i] = shorter * (k - i) + longer * i;
        }
        return res;
    }

    /**
     * 数学极值方法
     * Code343
     */
    public int integerBreak3(int n) {
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        int q = n / 3, m = n % 3;
        if (m == 0) {
            return (int) Math.pow(3, q);
        } else if (m == 1) {
            return (int) Math.pow(3, q - 1) * 4;
        } else {
            return (int) Math.pow(3, q) * 2;
        }
    }

    /**
     * 罗马数字到整数
     *
     * @param s 罗马字符
     */
    public int romanToInt(String s) {
        assert s != null && s.length() != 0;
        int sum = 0;
        int prev = romanCharacterToDigit(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = romanCharacterToDigit(s.charAt(i));
            if (prev >= cur) {
                sum += prev;
            } else {
                sum -= prev;
            }
            prev = cur;
        }
        // 加上最后一个数
        sum += prev;
        return sum;
    }

    private int romanCharacterToDigit(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        // 每一个可能的值
        for (int i = 0; i < VALUES.length && num >= 0; i++) {
            // 贪心
            while (VALUES[i] <= num) {
                num -= VALUES[i];
                sb.append(SYMBOLS[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 633
     */
    public boolean judgeSquareSum(int c) {
        int hi = (int) (Math.sqrt(c));
        int lo = 0;
        while (lo <= hi) {
            int t = lo * lo + hi * hi;
            if (t > c) {
                hi -= 1;
            } else if (t < c) {
                lo += 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * https://leetcode-cn.com/problems/n-th-tribonacci-number/solution/di-n-ge-tai-bo-na-qi-shu-by-leetcode-sol-kn16/
     */
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }
        int[][] q = {{1, 1, 1}, {1, 0, 0}, {0, 1, 0}};
        int[][] res = pow(q, n);
        return res[0][2];
    }

    private int[][] pow(int[][] a, int x) {
        final int m = a.length, n = a[0].length;
        assert m == n;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            res[i][i] = 1;
        }
        while (x > 0) {
            if ((x & 1) == 1) {
                res = multiply(res, a);
            }
            x >>= 1;
            a = multiply(a, a);
        }
        return res;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        final int na = a[0].length, mb = b.length;
        assert na == mb;
        final int ma = a.length, nb = b[0].length;
        int[][] res = new int[ma][nb];
        for (int i = 0; i < ma; i++) {
            for (int j = 0; j < nb; j++) {
                for (int k = 0; k < na; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode-cn.com/problems/super-ugly-number/
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        long res = 1L;
        for (int i = 0; i < n; i++) {
            for (int prime : primes) {
                pq.offer(res * prime);
            }
            res = pq.poll();
            while (!pq.isEmpty() && pq.peek() == res) {
                pq.poll();
            }
        }
        return (int) res;
    }
}
