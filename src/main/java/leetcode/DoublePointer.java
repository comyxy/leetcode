package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2020/6/16
 */
public class DoublePointer {
    /**
     * 最小覆盖子串
     *
     * @param s 长串
     * @param t 子串
     * @return
     */
    public String minWindow(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        // window 记录寻找过程中子串中字符出现次数
        Map<Character, Integer> window = new HashMap<>();
        // need 记录子串t中字符出现次数
        Map<Character, Integer> need = new HashMap<>();
        for (Character c : ct) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        // 双指针[left,right)
        int left = 0, right = 0;
        // s中最小覆盖子串起始位置start 长度len
        int start = 0, len = Integer.MAX_VALUE;
        // valid 记录符合条件的字符数
        int valid = 0;
        // right 滑动
        while (right < cs.length) {
            Character c = cs[right];
            right++;
            // 该字符c在t中
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c)
                        .equals(need.get(c))) {
                    valid++;
                }
            }

//            System.out.print(valid + "\t");
//            System.out.println(left + "," + right);

            // left 滑动
            while (valid == need.size()) {
                // 更新寻找位置
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                Character d = cs[left];
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d)
                            .equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    /**
     * 字符串的排列
     *
     * @param s 长字符串
     * @param t 短字符串
     * @return t字符串的排列之一是不是s第二个字符串的子串
     */
    public boolean checkInclusion(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        // window 记录寻找过程中子串中字符出现次数
        Map<Character, Integer> window = new HashMap<>();
        // need 记录子串t中字符出现次数
        Map<Character, Integer> need = new HashMap<>();
        for (Character c : ct) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        // 双指针[left,right)
        int left = 0, right = 0;
        // valid 记录符合条件的字符数
        int valid = 0;
        // right 滑动
        while (right < cs.length) {
            Character c = cs[right];
            right++;
            // 该字符c在s1中
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c)
                        .equals(need.get(c))) {
                    valid++;
                }
            }

//            System.out.print(valid + "\t");
//            System.out.println(left + "," + right);

            // left 滑动
            while (right - left >= t.length()) {
                // 字符串种类与数量都对得上
                if (valid == need.size()) {
                    return true;
                }
                Character d = cs[left];
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d)
                            .equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return false;
    }

    /**
     * 找到所有字母异位词
     *
     * @param s 长字符串
     * @param t 短字符串
     * @return 异位词起始索引
     */
    public List<Integer> findAnagrams(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        // window 记录寻找过程中子串中字符出现次数
        Map<Character, Integer> window = new HashMap<>();
        // need 记录子串t中字符出现次数
        Map<Character, Integer> need = new HashMap<>();
        for (Character c : ct) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        // 记录异位词索引
        List<Integer> result = new ArrayList<>();
        // 双指针[left,right)
        int left = 0, right = 0;
        // valid 记录符合条件的字符数
        int valid = 0;
        // right 滑动
        while (right < cs.length) {
            Character c = cs[right];
            right++;
            // 该字符c在s1中
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c)
                        .equals(need.get(c))) {
                    valid++;
                }
            }

//            System.out.print(valid + "\t");
//            System.out.println(left + "," + right);

            // left 滑动
            while (right - left >= t.length()) {
                // 字符串种类与数量都对得上
                if (valid == need.size()) {
                    result.add(left);
                }
                Character d = cs[left];
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d)
                            .equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return result;
    }

    /**
     * 最长无重复子串
     *
     * @param s
     * @return 最长无重复子串长度
     */
    public int lengthOfLongestSubstring(String s) {
        char[] cs = s.toCharArray();
        // window 记录寻找过程中子串中字符出现次数
        Map<Character, Integer> window = new HashMap<>();
        // 双指针[left,right)
        int left = 0, right = 0;
        // result
        int result = 0;
        // right 滑动
        while (right < cs.length) {
            Character c = cs[right];
            right++;
            // 该字符c在s1中
            window.put(c, window.getOrDefault(c, 0) + 1);

//            System.out.print(valid + "\t");
//            System.out.println(left + "," + right);

            // left 滑动
            while (window.get(c) > 1) {
                Character d = cs[left];
                left++;
                window.put(d, window.get(d) - 1);
            }
            result = Math.max(result, right - left);
        }
        return result;

    }
}
