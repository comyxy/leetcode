package zhousai.s2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 2020/11/22
 */
public class W13 {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        StringBuilder sb = new StringBuilder();
        for (String w : word1) {
            sb.append(w);
        }
        StringBuilder sb1 = new StringBuilder();
        for (String w : word2) {
            sb1.append(w);
        }
        return sb.toString()
                .equals(sb1.toString());
    }

    public String getSmallestString(int n, int k) {
        int[] nums = new int[n];
        int a = k / n, t = k % n;
        for (int i = 0; i < n; i++) {
            nums[i] = a;
        }
        for (int i = n - 1; i >= 0; i--) {
            if (t == 0) {
                break;
            }
            if (nums[i] + t <= 26) {
                nums[i] += t;
                t = 0;
            } else {
                t -= (26 - nums[i]);
                nums[i] = 26;
            }
        }
        int i = 0, j = n - 1;
        while (i < j) {
            if (nums[i] > 1 && nums[j] < 26) {
                nums[i]--;
                nums[j]++;
            } else if (nums[i] <= 1) {
                i++;
            } else if (nums[j] >= 26) {
                j--;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append((char) (num + 'a' - 1));
        }
        return sb.toString();
    }

    public int waysToMakeFair(int[] nums) {
        int n = nums.length;
        int[] postSum = new int[n + 2];
        int[] preSum = new int[n + 2];
        for (int i = n - 1, j = 2; i >= 0; i--, j++) {
            postSum[j] = postSum[j - 2] + nums[i];
        }
        for (int i = 0, j = 2; i < n; i++, j++) {
            preSum[j] = preSum[j - 2] + nums[i];
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int k1, k2;
            if (i % 2 == 0) {
                k1 = preSum[i] + postSum[n - i];
                k2 = preSum[i + 1] + postSum[n - i - 1];
            } else {
                k1 = preSum[i + 1] + postSum[n - i - 1];
                k2 = preSum[i] + postSum[n - i];
            }
            if (k1 == k2) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * https://leetcode-cn.com/problems/minimum-initial-energy-to-finish-tasks/solution/wan-cheng-suo-you-ren-wu-de-zui-shao-chu-shi-neng-/
     */
    public int minimumEffort(int[][] tasks) {
        Arrays.sort(tasks, Comparator.comparingInt(o -> (o[0] - o[1])));
        int result = 0, sum = 0;
        for (int[] task : tasks) {
            result = Math.max(result, sum + task[1]);
            sum += task[0];
        }
        return result;
    }
    
    public static void main(String[] args) {
        W13 w = new W13();
    }
}
