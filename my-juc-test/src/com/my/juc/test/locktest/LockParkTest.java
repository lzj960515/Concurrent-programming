package com.my.juc.test.locktest;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 测试 LockSupport 方法响应中断
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class LockParkTest {

    public static void main(String[] args) {
        new LockParkTest().testLockSupport();
    }

    public void testLockSupport(){
        Thread t1 = new Thread(()->{
            System.out.println("中断线程");
            LockSupport.park(this);
            System.out.println("中断已停止，线程状态是否为中断"+Thread.interrupted());
        },"线程1");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }
}
