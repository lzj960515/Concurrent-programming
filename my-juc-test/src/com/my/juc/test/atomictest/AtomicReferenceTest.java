package com.my.juc.test.atomictest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 测试原子引用
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class AtomicReferenceTest {

    private static AtomicReference<User> atomicReference = new AtomicReference<>(new User());

    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                User user = atomicReference.get();
                wait(finalI*100000);//等待一会，模拟并发
                User user1 = new User();
                if (atomicReference.compareAndSet(user, user1)) {
                    count.incrementAndGet();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("修改成功次数：" + count.get());
    }

    public static void wait(final int time) {
        for (int i = 0; i < time; i++) {
        }
    }
}
