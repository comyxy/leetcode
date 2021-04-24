package math;


/**
 * @author comyxy
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

    /**
     * 数学极值方法
     * LeetCode343
     * @param n
     * @return
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
     * @param s 罗马字符
     * @return
     */
    public int romanToInt(String s) {
        assert s != null && s.length() != 0;
        int sum = 0;
        int prev = romanCharacterToDigit(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = romanCharacterToDigit(s.charAt(i));
            if(prev >= cur){
                sum += prev;
            }else{
                sum -= prev;
            }
            prev = cur;
        }
        // 加上最后一个数
        sum += prev;
        return sum;
    }

    private int romanCharacterToDigit(char c){
        switch (c){
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

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
}
