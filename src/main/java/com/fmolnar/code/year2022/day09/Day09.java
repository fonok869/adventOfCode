package com.fmolnar.code.year2022.day09;

import com.fmolnar.code.FileReaderUtils;
import com.fmolnar.code.year2022.day08.Day08OOP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day09 {
    Point pointH = new Point(0,0);
    Point pointL = new Point(0,0);
    Map<String, Point> directions = new HashMap<>();
    Set<Point> pointVisitedByL = new HashSet<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day09/input.txt");
        directions.put("L", new Point(-1, 0));
        directions.put("U", new Point(0, 1));
        directions.put("R", new Point(1, 0));
        directions.put("D", new Point(0, -1));
        pointVisitedByL.add(new Point(0,0));

        List<Instruction> instructions = new ArrayList<>();
        for(String line : lines){
            String[] splitted = line.split(" ");
            instructions.add(new Instruction(splitted[0], Integer.valueOf(splitted[1])));
        }

        for(int i=0; i<instructions.size(); i++){
            Instruction inst = instructions.get(i);
            makeSteps(inst);
        }

        System.out.println("Day09Challenge01: " + pointVisitedByL.size());
    }

    private void makeSteps(Instruction inst) {
        Point direction = directions.get(inst.direction);
        for(int i=0; i<inst.step; i++){
            pointH = new Point(pointH.x + direction.x, pointH.y+direction.y);
            int diff = Math.abs(pointL.x-pointH.x) + Math.abs(pointL.y-pointH.y);
            if(diff<=1){
                // do not move
            }
            else if(diff==2){
                // Same line or row
                if(pointH.x == pointL.x){
                    pointL = new Point(pointL.x, pointL.y + (pointH.y-pointL.y)/2);
                    pointVisitedByL.add(pointL);
                } else if(pointH.y == pointL.y){
                    pointL = new Point(pointL.x + (pointH.x - pointL.x)/2, pointL.y);
                    pointVisitedByL.add(pointL);
                } else {
                    // do not move
                }
            }
            else if(diff == 3){
                if(Math.abs(pointH.x - pointL.x) == 1){
                    pointL = new Point(pointL.x + (pointH.x -  pointL.x), pointL.y + (pointH.y - pointL.y)/2);
                    pointVisitedByL.add(pointL);
                } else if(Math.abs(pointH.y- pointL.y) == 1){
                    pointL = new Point(pointL.x + (pointH.x - pointL.x)/2, pointL.y + (pointH.y-pointL.y));
                    pointVisitedByL.add(pointL);
                } else {
                    System.out.println("Nem kellene itt lennie 3 ");
                }

            } else {
                System.out.println("Nem kellene itt lennie 2 ");
            }
        }
    }


    record Instruction(String direction, int step){};
    record Point(int x, int y){};
}
