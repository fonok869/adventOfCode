package com.fmolnar.code.monad;

public class SimpleError {

    private final String info;

    public SimpleError(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return info;
    }
}
