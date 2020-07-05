package com.my.juc.test.volatiletest;

/**
 * 测试指令重排 (该测试存在问题)
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ReOrderTest {

    public
    volatile //不加volatile则将出现x=0,y=0的情况
    static int a = 0, b = 0;
    public static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        long count = 0;
        for (; ; ) {
            count++;
            a = b = x = y = 0;
            Thread t1 = new Thread(() -> {
                nanosWait(50000);//由于线程1先执行，等一会线程2
                a = 1;
                x = b;
            });
            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });
            //x = 0, y = 1
            //x = 1, y = 0
            //x = 1, y = 1
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (x == 0 && y == 0) {
                System.err.println("第" + count + "次循环，x=" + x + ",y=" + y);
                return;
            } else
                System.out.println("第" + count + "次循环，x=" + x + ",y=" + y);
        }
    }

    public static void nanosWait(long time) {
        long start = System.nanoTime();
        while (System.nanoTime() - start < time){}
    }
}
