package com.fmolnar.code.year2020.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01Challenge {

    public Day01Challenge() {

    }

    public void calculateDay1() throws IOException {
        calculate(getArrayOfInteger());
    }

    public List<Integer> getArrayOfInteger() throws IOException {
        List<Integer> integers = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream ("/2020/day01/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String values;
            while ((values = file.readLine()) != null) {
                integers.add(Integer.valueOf(values));
            }
        }
        return integers;
    }

    private void calculate(List<Integer> integers){
        Collections.sort(integers);
        int max = integers.size();
        System.out.println("Length of loop:" + max);
        int first;
        int second;
        int third;
        int secondSum = 0;
        int thirdSum = 0;

        outerloop:
        for(int i = 0; i<max; i++){
            first = integers.get(i);
            secondSum = 2020 - first;
            for(int j=i+1; j<max; j++){
                second = integers.get(j);
                if((1.0*second) > (1.0*secondSum)/2.0){
                    break;
                }
                thirdSum = secondSum - second;
                for (int k=j+1; k<max; k++){
                    third = integers.get(k);
                    if(third>thirdSum){
                        break;
                    }
                    if(thirdSum == third){
                        System.out.println("First: " + first + " Second: " + second + " Third: " + third);
                        System.out.println("Multiply: " + first*second*third);
                        System.out.println("i: " + i);
                        System.out.println("j: " + j);
                        System.out.println("k: " + k);
                        break outerloop;
                    }
                }
            }
        }
    }


}
