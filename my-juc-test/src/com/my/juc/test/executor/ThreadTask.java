package com.my.juc.test.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 测试创建一般线程
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ThreadTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = new RunnableTask();
        Thread t1 = new Thread(runnable);
        t1.start();
        FutureTask<String> futureTask = new FutureTask<>(new CallableTask());
        Thread t2 = new Thread(futureTask);
        t2.start();
        System.out.println(futureTask.get());
    }
}
