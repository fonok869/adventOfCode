package com.fmolnar.code;

public record PointXY(int x, int y) {
    public PointXY add(PointXY pointXY) {
        return new PointXY(pointXY.x() + x, pointXY.y() + y);
    }

    public boolean szomszed(PointXY pointXY) {
        if ((Math.abs(pointXY.x() - x) + Math.abs(pointXY.y() - y)) < 2) {
            return true;
        }
        return false;
    }
}
