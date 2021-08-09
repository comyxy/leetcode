package concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RWLock {
    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writingAccesses = 0;

    private int writingRequests = 0;

    private Thread writingThread = null;

    public synchronized void readLock() throws InterruptedException {
        Thread t = Thread.currentThread();
        while (!canRead(t)) {
            wait();
        }

        readingThreads.put(t, readingThreads.getOrDefault(t, 0) + 1);
    }

    public synchronized void readUnlock() {
        Thread t = Thread.currentThread();
        Integer readTimes = readingThreads.getOrDefault(t, 0);
        if (readTimes.equals(1)) {
            readingThreads.remove(t);
        } else if (readTimes.compareTo(1) > 0) {
            readingThreads.put(t, readTimes - 1);
        }
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        Thread t = Thread.currentThread();
        writingRequests++;
        while (!canWrite(t)) {
            wait();
        }
        writingRequests--;
        writingAccesses++;
        writingThread = t;
    }

    public synchronized void writeUnlock() {
        writingAccesses--;
        if (writingAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canWrite(Thread t) {
        if (hasReader()) {
            return false;
        }
        if (writingThread == null) {
            return true;
        }
        if (writingThread != t) {
            return false;
        }
        return true;
    }

    private boolean hasReader() {
        return readingThreads.size() > 0;
    }

    private boolean canRead(Thread t) {
        if (writingThread != null) {
            return false;
        }
        if (inReadingThreads(t)) {
            return true;
        }
        if (writingAccesses > 0) {
            return false;
        }
        return true;
    }

    private boolean inReadingThreads(Thread t) {
        return readingThreads.get(t) != null;
    }

    public static void main(String[] args) throws InterruptedException {
        RWLock rwLock = new RWLock();

        ExecutorService es = Executors.newScheduledThreadPool(10);
        es.submit(() -> {
            try {
                rwLock.readLock();
                for (int i = 0; i < 10; i++) {
                    System.out.println("r1 " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.readUnlock();
            }
        });

        Thread.sleep(200);
        es.submit(() -> {
            try {
                rwLock.writeLock();
                System.out.println("w1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.writeUnlock();
            }
        });

        es.submit(() -> {
            try {
                rwLock.readLock();
                for (int i = 0; i < 4; i++) {
                    System.out.println("r2 " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.readUnlock();
            }
        });

        es.shutdown();
    }
}