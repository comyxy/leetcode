package concurrent;

import java.util.concurrent.RecursiveAction;

/**
 * @since 2021/8/30 16:14
 */
public class PrintTask extends RecursiveAction {

    private static final int THRESHOLD = 10;

    // [left, right)
    private final int start;
    private final int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
        else {
            int mid = (start + end) / 2;
            PrintTask leftTask = new PrintTask(start, mid);
            PrintTask rightTask = new PrintTask(mid, end);
            invokeAll(leftTask, rightTask);
        }
    }
}
