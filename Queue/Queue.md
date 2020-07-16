## 写时复制List `CopyOnWriteArrayList`

```java
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();
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
        }
    }
}
```

## 延时队列 `DelayQueue`

```java
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
```

## 阻塞队列

```java
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
```

## 自定义阻塞队列

```java
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
}
```

