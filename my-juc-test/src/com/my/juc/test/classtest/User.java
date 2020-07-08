package com.my.juc.test.classtest;

/**
 * @author Zijian Liao
 * @since
 */
public class User extends Person {

    final Child newChildren(){
        return new Children();
    }
}
