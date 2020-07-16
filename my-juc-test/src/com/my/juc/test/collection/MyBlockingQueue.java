package com.my.juc.test.collection;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义阻塞队列
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class MyBlockingQueue<T> {

    int count;
    //拿元素指针
    int head;
    //放元素指针
    int tail;
    Object[] data;
    public MyBlockingQueue(int capacity){
        data = new Object[capacity];
    }
    /**********锁机制***********/
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public void put(T t) throws InterruptedException {
        try {
            //可中断锁
            lock.lockInterruptibly();
            if(count == data.length){
                //1.将当前结点加入条件队列
                //2.释放锁唤醒同步队列的结点
                //3.陷入等待
                //4.被唤醒后获取锁
                notFull.await();
            }
            data[tail] = t;
            if(++tail == data.length){
                tail = 0;
            }
            count++;
            //将notEmpty中条件队列的结点转移到lock的同步队列
            notEmpty.signal();
        }finally {
            //唤醒下一个同步队列中的结点
            lock.unlock();
        }

    }

    @SuppressWarnings("unchecked")
    public T take() throws InterruptedException {
        try {
            lock.lockInterruptibly();
            if(count == 0){
                //1.将当前结点加入条件队列
                //2.释放锁唤醒同步队列的结点
                //3.陷入等待
                //4.被唤醒后获取锁
                notEmpty.await();
            }
            T t = (T) data[head];
            data[head] = null;
            if(++head == data.length){
                head = 0;
            }
            count--;
            //将notFull中条件队列的结点转移到lock的同步队列
            notFull.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyBlockingQueue<Boll> myBlockingQueue = new MyBlockingQueue<>(1);
        Thread t1 = new Thread(() -> {
            int i = 0;
            for (; ; i++) {
                try {
                    System.out.println("准备放入编号为" + i + "的球");
                    myBlockingQueue.put(new Boll(i));
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
                    Boll boll = myBlockingQueue.take();
                    System.out.println("拿到了编号为" + boll.number + "的球");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }


}
