package com.fmolnar.code;

public enum Direction {
    S(0, 1),
    W(-1, 0),
    N(0, -1),
    E(1, 0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int y() {
        return y;
    }


    int x() {
        return x;
    }


    public Direction turnRight() {
        return switch (this) {
            case S -> W;
            case W -> N;
            case N -> E;
            case E -> S;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case S -> E;
            case E -> N;
            case N -> W;
            case W -> S;
        };
    }
}


