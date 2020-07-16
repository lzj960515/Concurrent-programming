package com.my.juc.test.atomictest;

import com.my.juc.test.util.UnsafeInstance;
import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义原子操作
 *
 * @author Zijian Liao
 * @since 1.0
 */
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
