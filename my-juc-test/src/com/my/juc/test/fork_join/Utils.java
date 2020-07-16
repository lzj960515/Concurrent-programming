package com.my.juc.test.fork_join;

import java.util.Random;

public class Utils {

    public static int[] buildRandomIntArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }
}