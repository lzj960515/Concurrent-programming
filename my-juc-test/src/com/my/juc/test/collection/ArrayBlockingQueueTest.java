package com.my.juc.test.collection;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 测试阻塞队列
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<Boll> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
        Thread t1 = new Thread(() -> {
            int i = 0;
            for (; ; i++) {
                try {
                    System.out.println("准备放入编号为" + i + "的球");
                    arrayBlockingQueue.put(new Boll(i));
                    System.out.println("已放入编号为" + i + "的球");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (; ; ) {
                try {
                    System.out.println("准备拿一个球");
                    Boll boll = arrayBlockingQueue.take();
                    System.out.println("拿到了编号为" + boll.number + "的球");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
}

class Boll {
    int number;

    public Boll(int number) {
        this.number = number;
    }

}