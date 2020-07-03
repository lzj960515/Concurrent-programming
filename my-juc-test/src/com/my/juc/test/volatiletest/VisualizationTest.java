package com.my.juc.test.volatiletest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试 volatile可视化
 * @author Zijian Liao
 */
public class VisualizationTest {

    public static
    volatile //若不加volatile修饰，则线程1一直运行
    boolean flag = true;
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        String name = "Thread-1";
        new Thread(()->{
            System.out.println(name + " 启动中......");
            while (flag){
                /*lock.lock(); //加锁，使得发生上下文切换,将变量重新从主内存读取
                lock.unlock();*/
//                synchronized (VisualizationTest.class){}
            }
            System.out.println(name + " 执行完毕......");
        },name).start();

        TimeUnit.SECONDS.sleep(1);

        String name2 = "Thread-2";
        new Thread(()->{
            flag = false;
            System.out.println(name2 + " 修改静态变量flag:"+flag);
        }, name2).start();
    }
}
