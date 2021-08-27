package leetcode;

import java.util.*;

/**
 * 双指针法
 * 2020/6/16
 * @author comyxy
 */
public class TwoPointer {

    /**
     * LeetCode3 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度 双指针法
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * @param s 字符串
     * @return 最长无重复字符子串长度
     */
    public int lengthOfLongestSubstring(String s) {
        final int n = s.length();
        Set<Character> set = new HashSet<>();
        int left = 0, right = 0;
        int res = 0;
        while (left < n) {
            // move right
            while (right < n && !set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                right++;
            }
            res = Math.max(res, right - left);
            // move left
            set.remove(s.charAt(left++));
        }
        return res;
    }

    /**
     * https://leetcode-cn.com/problems/frequency-of-the-most-frequent-element/
     * 滑动窗口
     */
    public int maxFrequency(int[] nums, int k) {
        final int n = nums.length;
        Arrays.sort(nums);
        int total = 0;
        int l = 0, res = 1;
        for (int r = 1; r < n; r++) {
            total += (nums[r] - nums[r - 1]) * (r - l);
            while (total > k) {
                total -= (nums[r] - nums[l]);
                l++;
            }
            res = Math.max(res, r - l + 1);
        }
        return res;
    }

    /**
     * LeetCode11 盛最多水的容器 双指针法 数学证明
     * https://leetcode-cn.com/problems/container-with-most-water/solution/sheng-zui-duo-shui-de-rong-qi-by-leetcode-solution/
     * @param height 高度数组
     * @return 最大容量
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1, result = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            if (area > result) {
                result = area;
            }
            if (height[right] > height[left]) {
                left++;
            } else {
                right--;
            }
        }
        return result;
    }

    /**
     * 最小覆盖子串
     * https://leetcode-cn.com/problems/minimum-window-substring/
     * @param s 长串
     * @param t 子串
     */
    public String minWindow(String s, String t) {
        // 滑动窗口里的字符与对应出现次数
        Map<Character, Integer> inSlideWindows = new HashMap<>();

        Map<Character, Integer> needToCover = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            addOne(needToCover, t.charAt(i));
        }

        final int n = s.length();
        int l = 0, r = 0;
        int validCharacter = 0;
        int startIdx = 0, maxLen = Integer.MAX_VALUE;
        while (r < n) {
            char c = s.charAt(r);
            if(needToCover.containsKey(c)) {
                addOne(inSlideWindows, c);
                if(inSlideWindows.get(c).equals(needToCover.get(c))) {
                    validCharacter++;
                }
            }
            r++;

            while(validCharacter >= needToCover.size()) {
                if(r - l < maxLen) {
                    maxLen = r - l;
                    startIdx = l;
                }
                char rc = s.charAt(l);
                l++;
                if(needToCover.containsKey(rc)) {
                    if(inSlideWindows.get(rc).equals(needToCover.get(rc))) {
                        validCharacter--;
                    }
                    subtractOne(inSlideWindows, rc);
                }
            }
        }
        return maxLen != Integer.MAX_VALUE ? s.substring(startIdx, startIdx+maxLen) : "";
    }

    private void addOne(Map<Character, Integer> map, Character c) {
        map.put(c, map.getOrDefault(c, 0) + 1);
    }

    private void subtractOne(Map<Character, Integer> map, Character c) {
        map.put(c, map.getOrDefault(c, 0) - 1);
    }

    /**
     * 字符串的排列
     *
     * @param s 长字符串
     * @param t 短字符串
     * @return t字符串的排列之一是不是s第二个字符串的子串
     */
    public boolean checkInclusion(String s, String t) {
        // window 记录寻找过程中子串中字符出现次数
        Map<Character, Integer> window = new HashMap<>();

        // need 记录子串t中字符出现次数
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            addOne(need, t.charAt(i));
        }
        // 双指针[left,right)
        int left = 0, right = 0;
        // valid 记录符合条件的字符数
        int valid = 0;
        // right 滑动
        while (right < s.length()) {
            Character c = s.charAt(right);
            if (need.containsKey(c)) {
                addOne(window, c);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            right++;

            while (right - left >= t.length()) {
                // 范围大于t的长度
                if (valid == need.size()) {
                    // 字符串种类与数量都对得上
                    return true;
                }
                Character d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    subtractOne(window, d);
                }
            }
        }
        return false;
    }

    /**
     * 字符串的排列
     *
     * @param s 长字符串
     * @param t 短字符串
     * @return t字符串的排列之一是不是s第二个字符串的子串
     */
    public boolean checkInclusionV2(String s, String t) {
        final int ns = s.length(), nt = t.length();
        if(nt > ns) {
            return false;
        }
        int[] cnt = new int[26];
        for (int i = 0; i < nt; i++) {
            cnt[t.charAt(i)-'a']--;
        }
        int l = 0, r = 0;
        while(r < ns) {
            char c = s.charAt(r);
            r++;
            cnt[c-'a']++;
            while(cnt[c-'a'] > 0) {
                cnt[s.charAt(l)-'a']--;
                l++;
            }
            if(r-l == nt) {
                return true;
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

    public String reverseVowels(String s) {
        Set<Character> set = new HashSet<>(Set.of('a', 'e', 'i', 'o', 'u'));
        set.addAll(Set.of('A', 'E', 'I', 'O', 'U'));
        char[] cs = s.toCharArray();
        int i = 0, j = cs.length - 1;
        while(i < j) {
            while(i < j && !set.contains(cs[i])) {
                i++;
            }
            while(i < j && !set.contains(cs[j])) {
                j--;
            }
            if (i >= j) {
                break;
            }
            char t = cs[i];
            cs[i] = cs[j];
            cs[j] = t;
            i++;
            j--;
        }
        return String.valueOf(cs);
    }
}
