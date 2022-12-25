package com.fmolnar.code.year2022.day22;

import com.fmolnar.code.FileReaderUtils;
import com.fmolnar.code.year2021.day22.Day22Challenge02Beauty;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22Challenge02 {

    Map<Integer, Integer> xDataMin = new HashMap<>();
    Map<Integer, Integer> xDataMax = new HashMap<>();
    Map<Integer, Integer> yDataMin = new HashMap<>();
    Map<Integer, Integer> yDataMax = new HashMap<>();
    List<Instruction> ins = new ArrayList<>();
    public static final int fifty = 50;

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

        int step = 0;
        System.out.println(init);
        if (init.facingManip == FacingManip.UP) {
            step = 3;
        }
        if (init.facingManip == FacingManip.LEFT) {
            step = 2;
        }
        if (init.facingManip == FacingManip.DOWN) {
            step = 1;
        }

        long password = Long.valueOf(init.position.x + 1) * 4l + 1000l * Long.valueOf(init.position.y + 1) + Long.valueOf(step);
        System.out.println("Password: " + password);


    }

    private Stop calculateNextStop(Stop init, Instruction instruction, int[][] matrix) {

        Stop actualStop = new Stop(init.facingManip, init.position);
        Stop stopBefore = new Stop(init.facingManip, init.position);
        for (int i = 0; i < instruction.distance; i++) {
            actualStop = moveStop(stopBefore);

            // WALL
            if (matrix[actualStop.position.y][actualStop.position.x] == 2) {
                actualStop = stopBefore;
                break;
            }
            stopBefore = actualStop;
        }

        FacingManip newFacing = actualStop.facingManip;
        // Iranyvaltas
        if (instruction.direction == 'L') {
            newFacing = FacingManip.fromFacing(actualStop.facingManip.getLeft());
        } else if (instruction.direction == 'R') {
            newFacing = FacingManip.fromFacing(actualStop.facingManip.getRight());
        } else if (instruction.direction == 'E') {
            // Nem csinalunk semmit
            System.out.println("Itt van: " + instruction.direction);
        } else {
            System.out.println("Gond Van");
        }

        return new Stop(newFacing, actualStop.position);
    }

    private Stop moveStop(Stop stopActuel) {
        Position positionActuel = stopActuel.position;
        Position instruction = stopActuel.facingManip.direction;
        if (FacingManip.UP.getDirection().equals(instruction)) {
            int ymin = positionActuel.y + instruction.y;
            if (ymin < yDataMin.get(positionActuel.x)) {
                // 1 eset
                if (positionActuel.x < fifty) {
                    int yPosition = fifty + positionActuel.x;
                    return new Stop(FacingManip.RIGHT, new Position(yPosition, xDataMin.get(yPosition)));
                }
                // 2 eset
                if (positionActuel.x < 2 * fifty) {
                    int yPosition = 2 * fifty + positionActuel.x;
                    return new Stop(FacingManip.RIGHT, new Position(yPosition, xDataMin.get(yPosition)));
                }
                // 3 eset
                if (positionActuel.x < 3 * fifty) {
                    int xPoisiton = positionActuel.x - (2 * fifty);
                    return new Stop(FacingManip.UP, new Position(yDataMax.get(xPoisiton), xPoisiton));
                }

                System.out.println("Nem kell itt lennie 1");
            }
        } else if (FacingManip.DOWN.getDirection().equals(instruction)) {
            int ymax = positionActuel.y + instruction.y;
            if (yDataMax.get(positionActuel.x) < ymax) {
                // 1 eset
                if (positionActuel.x < fifty) {
                    int xPosition = positionActuel.x + 2 * fifty;
                    return new Stop(FacingManip.DOWN, new Position(yDataMin.get(xPosition), xPosition));
                }

                // 2 eset
                if (positionActuel.x < 2 * fifty) {
                    int yPosition = positionActuel.x + 2*fifty;
                    return new Stop(FacingManip.LEFT, new Position(yPosition, xDataMax.get(yPosition)));
                }


                // 3 eset
                if (positionActuel.x < 3 * fifty) {
                    int yPosition = positionActuel.x - (fifty);
                    return new Stop(FacingManip.LEFT, new Position(yPosition, xDataMax.get(yPosition)));
                }
                System.out.println("Nem kell itt lennie 2");
            }
        } else if (FacingManip.LEFT.getDirection().equals(instruction)) {
            int xmin = positionActuel.x + instruction.x;
            if (xmin < xDataMin.get(positionActuel.y)) {

                // 1 eset
                if (positionActuel.y < fifty) {
                    int yPosition = (fifty - 1  - positionActuel.y) + 2 * fifty;
                    return new Stop(FacingManip.RIGHT, new Position(yPosition, xDataMin.get(yPosition)));
                }

                if (positionActuel.y < 2 * fifty) {
                    int xPosition = positionActuel.y - (fifty) ;
                    return new Stop(FacingManip.DOWN, new Position(yDataMin.get(xPosition), xPosition));
                }

                if (positionActuel.y < 3 * fifty) {
                    int yPoisition = (3 * fifty -1) - positionActuel.y;
                    return new Stop(FacingManip.RIGHT, new Position(yPoisition, xDataMin.get(yPoisition)));
                }

                if (positionActuel.y < 4 * fifty) {
                    int xPoisition = positionActuel.y - (3 * fifty) + fifty;
                    return new Stop(FacingManip.DOWN, new Position(yDataMin.get(xPoisition), xPoisition));
                }

                System.out.println("Nem kell itt lennie 3");
            }
        } else if (FacingManip.RIGHT.getDirection().equals(instruction)) {
            int xmax = positionActuel.x + instruction.x;
            if (xDataMax.get(positionActuel.y) < xmax) {
                // 1 eset
                if (positionActuel.y < fifty) {
                    int yPoisition = (fifty - 1  - positionActuel.y) + 2 * fifty;
                    return new Stop(FacingManip.LEFT, new Position(yPoisition, xDataMax.get(yPoisition)));
                }

                if (positionActuel.y < 2 * fifty) {
                    int xPosition = fifty + positionActuel.y;
                    return new Stop(FacingManip.UP, new Position(yDataMax.get(xPosition), xPosition));
                }

                if (positionActuel.y < 3 * fifty) {
                    int yPosition = (3 * fifty -1 ) - positionActuel.y;
                    return new Stop(FacingManip.LEFT, new Position(yPosition, xDataMax.get(yPosition)));
                }

                if (positionActuel.y < 4 * fifty) {
                    int xPosition = positionActuel.y - 2 * fifty;
                    return new Stop(FacingManip.UP, new Position(yDataMax.get(xPosition), xPosition));
                }

                System.out.println("Nem kell itt lennie 3");
            }
        }

        return new Stop(stopActuel.facingManip, new Position(positionActuel.y + instruction.y, positionActuel.x + instruction.x));
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
