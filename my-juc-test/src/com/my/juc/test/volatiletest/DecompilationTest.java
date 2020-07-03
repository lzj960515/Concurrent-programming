package com.my.juc.test.volatiletest;

/**
 * 测试 汇编代码
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp
 * @author Zijian Liao
 */
public class DecompilationTest {

    public static volatile int age = 18;

    public static void main(String[] args) {

    }
}
