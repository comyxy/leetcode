package concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author comyxy
 */
public class Philosopher implements Runnable {
    private static final int N = 5;
    /**
     * 记录每个人的状态
     */
    private static State[] state = new State[N];

    /**
     * mutex 检查状态
     */
    private static Semaphore mutex = new Semaphore(1);

    /**
     * 记录临界资源
     */
    private static Semaphore[] s = new Semaphore[N];

    static {
        for (int i = 0; i < N; i++) {
            s[i] = new Semaphore(0);
        }
    }

    /**
     * 哲学家状态
     */
    enum State {
        // 思考
        THINKING,
        // 饥饿
        HUNGRY,
        // 恰饭
        EATING,
    }

    private static int left(int i) {
        return (i + N - 1) % N;
    }

    private static int right(int i) {
        return (i + 1) % N;
    }

    private final int index;

    public Philosopher(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(index + "\tthinking");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            takeForks(index);
            try {
                System.out.println(index + "\teating");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            putForks(index);
        }
    }

    /**
     * 放叉子
     * @param idx 位置
     */
    private void putForks(int idx) {
        // 拿到排他锁情况下检查和设置状态
        mutex.acquireUninterruptibly();
        state[idx] = State.THINKING;
        test(left(idx));
        test(right(idx));
        mutex.release();
    }

    /**
     * 拿叉子
     * @param idx 位置
     */
    private void takeForks(int idx) {
        // 获取排他锁的情况下检查并设置状态
        mutex.acquireUninterruptibly();
        state[idx] = State.HUNGRY;
        test(idx);
        mutex.release();
        // 没有在test函数中设置s[idx]会在这里block 只能通过其它idx来设置s[idx]
        s[idx].acquireUninterruptibly();
    }

    private void test(int idx) {
        if(state[idx] == State.HUNGRY && state[left(idx)] != State.EATING && state[right(idx)] != State.EATING){
            state[idx] = State.EATING;
            s[idx].release();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < Philosopher.N; i++) {
            new Thread(new Philosopher(i)).start();
        }
    }
}
