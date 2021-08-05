package sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import static utils.EasyUtil.*;


/**
 * 2020/9/13
 *
 * @author comyxy
 */
public class Sort {

    private static final Logger log = LoggerFactory.getLogger(Sort.class);

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快排 增序
     *
     * @param arr 数组
     * @param lo  左边界
     * @param hi  右边界
     */
    public static void quickSort(int[] arr, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int j = partition(arr, lo, hi);
        quickSort(arr, lo, j - 1);
        quickSort(arr, j + 1, hi);
    }

    private static int partition(int[] arr, int lo, int hi) {
        int i = lo, j = hi + 1;
        int v = arr[lo];
        while (true) {
            // 找到一个大于v的
            while (arr[++i] < v) {
                if (i == hi) {
                    break;
                }
            }
            // 找到一个小于v的
            while (arr[--j] > v) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swapInt(arr, i, j);
        }
        swapInt(arr, lo, j);
        return j;
    }

    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) >>> 1;
        mergeSort(arr, lo, mid);
        mergeSort(arr, mid + 1, hi);
        merge(arr, lo, mid, hi);
    }

    private static void merge(int[] arr, int lo, int mid, int hi) {
        int[] buf = Arrays.copyOfRange(arr, lo, hi + 1);
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) {
            if (arr[i] <= arr[j]) {
                buf[k++] = arr[i++];
            } else {
                buf[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            buf[k++] = arr[i++];
        }
        while (j <= hi) {
            buf[k++] = arr[j++];
        }
        System.arraycopy(buf, 0, arr, lo, hi - lo + 1);
    }

    // 用排序解决其他问题

    /**
     * 计算逆序对 归并排序
     */
    public static int inversePairsCount(int[] arr) {
        class Inverse {
            int inversePair = 0;

            void mergeSort(int[] arr, int lo, int hi) {
                if (lo >= hi) {
                    return;
                }
                int mid = (lo + hi) >>> 1;
                mergeSort(arr, lo, mid);
                mergeSort(arr, mid + 1, hi);
                merge(arr, lo, mid, hi);
            }

            void merge(int[] arr, int lo, int mid, int hi) {
                int[] buf = Arrays.copyOfRange(arr, lo, hi + 1);
                int i = lo, j = mid + 1, k = 0;
                while (i <= mid && j <= hi) {
                    if (arr[i] <= arr[j]) {
                        buf[k++] = arr[i++];
                    } else {
                        buf[k++] = arr[j++];
                        inversePair += mid - i + 1;
                        inversePair %= MOD;
                    }
                }
                while (i <= mid) {
                    buf[k++] = arr[i++];
                }
                while (j <= hi) {
                    buf[k++] = arr[j++];
                }
                System.arraycopy(buf, 0, arr, lo, hi - lo + 1);
            }
        }
        Inverse in = new Inverse();
        in.mergeSort(arr, 0, arr.length - 1);
        return in.inversePair;
    }

    /**
     * 计算在上下界范围里区间和的个数 归并排序
     */
    public static int countRangeSum(int[] arr, int lower, int upper) {
        class RangeSum {
            int mergeSort(int[] sum, int lo, int hi, int lower, int upper) {
                if (hi <= lo) {
                    return 0;
                }
                int mid = (lo + hi) >>> 1;
                int lv = mergeSort(sum, lo, mid, lower, upper);
                int rv = mergeSort(sum, mid + 1, hi, lower, upper);
                int ret = lv + rv;

                // 统计在范围内的区间和个数
                // 区间[lo, mid] [mid+1, hi] 都已排序
                int i = lo, l = mid + 1, r = mid + 1;
                while (i <= mid) {
                    while (l <= hi && sum[l] - sum[i] < lower) {
                        l++;
                    }
                    while (r <= hi && sum[r] - sum[i] <= upper) {
                        r++;
                    }
                    // [l, r)
                    ret += r - l;
                    i++;
                }

                // merge
                merge(sum, lo, mid, hi);
                return ret;
            }

            void merge(int[] sum, int lo, int mid, int hi) {
                int[] buf = Arrays.copyOfRange(sum, lo, hi + 1);
                int i = lo, j = mid + 1, k = 0;
                while (i <= mid && j <= hi) {
                    if (sum[i] <= sum[j]) {
                        buf[k++] = sum[i++];
                    } else {
                        buf[k++] = sum[j++];
                    }
                }
                while (i <= mid) {
                    buf[k++] = sum[i++];
                }
                while (j <= hi) {
                    buf[k++] = sum[j++];
                }
                System.arraycopy(buf, 0, sum, lo, hi - lo + 1);
            }
        }
        RangeSum rs = new RangeSum();
        // 前缀和
        int s = 0;
        int[] sum = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            s += arr[i];
            sum[i + 1] = s;
        }
        return rs.mergeSort(sum, 0, sum.length - 1, lower, upper);
    }

    // 多线程排序任务

    static class SortTask extends RecursiveAction {
        final int[] arr;
        final int lo, hi;

        SortTask(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        SortTask(int[] arr) {
            this(arr, 0, arr.length - 1);
        }

        @Override
        protected void compute() {
            if (hi - lo < THRESHOLD) {
                sortSequentially(lo, hi);
            } else {
                int mid = (lo + hi) >>> 1;
                invokeAll(new SortTask(arr, lo, mid),
                        new SortTask(arr, mid, hi));
                merge(lo, mid, hi);
            }
        }

        static final int THRESHOLD = 1000;

        private void sortSequentially(int lo, int hi) {
            Sort.quickSort(arr, lo, hi);
        }

        private void merge(int lo, int mid, int hi) {
            int[] buf = Arrays.copyOfRange(arr, lo, mid);
            for (int i = 0, j = lo, k = mid; i < buf.length; j++) {
                arr[j] = (k == hi || buf[i] < arr[k]) ?
                        buf[i++] : arr[k++];
            }
        }
    }

    public static void main(String[] args) {
        int[] origin = getRandomArray(1000000, 1000000);
        int[] nums1 = Arrays.copyOf(origin, origin.length);
        long start = System.currentTimeMillis();
        Sort.quickSort(nums1);
        System.out.println(System.currentTimeMillis() - start);
//        Arrays.stream(nums1).forEach(e -> System.out.print(e + " "));

        int[] nums2 = Arrays.copyOf(origin, origin.length);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        start = System.currentTimeMillis();
        ForkJoinTask<Void> result = forkJoinPool.submit(new SortTask(nums2));
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
//        Arrays.stream(nums2).forEach(e -> System.out.print(e + " "));

        int[] nums3 = Arrays.copyOf(origin, origin.length);
        start = System.currentTimeMillis();
        mergeSort(nums3);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(checkUpIntSequence(nums3));
//        Arrays.stream(nums3).forEach(e -> System.out.print(e + " "));

        int[] nums4 = new int[]{9, 8, 7};
        System.out.println(inversePairsCount(nums4));

        int[] nums5 = new int[]{-2, 5, -1};
        System.out.println(countRangeSum(nums5, -2, 2));
    }
}
