package str;

import java.util.Arrays;

/**
 * @author comyxy
 */
public class Str {
    /**
     * Sunday
     * @param haystack string
     * @param needle pattern
     * @return index of the pattern in string
     */
    public int strStr(String haystack, String needle) {
        int n = needle.length();
        if(n==0) {
            return 0;
        }
        int m = haystack.length();
        // 建立偏移表
        int[] offset = new int[26];
        Arrays.fill(offset, n+1);
        for(int i=0;i<n;i++) {
            offset[needle.charAt(i)-'a'] = n-i;
        }

        // sunday
        int idx = 0;
        while(idx+n <= m) {
            String s = haystack.substring(idx, idx+n);
            if(needle.equals(s)) {
                return idx;
            }
            if(idx+n >= m) {
                return -1;
            }
            char nc = haystack.charAt(idx+n);
            idx += offset[nc-'a'];
        }
        return -1;
    }
}
