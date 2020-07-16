package com.my.juc.test.executor;

/**
 * 无返回值任务
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class RunnableTask implements Runnable {
    @Override
    public void run() {
        System.out.println("runnable 任务执行了,执行的线程是 "+ Thread.currentThread().getName());
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("任务执行完毕，执行的线程是 "+Thread.currentThread().getName());
    }
}
