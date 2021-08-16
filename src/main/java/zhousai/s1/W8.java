package zhousai.s1;

import java.util.*;

/**
 * 2020/10/18
 */
public class W8 {
    public static void main(String[] args) {
        //
        W8 w8 = new W8();
        //    int abca = w8.maxLengthBetweenEqualCharacters("cbzxy");
        //    System.out.println(abca);

        //    String rotate = w8.findLexSmallestString("5525", 9 , 2);
        //    System.out.println(rotate);

        int[] scores = {9, 2, 8, 8, 2};
        int[] ages = {4, 1, 3, 3, 5};
        int bestTeamScore = w8.bestTeamScore2(scores, ages);
        System.out.println(bestTeamScore);
    }

    public int maxLengthBetweenEqualCharacters(String s) {
        int max = -1;
        int[] indexes = new int[26];
        Arrays.fill(indexes, -1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (indexes[c - 'a'] == -1) {
                indexes[c - 'a'] = i;
            } else {
                max = Math.max(max, i - indexes[c - 'a'] - 1);
            }
        }
        return max;
    }

    public String findLexSmallestString(String s, int a, int b) {
        TreeSet<String> set = new TreeSet<>();
        Queue<String> queue = new LinkedList<>();
        set.add(s);
        queue.offer(s);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String poll = queue.poll();
                String p1 = add(poll, a);
                if (!set.contains(p1)) {
                    set.add(p1);
                    queue.offer(p1);
                }
                String p2 = rotate(poll, b);
                if (!set.contains(p2)) {
                    set.add(p2);
                    queue.offer(p2);
                }
            }
        }
        return set.first();
    }

    private String add(String s, int a) {
        char[] cs = s.toCharArray();
        for (int i = 1; i < cs.length; i += 2) {
            int sum = cs[i] - '0';
            sum += a;
            if (sum >= 10) {
                sum = sum % 10;
            }
            cs[i] = (char) (sum + '0');
        }
        return new String(cs);
    }

    private String rotate(String s, int b) {
        char[] cs = s.toCharArray();
        b = b % cs.length;
        char[] tmp = new char[b];
        for (int i = 0; i < b; i++) {
            tmp[i] = cs[cs.length - b + i];
        }
        for (int i = cs.length - b; i > 0; i--) {
            cs[i - 1 + b] = cs[i - 1];
        }
        for (int i = 0; i < b; i++) {
            cs[i] = tmp[i];
        }
        return new String(cs);
    }

    int ans = 0;
    Set<Integer> visited = new HashSet<>();

    public int bestTeamScore(int[] scores, int[] ages) {
        backtrace(scores, ages, 0, 0);
        return ans;
    }

    /**
     * 回溯超时
     */
    private void backtrace(int[] scores, int[] ages, int index, int s) {
        for (int i = index; i < scores.length; i++) {

            boolean flag = false;
            for (Integer v : visited) {
                if ((ages[v] > ages[i] && scores[v] < scores[i]) || (ages[v] < ages[i] && scores[v] > scores[i])) {
                    flag = true;
                    break;
                }
            }
            if (flag) continue;

            ans = Math.max(ans, s + scores[i]);
            visited.add(i);
            System.out.println(visited);
            backtrace(scores, ages, i + 1, s + scores[i]);
            visited.remove(i);
        }
    }

    /**
     * 排序+动态规划
     */
    public int bestTeamScore2(int[] scores, int[] ages) {
        int n = scores.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (i, j) -> {
            if (ages[i] == ages[j]) {
                return scores[i] - scores[j];
            }
            return ages[i] - ages[j];
        });
        // dp[i]最后一个队员是第i个队员时分数
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int idx = order[i];
            dp[i] = scores[idx];
            for (int j = 0; j < i; j++) {
                int last = order[j];
                if (scores[last] <= scores[idx]) {
                    dp[i] = Math.max(dp[i], dp[j] + scores[idx]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
