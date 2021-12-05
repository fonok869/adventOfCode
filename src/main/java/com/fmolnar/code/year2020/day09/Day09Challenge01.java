package com.fmolnar.code.year2020.day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day09Challenge01 {

    public Day09Challenge01() {
    }

    List<Long> numbers = new ArrayList<>();

    int maxnumber = 25;
    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day09/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    Long newValue = Long.valueOf(line);

                    if(numbers.size() == maxnumber){
                        List<Long> firstList = new ArrayList(numbers);
                        List<Long> secondList = new ArrayList(numbers);
                        boolean breaked = false;
                        outerlopp:
                        for(Long first : firstList){
                            for(Long second: secondList){
                                if(first+second == newValue && (first!=second)){
                                    numbers.remove(0);
                                    numbers.add(newValue);
                                    breaked = true;
                                    break outerlopp;
                                }
                            }
                        }
                        if(breaked == false){
                            System.out.println("Rossz Szam " + newValue);
                            return;
                        }
                    }
                    else{
                        numbers.add(newValue);
                    }
                }
            }
        }


    }
}
