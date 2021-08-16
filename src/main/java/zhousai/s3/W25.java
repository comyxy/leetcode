package zhousai.s3;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2021/7/25 10:29
 */
public class W25 {
    public int getLucky(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < s.length(); j++) {
            sb.append(s.charAt(j) - 'a' + 1);
        }
        String s1 = sb.toString();
        for (int i = 0; i < k; i++) {
            long tmp = 0;
            for (int j = 0; j < s1.length(); j++) {
                tmp += s1.charAt(j) - '0';
            }
            s1 = String.valueOf(tmp);
        }
        return (int) Long.parseLong(s1);
    }

    public String maximumNumber(String num, int[] change) {
        char[] cs = num.toCharArray();
        boolean start = false;
        for (int i = 0; i < cs.length; i++) {
            int v = cs[i] - '0';
            if (change[v] > v) {
                cs[i] = (char) (change[v] + '0');
                start = true;
            } else if (change[v] < v && start) {
                break;
            }
        }
        return String.valueOf(cs);
    }

    public int maxCompatibilitySumV2(int[][] students, int[][] mentors) {
        final int m = students.length;
        final int n = students[0].length;
        int[][] matrix = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    matrix[i][j] += students[i][k] == mentors[j][k] ? 1 : 0;
                }
            }
        }
        int[][] dp = new int[m][1 << m];
        // 最终状态 [1,1,1...1]
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < (1 << m); j++) {
                if (popCount(j) != i + 1) {
                    continue;
                }
                for (int k = 0; k < m; k++) {
                    if ((j & (1 << k)) > 0) {
                        // to j by 1 << k
                        dp[i][j] = Math.max(dp[i][j], (i != 0 ? dp[i - 1][j ^ (1 << k)] : 0) + matrix[i][k]);
                    }
                }
            }
        }
        return dp[m - 1][(1 << m) - 1];
    }

    private int popCount(int x) {
        int count = 0;
        while (x > 0) {
            count++;
            x &= (x - 1);
        }
        return count;
    }

    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        final int m = students.length;
        final int n = students[0].length;
        int[][] matrix = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    matrix[i][j] += students[i][k] == mentors[j][k] ? 1 : 0;
                }
            }
        }
        int[] nums = new int[m];
        for (int i = 0; i < m; i++) {
            nums[i] = i;
        }
        int res = 0;
        for (List<Integer> order : permute(nums)) {
            int cur = 0;
            for (int i = 0; i < m; i++) {
                cur += matrix[i][order.get(i)];
            }
            res = Math.max(res, cur);
        }
        return res;
    }

    /**
     * 全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        permuteHelper(res, nums, 0);
        return res;
    }

    private void permuteHelper(List<List<Integer>> result, int[] nums, int start) {
        if (start >= nums.length) {
            // copy
            List<Integer> tmp = new ArrayList<>();
            for (int num : nums) {
                tmp.add(num);
            }
            result.add(tmp);
        }
        for (int i = start; i < nums.length; i++) {
            swapInt(nums, i, start);
            permuteHelper(result, nums, start + 1);
            swapInt(nums, i, start);
        }
    }

    private void swapInt(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public static void main(String[] args) {
        W25 w25 = new W25();
//        System.out.println(w25.getLucky("fleyctuuajsr", 5));
//        System.out.println(w25.maximumNumber("214010", new int[]{6,7,9,7,4,0,3,4,4,7}));
//        System.out.println(w25.maximumNumber("334111", new int[]{0,9,2,3,3,2,5,5,5,5}));

        System.out.println(w25.maxCompatibilitySumV2(new int[][]{{1, 1, 0}, {1, 0, 1}, {0, 0, 1}}, new int[][]{{1, 0, 0}, {0, 0, 1}, {1, 1, 0}}));
    }
}
