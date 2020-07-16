package com.my.juc.test.atomictest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试ABA问题
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class AtomicABAProblem {

    private static AtomicInteger atomicInteger = new AtomicInteger(10);

    public void test(){};

    public static void main(String[] args) {
        Thread owner = new Thread(() -> {
            int money = atomicInteger.get();
            System.out.println("第一次查看金库,金额：" + money);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("准备更新金库金额");
            if(atomicInteger.compareAndSet(money, 20))
                System.out.println("更新金库成功，金库剩余金额:"+atomicInteger.get());
            else
                System.err.println("更新失败，有人动了我的金库！");

        });

        Thread stealer = new Thread(() -> {
            int money = atomicInteger.addAndGet(-3);
            System.out.println("准备偷取金库，金库剩余金额" + money);
            //使用金额做点别的事，比如炒一波股
            money = atomicInteger.addAndGet(3);
            System.out.println("将金额还回金库，金库剩余金额" + money);
        });
        owner.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stealer.start();
    }
}
