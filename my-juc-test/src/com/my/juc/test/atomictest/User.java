package com.my.juc.test.atomictest;

/**
 * 用户
 *
 * @author Zijian Liao
 * @since
 */
public class User {
     volatile String username;
     volatile int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", age=" + age +
//                '}';
//    }
}
