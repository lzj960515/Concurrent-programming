package com.my.juc.test.volatiletest;

/**
 * @author Zijian Liao
 * @since 1.0
 */
public class ReOrderTest2 {
    public static int a = 0, b = 0;
    public static int x = 0, y = 0;
    public static void main(String[] args) throws InterruptedException {
//        a=0;
        Thread t1 = new Thread(() -> {
            System.out.println("线程1开始执行");
            nanosWait(20000);//由于线程1先执行，等一会线程2

            a = 1;
//            while (true){}
            System.out.println("线程1执行完毕");
            nanosWait(1000000000);
        });
        Thread t2 = new Thread(() -> {
            System.out.println("线程2开始执行");
             a = 0;
//            System.out.println("a:"+a);
            nanosWait(30000);
//            while (a == 0){}
            System.out.println("a:"+a);
            System.out.println("线程2执行完毕");
        });
        t1.start();
//        t1.join();
        t2.start();
//        nanosWait(2700);
//        System.out.println("主线程a:"+a);
    }

    public static void nanosWait(long time) {
//        long start = System.nanoTime();
//        while (System.nanoTime() - start < time){}
        for(int i =0;i<time;i++ ){}
    }
}
