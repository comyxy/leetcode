package concurrent;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @since 2021/8/30 16:22
 */
public class ForkJoinPoolTest {
    public static void main(String[] args)  {
        testPrintTask();
        testAccumulateTask();
    }

    private static void testAccumulateTask() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] nums = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
        int serialSum = Arrays.stream(nums).sum();
        System.out.println(serialSum);
        ForkJoinTask<Integer> task = pool.submit(new AccumulateTask(nums));
        try {
            Integer result = task.get();
            System.out.println(result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testPrintTask() {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Void> task = pool.submit(new PrintTask(1, 201));
        try {
            task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
