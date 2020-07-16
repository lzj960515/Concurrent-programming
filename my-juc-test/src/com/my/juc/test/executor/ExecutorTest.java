package com.my.juc.test.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试线程池
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ExecutorTest {

    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        ExecutorService executor = Executors.newCachedThreadPool();//若无空闲线程处理，则一直添加线程
        Executor executor = new MyThreadPoolExecutor(5,20);
        Runnable runnable = new RunnableTask();
        for(int i = 0;i<20;i++){
            executor.execute(runnable);
        }
//        executor.shutdown();
//        while (!executor.isShutdown()){}
//        long taskCount = ((ThreadPoolExecutor) executor).getTaskCount();
//        System.out.println("任务总数+"+taskCount);
//        long completedTaskCount = ((ThreadPoolExecutor) executor).getCompletedTaskCount();
//        System.out.println("完成任务总数+"+completedTaskCount);

//        executor.shutdown();
//        executor.shutdownNow();
    }
}
