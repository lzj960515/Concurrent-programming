package com.my.juc.test.collection;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 测试延时队列
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {
//        List<Ticket> list = new ArrayList<>();
//        list.add(new Ticket("票据1", 10000));
//        list.add(new Ticket("票据2", 15000));
//        list.add(new Ticket("票据3", 5000));
//        for(int i =0;i<list.size();){
//            System.out.println(list.get(i).name);
//            list.remove(i);
//        }
        DelayQueue<Ticket> delayQueue = new DelayQueue<>();
        delayQueue.add(new Ticket("票据1", 10000));
        delayQueue.add(new Ticket("票据2", 15000));
        delayQueue.add(new Ticket("票据3", 5000));
        while (delayQueue.size() > 0) {
            System.out.println(delayQueue.take().name);
        }
    }
}

class Ticket implements Delayed {

    public String name;
    /**
     * 过期时间（毫秒）
     */
    public long expireTime;

    public Ticket() {
    }

    public Ticket(String name, long delayTime) {
        this.name = name;
        this.expireTime = System.currentTimeMillis() + delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
