package com.fmolnar.code.year2022.day20;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day20 {

    private static Long MAX = 0L;

    public void solutions() throws IOException {
        System.out.println("Part 1: ");
        calculate(1,1L);
        System.out.println("Part 2: ");
        calculate(10,811589153L);
    }

    public void calculate(int mixing,Long encryptionFactor) throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day20/input.txt");

        List<Chain> chains = new ArrayList<>();

        Chain first = null;
        Chain before = null;
        Chain now = null;
        for (int i = 0; i < lines.size(); i++) {
            long actualNumber = (Long.valueOf(lines.get(i)) * encryptionFactor);
            if (Long.valueOf(lines.get(i)) > 0 && actualNumber < 0) {
                System.out.println("Allj");
            }
            now = new Chain(i, actualNumber);
            if (i == 0) {
                first = now;
            } else if (i == lines.size() - 1) {
                now.setBefore(before);
                before.setAfter(now);
                now.setAfter(first);
                first.setBefore(now);
            } else {
                now = new Chain(i, actualNumber);
                now.setBefore(before);
                before.setAfter(now);
            }
            before = now;
            chains.add(now);
        }

        MAX = Long.valueOf(chains.size());

        for (int j = 0; j < mixing; j++) {
            for (int i = 0; i < chains.size(); i++) {
                chains.get(i).moveToPlace();
            }
        }

        Chain zero = chains.stream().filter(c -> c.value == 0).findFirst().get();

        long sum = zero.getNeighbours(1000) * 1L + zero.getNeighbours(2000) * 1L + zero.getNeighbours(3000) * 1L;
        System.out.println("Sum: " + sum);
    }

    class Chain {
        final Integer order;
        final Long value;
        Chain before;
        Chain after;

        public Chain(Integer order, Long value) {
            this.order = order;
            this.value = value;
        }

        long getNeighbours(int iteration) {
            Chain neighbour = this;
            for (int i = 0; i < iteration; i++) {
                neighbour = neighbour.after;
            }
            return neighbour.value;
        }

        void moveToPlace() {
            Chain nextRight = after;
            Chain nextLeft = before;
            if (value == 0) {
                return;
            } else if (value < 0) {
                for (int j = 0; (value % (MAX - 1)) < j; j--) {
                    nextLeft = nextLeft.before;
                    if (nextLeft == this) {
                        nextLeft = this.before;
                    }
                }
                nextRight = nextLeft.after;

            } else if (0 < value) {
                for (int i = 0; i < (value % (MAX - 1)); i++) {
                    nextRight = nextRight.after;
                    if (nextRight == this) {
                        nextRight = this.after;
                    }
                }
                nextLeft = nextRight.before;

            }
            // Remplacement
            this.before.setAfter(this.after);
            this.after.setBefore(this.before);

            //
            nextLeft.setAfter(this);
            nextRight.setBefore(this);
            this.setAfter(nextRight);
            this.setBefore(nextLeft);

        }

        public void setBefore(Chain before) {
            this.before = before;
        }

        public void setAfter(Chain after) {
            this.after = after;
        }

        public Long getValue() {
            return value;
        }
    }
}
