package com.fmolnar.code.kata.bowling;

public record Roll(String pin) {
    int result() {
        if ("/".equals(pin)) {
            return 10;
        } else if ("-".equals(pin)) {
            return 0;
        } else if ("X".equals(pin)) {
            return 10;
        }
        return Integer.valueOf(pin);
    }
}
