package com.fmolnar.code.year2022.day04;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.List;

public class Day04 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day04/input.txt");


        int counterA = 0;
        int counterB = 0;
        for (String line : lines) {

            String[] splitted = line.split(",");
            String first = splitted[0];
            String second = splitted[1];

            int firstI = first.indexOf('-');
            int secondI = second.indexOf('-');

            int firstMin = Integer.valueOf(first.substring(0, firstI));
            int firstMax = Integer.valueOf(first.substring(firstI+1));

            int secondMin = Integer.valueOf(second.substring(0, secondI));
            int secondMax = Integer.valueOf(second.substring(secondI+1));

            if(secondMin<=firstMax && secondMax<=firstMax && firstMin<=secondMax && firstMin<=secondMin){
                counterA++;
            } else if(secondMin<=firstMin && secondMin<=firstMax && firstMin<=secondMax && firstMax<=secondMax){
                counterA++;
            }

            if(firstMin<=secondMin && secondMin<=firstMax){
                counterB++;
            } else if(firstMin<=secondMax && secondMax<=firstMax){
                counterB++;
            } else if(secondMin<=firstMin && firstMin<=secondMax){
                counterB++;
            } else if(secondMin<=firstMax && firstMax<=secondMax){
                counterB++;
            }


        }

        System.out.println("First: " + counterA);
        System.out.println("Second: " + counterB);
    }
}

