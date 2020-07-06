package com.my.juc.test.volatiletest;

import java.util.concurrent.TimeUnit;

/**
 * 测试上下文切换  测试结果：自发性的线程上下文切换并不会导致工作内存中的数据刷回主内存
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class SwitchContextTest {

    public static boolean FLAG = true;
    public static volatile boolean FLAG2 = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("开始执行线程1");
            while (FLAG){
                nanosWait(100000);
            }
            System.out.println("线程1执行结束");
        });


        Thread t2 = new Thread(()->{
            System.out.println("开始执行线程2");
            FLAG = false;
            while (FLAG2){
                nanosWait(100000);
            }
            System.out.println("线程2执行结束");
        });

        Thread t3 = new Thread(()->{
            System.out.println("开始执行线程3");
            FLAG = false;
            while (FLAG2){
                nanosWait(100000);
            }
            System.out.println("线程3执行结束");
        });

        Thread t4 = new Thread(()->{
            System.out.println("开始执行线程4");
            FLAG = false;
            while (FLAG2){
                nanosWait(100000);
            }
            System.out.println("线程4执行结束");
        });

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        System.out.println("线程1执行结束，将FLAG2改为false");
        FLAG2 = false;
        t2.join();
        System.out.println("主线程结束");
    }

    public static void nanosWait(long time) {
        for(int i =0;i<time;i++ ){}
    }

}
