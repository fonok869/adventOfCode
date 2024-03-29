package com.fmolnar.code.multihthreading;

public class MainLongWrapper {

    public static void main(String[] args) throws InterruptedException {

        LongWrapper longWrapper = new LongWrapper();

        Runnable runnable = () -> {
            for(int i=0; i<1000; i++){
                longWrapper.increment();
            }
        };

        Thread[] threads = new Thread[1000];
        for(int i=0; i<1000; i++){
            threads[i] = new Thread(runnable);
            threads[i].start();
        }

        for(int i=0; i<1000; i++){
            threads[i].join();
        }

        System.out.println("Value: " + longWrapper.getValue());

    }
}
