package com.my.juc.test.locktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试锁
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ReentrantLockTest {
    public static ReentrantLock LOCK = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(ReentrantLockTest::testLock,"Thread-"+i).start();
        }
    }

    public static void testLock() {
        LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 得到了锁");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName() + " 准备释放锁");
            LOCK.unlock();
        }

    }
}
