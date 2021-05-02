package concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author comyxy
 */
public class ReadWrite implements Runnable{
    /**
     * 控制对rc的访问
     */
    private static Semaphore mutex = new Semaphore(1);

    /**
     * 控制对database的访问
     */
    private static Semaphore db = new Semaphore(1);

    private static int rc = 0;

    private static int val = 100;

    private int kind;

    public ReadWrite(int kind) {
        this.kind = kind;
    }

    @Override
    public void run() {
        if(this.kind == 0) {
            reader();
        }else{
            writer();
        }
    }

    private void writer() {
        while (true) {
            int newVal = new Random().nextInt();
            db.acquireUninterruptibly();
            val = newVal;
            System.out.println("writer\t" + val);
            db.release();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void reader() {
        while(true) {
            mutex.acquireUninterruptibly();
            rc += 1;
            if(rc == 1) {
                db.acquireUninterruptibly();
            }
            mutex.release();

            System.out.println("read\t" + val);

            mutex.acquireUninterruptibly();
            rc -= 1;
            if(rc == 0) {
                db.release();
            }
            mutex.release();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(new ReadWrite(0)).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(new ReadWrite(1)).start();
        }
    }
}
