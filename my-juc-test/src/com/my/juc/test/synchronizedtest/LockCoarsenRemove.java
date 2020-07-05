package com.my.juc.test.synchronizedtest;

/**
 * 锁粗化与锁消除
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class LockCoarsenRemove {

    private Object object = new Object();

    /**
     * 锁粗化
     */
    public void coarsen(){
        synchronized (object){

        }
        //少量的业务代码
        synchronized (object){

        }
    }

    /**
     * 锁消除
     */
    public void remove(){
        StringBuffer sb = new StringBuffer();
        sb.append(1);
        sb.append(2);
        sb.append(3);
    }
}
