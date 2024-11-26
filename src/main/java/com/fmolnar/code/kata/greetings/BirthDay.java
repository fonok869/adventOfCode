package com.fmolnar.code.kata.greetings;

import java.time.LocalDate;

public record BirthDay(LocalDate value) {
    boolean hasBirthDay(int month, int day) {
        if (value.getMonth().getValue() == 2 && value.getDayOfMonth() == 29) {
            return day == 28;
        } else {
            return value().getMonth().getValue() == month && value().getDayOfMonth() == day;
        }
    }
}
