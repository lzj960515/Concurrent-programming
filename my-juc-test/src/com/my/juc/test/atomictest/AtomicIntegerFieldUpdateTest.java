package com.my.juc.test.atomictest;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 测试原子更新字段
 *
 * @author Zijian Liao
 * @since
 */
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
