## Semaphore

Semaphore 字面意思是信号量的意思，它的作用是控制访问特定资源的线程数目。[获取释放资源详细流程](https://www.processon.com/view/link/5f0541656376891e810354b3)

### 简单使用

1. 构造方法

   - public Semaphore(int permits)
   - public Semaphore(int permits, boolean fair)

   > permits:可用的初始许可证数量，用来控制同时可加锁线程的数量
   >
   > fair:`true`表示创建一个公平锁，`false`表示创建一个非公平锁，默认创建非公平锁

2. 主要方法

   - acquire() 用于获取许可资源
   - release() 用于释放许可资源

3. 代码演示

应用场景：资源访问，服务限流。 

```java
public class Semaphoretest {

    public static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(Semaphoretest::semaphoreTest, "thread-" + i).start();
        }
    }

    public static void semaphoreTest() {
        try {
            //可加锁次数为semaphore初始值，若超过则等待释放锁
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+"获得了锁");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+"释放了锁");
            semaphore.release();
        }
    }
}
```

## CountDownLatch

CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行。例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有的框架服务之后再执行。

### 实现原理

CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。

### 简单使用

1. 构造方法

   - public CountDownLatch(int count)  count为计数器的初始值（资源的初始值）

2. 主要方法

   - countDown(); 这里主要是在做释放资源（count-1）的操作（解锁），每次释放都会唤醒阻塞线程。
   - await(); 这里主要在做加锁操作，判断条件为资源池的资源不为0就表示加锁失败，入队阻塞。

3. 代码演示

   ```java
public class CountDownLatchTest {
   
       public static CountDownLatch countDownLatch = new CountDownLatch(2);
   
       public static void main(String[] args) throws InterruptedException {
           new Thread(()->{
               for (int i = 0; i < 10; i++) {
                   try {
                       TimeUnit.SECONDS.sleep(1);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   new Thread(CountDownLatchTest::countDownLatchTest, "thread-" + i).start();
               }
           }).start();
   
           System.out.println("等待任务结束");
           //当countDown数达到CountDownLatch初始化值，则结束等待
           countDownLatch.await();
           System.out.println("任务结束");
       }
   
       public static void countDownLatchTest() {
           try {
   
               System.out.println(Thread.currentThread().getName()+"开始执行任务");
               TimeUnit.SECONDS.sleep(3);
           } catch (InterruptedException e) {
               e.printStackTrace();
           } finally {
               System.err.println(Thread.currentThread().getName()+"执行任务完毕");
               countDownLatch.countDown();
           }
       }
   }
   ```
   
## CyclicBarrier

   栅栏屏障，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。 

### 应用场景

可以用于多线程计算数据，最后合并计算结果的场景。例如，用一个Excel保存了用户 所有银行流水，每个Sheet保存一个账户近一年的每笔银行流水，现在需要统计用户的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日均银行流水，最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流水。

   ### 简单使用

      1. 构造方法
         - CyclicBarrier(int parties) 其参数表示屏障拦截的线程数量，每个线程调用await方法告CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
         - public CyclicBarrier(int parties, Runnable barrierAction) 当所有线程都到大屏障时，会调用一次barrierAction
   2. 主要方法
      - await()
   3. 代码演示

```java
public class CyclicBarrierTest {
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
        System.out.println("hello");
    });

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    //当await数达到CyclicBarrier初始化的parties值，则调用一次CyclicBarrier中的Run方法
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

```

> 注意，若调用await()方法的线程达不到初始值，则会一直陷入阻塞

   