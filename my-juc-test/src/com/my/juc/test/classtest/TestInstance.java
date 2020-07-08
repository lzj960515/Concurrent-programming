package com.my.juc.test.classtest;

/**
 * @author Zijian Liao
 * @since
 */
public class TestInstance {

    public static void main(String[] args) {
        User user = new User();
        user.setPersonName("jack");
        Child child = user.newChildren();
        System.out.println(child.getUsername());
    }
}
