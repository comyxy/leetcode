package str

/**
 * @since 2021/8/4 22:52
 */
class Sunday {
    fun sunday(haystack: String, pattern: String): Int {
        val n = pattern.length
        if (n == 0) {
            return 0;
        }
        val m = haystack.length
        // haystack中字符不在pattern中 右移m+1
        val offset = IntArray(26) { _ -> n + 1 }
        // haystack中字符在pattern中 右移(n-i)
        for (i in 0 until n) {
            offset[pattern[i] - 'a'] = n - i
        }
        //
        var idx = 0
        while (idx <= m - n) {
            val sub = haystack.substring(idx, idx + n)
            if (sub == pattern) {
                return idx
            }
            if (idx + n >= m) {
                return -1
            }
            val c = haystack[idx + n]
            idx += offset[c - 'a']
        }
        return -1
    }
}