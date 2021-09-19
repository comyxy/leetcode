package quiz;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @since 2021/9/10 18:55
 */
public class Ali {

    public static void main(String[] args) {
        int result = q1(0, 11);
        System.out.println(result);
    }

    private static int q1(int a, int b) {
        int t = Math.abs(a - b);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        int k = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer p = queue.poll();
                if (p == t) {
                    return k;
                }
                queue.offer(p + k + 1);
                queue.offer(p - k - 1);
            }
            k++;
        }
        return -1;
    }
}
