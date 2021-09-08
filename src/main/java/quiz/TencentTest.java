package quiz;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @since 2021/9/3 18:37
 */
public class TencentTest {

    public int q1(String[] strs) {
        Deque<Integer> adds = new LinkedList<>();
        Deque<Integer> times = new LinkedList<>();
        adds.push(0);
        for(String str : strs) {
            if ("add".equals(str)) {
                int cur = adds.pop();
                if (addOverflow(cur, 1)) {
                    return Integer.MAX_VALUE;
                }
                adds.push(cur + 1);
            }
            else if ("end".equals(str)) {
                int cur = adds.pop();
                int time = times.pop();
                if(multiplyOverflow(cur, time)) {
                    return Integer.MAX_VALUE;
                }
                cur *= time;
                int add = adds.pop();
                if(addOverflow(cur, add)) {
                    return Integer.MAX_VALUE;
                }
                cur += add;
                adds.push(cur);
            }
            else {
                String[] cs = str.split(" ");
                int time = Integer.parseInt(cs[1]);
                times.push(time);
                adds.push(0);
            }
        }
        return adds.pop();
    }

    private boolean multiplyOverflow(int cur, int time) {
        return Integer.MAX_VALUE / time < cur;
    }

    private boolean addOverflow(int cur, int add) {
        return Integer.MAX_VALUE - add < cur;
    }

    public int q2(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int mid = n / 2;
        int i = 0, j = mid;
        int res = 0;
        while (i < mid && j < n){
            while (j < n && nums[i] * k > nums[j]) {
                j++;
            }
            if (j < n) {
                res++;
            }
            i++;
        }
        return res;
    }
}
