package zhousai.s2;

import java.util.*;

/**
 * @author comyxy
 * 245
 */
public class W19 {
    public boolean makeEqual(String[] words) {
        int[] counts = new int[26];
        int n = words.length;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                counts[word.charAt(i) - 'a']++;
            }
        }
        for (int count : counts) {
            if (count % n != 0) {
                return false;
            }
        }
        return true;
    }

    public int maximumRemovals(String s, String p, int[] removable) {
        int n = removable.length;

        int lo = 0, hi = n;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int[] tmp = Arrays.copyOf(removable, mid);
            Arrays.sort(tmp);
            StringBuilder sb = new StringBuilder();
            int start = 0;
            for (int i = 0; i < mid; i++) {
                sb.append(s, start, tmp[i]);
                start = tmp[i] + 1;
            }
            sb.append(s, start, s.length());
            if (!isSubsequence(p, sb.toString())) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return Math.max(hi, 0);
    }

    public boolean isSubsequence(String s, String t) {
        char[] ch = s.toCharArray();
        int index = -1;
        for (int i = 0; i < ch.length; i++) {
            index = t.indexOf(ch[i], index + 1);
            if (index == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean mergeTriplets(int[][] triplets, int[] target) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < triplets.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (triplets[i][j] == target[j]) {
                    List<Integer> list = map.getOrDefault(j, new ArrayList<>());
                    list.add(i);
                    map.put(j, list);
                }
            }
        }
        List<Integer> l0 = map.getOrDefault(0, new ArrayList<>());
        List<Integer> l1 = map.getOrDefault(1, new ArrayList<>());
        List<Integer> l2 = map.getOrDefault(2, new ArrayList<>());
        for (Integer i0 : l0) {
            for (Integer i1 : l1) {
                for (Integer i2 : l2) {
                    if (Math.max(triplets[i0][0], Math.max(triplets[i1][0], triplets[i2][0])) == target[0] &&
                            Math.max(triplets[i0][1], Math.max(triplets[i1][1], triplets[i2][1])) == target[1] &&
                            Math.max(triplets[i0][2], Math.max(triplets[i1][2], triplets[i2][2])) == target[2]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private int[][][] dpMax, dpMin;

    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        this.dpMax = new int[30][30][30];
        this.dpMin = new int[30][30][30];
        if (firstPlayer > secondPlayer) {
            int t = firstPlayer;
            firstPlayer = secondPlayer;
            secondPlayer = t;
        }
        return dp(n, firstPlayer, secondPlayer);
    }

    private int[] dp(int n, int f, int s) {
        if (dpMin[n][f][s] != 0) {
            return new int[]{dpMin[n][f][s], dpMax[n][f][s]};
        }
        if (f + s == n + 1) {
            return new int[]{1, 1};
        }
        if (f + s > n + 1) {
            int[] ret = dp(n, n + 1 - s, n + 1 - f);
            dpMin[n][f][s] = ret[0];
            dpMax[n][f][s] = ret[1];
            return new int[]{dpMin[n][f][s], dpMax[n][f][s]};
        }

        int l = Integer.MAX_VALUE, r = Integer.MIN_VALUE;
        int half = (n + 1) / 2;

        if (s <= half) {
            for (int i = 0; i < f; i++) {
                for (int j = 0; j < s - f; j++) {
                    int[] ret = dp(half, i + 1, i + j + 2);
                    l = Math.min(l, ret[0] + 1);
                    r = Math.max(r, ret[1] + 1);
                }
            }
        } else {
            int s1 = n + 1 - s;
            int s12s = (n - 2 * s1 + 1) / 2;
            for (int i = 0; i < f; i++) {
                for (int j = 0; j < s1 - f; j++) {
                    int[] ret = dp(half, i + 1, i + j + 2 + s12s);
                    l = Math.min(l, ret[0] + 1);
                    r = Math.max(r, ret[1] + 1);
                }
            }
        }
        dpMin[n][f][s] = l;
        dpMax[n][f][s] = r;
        return new int[]{dpMin[n][f][s], dpMax[n][f][s]};
    }
}
