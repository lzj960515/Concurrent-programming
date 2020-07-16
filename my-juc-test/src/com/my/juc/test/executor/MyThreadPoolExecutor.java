package com.my.juc.test.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池
 * 核心关键点，包装任务，阻塞队列
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class MyThreadPoolExecutor implements Executor {

    private AtomicInteger number = new AtomicInteger(1);
    /**
     * 包装任务
     */
    private class Worker implements Runnable {
        Thread thread;
        Runnable firstTask;

        Worker(Runnable runnable) {
            this.firstTask = runnable;
            this.thread = new Thread(this, "my-thread-pool-" + number.getAndIncrement());
        }

        @Override
        public void run() {
            runWorker(this);
        }
    }

    /**
     * 阻塞队列
     */
    private ArrayBlockingQueue<Runnable> workerQueue;
    /**
     * 线程数
     */
    private int capacity;

    private AtomicInteger count = new AtomicInteger();

    public MyThreadPoolExecutor(int capacity, int queueSize) {
        this.capacity = capacity;
        workerQueue = new ArrayBlockingQueue<>(queueSize);
    }


    @Override
    public void execute(Runnable command) {
        //1.如果线程池里面的线程数小于线程池容量
        if (count.get() < capacity){
            addWorker(command);
        }else if(!workerQueue.offer(command)) //否则将任务加入到队列
           //加入队列失败
            System.err.println("队列已满");

    }

    private void addWorker(Runnable command){
        count.incrementAndGet();
        //将任务封装到自定义worker中，并启动
        Worker worker = new Worker(command);
        worker.thread.start();
    }

    private void runWorker(Worker worker){
        Runnable task = worker.firstTask;
        worker.firstTask = null;//help gc
        //处理真正的任务
        while(task != null || (task = getTask())!=null){
            task.run();
            task = null;
        }
    }
    /**
     * 从阻塞队列中取，若没有任务，则陷入阻塞等待
     * @return {@link Runnable}
     */
    private Runnable getTask(){
        try {
            return workerQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
