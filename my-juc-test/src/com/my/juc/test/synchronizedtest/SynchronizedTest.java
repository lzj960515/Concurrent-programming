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

    public static final Object object = new Object();

    //synchronized修饰非静态方法，锁的是当前实例对象
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
        //锁的是括号内的对象
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
    //synchronized修饰静态方法，锁的是该方法的class对象
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
