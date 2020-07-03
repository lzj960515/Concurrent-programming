## volatile原理与内存语义

`volatile`是`JVM`提供的轻量级的同步机制

1. `volatile`语义有以下两个作用:
   - 可见性：保证被volatile修饰的共享变量对所有线程总是可见的，也就是当一个线程修改了一个被`volatile`修饰共享变量的值，新值总是可以被其他线程立即得知。
   - 有序性：禁止指令重排。

2. `volatile`缓存可见性实现原理:
   - `JVM`交互层面，在操作对被`volatile`修饰的共享变量时，`read`,`load`,`use`和`assign`,`store`,`write`必须是连续的，即修改后必须立即同步会主内存，使用时必须从主内存刷新，由此保证volatile变量的可见性。（但`use`和`assign`不是连续执行，由此可见`volatile`并不保证原子性）
- 底层实现：通过汇编lock前缀指令，它会锁定变量缓存行区域并写回主内存，这个操作称为“缓存锁定”，缓存一致性机制会阻止同时修改被两个以上处理器缓存的内存区域数据。一个处理器的缓存回写到内存内存会导致其他处理器的缓存无效
   
   > 查看汇编指令：-XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp

```java
public class DecompilationTest {

    public static volatile int age = 18;

    public static void main(String[] args) {

    }
}
```

```properties
  0x00000000030b205b: nopl   0x0(%rax,%rax,1)
  0x00000000030b2060: jmpq   0x00000000030b20f4  ;   {no_reloc}
  0x00000000030b2065: add    %al,(%rax)
  0x00000000030b2067: add    %al,(%rax)
  0x00000000030b2069: add    %bh,0x12(%rdi)
  0x00000000030b206f: mov    %edi,0x68(%rsi)
  0x00000000030b2072: lock addl $0x0,(%rsp)     ;*putstatic age
                                                ; - com.my.juc.test.volatiletest.DecompilationTest::<clinit>@2 (line 10)
```

> 从以上截取的部分汇编指令中我们可以看到age变量确实加了lock前缀

## volatile 保证可见性

```java
public class VisualizationTest {

    public static
    volatile //若不加volatile修饰，则线程1一直运行
    boolean flag = true;
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        String name = "Thread-1";
        new Thread(()->{
            System.out.println(name + " 启动中......");
            while (flag){
                /*lock.lock(); //加锁，使得发生上下文切换,将变量重新从主内存读取
                lock.unlock();*/
//                synchronized (VisualizationTest.class){}
            }
            System.out.println(name + " 执行完毕......");
        },name).start();

        TimeUnit.SECONDS.sleep(1);

        String name2 = "Thread-2";
        new Thread(()->{
            flag = false;
            System.out.println(name2 + " 修改静态变量flag:"+flag);
        }, name2).start();
    }
}
```

## volatile 禁止指令重排

```java
public class ReOrderTest {

    public 
    volatile //不加volatile则将出现x=0,y=0的情况 
    static int a = 0, b = 0;
    public static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        long count = 0;
        for (; ; ) {
            count++;
            a = b = x = y = 0;
            Thread t1 = new Thread(() -> {
                //由于线程1先执行，等一会线程2
                nanosWait(50000);
                a = 1;
                x = b;
            });
            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });
            //x = 0, y = 1
            //x = 1, y = 0
            //x = 1, y = 1
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (x == 0 && y == 0) {
                System.err.println("第" + count + "次循环，x=" + x + ",y=" + y);
                return;
            } else
                System.out.println("第" + count + "次循环，x=" + x + ",y=" + y);
        }
    }

    public static void nanosWait(long time) {
        long start = System.nanoTime();
        while (System.nanoTime() - start < time){}
    }
}
```

