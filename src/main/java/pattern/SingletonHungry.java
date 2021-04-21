package pattern;

import java.util.concurrent.*;

/**
 * @author comyxy
 */
public class SingletonHungry {
    private static final SingletonHungry INSTANCE = new SingletonHungry();

    private int value = 0;

    private SingletonHungry() {
    }

    public static SingletonHungry getInstance() {
        return INSTANCE;
    }

    public void add1() {
        value++;
    }

    public int getValue(){
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        SingletonHungry ins1 = getInstance();
        SingletonHungry ins2 = getInstance();

        System.out.println(ins1.getValue());
        ins1.add1();
        System.out.println(ins2.getValue());

        // race condition
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10000;i++){
                    ins1.add1();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10000;i++){
                    ins2.add1();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(ins1.getValue());
    }
}
