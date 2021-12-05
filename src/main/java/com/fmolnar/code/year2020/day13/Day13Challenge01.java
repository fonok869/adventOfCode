package com.fmolnar.code.year2020.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13Challenge01 {

    public Day13Challenge01() {
    }

    Integer time;
    String listBus;
    List<Integer> buses = new ArrayList<>();

    List<String> lines = new ArrayList<>();
    public void calculate() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/2020/day13/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counter =0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if(counter==0){
                        time = Integer.valueOf(line);
                    }
                    listBus = line;
                    counter++;
                }
            }
        }
        countBuses();
        Collections.sort(buses);
        countModulo();
        System.out.println(lines);
    }

    private void countModulo() {
        int id=0;
        int kulonbseg = 0;
        for(Integer integ : buses){
            if(id==0){
                id = integ;
                kulonbseg = Math.abs(integ - (time % integ));
            }
            if(kulonbseg!=0){
                int ujKulonbseg = Math.abs(integ - (time % integ));
                if(ujKulonbseg<kulonbseg){
                    id = integ;
                    kulonbseg = ujKulonbseg;
                }
            }
        }
        System.out.println("Result" + id * kulonbseg);
    }

    private void countBuses() {
        String bus = "";
        for(int i=0; i<listBus.length(); i++){
            if(listBus.charAt(i)=='x'){
                continue;
            } else  if(listBus.charAt(i)==',' && bus.length()!=0){
                buses.add(Integer.valueOf(bus));
                bus="";
            } else if (listBus.charAt(i)==',' && bus.length()==0){
                bus = "";
            } else {
                bus = bus + listBus.charAt(i);
            }

        }
    }


}
