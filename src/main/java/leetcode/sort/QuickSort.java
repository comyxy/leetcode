package leetcode.sort;

import java.util.Arrays;
import java.util.stream.IntStream;

/** 2020/9/13 */
public class QuickSort {
  public static int[] getRandomArray(int len, int max) {
    int[] arr = new int[len];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (int) (Math.random() * max);
    }
    return arr;
  }

  /**
   * 增序
   *
   * @param arr 数组
   * @param left 左边界
   * @param right 右边界
   */
  public void quickSort(int[] arr, int left, int right) {
    if (left >= right) {
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
    quickSort(arr, left, j - 1);
    quickSort(arr, j + 1, right);
  }

  public static void main(String[] args) {
    //
    int[] nums = QuickSort.getRandomArray(1000000, Integer.MAX_VALUE);
    long start = System.currentTimeMillis();
    QuickSort sort = new QuickSort();
    sort.quickSort(nums, 0, nums.length - 1);
    System.out.println(System.currentTimeMillis() - start);
//    Arrays.stream(nums).forEach(e -> System.out.print(e + " "));
  }
}
