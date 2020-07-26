package leetcode;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author comyxy
 * @date 2020/5/4
 */
public class Leet {
    /**
     * 双指针法
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        char[] cs = s.toCharArray();
        int n = cs.length;
        Set<Character> set = new HashSet<>();
        int result = 0;
        int left = 0, right = 0;
        while (left < n) {
            if (left != 0) {
                set.remove(cs[left - 1]);
            }
            while (right < n && !set.contains(cs[right])) {
                set.add(cs[right]);
                right++;
            }
            result = Math.max(result, right - left);
            left++;
        }
        return result;
    }

    /**
     * 双指针法
     *
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
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
     * 两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> pair = new HashMap<>();
        final int length = nums.length;
        int[] res = new int[2];
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            if (!pair.containsKey(num)) {
                pair.put(target - num, i);
            } else {
                res[0] = pair.get(num);
                res[1] = i;
            }
        }
        return res;
    }

    /**
     * 两数之和
     *
     * @param nums
     * @param start
     * @param target
     * @return 返回值对
     */
    public List<List<Integer>> twoSum2(int[] nums, int start, int target) {
        // 排序
        Arrays.sort(nums);
        final int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        int left = start, right = n - 1;
        while (left < right) {
            int leftValue = nums[left];
            int rightValue = nums[right];
            int sum = leftValue + rightValue;
            if (sum < target) {
                while (left < right && nums[left] == leftValue) {
                    left++;
                }
            } else if (sum > target) {
                while (left < right && nums[right] == rightValue) {
                    right--;
                }
            } else {
                List<Integer> t = new ArrayList<>();
                t.add(nums[left]);
                t.add(nums[right]);
                res.add(t);
                while (left < right && nums[left] == leftValue) {
                    left++;
                }
                while (left < right && nums[right] == rightValue) {
                    right--;
                }
            }
        }
        return res;
    }

    /**
     * 三数之和
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        final int n = nums.length;
        // first < second < third   (index)
        // 第一重循环
        for (int first = 0; first < n; first++) {
            // 重复则直接跳过
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            int target = -nums[first];
            // 三重循环位置
            int third = n - 1;
            // 第二重循环
            for (int second = first + 1; second < n; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }

                // 第三重循环
                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }
                // 第二个数与第三个数重合 排除
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> t = new ArrayList<>(3);
                    t.add(nums[first]);
                    t.add(nums[second]);
                    t.add(nums[third]);
                    res.add(t);
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum2(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        final int n = nums.length;
        for (int i = start; i < n; i++) {
            // 重复了
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> twoSum2Result = twoSum2(nums, i + 1, target - nums[i]);
            for (List<Integer> t : twoSum2Result) {
                t.add(nums[i]);
                res.add(t);
            }
        }
        return res;
    }

    /**
     * 四数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        final int n = nums.length;
        for (int i = 0; i < n; i++) {
            // 重复了
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> twoSum3Result = threeSum2(nums, i + 1, target - nums[i]);
            for (List<Integer> t : twoSum3Result) {
                t.add(nums[i]);
                res.add(t);
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
     * 在两个排序数组中找到第k小的元素(k=1,...)
     * nums1[k/2-1]前面有0,1,...,k/2-2共k/2-1个元素
     * nums2[k/2-1]前面有0,1,...,k/2-2共k/2-1个元素
     * 当nums1[k/2-1]<nums2[k/2-1]时 比nums1[k/2-1]小的最多只用(k/2-1)+(k/2-1)=k-2个
     * nums1[k/2-1]不可能是第k小的元素
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
        if (!positive && (t > (Integer.MAX_VALUE - c) / 10 || (c > 0 && t > (Integer.MAX_VALUE - c + 1) / 10))) {
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
                if (t == map.get(c)) {
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
    public static List<String> generateParenthesis(int n) {
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
    private static void generateParenthesisHelper(StringBuilder sb, int left, int right, List<String> result) {
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
     * 子集（幂集）
     * 回溯 深度优先dfs
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

    private static void subsetsHelper(List<List<Integer>> result, List<Integer> list, int[] nums, int start) {
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
     * 格雷码
     * 动态规划
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

    public int[] intersect3(int[] nums1, String pathOfNums2) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();

        InputStream in = Leet.class.getClassLoader()
                .getResourceAsStream(pathOfNums2);
        if (in == null) {
            throw new IllegalArgumentException();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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
     * LeetCode410
     * 二分法
     * @param nums
     * @param m
     * @return
     */
    public int splitArray(int[] nums, int m) {
        final int n = nums.length;
        // 下界 数组中的最大数 上界 数组中所以元素和
        int left = 0, right = 0;
        for (int num : nums) {
            right += num;
            if(left < num){
                left = num;
            }
        }
        // [left, right]
        while(left < right){
            int mid = left + (right - left) / 2;
            if(check(nums, mid, m)){
                // true 代表最终结果小于等于mid
                right = mid;
            }else{
                // false 代表最终结果大于mid
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean check(int[] nums, int mid, int m) {
        int sum = 0;
        int blocks = 1;
        for(int num : nums){
            if(sum + num > mid){
                sum = num;
                blocks++;
            }else {
                sum += num;
            }
        }
        return blocks <= m;
    }
}
