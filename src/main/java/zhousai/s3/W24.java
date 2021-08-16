package zhousai.s3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @date 2021/7/18 13:
 * https://leetcode-cn.com/contest/weekly-contest-250/
 */
public class W24 {
    public int canBeTypedWords(String text, String brokenLetters) {
        Set<Character> brokenLettersSet = new HashSet<>();
        for (int i = 0; i < brokenLetters.length(); i++) {
            brokenLettersSet.add(brokenLetters.charAt(i));
        }
        int res = 0;
        String[] splitText = text.split(" ");
        for (String word : splitText) {
            boolean canType = true;
            for (int i = 0; i < word.length(); i++) {
                if (brokenLettersSet.contains(word.charAt(i))) {
                    canType = false;
                    break;
                }
            }
            if (canType) {
                res++;
            }
        }
        return res;
    }

    public int addRungs(int[] rungs, int dist) {
        int res = 0;
        for (int i = 0; i < rungs.length; i++) {
            if (i == 0) {
                res += (rungs[i] - 1) / dist;
            } else {
                res += (rungs[i] - rungs[i - 1] - 1) / dist;
            }
        }
        return res;
    }

    public long maxPoints(int[][] points) {
        final int m = points.length;
        final int n = points[0].length;
        long[][] dp = new long[m][n];
        long[][] left = new long[m][n];
        long[][] right = new long[m][n];
        for (int i = 0; i < m; i++) {
            if (i == 0) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = points[i][j];
                }
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    left[i][j] = dp[i - 1][j];
                    continue;
                }
                left[i][j] = Math.max(left[i][j - 1], dp[i - 1][j] + j);
            }
            for (int j = n - 1; j >= 0; j--) {
                if (j == n - 1) {
                    right[i][j] = dp[i - 1][j] - (n - 1);
                    continue;
                }
                right[i][j] = Math.max(right[i][j + 1], dp[i - 1][j] - j);
            }
            for (int j = 0; j < n; j++) {
                dp[i][j] = Math.max(left[i][j] - j, right[i][j] + j) + points[i][j];
            }
        }
        return Arrays.stream(dp[m - 1]).max().orElse(-1);
    }
}
