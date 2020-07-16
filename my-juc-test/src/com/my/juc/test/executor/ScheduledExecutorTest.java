package com.my.juc.test.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试定时任务线程池
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ScheduledExecutorTest {

    public static void main(String[] args) {
        int initialDelay = 5;
        System.out.println(Thread.currentThread().getName() + " " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        //以上一个任务开始的时间计时，period时间过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
        executorService.scheduleAtFixedRate(()->{
            System.out.println(Thread.currentThread().getName() + " " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },initialDelay,5, TimeUnit.SECONDS);

        //以上一个任务结束时开始计时，period时间过去后，立即执行
        executorService.scheduleWithFixedDelay(()->{
            System.out.println(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },initialDelay,5, TimeUnit.SECONDS);
        //执行一次延时任务
        executorService.schedule(new RunnableTask(),5,TimeUnit.SECONDS);
    }
}
