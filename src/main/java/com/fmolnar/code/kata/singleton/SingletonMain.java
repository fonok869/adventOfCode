package com.fmolnar.code.kata.singleton;

public class SingletonMain {

    public static void main(String[] args) {

        Thread threadFoo = new Thread(new ThreadFoo());
        threadFoo.start();
        Thread threadBar = new Thread(new ThreadBar());
        threadBar.start();
    }

    static class ThreadFoo implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(singleton.getName());
        }
    }

    static class ThreadBar implements Runnable {
        @Override
        public void run() {
            Singleton singleton = Singleton.getInstance("BAR");
            System.out.println(singleton.getName());
        }
    }
}

