package zhousai.s2;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.*;

/**
 * @author comyxy
 * weekly-contest-244
 */
public class W18 {

    public boolean findRotation(int[][] mat, int[][] target) {
        int n = mat.length;
        if (n == 1) {
            return mat[0][0] == target[0][0];
        }
        if (isSame(mat, target)) {
            return true;
        }
        rotate(mat);
        if (isSame(mat, target)) {
            return true;
        }
        rotate(mat);
        if (isSame(mat, target)) {
            return true;
        }
        rotate(mat);
        if (isSame(mat, target)) {
            return true;
        }
        return false;
    }

    private void rotate(int[][] mat) {
        int n = mat.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int t = mat[i][j];
                mat[i][j] = mat[n - 1 - j][i];
                mat[n - 1 - j][i] = mat[n - 1 - i][n - 1 - j];
                mat[n - 1 - i][n - 1 - j] = mat[j][n - 1 - i];
                mat[j][n - 1 - i] = t;
            }
        }
    }

    private boolean isSame(int[][] mat, int[][] target) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != target[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int reductionOperations(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new TreeMap<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < n; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        int res = 0, tmp = 0;
        if (map.size() == 1) {
            return res;
        }
        Iterator<Map.Entry<Integer, Integer>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = iter.next();
            if (iter.hasNext()) {
                tmp += entry.getValue();
                res += tmp;
            }
        }
        return res;
    }

    public int minFlips(String s) {
        int n = s.length();
        // 以0结尾
        int[] f0 = new int[n * 2];
        // 以1结尾
        int[] f1 = new int[n * 2];
        f0[0] = s.charAt(0) == '1' ? 1 : 0;
        f1[0] = s.charAt(0) == '0' ? 1 : 0;
        for (int i = 1; i < n * 2; i++) {
            f0[i] = f1[i - 1] + (s.charAt(i % n) == '1' ? 1 : 0);
            f1[i] = f0[i - 1] + (s.charAt(i % n) == '0' ? 1 : 0);
        }
        //没有操作1时的最小操作数
        int res = Math.min(f0[n - 1], f1[n - 1]);
        if ((n & 1) == 0) {
            for (int i = n; i < n * 2; i++) {
                res = Math.min(res, Math.min(f0[i] - f0[i - n], f1[i] - f1[i - n]));
            }
        } else {
            for (int i = n; i < n * 2; i++) {
                res = Math.min(res, Math.min(f0[i] - f1[i - n], f1[i] - f0[i - n]));
            }
        }
        return res;
    }

    private static final int MOD = 1000000007;

    public int minWastedSpace(int[] packages, int[][] boxes) {
        Arrays.sort(packages);
        int n = packages.length;
        long[] preSum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + packages[i - 1];
        }
        long res = Long.MAX_VALUE;
        for (int[] box : boxes) {
            Arrays.sort(box);
            if (box[box.length - 1] < packages[n - 1]) {
                continue;
            }
            long waste = 0;
            int prev = 0;
            for (int i = 0; i < box.length; i++) {
                // package[cur-1] < box[i] <= package[cur]
                int cur = binarySearch(packages, box[i]);
                waste += (long) box[i] * (cur - prev) - (preSum[cur] - preSum[prev]);
                prev = cur;
            }
            res = Math.min(res, waste);
        }
        return res == Long.MAX_VALUE ? -1 : (int) (res % MOD);
    }

    private int binarySearch(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] <= target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}
