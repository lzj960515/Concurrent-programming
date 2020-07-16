package com.my.juc.test.collection;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 测试阻塞队列
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
//        LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>();
//        linkedBlockingQueue.put(new Object());
//        Object take = linkedBlockingQueue.take();
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>(true);
//        System.out.println("准备使用put方式放入元素");
//        synchronousQueue.put(new Object());
//        System.out.println("元素已放入");
        System.out.println("获取一个元素");
        Object take = synchronousQueue.take();
        System.out.println("获取元素"+take);

        System.out.println("准备使用offer方式放入元素");
        boolean offer = synchronousQueue.offer(new Object());
        System.out.println("元素是否放入："+offer);
//        Object take1 = synchronousQueue.take();

//        PriorityBlockingQueue<Ticket> priorityBlockingQueue = new PriorityBlockingQueue<>(1);
//        priorityBlockingQueue.add(new Ticket());
//        priorityBlockingQueue.add(new Ticket());
//
//        Object take3 = priorityBlockingQueue.take();

    }
}
