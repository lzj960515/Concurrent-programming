package com.my.juc.test.synchronizedtest;

import java.util.concurrent.TimeUnit;

/**
 * 测试synchronized关键字
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class SynchronizedTest {

    public static SynchronizedTest synchronizedTest = new SynchronizedTest();
    Class<SynchronizedTest> synchronizedTestClass = SynchronizedTest.class;

    public static final Object object = new Object();

    //synchronized修饰方法，锁的是当前类对象
    public synchronized void syncMethod(){
        System.out.println(Thread.currentThread().getName() + " come! ");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " leave! ");
    }

    public synchronized void syncMethod2(){
        System.out.println(Thread.currentThread().getName() + " come! ");
    }

    public void syncObject(){
        synchronized(object){
            System.out.println(Thread.currentThread().getName() + " come! ");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " leave! ");
        }
    }

    public void syncClass(){
        synchronized(SynchronizedTest.class){
            System.out.println(Thread.currentThread().getName() + " come! ");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " leave! ");
        }

    }

    public static synchronized void staticSyncMethod(){
        System.out.println(Thread.currentThread().getName() + " come! ");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " leave! ");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            SynchronizedTest.staticSyncMethod();
//            synchronizedTest.syncMethod();
        }, "Thread-1");
        Thread t2 = new Thread(()->{
            synchronizedTest.syncClass();
        }, "Thread-2");
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t2.start();
    }

}
