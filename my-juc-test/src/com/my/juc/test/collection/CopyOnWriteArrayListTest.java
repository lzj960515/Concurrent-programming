package com.my.juc.test.collection;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 测试写时复制队列
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) throws InterruptedException {
        /*List<Integer> list = new CopyOnWriteArrayList<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 100000; i < 200000; i++) {
                list.add(i);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            System.out.println("第" + i + "个元素是：" + list.get(i));
        }*/

        Integer[] arr = {0,1,2,3,4,5};
        /*CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(arr);
        new Thread(()->{
            list.get(5);
        }).start();
        new Thread(()->{
            list.add(6);
        }).start();*/
        Integer[] copy = new Integer[arr.length+1];
        System.arraycopy(arr, 0, copy,0,Math.min(arr.length,copy.length));
        copy[0]=10;
        System.out.println(arr[0]);

    }
}
