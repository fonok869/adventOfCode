package com.fmolnar.code.year2020.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12Challenge01 {

    private static final Character EAST = 'E';
    private static final Character WEST = 'W';
    private static final Character SOUTH = 'S';
    private static final Character NORTH = 'N';


    public Day12Challenge01() {
        directions.put(EAST, new Direction(EAST, NORTH, SOUTH, WEST,SOUTH, WEST, NORTH));
        directions.put(SOUTH, new Direction(SOUTH, EAST, WEST, NORTH, WEST, NORTH, EAST));
        directions.put(NORTH, new Direction(NORTH, WEST, EAST, SOUTH, EAST, SOUTH, WEST));
        directions.put(WEST, new Direction(WEST, SOUTH, NORTH, EAST, NORTH, EAST, SOUTH));
    }

    Map<Character, Direction> directions = new HashMap<>();

    List<String> insctuctions = new ArrayList<>();
    List<String> forwardInstructions = new ArrayList<>();
    int x =0;
    int y =0;

    public void calculate() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/2020/day12/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (line.startsWith("F") || line.startsWith("R") || line.startsWith("L")) {
                        forwardInstructions.add(line);
                    } else {
                        insctuctions.add(line);
                    }
                }
            }
        }
        trnasformForwardInstructions();
        comptage();

        System.out.println("Result : "+  String.valueOf(Math.abs(x)+Math.abs(y)));
    }

    private void comptage() {
        for(String inst : insctuctions){
            String lepesString = inst.substring(1);
            Character direction = inst.charAt(0);
            int lepes = Integer.valueOf(lepesString);
            if(EAST == direction){
                x = x+lepes;
            } else if(WEST == direction){
                x = x-lepes;
            } else if (NORTH == direction){
                y = y +lepes;
            } else if (SOUTH == direction){
                y = y -lepes;
            } else {
                throw  new RuntimeException("baj van");
            }

        }
    }

    private void trnasformForwardInstructions() {
        Character initialDirection = EAST;
        for(String inst : forwardInstructions){
            String lepesString = inst.substring(1);
            Character direction = inst.charAt(0);
            int lepes = Integer.valueOf(lepesString);
            if(direction == 'F'){
                insctuctions.add(String.valueOf(initialDirection)+String.valueOf(lepes));
            } else if ("R90".equals(inst)){
                initialDirection = directions.get(initialDirection).getRight90();
            } else if ("L90".equals(inst)){
                initialDirection = directions.get(initialDirection).getLeft90();
            } else if ("R180".equals(inst)){
                initialDirection = directions.get(initialDirection).getRight180();
            } else if ("L180".equals(inst)){
                initialDirection = directions.get(initialDirection).getLeft180();
            } else if ("R270".equals(inst)){
                initialDirection = directions.get(initialDirection).getRight270();
            } else if ("L270".equals(inst)){
                initialDirection = directions.get(initialDirection).getLeft270();
            }

        }

    }

    public class Direction {
        Character origin;
        Character left90;
        Character right90;
        Character left180;
        Character left270;
        Character right180;
        Character right270;

        public Direction(Character origin, Character left90, Character right90, Character left180, Character left270, Character right180, Character right270) {
            this.origin = origin;
            this.left90 = left90;
            this.right90 = right90;
            this.left180 = left180;
            this.left270 = left270;
            this.right180 = right180;
            this.right270 = right270;
        }

        public Character getLeft180() {
            return left180;
        }

        public Character getLeft270() {
            return left270;
        }

        public Character getRight180() {
            return right180;
        }

        public Character getRight270() {
            return right270;
        }

        public Character getLeft90() {
            return left90;
        }

        public Character getRight90() {
            return right90;
        }
    }
}
