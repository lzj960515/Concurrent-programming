package com.my.juc.test.locktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ReadWriteLockTest {
    public static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(ReadWriteLockTest::testReadLock,"Thread-"+1).start();
            new Thread(ReadWriteLockTest::testWriteLock,"Thread-"+2).start();
            TimeUnit.SECONDS.sleep(1);
//        }
    }

    public static void testReadLock() {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 得到了读锁");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 准备释放读锁");
        reentrantReadWriteLock.readLock().unlock();
    }
    public static void testWriteLock() {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 得到了写锁");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 准备释放写锁");
        reentrantReadWriteLock.writeLock().unlock();
    }
}
