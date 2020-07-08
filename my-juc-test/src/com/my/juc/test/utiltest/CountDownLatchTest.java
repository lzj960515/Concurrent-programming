package com.my.juc.test.utiltest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Zijian Liao
 * @since 1.0
 */
public class CountDownLatchTest {

    public static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(CountDownLatchTest::countDownLatchTest, "thread-" + i).start();
            }
        }).start();

        System.out.println("等待任务结束");
        //当countDown数达到CountDownLatch初始化值，则结束等待
        countDownLatch.await();
        System.out.println("任务结束");

    }

    public static void countDownLatchTest() {
        try {

            System.out.println(Thread.currentThread().getName()+"开始执行任务");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.err.println(Thread.currentThread().getName()+"执行任务完毕");
            countDownLatch.countDown();
        }
    }
}
