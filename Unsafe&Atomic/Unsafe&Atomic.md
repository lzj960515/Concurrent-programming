## Unsafe

### 通过反射获取`Unsafe`

```java
public class UnsafeInstance {

    public static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("反射获取Unsafe失败");
        }
    }
}
```

### 分配直接内存

```java
Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

long oneHundred = Long.MAX_VALUE;
long size = 1;
/*
* 调用allocateMemory分配内存
*/
long memoryAddress = unsafe.allocateMemory(size);

/*
* 将1写入到内存中
*/
unsafe.putAddress(memoryAddress, oneHundred);

/*
* 内存中读取数据
*/
long readValue = unsafe.getAddress(memoryAddress);

System.out.println("value : " + readValue);
```

### 读写屏障

```java
 		UnsafeInstance.reflectGetUnsafe().loadFence();//读屏障

        UnsafeInstance.reflectGetUnsafe().storeFence();//写屏障

        UnsafeInstance.reflectGetUnsafe().fullFence();//读写屏障
```

### 锁

```java
        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

        unsafe.monitorEnter(object);
        //业务逻辑写在此处之间
        unsafe.monitorExit(object);
```

## Atomic

### `AtomicInteger`

```java
public class AtomicIntegerTest {

    public static AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    public static volatile int number = 0;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list1 = new ArrayList<>(10);
        List<Thread> list2 = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Thread t =  new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ATOMIC_INTEGER.incrementAndGet();
                }
            }, "Thread-" + i);
            list1.add(t);
        }

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    number++;
                }
            }, "Thread-" + i);
            list2.add(t);
        }
        list1.forEach(Thread::start);
        list2.forEach(Thread::start);

        TimeUnit.SECONDS.sleep(1);
        System.out.println(ATOMIC_INTEGER.get());
        System.out.println(number);

    }

}
```

### `AtomicIntegerArray`

```java
public class AtomicIntegerArrayTest {

    private static int[] ARR = {1,2};
    private static AtomicIntegerArray ATOMIC_ARRAY = new AtomicIntegerArray(ARR);

    public static void main(String[] args) {
        ATOMIC_ARRAY.set(0,3);
        System.out.println(ATOMIC_ARRAY.get(0));
        System.out.println(ARR[0]);
    }
}

```

### `AtomicReference`

```java
public class AtomicReferenceTest {

    private static AtomicReference<User> atomicReference = new AtomicReference<>(new User());

    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                User user = atomicReference.get();
                wait(finalI*100000);//等待一会，模拟并发
                User user1 = new User();
                if (atomicReference.compareAndSet(user, user1)) {
                    count.incrementAndGet();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("修改成功次数：" + count.get());
    }

    public static void wait(final int time) {
        for (int i = 0; i < time; i++) {
        }
    }
}
```

### `AtomicIntegerFieldUpdater`

```java
public class AtomicIntegerFieldUpdateTest {

    static AtomicIntegerFieldUpdater<User> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
    static AtomicReferenceFieldUpdater<User, String> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "username");

    public static void main(String[] args) {
        User user = new User();
        atomicIntegerFieldUpdater.set(user,18);
        atomicReferenceFieldUpdater.set(user,"lzj");
        System.out.println(atomicIntegerFieldUpdater.get(user));
        System.out.println(atomicReferenceFieldUpdater.get(user));

    }
}
```

### ABA问题

```java
public class AtomicABAProblem {

    private static AtomicInteger atomicInteger = new AtomicInteger(10);

    public static void main(String[] args) {
        Thread owner = new Thread(() -> {
            int money = atomicInteger.get();
            System.out.println("第一次查看金库,金额：" + money);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("准备更新金库金额");
            if(atomicInteger.compareAndSet(money, 20))
                System.out.println("更新金库成功，金库剩余金额:"+atomicInteger.get());
            else
                System.err.println("更新失败，有人动了我的金库！");

        });

        Thread stealer = new Thread(() -> {
            int money = atomicInteger.addAndGet(-3);
            System.out.println("准备偷取金库，金库剩余金额" + money);
            //使用金额做点别的事，比如炒一波股
            money = atomicInteger.addAndGet(3);
            System.out.println("将金额还回金库，金库剩余金额" + money);
        });
        owner.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stealer.start();
    }
}
```

### ABA问题解决

```java
public class SolveABAProblem {

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(10, 0);

    public static void main(String[] args) {
        Thread owner = new Thread(() -> {
            int money = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            System.out.println("第一次查看金库,金额：" + money + ",版本号：" + stamp);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("准备更新金库金额");
            if (atomicStampedReference.compareAndSet(money, 20, stamp, stamp + 1))
                System.out.println("更新金库成功，金库剩余金额:" + atomicStampedReference.getReference() + " 版本号：" + atomicStampedReference.getStamp());
            else
                System.err.println("更新失败，有人动了我的金库！");

        });

        Thread stealer = new Thread(() -> {
            int[] stamp = new int[1];
            int current = atomicStampedReference.get(stamp);
            int remain = 7;
            boolean success = atomicStampedReference.compareAndSet(current, remain, stamp[0], stamp[0] + 1);
            if(success){
                System.out.println("准备偷取金库，金库剩余金额" + remain);
                //使用金额做点别的事，比如炒一波股
                success =  atomicStampedReference.compareAndSet(remain, current, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                if(success){
                    System.out.println("将金额还回金库，金库剩余金额" + atomicStampedReference.getReference());
                }else {
                    System.err.println("完了，还回失败了");
                }
            }else {
                System.err.println("偷取失败了");
            }


        });
        owner.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stealer.start();
    }
}
```

### 自定义原子类

```java
public class MyAtomicInteger {

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;

    public MyAtomicInteger(int initialValue) {
        value = initialValue;
    }

    public int get() {
        return value;
    }

    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    public static void main(String[] args) {
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger(10);
        int i = myAtomicInteger.get();
        int update = i + 1;
        boolean b = myAtomicInteger.compareAndSet(i, update);
        System.out.println(b);
        System.out.println(myAtomicInteger.get());

    }
}
```



