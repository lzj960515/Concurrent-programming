package com.my.juc.test.unsafe;

import com.my.juc.test.util.UnsafeInstance;
import sun.misc.Unsafe;

public class DirectMemoryAccessTest {

    public static void main(String[] args) {

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
    }
}