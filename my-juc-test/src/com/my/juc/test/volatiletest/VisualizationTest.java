package com.my.juc.test.volatiletest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 测试 volatile可视化
 * @author Zijian Liao
 */
public class VisualizationTest {

    public static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        String name = "Thread-1";
        new Thread(()->{
            System.out.println(name + " 启动中......");
            while (flag){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前静态变量 flag:"+flag);
            }
            System.out.println(name + " 执行完毕......");
        },name).start();

        TimeUnit.SECONDS.sleep(1);

        String name2 = "Thread-2";
        new Thread(()->{
            flag = false;
            System.out.println(name2 + " 修改静态变量flag:"+flag);
        }, name2).start();
       Thread t = new Thread();
       LockSupport.parkUntil();
       LockSupport.parkNanos();
    }
}
