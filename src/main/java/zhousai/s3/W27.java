package zhousai.s3;

import java.util.*;

/**
 * @since 2021/8/8 10:31
 */
public class W27 {
    public boolean isPrefixString(String s, String[] words) {
        int ins = 0;
        for (String word : words) {
            for (int i = 0; i < word.length() && ins < s.length(); i++) {
                if (word.charAt(i) != s.charAt(ins)) {
                    return false;
                }
                if (ins == s.length() - 1 && i != word.length() - 1) {
                    return false;
                }
                ins++;
            }
        }
        return ins >= s.length();
    }

    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int pile : piles) {
            pq.offer(pile);
        }
        while (k-- > 0 && !pq.isEmpty()) {
            Integer p = pq.poll();
            p = p - p / 2;
            if (p > 0) {
                pq.offer(p);
            }
        }
        int res = 0;
        while (!pq.isEmpty()) {
            res += pq.poll();
        }
        return res;
    }

    public int minSwaps(String s) {
        char[] cs = s.toCharArray();
        int left = 0;
        int i = 0, j = cs.length - 1;
        int res = 0;
        while (i < j) {
            if (cs[i] == '[') {
                left++;
            } else if (cs[i] == ']') {
                if (left > 0) {
                    left--;
                } else {
                    int right = 0;
                    // 从右向左找个一个没有匹配的左括号
                    while (i < j) {
                        if (cs[j] == ']') {
                            right++;
                        } else if (cs[j] == '[') {
                            if (right > 0) {
                                right--;
                            } else {
                                char t = cs[j];
                                cs[j] = cs[i];
                                cs[i] = t;
                                res++;
                                j--;
                                break;
                            }
                        }
                        j--;
                    }
                    left++;
                }
            }
            i++;
        }
        return res;
    }

    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        final int n = obstacles.length;
        int[] res = new int[n];
        List<Integer> lis = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int it = upperBound(lis, obstacles[i]);
            if (it == lis.size() || (it == lis.size() - 1 && lis.get(lis.size() - 1) == obstacles[i])) {
                lis.add(obstacles[i]);
                res[i] = lis.size();
            } else {
                res[i] = it + 1;
                lis.set(it, obstacles[i]);
            }
        }
        return res;
    }

    private int upperBound(List<Integer> lis, int target) {
        int left = 0, right = lis.size();
        while (left < right) {
            int mid = (left + right) >> 1;
            if (lis.get(mid) <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }


    public static void main(String[] args) {
        W27 w = new W27();
//        int[] piles = {5,4,9};
//        int k = 2;
//        int sum = w.minStoneSum(piles, k);
//        System.out.println(sum);
//        String s = "]]][[[";
//        int count = w.minSwaps(s);
//        System.out.println(count);
        int[] obs = new int[]{5, 1, 5, 5, 1, 3, 4, 5, 1, 4};
        int[] results = w.longestObstacleCourseAtEachPosition(obs);
        for (int result : results) {
            System.out.println(result);
        }
    }
}
