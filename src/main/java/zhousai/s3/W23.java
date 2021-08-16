package zhousai.s3;

/**
 * @date 2021/7/11 10:31
 */
public class W23 {
    public int[] getConcatenation(int[] nums) {
        final int n = nums.length;
        int[] res = new int[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            res[i] = nums[i % n];
        }
        return res;
    }

    public int countPalindromicSubsequence(String s) {
        char[] cs = new char[3];
        int res = 0;
        for (int i = 0; i < 26; i++) {
            cs[0] = cs[2] = (char) ('a' + i);
            for (int j = 0; j < 26; j++) {
                cs[1] = (char) ('a' + j);
                if (isSubsequence(s, cs)) {
                    res++;
                }
            }
        }
        return res;
    }

    private boolean isSubsequence(String s, char[] cs) {
        int i = 0, j = 0;
        while (i < cs.length && j < s.length()) {
            if (cs[i] == s.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == cs.length;
    }
}
