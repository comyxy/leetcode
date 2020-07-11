package leetcode.datastructure;

import java.util.*;

/**
 * @date 2020/7/11
 */
public class UseTreeArray {

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int[] a = discretization(nums);
        TreeArray treeArray = new TreeArray(a.length);
        for (int i = nums.length - 1; i >= 0; i--) {
            int id = Arrays.binarySearch(a, nums[i]) + 1;
            res.add(treeArray.query(id - 1));
            treeArray.update(id, 1);
        }
        Collections.reverse(res);
        return res;
    }

    private int[] discretization(int[] nums) {
        Set<Integer> set = new HashSet<>();
        Arrays.stream(nums)
                .forEach(set::add);
        int[] a = new int[set.size()];
        int i = 0;
        for (Integer num : set) {
            a[i++] = num;
        }
        Arrays.sort(a);
        return a;
    }

    public static void main(String[] args) {
        int[] nums = {7, 5, 5, 2, 3, 6};
        UseTreeArray useTreeArray = new UseTreeArray();
        useTreeArray.countSmaller(nums);
    }
}
