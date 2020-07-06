package com.my.juc.test.volatiletest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试 volatile可视化
 *
 * @author Zijian Liao
 */
public class VisualizationTest {

    public static
//    volatile //若不加volatile修饰，则线程1一直运行
            boolean flag = true;
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        String name = "Thread-1";
        new Thread(() -> {
            System.out.println(name + " 启动中......");
            while (flag) {
                /*lock.lock(); //加锁，使得发生上下文切换,将变量重新从主内存读取
                lock.unlock();*/
//                synchronized (VisualizationTest.class){}
                for (int i = 0; i < 1000000; i++) {
                    new User();
                }
//                long startTime = System.currentTimeMillis();
//                long endTime = System.currentTimeMillis();
//                while (endTime - startTime < 5000) {
//                    endTime = System.currentTimeMillis();
//                }
//            if(flag){
//                System.err.println(name + " 执行完毕......flag=true");
//            }else{
//                System.err.println(name + " 执行完毕......flag=false");
//            }
            }
            System.err.println(name + " 执行完毕......");

        }, name).start();

        TimeUnit.SECONDS.sleep(1);
        flag = false;
        System.out.println(" 修改静态变量flag:"+flag);
        for (int i = 0; i < 10000; i++) {//强迫线程1发生上下文切换
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    new User();
                }
                System.out.println(Thread.currentThread().getName()+"执行完毕");
            }, "Thread" + i).start();
        }
//        long startTime = System.currentTimeMillis();
//        long endTime = System.currentTimeMillis();
//        while (endTime - startTime < 100) {
//            endTime = System.currentTimeMillis();
//        }
//        System.out.println(flag);
//
//        String name2 = "Thread-2";

    }
}
class User {

    byte[] bytes = new byte[1024];
}
