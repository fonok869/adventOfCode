package com.fmolnar.code.multihthreading;

public class LongWrapper {

    private Object key = new Object();

    private long l = 0;

    public long getValue() {
        synchronized (key) {
            return l;
        }
    }

    public void increment() {
        synchronized (key) {
            l = l + 1;
        }
    }
}
