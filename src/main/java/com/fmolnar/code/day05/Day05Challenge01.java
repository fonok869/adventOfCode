package com.fmolnar.code.day05;

import com.fmolnar.code.day04.Day04Challenge01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day05Challenge01 {

    public Day05Challenge01() {
    }

    public void calculateDay0501() throws IOException {
        getList();
    }

    public List<String> getList() throws IOException {
        List<Integer> ids = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream("/day05/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            List<String> passeportDetails = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                ids.add(calculateSeatId(line));
            }
        }

        System.out.println("Maximum:"  + ids.stream().max(Integer::compareTo).get());
        return null;
    }

    private Integer calculateSeatId(String line){
        if(line.length() == 10){
            int seged1 = 64;
            int seged2 = 4;
            int seatID = 0;
            for(int i = 0 ; i< line.length() ; i++){
                if(i<7){
                    if(line.charAt(i)=='B'){
                        seatID = seged1 + seatID;
                    }
                    seged1 = seged1/2;
                }
                else {
                    if(i==7){
                        seatID = 8*seatID;
                    }
                    if(line.charAt(i)=='R'){
                        seatID = seged2 + seatID;
                    }
                    seged2 = seged2/2;
                }
            }
            return seatID;
        }
        throw new RuntimeException("Baj van");
    }
}
