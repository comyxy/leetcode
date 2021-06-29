package zhousai.random;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author comyxy
 * weekly-contest-114
 */
public class RW1 {
    public boolean isAlienSorted(String[] words, String order) {
        int[] characterOrder = new int[26];
        for (int i = 0; i < order.length(); i++) {
            characterOrder[order.charAt(i) - 'a'] = i;
        }
        eachWord:
        for (int i = 1; i < words.length; i++) {
            int m = words[i - 1].length(), n = words[i].length();
            for (int j = 0; j < m && j < n; j++) {
                char a = words[i - 1].charAt(j), b = words[i].charAt(j);
                if (characterOrder[a - 'a'] < characterOrder[b - 'a']) {
                    continue eachWord;
                } else if (characterOrder[a - 'a'] > characterOrder[b - 'a']) {
                    return false;
                }
            }
            if (m > n) {
                return false;
            }
        }
        return true;
    }

    public boolean canReorderDoubled(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int a : arr) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        // boxed
        Integer[] tmp = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            tmp[i] = arr[i];
        }
        Arrays.sort(tmp, Comparator.comparingInt(Math::abs));
        for (Integer t : tmp) {
            if (map.get(t) == 0) {
                continue;
            }
            if (map.getOrDefault(2 * t, 0) <= 0) {
                return false;
            }
            map.put(t, map.get(t) - 1);
            map.put(2 * t, map.get(2 * t) - 1);
        }
        return true;
    }

    public int minDeletionSize(String[] strs) {
        int m = strs.length, n = strs[0].length();
        int res = 0;

        String[] cur = new String[m];
        for (int j = 0; j < n; j++) {
            String[] next = Arrays.copyOf(cur, m);
            for (int i = 0; i < m; i++) {
                next[i] += strs[i].charAt(j);
            }
            if (isSorted(next)) {
                cur = next;
            } else {
                res++;
            }
        }
        return res;
    }

    private boolean isSorted(String[] strs) {
        for (int i = 1; i < strs.length; i++) {
            if (strs[i - 1].compareTo(strs[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    public int tallestBillboard(int[] rods) {
        // dp(i,j) 第i个 高度差为0时 高度
        Map<Integer, Integer> dp = new HashMap<>();
        dp.put(0, 0);
        for (int rod : rods) {
            Map<Integer, Integer> tmp = new HashMap<>(dp);
            for (Map.Entry<Integer, Integer> entry : tmp.entrySet()) {
                int d = entry.getKey(), v = entry.getValue();
                dp.put(d + rod, Math.max(dp.getOrDefault(d + rod, 0), v + rod));
                dp.put(d - rod, Math.max(dp.getOrDefault(d - rod, 0), v));
            }
        }
        return dp.get(0);
    }
}
