package com.my.juc.test.atomictest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试整型原子操作
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class AtomicIntegerTest {

    public static AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    public static volatile int number = 0;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list1 = new ArrayList<>(10);
        List<Thread> list2 = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Thread t =  new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ATOMIC_INTEGER.incrementAndGet();
                }
            }, "Thread-" + i);
            list1.add(t);
        }

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    number++;
                }
            }, "Thread-" + i);
            list2.add(t);
        }
        list1.forEach(Thread::start);
        list2.forEach(Thread::start);

        TimeUnit.SECONDS.sleep(1);
        System.out.println(ATOMIC_INTEGER.get());
        System.out.println(number);

    }

}
