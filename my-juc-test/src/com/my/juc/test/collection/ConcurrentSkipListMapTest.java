package com.my.juc.test.collection;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 测试跳表
 *
 * @author Zijian Liao
 * @since 1.0.0
 */
public class ConcurrentSkipListMapTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put(1,1);
        skipListMap.put(10,1);
        skipListMap.put(3,1);
        skipListMap.put(6,1);
        skipListMap.put(2,1);
        NavigableSet<Integer> integers = skipListMap.keySet();
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
