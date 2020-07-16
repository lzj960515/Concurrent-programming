package com.my.juc.test.atomictest;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 测试原子整型数组
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class AtomicIntegerArrayTest {

    private static int[] ARR = {1,2};
    private static AtomicIntegerArray ATOMIC_ARRAY = new AtomicIntegerArray(ARR);

    public static void main(String[] args) {
        ATOMIC_ARRAY.set(0,3);
        System.out.println(ATOMIC_ARRAY.get(0));
        System.out.println(ARR[0]);
    }
}
