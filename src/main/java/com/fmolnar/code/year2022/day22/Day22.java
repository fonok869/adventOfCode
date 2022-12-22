package com.fmolnar.code.year2022.day22;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {

    Map<Integer, Integer> xDataMin = new HashMap<>();
    Map<Integer, Integer> xDataMax = new HashMap<>();
    Map<Integer, Integer> yDataMin = new HashMap<>();
    Map<Integer, Integer> yDataMax = new HashMap<>();
    List<Instruction> ins = new ArrayList<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day22/input.txt");

        int xMax = lines.stream().mapToInt(s -> s.length()).max().getAsInt();
        int yMax = lines.size();
        int[][] matrix = new int[yMax][xMax];
        String commands = "";

        boolean command = false;
        for (int j = 0; j < yMax; j++) {

            String line = lines.get(j);
            if (command) {
                int lastLetter = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'R' || line.charAt(i) == 'L') {
                        int step = Integer.valueOf(line.substring(lastLetter, i));
                        lastLetter = i + 1;
                        ins.add(new Instruction(step, line.charAt(i)));
                    }
                }
                ins.add(new Instruction(Integer.valueOf(line.substring(lastLetter)), 'E'));
            }

            int xMinValue = -1;
            int xMaxValue = -1;
            if (StringUtils.isNotEmpty(line) && !command) {
                for (int i = 0; i < line.length(); i++) {
                    char charActual = line.charAt(i);
                    if (charActual == ' ') {
                        matrix[j][i] = 0;
                    } else if (charActual == '.') {
                        xMinValue = i;
                        xMaxValue = i;
                        matrix[j][i] = 1;
                    } else if (charActual == '#') {
                        xMinValue = i;
                        xMaxValue = i;
                        matrix[j][i] = 2;
                    }
                    if (xMinValue != -1 && xDataMin.get(j) == null) {
                        xDataMin.put(j, xMinValue);
                    }
                }
                xDataMax.put(j, xMaxValue);
            } else {
                command = true;
            }


        }
        for (int i = 0; i < xMax; i++) {
            int yMinValue = -1;
            int yMaxValue = -1;
            for (int j = 0; j < yMax; j++) {
                if (matrix[j][i] == 1 || matrix[j][i] == 2) {
                    yMinValue = j;
                    yMaxValue = j;
                }

                if (yMinValue != -1 && yDataMin.get(i) == null) {
                    yDataMin.put(i, yMinValue);
                }
            }
            yDataMax.put(i, yMaxValue);
        }

        Stop init = new Stop(FacingManip.RIGHT, new Position(0, xDataMin.get(0)));

        for (Instruction instruction : ins) {
            init = calculateNextStop(init, instruction, matrix);
        }

        int step  = calculateNextStopDistance(init, matrix);
        System.out.println(init);

        long password = Long.valueOf(init.position.x+1)*4l+1000l*Long.valueOf(init.position.y+1)+Long.valueOf(step);
        System.out.println("Password: " + password);


    }

    private int calculateNextStopDistance(Stop init, int[][] matrix) {

        Position positionActuel = init.position;
        Position positionBefore = init.position;
        for (int i = 0; i < 1000; i++) {
            positionActuel = addTwoPosition(positionBefore, init.facingManip.direction);

            // WALL
            if (matrix[positionActuel.y][positionActuel.x] == 2) {
                positionActuel = positionBefore;
                return i;
            }
            positionBefore = positionActuel;
        }
        return -1;
    }

    private Stop calculateNextStop(Stop init, Instruction instruction, int[][] matrix) {

        Position positionActuel = init.position;
        Position positionBefore = init.position;
        for (int i = 0; i < instruction.distance; i++) {
            positionActuel = addTwoPosition(positionBefore, init.facingManip.direction);

            // WALL
            if (matrix[positionActuel.y][positionActuel.x] == 2) {
                positionActuel = positionBefore;
                break;
            }
            positionBefore = positionActuel;
        }

        FacingManip newFacing = init.facingManip;
        // Iranyvaltas
        if (instruction.direction == 'L') {
            newFacing = FacingManip.fromFacing(init.facingManip.getLeft());
        } else if (instruction.direction == 'R') {
            newFacing = FacingManip.fromFacing(init.facingManip.getRight());
        } else if (instruction.direction == 'E') {
            // Nem csinalunk semmit
        } else {
            System.out.println("Gond Van");
        }

        return new Stop(newFacing, positionActuel);
    }

    private Position addTwoPosition(Position positionActuel, Position instruction) {
        Position newPoisition;
        if (FacingManip.UP.getDirection().equals(instruction)) {
            int ymin = positionActuel.y + instruction.y;
            if (ymin < yDataMin.get(positionActuel.x)) {
                ymin = yDataMax.get(positionActuel.x);
                return new Position(ymin, positionActuel.x);
            }
        } else if (FacingManip.DOWN.getDirection().equals(instruction)) {
            int ymax = positionActuel.y + instruction.y;
            if (yDataMax.get(positionActuel.x) < ymax) {
                ymax = yDataMin.get(positionActuel.x);
                return new Position(ymax, positionActuel.x);
            }
        } else if (FacingManip.LEFT.getDirection().equals(instruction)) {
            int xmin = positionActuel.x + instruction.x;
            if (xmin < xDataMin.get(positionActuel.y)) {
                xmin = xDataMax.get(positionActuel.y);
                return new Position(positionActuel.y, xmin);
            }
        } else if (FacingManip.RIGHT.getDirection().equals(instruction)) {
            int xmax = positionActuel.x + instruction.x;
            if (xDataMax.get(positionActuel.y) < xmax) {
                xmax = xDataMin.get(positionActuel.y);
                return new Position(positionActuel.y, xmax);
            }
        }


        return new Position(positionActuel.y + instruction.y, positionActuel.x + instruction.x);
    }

    record Instruction(int distance, char direction) {
    }

    record Stop(FacingManip facingManip, Position position) {
    }

    ;

    record Position(int y, int x) {

    }

    enum Facing {
        U,
        R,
        D,
        L;
    }

    enum FacingManip {
        UP(Facing.L, Facing.R, new Position(-1, 0)),
        RIGHT(Facing.U, Facing.D, new Position(0, 1)),
        DOWN(Facing.R, Facing.L, new Position(1, 0)),
        LEFT(Facing.D, Facing.U, new Position(0, -1));

        Facing left;
        Facing right;
        Position direction;

        FacingManip(Facing left, Facing right, Position direction) {
            this.left = left;
            this.right = right;
            this.direction = direction;
        }

        Facing getRight() {
            return right;
        }

        Facing getLeft() {
            return left;
        }

        public Position getDirection() {
            return direction;
        }

        public static FacingManip fromFacing(Facing facing) {
            switch (facing) {
                case D -> {
                    return DOWN;
                }
                case L -> {
                    return LEFT;
                }
                case R -> {
                    return RIGHT;
                }
                case U -> {
                    return UP;
                }
            }
            return null;
        }
    }


}
