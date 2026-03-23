package com.fmolnar.code.kata.singleton;

public class Singleton {

    private static Singleton instance = null;

    private final String name;

    private Singleton(String name) {
        this.name = name;
    }

    public static synchronized Singleton getInstance(String name) {
        if (instance == null) {
            instance = new Singleton(name);
        }
        return instance;
    }

    public String getName() {
        return name;
    }
}
