`Java`并发编程核心在于`java.concurrent.util`包
而`juc`当中的大多数同步器实现都是围绕着共同的基础行为，比如等待队列、条件队列、独占获取、共享获取等，而这个行为的抽象就是基于`AbstractQueuedSynchronizer`简称`AQS`，`AQS`定义了一套多线程访问共享资源的同步器框架，是一个依赖状态(state)的同步器。

## `AQS`具备特性

- 阻塞等待队列
- 共享/独占
- 公平/非公平
- 可重入
- 允许中断

## `ReentrantLock`

`ReentrantLock`定义了一个内部类`Sync`继承了`AQS`，通过这种方式将同步器所有调用都映射到Sync对应的方法，同时`ReentrantLock`也具备`AQS`的特性。`ReentrantLock`具备以下特性

- 阻塞等待队列
- 独占
- 公平/非公平
- 可重入
- 允许中断

### `ReentrantLock`继承关系

`ReentrantLock`里面定义了三个内部类`Sync`、`FairSync`、`NonfairSync`，`FairSync`、`NonfairSync`表示公平锁与非公平锁，通过这两个内部类实现公平与非公平的机制，具体关系如下图：

![](lock.png)



> `FairSync`、`NonfairSync`是通过继承`Sync`实现的，其中`FairSync`加锁机制为先判断列队中是否有其它线程正在等待加锁，如果有则直接入队，没有才会尝试加锁；而`NonfairSync`加锁机制为先进行尝试加锁，加锁失败才进行入队。

除此之外，`ReentrantLock`还可通过`lockInterruptibly()`加锁时可使用`Thread.interrupt()`进行中断加锁过程。

### `ReentrantLock`加锁过程(简述)

[`ReentrantLock`加锁详细过程](https://www.processon.com/view/link/5f018c7a6376891e81fc2708)

**公平锁加锁过程**

1. 查看队列中是否有其它线程等待加锁
2. 队列为空或者等待加锁的线程为当前线程，尝试`CAS`加锁
3. 加锁成功 `state+1`，结束
4. 加锁失败（只发生在队列为空的情况），或者队列不为空且等待加锁的线程非当前线程，进行入队。
5. 入队完毕，将前驱结点信号量(`waitStatus`)改为-1，阻塞等待`LockSupport.park(this)`

**解锁过程**

1. 解锁`state-1`
2. 判断`state==0`(可重入锁，每次加锁`state`都会`+1`，释放锁次数必须等于加锁次数才算已解锁)
3. 判断队列中是否有等待加锁的线程
4. 将线程唤醒`LockSupport.unpark(thread)`

### `ReentrantLock`与`synchronized`比较

1. 加锁机制比较

   - `ReentrantLock`，基于`AQS`实现的锁，需要手动加锁解锁，细粒度和灵活度更高

   - `synchronized`是`JVM`内部锁，`JVM`会自动加锁与解锁

2. 底层原理比较

   - 都是依赖底层操作系统的`Mutex lock（互斥锁）`实现 

3. 锁类型比较

   - `synchronized`是一种可重入的非公平锁
   - `ReentrantLock`是基于`AQS`实现，具体特性上文已有说明

4. 性能比较（未实测）

   - 在并发量小时，`synchronized`性能更高一些
   - 并发量高时，`synchronized`性能将会下降（升级为重量级锁），而`ReentrantLock`基本不变。

   > 我这里的理解是，`synchronized`和`ReentrantLock`底层阻塞原理虽然是一样的，但是`synchronized`锁释放后，将出现线程争抢的情况，而`ReentrantLock`利用`CAS`自旋操作实现锁，底层维护了一个同步队列，当有线程解锁，只会出队一个线程进行加锁。

   

   



