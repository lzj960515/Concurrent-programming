package com.my.juc.test.utiltest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Zijian Liao
 * @since 1.0
 */
public class CyclicBarrierTest {
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(11, () -> {
        System.out.println("hello");
    });

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //当await数达到CyclicBarrier初始化的parties值，则调用一次CyclicBarrier中的Run方法
                    cyclicBarrier.await();
                    System.out.println("ok");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        cyclicBarrier.await();
    }
}
