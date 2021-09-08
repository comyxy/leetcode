package concurrent;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * @since 2021/8/30 16:38
 */
public class AccumulateTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 10;

    private final int[] nums;

    public AccumulateTask(int[] nums) {
        this.nums = nums;
    }

    @Override
    protected Integer compute() {
        int n = nums.length;
        if (n < THRESHOLD) {
            return Arrays.stream(nums).sum();
        }
        else {
            int mid = n / 2;
            AccumulateTask leftTask = new AccumulateTask(Arrays.copyOfRange(nums, 0, mid));
            AccumulateTask rightTask = new AccumulateTask(Arrays.copyOfRange(nums, mid, n));
            invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        }
    }
}
