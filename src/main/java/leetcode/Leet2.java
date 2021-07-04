package leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 2021/2/27
 *
 * @author comyxy
 */
public class Leet2 {
    public int longestSubstring(String s, int k) {
        final int n = s.length();
        return longestSubstringHelper(s, 0, n - 1, k);
    }

    private int longestSubstringHelper(String s, int left, int right, int k) {
        int[] cnt = new int[26];
        for (int i = left; i <= right; i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        char split = 0;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] > 0 && cnt[i] < k) {
                split = (char) ('a' + i);
                break;
            }
        }
        if (split == 0) {
            return right - left + 1;
        }
        // 找到split字符对应的位置
        int i = left, res = 0;
        while (i <= right) {
            while (i <= right && s.charAt(i) == split) {
                i++;
            }
            if (i > right) {
                break;
            }
            int start = i;
            while (i <= right && s.charAt(i) != split) {
                i++;
            }
            int length = longestSubstringHelper(s, start, i - 1, k);
            res = Math.max(res, length);
        }
        return res;
    }

    public String mergeAlternately(String word1, String word2) {
        int n1 = word1.length(), n2 = word2.length();
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0, k = 0;
        for (; i < n1 && j < n2; k++) {
            if ((k & 1) == 0) {
                sb.append(word1.charAt(i++));
            } else {
                sb.append(word2.charAt(j++));
            }
        }
        if (i == n1 && j < n2) {
            for (; j < n2; ) {
                sb.append(word2.charAt(j++));
            }
        } else if (i < n1 && j == n2) {
            for (; i < n1; ) {
                sb.append(word1.charAt(i++));
            }
        }
        return sb.toString();
    }

    public int[] minOperations(String boxes) {
        int n = boxes.length();
        int[] res = new int[n];
        // 计算索引0时操作数
        for (int i = 0; i < n; i++) {
            if (boxes.charAt(i) == '1') {
                res[0] += i;
            }
        }
        // 前序和与后序和
        int[] pre = new int[n], post = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                pre[i] = boxes.charAt(i) == '1' ? 1 : 0;
            } else {
                pre[i] = pre[i - 1] + (boxes.charAt(i) == '1' ? 1 : 0);
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1) {
                post[i] = boxes.charAt(i) == '1' ? 1 : 0;
            } else {
                post[i] += post[i + 1] + (boxes.charAt(i) == '1' ? 1 : 0);
            }
        }
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] + pre[i - 1] - post[i];
        }
        return res;
    }

    public int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length, m = multipliers.length;
        int[][] dp = new int[m + 1][m + 1];
        for (int k = 1; k <= m; k++) {
            for (int i = 0; i <= k; i++) {
                if (i == 0) {
                    // 全部从尾部拿
                    dp[i][k - i] = dp[i][k - i - 1] + multipliers[k - 1] * nums[n - (k - i)];
                } else if (i == k) {
                    // 全部从头部拿
                    dp[i][k - i] = dp[i - 1][k - i] + multipliers[k - 1] * nums[i - 1];
                } else {
                    dp[i][k - i] = Math.max(dp[i][k - i - 1] + multipliers[k - 1] * nums[n - (k - i)],
                            dp[i - 1][k - i] + multipliers[k - 1] * nums[i - 1]);
                }
            }
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i <= m; i++) {
            res = Math.max(res, dp[i][m - i]);
        }
        return res;
    }

    public int longestPalindrome(String word1, String word2) {
        // 1 翻转word2 并拼接两个字符串
        int n1 = word1.length(), n2 = word2.length();
        int n = n1 + n2;
        char[] cs = new char[n];
        for (int k = 0; k < n; k++) {
            if (k < n1) {
                cs[k] = word1.charAt(k);
            } else {
                cs[k] = word2.charAt(k - n1);
            }
        }
        // 最长回文子序列变形
        int[][] dp = new int[n][n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (cs[i] == cs[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                    // 从第一第二个字符串中都取了值
                    if (i < n1 && j >= n1) {
                        res = Math.max(res, dp[i][j]);
                    }
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }
        return res;
    }

    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        int res = 0;
        for (List<String> item : items) {
            if ("type".equals(ruleKey) && ruleValue.equals(item.get(0))) {
                res++;
            } else if ("color".equals(ruleKey) && ruleValue.equals(item.get(1))) {
                res++;
            } else if ("name".equals(ruleKey) && ruleValue.equals(item.get(2))) {
                res++;
            }
        }
        return res;
    }

    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        for (int base : baseCosts) {
            closestCostHelper(0, base, toppingCosts, target);
        }
        return closestCostRes;
    }

    private int closestCostRes = Integer.MAX_VALUE;

    private void closestCostHelper(int i, int cost, int[] toppingCosts, int target) {
        if (Math.abs(target - closestCostRes) > Math.abs(target - cost)) {
            closestCostRes = cost;
        }
        if (Math.abs(target - closestCostRes) == Math.abs(target - cost) && cost < closestCostRes) {
            closestCostRes = cost;
        }
        if (cost >= target) {
            return;
        }
        for (int j = i; j < toppingCosts.length; j++) {
            closestCostHelper(j + 1, cost + toppingCosts[j], toppingCosts, target);
            closestCostHelper(j + 1, cost + 2 * toppingCosts[j], toppingCosts, target);
        }
    }

    public int closestCostV2(int[] baseCosts, int[] toppingCosts, int target) {
        // 转化为01背包问题
        boolean[] bags = new boolean[20001];
        for (int base : baseCosts) {
            bags[base] = true;
        }
        for (int topping : toppingCosts) {
            for (int i = 1; i <= 20000; i++) {
                bags[i] = bags[i - topping] || bags[i];
            }
        }
        for (int topping : toppingCosts) {
            for (int i = 1; i <= 20000; i++) {
                bags[i] = bags[i - topping] || bags[i];
            }
        }
        int res = 0, diff = Integer.MAX_VALUE;
        for (int i = 1; i <= 20000; i++) {
            if (bags[i] && Math.abs(target - i) < diff) {
                res = i;
                diff = Math.abs(target - i);
            }
        }
        return res;
    }

    public int minOperations(int[] nums1, int[] nums2) {
        int sum1 = Arrays.stream(nums1).sum();
        int sum2 = Arrays.stream(nums2).sum();
        if (sum1 == sum2) {
            return 0;
        }
        if (sum1 > sum2) {
            return minOperations(nums2, nums1);
        }
        // sum1 < sum2
        int diff = sum2 - sum1;
        int[] freq = new int[6];
        for (int i : nums1) {
            freq[6 - i]++;
        }
        for (int i : nums2) {
            freq[i - 1]++;
        }
        int res = 0;
        for (int i = 5; i >= 1 && diff > 0; i--) {
            for (; freq[i] > 0 && diff > 0; ) {
                res++;
                freq[i]--;
                diff -= i;
            }
        }
        return diff <= 0 ? res : -1;
    }

    public int calculate(String s) {
        char[] cs = s.toCharArray();
        Deque<Character> queue = new LinkedList<>();
        for(char c : cs) {
            queue.offer(c);
        }
        return calculateHelper(queue);
    }

    private int calculateHelper(Deque<Character> queue) {
        Deque<Integer> st = new LinkedList<>();
        char op = '+';
        int num = 0;
        while(!queue.isEmpty()) {
            char c = queue.pop();
            if(c <= '9' && c >= '0') {
                num = num * 10 + c - '0';
            }

            if(c == '(') {
                num = calculateHelper(queue);
            }

            if(c == '+' || c == '-' || c == '*' || c == '/' || queue.isEmpty() || c == ')') {
                if(op == '+') {
                    st.push(num);
                }else if(op == '-') {
                    st.push(-num);
                }else if(op == '*') {
                    int t = st.pop();
                    st.push(t * num);
                }else if(op == '/') {
                    int t = st.pop();
                    st.push(t / num);
                }
                // reset
                op = c;
                num = 0;
            }

            if(c == ')') {
                break;
            }
        }
        int res = 0;
        while(!st.isEmpty()) {
            res += st.pop();
        }
        return res;
    }
}
