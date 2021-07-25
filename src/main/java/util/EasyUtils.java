package util;

import java.util.Random;

/**
 * @date 2021/7/16 23:30
 */
public class EasyUtils {
    private EasyUtils() throws IllegalAccessException {
        throw new IllegalAccessException("工具类不能实例化");
    }

    public static final int MOD = 1000000007;

    public static int[] getRandomArray(int len, int max) {
        int[] arr = new int[len];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(max);
        }
        return arr;
    }

    public static void swapInt(int[] arr, int i, int j) {
        int t = arr[j];
        arr[j] = arr[i];
        arr[i] = t;
    }

    public static boolean checkUpIntSequence(int[] arr) {
        for(int i=0;i< arr.length-1;i++) {
            if (arr[i+1] < arr[i]) {
                return false;
            }
        }
        return true;
    }

}
