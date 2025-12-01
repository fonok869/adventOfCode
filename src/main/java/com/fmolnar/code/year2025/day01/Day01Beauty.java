package com.fmolnar.code.year2025.day01;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01Beauty {
    List<Dialer> dialers = new ArrayList<>();

    public void calculate() throws IOException {
        initDialer();
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day01/input.txt");

        Dialer init = dialers.get(50);
        int counterZeroFinal = 0;
        int counterZeroPassed = 0;
        for (String line : lines) {
            int step = Integer.valueOf(line.substring(1));
            if (line.startsWith("L")) {
                for (int i = 1; i <= step; i++) {
                    init = init.getLeft();
                    counterZeroPassed += init.isOnZero();
                }
            } else if (line.startsWith("R")) {
                for (int i = 1; i <= step; i++) {
                    init = init.getRight();
                    counterZeroPassed += init.isOnZero();
                }
            }
            counterZeroFinal += init.isOnZero();
        }
        System.out.println("First: " + counterZeroFinal);
        System.out.println("Second: " + counterZeroPassed);
    }


    private void initDialer() {
        for (int i = 0; i < 100; i++) {
            dialers.add(new Dialer(i));
        }
        for (int i = 0; i < 100; i++) {
            if (i == 0) {
                Dialer dialerActual = dialers.get(i);
                dialerActual.setLeft(dialers.get(99));
                dialerActual.setRight(dialers.get(i + 1));
            } else if (i == 99) {
                Dialer dialerActual = dialers.get(i);
                dialerActual.setLeft(dialers.get(i - 1));
                dialerActual.setRight(dialers.get(0));
            } else {
                Dialer dialerActual = dialers.get(i);
                dialerActual.setLeft(dialers.get(i - 1));
                dialerActual.setRight(dialers.get(i + 1));
            }
        }
    }
}

class Dialer {
    private int position;
    private Dialer right;
    private Dialer left;

    public Dialer(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Dialer getRight() {
        return right;
    }

    public void setRight(Dialer right) {
        this.right = right;
    }

    public Dialer getLeft() {
        return left;
    }

    public void setLeft(Dialer left) {
        this.left = left;
    }

    public int isOnZero() {
        return this.position == 0 ? 1 : 0;
    }
}
