package com.fmolnar.code.day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day05Challenge02 {

    public Day05Challenge02() {
    }

    public void calculateDay0502() throws IOException {
        getList();
    }

    public void getList() throws IOException {
        List<Integer> ids = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream("/day05/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                ids.add(calculateSeatId(line));
            }
        }

        List<Integer> allPlaces = calculateAllPlaces();
        allPlaces.removeAll(ids);

        System.out.println("Missing seat: "  + allPlaces.get(0));
    }

    private List<Integer> calculateAllPlaces() {
        List<Integer> allPlaces = new ArrayList<>();
        for(int row=1; row<127; row++){
            for(int line = 0; line<8; line++){
                int sum = row * 8 + line;
                if(sum<=813){
                    allPlaces.add(sum);
                }
            }
        }
        return allPlaces;
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
