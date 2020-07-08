package com.my.juc.test.utiltest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * semaphore test 共享锁
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class Semaphoretest {

    public static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(Semaphoretest::semaphoreTest, "thread-" + i).start();
        }
    }

    public static void semaphoreTest() {
        try {
            //可加锁次数为semaphore初始值，若超过则等待释放锁
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+"获得了锁");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+"释放了锁");
            semaphore.release();
        }
    }
}
