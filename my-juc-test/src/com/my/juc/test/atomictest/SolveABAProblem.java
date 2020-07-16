package com.my.juc.test.atomictest;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决ABA问题
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class SolveABAProblem {

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(10, 0);

    public static void main(String[] args) {
        Thread owner = new Thread(() -> {
            int money = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            System.out.println("第一次查看金库,金额：" + money + ",版本号：" + stamp);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("准备更新金库金额");
            if (atomicStampedReference.compareAndSet(money, 20, stamp, stamp + 1))
                System.out.println("更新金库成功，金库剩余金额:" + atomicStampedReference.getReference() + " 版本号：" + atomicStampedReference.getStamp());
            else
                System.err.println("更新失败，有人动了我的金库！");

        });

        Thread stealer = new Thread(() -> {
            int[] stamp = new int[1];
            int current = atomicStampedReference.get(stamp);
            int remain = 7;
            boolean success = atomicStampedReference.compareAndSet(current, remain, stamp[0], stamp[0] + 1);
            if(success){
                System.out.println("准备偷取金库，金库剩余金额" + remain);
                //使用金额做点别的事，比如炒一波股
                success =  atomicStampedReference.compareAndSet(remain, current, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                if(success){
                    System.out.println("将金额还回金库，金库剩余金额" + atomicStampedReference.getReference());
                }else {
                    System.err.println("完了，还回失败了");
                }
            }else {
                System.err.println("偷取失败了");
            }


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
