package str;

import java.util.Arrays;

/**
 * @author comyxy
 */
public class StrStr {
    /**
     * Sunday
     *
     * @param haystack string
     * @param pattern  pattern
     * @return index of the pattern in string
     */
    public int sunday(String haystack, String pattern) {
        int n = pattern.length();
        if (n == 0) {
            return 0;
        }
        int m = haystack.length();
        // 建立偏移表
        int[] offset = new int[26];
        Arrays.fill(offset, n + 1);
        for (int i = 0; i < n; i++) {
            offset[pattern.charAt(i) - 'a'] = n - i;
        }

        // sunday
        int idx = 0;
        while (idx + n <= m) {
            String s = haystack.substring(idx, idx + n);
            if (pattern.equals(s)) {
                return idx;
            }
            if (idx + n >= m) {
                return -1;
            }
            char nc = haystack.charAt(idx + n);
            idx += offset[nc - 'a'];
        }
        return -1;
    }
}
