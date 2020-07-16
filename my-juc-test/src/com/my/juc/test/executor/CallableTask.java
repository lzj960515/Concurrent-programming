package com.my.juc.test.executor;

import java.util.concurrent.Callable;

/**
 * 有返回值任务
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class CallableTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("callable 任务执行了");
        Thread.sleep(2000);
        return "ok";
    }
}
