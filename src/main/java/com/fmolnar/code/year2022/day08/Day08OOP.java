package com.fmolnar.code.year2022.day08;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day08OOP {

    int yLength;
    int xLength;
    int[][] trees;

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day08/input.txt");

        yLength = lines.size();
        xLength = lines.get(0).length();

        trees = new int[yLength][xLength];
        for (int y=0; y<lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                trees[y][x] = Integer.valueOf(line.substring(x, x + 1));
            }
        }

        List<Direction> directions = getDirections();
        int numberCanSeeEdge = 0;
        int distnaceActual = 1;
        List<Integer> maxDistances = new ArrayList<>();
        for (int xx = 0; xx < xLength; xx++) {
            for (int yy = 0; yy < yLength; yy++) {
                for (Direction direction : directions) {
                    if (canSeeTheEdge(xx, yy, direction)) {
                        numberCanSeeEdge += 1;
                        break;
                    }
                }
                for (Direction direction : directions) {
                    distnaceActual *= calculDistanceVisibility(xx, yy, direction);
                }
                maxDistances.add(distnaceActual);
                distnaceActual = 1;
            }
        }
        System.out.println("First: " + numberCanSeeEdge);
        System.out.println("Second: " + maxDistances.stream().mapToInt(s -> s).max().getAsInt());
    }

    private int calculDistanceVisibility(int xx, int yy, Direction direction) {
        int treeActual = trees[yy][xx];
        int distance = 0;
        while (true) {
            xx = xx + direction.x;
            yy = yy + direction.y;

            // We are outside --> distance to return
            if (xx < 0 || xLength <= xx || yy < 0 || yLength <= yy) {
                return distance;
            }

            // We see the tree, so we have to add
            if (treeActual <= trees[yy][xx]) {
                // We can see this tree --> has to add
                return distance + 1;
            }
            distance++;
        }
    }

    private boolean canSeeTheEdge(int xx, int yy, Direction direction) {
        int treeActual = trees[yy][xx];
        while (true) {
            xx = xx + direction.x;
            yy = yy + direction.y;

            // Outside --> OK
            if (xx < 0 || xLength <= xx || yy < 0 || yLength <= yy) {
                return true;
            }

            // Can not see further --> KO
            if (treeActual <= trees[yy][xx]) {
                return false;
            }
        }
    }

    private List<Direction> getDirections() {
        List<Direction> directions = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i == 0 || j == 0) && !(i == 0 && j == 0)) {
                    directions.add(new Direction(i, j));
                }
            }
        }
        return directions;
    }

    record Direction(int x, int y) { };


}
