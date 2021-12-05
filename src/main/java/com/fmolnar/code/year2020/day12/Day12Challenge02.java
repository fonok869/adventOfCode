package com.fmolnar.code.year2020.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day12Challenge02 {

    private static final Character EAST = 'E';
    private static final Character WEST = 'W';
    private static final Character SOUTH = 'S';
    private static final Character NORTH = 'N';


    public Day12Challenge02() {
    }



    List<String> insctuctions = new ArrayList<>();
    int wayPointXX = 10;
    int wayPointYY = 1;
    int stepX = 0; // EAST 10
    int stepY = 0; // NORTH 1

    public void calculate() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/2020/day12/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    insctuctions.add(line);
                }
            }
        }
        comptage();

        System.out.println("Result : " + String.valueOf(Math.abs(stepX) + Math.abs(stepY)));
    }

    private void comptage() {
        for (String inst : insctuctions) {
            if(inst.startsWith("N") || inst.startsWith("E") || inst.startsWith("W") || inst.startsWith("S")){
                wayPointMove(inst);
            } else if (inst.startsWith("F")){
                moveStep(inst);
            } else if (inst.startsWith("L") || inst.startsWith("R")){
                wayPointForgatas(inst);
            } else {
                throw new RuntimeException("baj van");
            }

        }
    }

    private void wayPointForgatas(String inst) {
        String lepesString = inst.substring(1);
        Character direction = inst.charAt(0);
        int lepes = Integer.valueOf(lepesString);
        if('R' == direction){
            if(lepes == 180){
                wayPointXX = wayPointXX * (-1);
                wayPointYY = wayPointYY * (-1);
            }
            if(lepes == 90){
                int wayX = wayPointXX;
                int wayY = wayPointYY;
                wayPointXX = wayY;
                wayPointYY = wayX * (-1);
            }
            if(lepes == 270){
                int wayX = wayPointXX;
                int wayY = wayPointYY;
                wayPointXX = wayY * (-1);
                wayPointYY = wayX;
            }
        } else if('L' == direction){
            if(lepes == 180){
                wayPointXX = wayPointXX * (-1);
                wayPointYY = wayPointYY * (-1);
            }
            if(lepes == 90){
                int wayX = wayPointXX;
                int wayY = wayPointYY;
                wayPointXX = wayY * (-1);
                wayPointYY = wayX;
            }
            if(lepes == 270){
                int wayX = wayPointXX;
                int wayY = wayPointYY;
                wayPointXX = wayY;
                wayPointYY = wayX * (-1);
            }
        }
    }

    private void moveStep(String stepInst) {
        String lepesString = stepInst.substring(1);
        Character direction = stepInst.charAt(0);
        int lepes = Integer.valueOf(lepesString);
        stepX = stepX + lepes * wayPointXX;
        stepY = stepY + lepes * wayPointYY;
    }

    private void wayPointMove(String inst) {
        String lepesString = inst.substring(1);
        Character direction = inst.charAt(0);
        int lepes = Integer.valueOf(lepesString);
        if (EAST == direction) {
            wayPointXX = wayPointXX + lepes;
        } else if (WEST == direction) {
            wayPointXX = wayPointXX - lepes;
        } else if (NORTH == direction) {
            wayPointYY = wayPointYY + lepes;
        } else if (SOUTH == direction) {
            wayPointYY = wayPointYY - lepes;
        } else {
            throw new RuntimeException("baj van");
        }
    }

}
