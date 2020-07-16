package com.my.juc.test.unsafe;

import com.my.juc.test.util.UnsafeInstance;
import sun.misc.Unsafe;

public class ObjectMonitorTest {
    static Object object = new Object();

/*    public void method1(){
        unsafe.monitorEnter(object);
    }

    public void method2(){
        unsafe.monitorExit(object);
    }*/

    public static void main(String[] args) {

        /*synchronized (object){
        }*/
        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

        unsafe.monitorEnter(object);
        //业务逻辑写在此处之间
        unsafe.monitorExit(object);

    }

}