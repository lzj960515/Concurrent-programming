package com.my.juc.test.classtest;

/**
 * @author Zijian Liao
 * @since
 */
public class Person {

    public String personName;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Children newC(){
        return new Children();
    }

    public class Children implements Child {
        @Override
        public String getUsername() {
            return getPersonName();
        }
    }
}
