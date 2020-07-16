package com.my.juc.test.classtest;

/**
 * @author Zijian Liao
 * @since 1.0
 */
public class TestInstance {

    private final Child[] children;

    protected TestInstance(Child[] children) {
        this.children = children;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setPersonName("jack");
        Person person = new Person();
        Child child = person.newC();
        System.out.println(child.getUsername());

    }

}
