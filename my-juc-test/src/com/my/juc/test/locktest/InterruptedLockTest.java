package com.my.juc.test.locktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试可中断锁
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class InterruptedLockTest {
    public static ReentrantLock LOCK = new ReentrantLock(true);


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + "尝试加锁");
                InterruptedLockTest.testInterrupted();
                System.out.println(Thread.currentThread().getName() + "解锁");
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + "已中断");
                e.printStackTrace();
            }
        },"线程1");

        Thread t2 = new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + "尝试加锁");
                InterruptedLockTest.testInterrupted();
                System.out.println(Thread.currentThread().getName() + "解锁");
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + "已中断");
                e.printStackTrace();
            }
        },"线程2");

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("将线程2标记为中断状态");
        t2.interrupt();


    }

    public static void testInterrupted() throws InterruptedException {
//        LOCK.lockInterruptibly();
        LOCK.lock();
        System.out.println(Thread.currentThread().getName() + "加锁成功");
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        while (endTime - startTime < 10000){
            endTime = System.currentTimeMillis();
        }
        LOCK.unlock();
    }
}
