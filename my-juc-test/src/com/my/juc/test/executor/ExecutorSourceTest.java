package com.my.juc.test.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试线程池源码
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ExecutorSourceTest {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;



    static class Person{
        Person person;
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.person =  person;
        System.out.println();
    }

    private void testBinary(){
        int a = 1;
        System.out.println(Integer.toBinaryString(~1));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-3));
        System.out.println(Integer.toBinaryString(-4));
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.toBinaryString(-6));
        System.out.println(Integer.toBinaryString(-7));
        System.out.println(Integer.toBinaryString(-8));
        System.out.println(RUNNING | 0);
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));
        System.out.println(Integer.toBinaryString(TERMINATED));

        System.out.println(Integer.toBinaryString(RUNNING).length());
        System.out.println(Integer.toBinaryString(SHUTDOWN).length());
        System.out.println(Integer.toBinaryString(STOP).length());
        System.out.println(Integer.toBinaryString(TIDYING).length());
        System.out.println(Integer.toBinaryString(TERMINATED).length());
    }

    private void testDefaultThreadFactory(){
        Runnable r = new RunnableTask();
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
//        threadFactory.newThread(new RunnableTask());
        AtomicInteger poolNumber = new AtomicInteger(1);
        AtomicInteger threadNumber = new AtomicInteger(1);
        SecurityManager s = System.getSecurityManager();
        ThreadGroup  group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
       String  namePrefix = "pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        System.out.println(t.getName());
    }

}
