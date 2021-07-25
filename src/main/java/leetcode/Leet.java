package leetcode;

import java.io.*;
import java.util.*;

/**
 * 2020/5/4
 */
public class Leet {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return fourSumCore(nums, 0, nums.length - 1, target);
    }

    private List<List<Integer>> fourSumCore(int[] nums, int start, int end, int target) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            // [start, end]
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> pairs = threeSumCore(nums, i + 1, end, target - nums[i]);
            for (List<Integer> pari : pairs) {
                pari.add(nums[i]);
                res.add(pari);
            }
        }
        return res;
    }

    private List<List<Integer>> threeSumCore(int[] nums, int start, int end, int target) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            // [start, end]
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> paris = twoSumCore(nums, i + 1, end, target - nums[i]);
            for (List<Integer> pari : paris) {
                pari.add(nums[i]);
                res.add(pari);
            }
        }
        return res;
    }

    private List<List<Integer>> twoSumCore(int[] nums, int start, int end, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int l = start, r = end;
        while (l < r) {
            int lv = nums[l], rv = nums[r];
            // 二分
            if (lv + rv > target) {
                while (nums[r] == rv && l < r) {
                    // skip same nums[r]
                    r--;
                }
            } else if (lv + rv < target) {
                while (nums[l] == lv && l < r) {
                    // skip same nums[l]
                    l++;
                }
            } else {
                res.add(new ArrayList<>(Arrays.asList(nums[l], nums[r])));
                while (nums[r] == rv && l < r) {
                    // skip same nums[r]
                    r--;
                }
                while (nums[l] == lv && l < r) {
                    // skip same nums[l]
                    l++;
                }
            }
        }
        return res;
    }

    /**
     * 寻找两个正序数组的中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        final int M = nums1.length, N = nums2.length;
        // 偶数时代表中位数左侧与右侧 奇数时right代表中位数
        // 方法1 O(m+n)
    /*        int left = -1, right = -1;
    int m = 0, n = 0;
    for (int i = 0; i <= (M + N) / 2; i++) {
        left = right;
        if(m < M && n <N){
            if(nums1[m] <nums2[n]){
                right = nums1[m++];
            }else {
                right = nums2[n++];
            }
        }else if(m == M){
            right = nums2[n++];
        }else if(n == N){
            right = nums1[m++];
        }
    }
    if ((M+N & 1) == 0){
        return (left+right)/2.0f;
    }else {
        return right;
    }*/
        // 方法2
        final int K = M + N;
        double median;
        if ((K & 1) == 0) {
            median = (getKth(nums1, nums2, K / 2) + getKth(nums1, nums2, K / 2 + 1)) / 2.0f;
        } else {
            median = getKth(nums1, nums2, K / 2 + 1);
        }
        return median;
    }

    /**
     * 在两个排序数组中找到第k小的元素(k=1,...) nums1[k/2-1]前面有0,1,...,k/2-2共k/2-1个元素
     * nums2[k/2-1]前面有0,1,...,k/2-2共k/2-1个元素 当nums1[k/2-1]<nums2[k/2-1]时
     * 比nums1[k/2-1]小的最多只用(k/2-1)+(k/2-1)=k-2个 nums1[k/2-1]不可能是第k小的元素
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    private static int getKth(int[] nums1, int[] nums2, int k) {
        final int M = nums1.length, N = nums2.length;
        int m = 0, n = 0;
        while (true) {
            if (m == M) {
                return nums2[k + n - 1];
            }
            if (n == N) {
                return nums1[k + m - 1];
            }
            if (k == 1) {
                return Math.min(nums1[m], nums2[n]);
            }

            int half = k / 2 - 1;
            int tm = Math.min(m + half, M - 1);
            int tn = Math.min(n + half, N - 1);
            int pm = nums1[tm], pn = nums2[tn];
            if (pm < pn) {
                k -= (tm - m + 1);
                m = tm + 1;
            } else {
                k -= (tn - n + 1);
                n = tn + 1;
            }
        }
    }

    /**
     * 最长回文子串
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        final char[] cs = s.toCharArray();
        final int length = cs.length;
        // dp[i][j]代表索引i到j [i,j] 的子串是否为回文串
        boolean[][] dp = new boolean[length][length];
        String res = "";
        // l 子字符串长度-1
        for (int l = 0; l < length; l++) {
            for (int i = 0; i + l < length; i++) {
                int j = i + l;
                if (l == 0) {
                    // 一个字符
                    dp[i][j] = true;
                } else if (l == 1) {
                    // 两个字符
                    dp[i][j] = (cs[i] == cs[j]);
                } else {
                    // 大于两个字符 状态递推
                    dp[i][j] = dp[i + 1][j - 1] && (cs[i] == cs[j]);
                }

                if (dp[i][j] && l + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    /**
     * 字符串转化为整数
     *
     * @param str
     * @return
     */
    public static int atoi(String str) {
        final char[] cs = str.toCharArray();
        int sum = 0;
        boolean start = false;
        boolean positive = true;
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (!start) {
                if (Character.isWhitespace(c)) continue;
                else if (c == '-') {
                    start = true;
                    positive = false;
                } else if (c == '+') {
                    start = true;
                    positive = true;
                } else if (Character.isDigit(c)) {
                    start = true;
                    positive = true;
                    sum = c - '0';
                } else {
                    break;
                }
            } else {
                if (Character.isDigit(c)) {
                    int t = c - '0';
                    if (isOutRange(sum, t, positive)) {
                        return positive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                    } else {
                        sum = 10 * sum + t;
                    }
                } else {
                    break;
                }
            }
        }
        return positive ? sum : -sum;
    }

    private static boolean isOutRange(int t, int c, boolean positive) {
        if (positive && t > (Integer.MAX_VALUE - c) / 10) {
            return true;
        }
        if (!positive
                && (t > (Integer.MAX_VALUE - c) / 10 || (c > 0 && t > (Integer.MAX_VALUE - c + 1) / 10))) {
            return true;
        }
        return false;
    }

    /**
     * 判断括号对是否匹配
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if ((s.length() & 1) == 1) {
            return false;
        }
        Map<Character, Character> map = new HashMap<>(3);
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Deque<Character> st = new LinkedList<>();
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            Character t, c = cs[i];
            if ((t = st.peek()) == null) {
                st.push(c);
            } else if (map.containsKey(c)) {
                if (t.equals(map.get(c))) {
                    st.poll();
                } else {
                    return false;
                }
            } else {
                st.push(c);
            }
        }
        return st.isEmpty();
    }

    /**
     * 字符串相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        final int l1 = num1.length(), l2 = num2.length();
        int[] res = new int[l1 + l2];
        for (int i = l1 - 1; i >= 0; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = l2 - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';
                int sum = n1 * n2 + res[i + j + 1];
                res[i + j + 1] = sum % 10;
                // 进位
                res[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < res.length; k++) {
            if (k == 0 && res[k] == 0) {
                continue;
            }
            sb.append(res[k]);
        }
        return sb.toString();
    }

    /**
     * 括号生成
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        generateParenthesisHelper(sb, n, n, result);
        return result;
    }

    /**
     * @param sb
     * @param left   剩余左括号数
     * @param right  剩余右括号数
     * @param result
     */
    private static void generateParenthesisHelper(
            StringBuilder sb, int left, int right, List<String> result) {
        // 添加到result
        if (left == 0 && right == 0) {
            result.add(sb.toString());
            return;
        }
        // 剪枝
        if (left > right) {
            return;
        }
        // 左括号还剩
        if (left > 0) {
            sb.append('(');
            generateParenthesisHelper(sb, left - 1, right, result);
            sb.deleteCharAt(sb.length() - 1);
        }
        // 右括号还剩
        if (right > 0) {
            sb.append(')');
            generateParenthesisHelper(sb, left, right - 1, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

  /*
  public static List<String> generateParenthesis(int n) {
      List<String> result = new ArrayList<>();
      int left = 0, right = 0;
      dfs(result, "", left, right, n);
      return result;
  }

  private static void dfs(List<String> result, String path, int left, int right, int n) {
      if (right > left || left > n || right > n) {
          return;
      }
      if (left == right && right == n) {
          result.add(path);
          return;
      }
      dfs(result, path + "(", left + 1, right, n);
      dfs(result, path + ")", left, right + 1, n);
  }
  */

    /**
     * 子集（幂集） 回溯 深度优先dfs
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        subsetsHelper(result, list, nums, 0);
        return result;
    }

    private static void subsetsHelper(
            List<List<Integer>> result, List<Integer> list, int[] nums, int start) {
        result.add(new ArrayList<>(list));
        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);
            subsetsHelper(result, list, nums, i + 1);
            list.remove(list.size() - 1);
        }
    }

    /**
     * 全排列
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(result, nums, 0);
        return result;
    }

    private static void permuteHelper(List<List<Integer>> result, int[] nums, int start) {
        if (start == nums.length) {
            List<Integer> list = new ArrayList<>(nums.length);
            for (Integer num : nums) {
                list.add(num);
            }
            result.add(list);
        }
        // 遍历交换
        for (int i = start; i < nums.length; i++) {
            swapInt(nums, i, start);
            permuteHelper(result, nums, start + 1);
            swapInt(nums, i, start);
        }
    }

    private static void swapInt(int[] nums, int i, int j) {
        if (i == j) return;
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 格雷码 动态规划
     *
     * @param n
     * @return
     */
    public static List<Integer> grayCode(int n) {
    /*
    List<Integer> result = new ArrayList<>();
    result.add(0);
    for (int i = 0; i < n; i++) {
        int add = 1 << i;
        for (int j = result.size() - 1; j >= 0; j--) {
            result.add(result.get(j) + add);
        }
    }
    return result;
    */
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            result.add(i ^ (i >> 1));
        }
        return result;
    }

    /**
     * LeetCode350
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int num : nums2) {
            Integer count = map.get(num);
            if (count != null && count > 0) {
                list.add(num);
                map.put(num, count - 1);
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public int[] intersect2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        int i = 0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            res[k] = list.get(k);
        }
        return res;
    }

    public int[] intersect3(int[] nums1, String pathOfNums2) throws UnsupportedEncodingException {

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();

        InputStream in = Leet.class.getClassLoader().getResourceAsStream(pathOfNums2);
        if (in == null) {
            throw new IllegalArgumentException();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        int c;
        try {
            StringBuilder sb = new StringBuilder();
            while ((c = reader.read()) != -1) {
                if (c == 32) {
                    int num = Integer.parseInt(sb.toString());
                    Integer count = map.get(num);
                    if (count != null && count > 0) {
                        list.add(num);
                        map.put(num, count - 1);
                    }
                    sb = new StringBuilder();
                } else {
                    sb.append((char) c);
                }
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] res = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            res[k] = list.get(k);
        }
        return res;
    }

    /**
     * LeetCode785
     *
     * @param graph
     * @return
     */
    private static final int UNCOLORED = 0;

    private static final int RED = 1;
    private static final int BLACK = 2;
    private boolean canBipartite;

    public boolean isBipartite(int[][] graph) {
        final int m = graph.length;
        if (m == 0) {
            throw new RuntimeException();
        }
        int[] colors = new int[m];
        // initial as zeros
        for (int i = 0; i < m; i++) {
            if (colors[i] == UNCOLORED) {
                isBipartiteHelper(i, RED, colors, graph);
            }
            if (!canBipartite) {
                return false;
            }
        }
        return canBipartite;
    }

    private void isBipartiteHelper(int node, int color, int[] colors, int[][] graph) {
        colors[node] = color;
        int neighborColor = color == RED ? BLACK : RED;
        for (int neighbor : graph[node]) {
            if (colors[neighbor] == UNCOLORED) {
                isBipartiteHelper(neighbor, neighborColor, colors, graph);
                if (!canBipartite) {
                    return;
                }
            } else if (colors[neighbor] != neighborColor) {
                canBipartite = false;
                return;
            }
        }
    }

    /**
     * LeetCode410 二分法
     *
     * @param nums
     * @param m
     * @return
     */
    public int splitArray(int[] nums, int m) {
        // 下界 数组中的最大数 上界 数组中所以元素和
        int left = 0, right = 0;
        for (int num : nums) {
            right += num;
            if (left < num) {
                left = num;
            }
        }
        // [left, right]
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid, m)) {
                // true 代表最终结果小于等于mid
                right = mid;
            } else {
                // false 代表最终结果大于mid
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean check(int[] nums, int mid, int m) {
        int sum = 0;
        int blocks = 1;
        for (int num : nums) {
            if (sum + num > mid) {
                sum = num;
                blocks++;
            } else {
                sum += num;
            }
        }
        return blocks <= m;
    }

    /**
     * LeetCode632 转化为从每一个列表中选择一个数 使其中最大值-最小值最小
     *
     * @param nums
     * @return
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        // 左右边界结果
        int left = 0, right = Integer.MAX_VALUE;
        int range = right - left, max = Integer.MIN_VALUE;
        // next[i] 代表 第i个列表中选择的索引
        int[] next = new int[nums.size()];
        // 最小栈 比较的是每个列表中选出来的数
        PriorityQueue<Integer> pq =
                new PriorityQueue<>(Comparator.comparing(index -> nums.get(index).get(next[index])));
        for (int i = 0; i < nums.size(); i++) {
            pq.offer(i);
            // 更新max 代表所有选出来的数中最大值
            max = Math.max(max, nums.get(i).get(next[i]));
        }
        while (true) {
            // 从最小堆弹出最小值的列表索引
            Integer minIndex = pq.poll();
            assert minIndex != null;
            Integer newRange = max - nums.get(minIndex).get(next[minIndex]);
            if (newRange < range) {
                range = newRange;
                left = nums.get(minIndex).get(next[minIndex]);
                right = max;
            }
            // 该列表选下一个
            next[minIndex]++;
            // 退出条件
            if (next[minIndex] == nums.get(minIndex).size()) {
                break;
            }
            pq.offer(minIndex);
            // 更新max
            max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
        }
        return new int[]{left, right};
    }

    /**
     * LeetCode696
     *
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int index = 0, res = 0, prevCount = 0;
        final int n = s.length();
        while (index < n) {
            int count = 0;
            char c = s.charAt(index);
            while (index < n && c == s.charAt(index)) {
                index++;
                count++;
            }
            res += Math.min(count, prevCount);
            prevCount = count;
        }
        return res;
    }

    /**
     * LeetCode491 递增子序列
     *
     * @return 递增子序列的列表
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        final int n = nums.length;
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> possibleSeq = getSeqUsingMask(i, nums);
            // RK hash算法 由于整数范围在-100到100 取进制为263 1e9+7为大的质数
            int hash = RKHash(possibleSeq, 263, (int) 1e9 + 7);
            if (incrCheck(possibleSeq) && !visited.contains(hash)) {
                ret.add(new ArrayList<>(possibleSeq));
                visited.add(hash);
            }
        }
        return ret;
    }

    private boolean incrCheck(List<Integer> seq) {
        for (int i = 1; i < seq.size(); i++) {
            if (seq.get(i) < seq.get(i - 1)) {
                return false;
            }
        }
        return seq.size() >= 2;
    }

    private int RKHash(List<Integer> seq, int base, int mod) {
        int retHash = 0;
        for (Integer s : seq) {
            // 如何处理序列全是0的情况
            // 序列0 0 与 序列 0 0 0 的hash 值  s+101
            retHash = retHash * base % mod + (s + 101);
            retHash = retHash % mod;
        }
        return retHash;
    }

    private List<Integer> getSeqUsingMask(int mask, int[] nums) {
        List<Integer> seq = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if ((mask & 1) != 0) {
                seq.add(nums[i]);
            }
            mask = mask >> 1;
        }
        return seq;
    }
}
