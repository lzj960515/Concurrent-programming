package com.my.juc.test.classtest;

/**
 * @author Zijian Liao
 * @since
 */
public abstract class Person {

    public String personName;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public class Children implements Child {
        @Override
        public String getUsername() {
            return getPersonName();
        }
    }
}
