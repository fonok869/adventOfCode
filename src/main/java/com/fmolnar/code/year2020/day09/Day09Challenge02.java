package com.fmolnar.code.year2020.day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day09Challenge02 {

    public Day09Challenge02() {
    }

    private List<Long> numbers = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day09/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    Long newValue = Long.valueOf(line);
                    numbers.add(newValue);

                }
            }
        }
        numbersToCalculate();
    }

    private Long numbersToCalculate(){
        outerloop:
        for(int i=0; i<numbers.size();i++){
            List<Long> newArraylist = new ArrayList<>();
            for(int j=i; j<numbers.size()-i; j++){
                newArraylist.add(numbers.get(j));
                // Shortcut for index to not to count for nothing
                if(595<j){
                    break ;
                }
                if(isGoodList(newArraylist)){
                    break outerloop;
                };
            }
        }
           return null;
    }

    private boolean isGoodList(List<Long> longer){
        Long searchFor = 258585477L;
        Long sum = longer.stream().mapToLong(s->s).sum();
        if(Objects.equals(searchFor,sum)){
            System.out.println("Min: " +longer.stream().mapToLong(s->s).min().getAsLong());
            System.out.println("Max: " +longer.stream().mapToLong(s->s).max().getAsLong());
            System.out.println("Sum: " + (longer.stream().mapToLong(s->s).min().getAsLong()+ longer.stream().mapToLong(s->s).max().getAsLong()));
            return true;
        }

        return false;
    }


    }
