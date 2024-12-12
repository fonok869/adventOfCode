package com.fmolnar.code;

public enum Direction {
    DOWN(0, 1),
    RIGHTDOWN(1, 1),
    LEFT(-1, 0),
    LEFTDOWN(-1, 1),
    UP(0, -1),
    UPLEFT(-1, -1),
    RIGHT(1, 0),
    UPRIGHT(1, -1);

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
            case DOWN -> LEFT;
            case RIGHTDOWN -> LEFTDOWN;
            case LEFT -> UP;
            case LEFTDOWN -> UPLEFT;
            case UP -> RIGHT;
            case UPLEFT -> UPRIGHT;
            case RIGHT -> DOWN;
            case UPRIGHT -> RIGHTDOWN;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case DOWN -> RIGHT;
            case RIGHT -> UP;
            case UP -> LEFT;
            case LEFT -> DOWN;
            case RIGHTDOWN -> UPRIGHT;
            case UPRIGHT -> UPLEFT;
            case UPLEFT -> LEFTDOWN;
            case LEFTDOWN -> RIGHTDOWN;
        };
    }

    public PointXY toPoint() {
        return new PointXY(x, y);
    }
}


