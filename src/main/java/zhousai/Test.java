package zhousai;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2020/12/2
 */
public class Test {
    public static void main(String[] args) {
        String func = func("/./home/a/./../../../..");
        System.out.println(func);
    }

    public static List<List<Integer>> so(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int k = n - 1;
            for (int j = i + 1; j < n; j++) {
                if (j - i > 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                while (j < k && nums[j] + nums[k] + nums[i] > 0) {
                    k--;
                }

                if (j == k) break;

                if (nums[j] + nums[k] + nums[i] == 0) {
                    List<Integer> li = new ArrayList<>();
                    li.add(nums[i]);
                    li.add(nums[j]);
                    li.add(nums[k]);
                    ans.add(li);
                }
            }
        }
        return ans;
    }

    public static String func(String s) {
        StringBuilder sb = new StringBuilder();
        String[] splits = s.split("/");
        List<String> path = new ArrayList<>();
        for (int i = 1; i < splits.length; i++) {
            if (splits[i].equals(".")) {
                continue;
            }
            if (splits[i].equals("..")) {
                if (!path.isEmpty())
                    path.remove(path.size() - 1);
                continue;
            }
            path.add(splits[i]);
        }
        if (sb.length() == 0) {
            sb.append("/");
        } else {
            for (String s1 : path) {
                sb.append("/")
                        .append(s1);
            }
        }

        return sb.toString();
    }
}
