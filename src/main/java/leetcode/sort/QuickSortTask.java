package leetcode.sort;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/** 2020/9/13 */
public class QuickSortTask extends RecursiveAction {
  private int[] arr;
  private int left;
  private int right;

  public QuickSortTask(int[] arr, int left, int right) {
    this.arr = arr;
    this.left = left;
    this.right = right;
  }

  @Override
  protected void compute() {
    if (left > right) {
      return;
    }
    int i = left, j = right, base = arr[left];
    while (i < j) {
      while (arr[j] >= base && i < j) {
        j--;
      }
      arr[i] = arr[j];
      while (arr[i] <= base && i < j) {
        i++;
      }
      arr[j] = arr[i];
    }
    arr[j] = base;
    QuickSortTask leftTask = new QuickSortTask(arr, left, j - 1);
    QuickSortTask rightTask = new QuickSortTask(arr, j + 1, right);
    leftTask.fork();
    rightTask.fork();
    leftTask.join();
    rightTask.join();
  }


  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //
    int[] nums = QuickSort.getRandomArray(1000000, Integer.MAX_VALUE);
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    long start = System.currentTimeMillis();
    QuickSortTask quickSortTask = new QuickSortTask(nums, 0, nums.length - 1);
    ForkJoinTask<Void> task = forkJoinPool.submit(quickSortTask);
    task.get();
    System.out.println(System.currentTimeMillis() - start);
//    Arrays.stream(nums).forEach(e->System.out.print(e+" "));
  }
}
