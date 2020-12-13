package com.fmolnar.code.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day13Challenge02 {

    public Day13Challenge02() {
    }

    Integer time;
    String listBus;
    List<Integer> buses = new ArrayList<>();

    List<String> lines = new ArrayList<>();
    public void calculate() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/day13/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counter =0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if(counter==1){
                        listBus = line;
                    }
                    counter++;
                }
            }
        }
        compteBusses();
        calulTobbszoros();
        System.out.println(lines);
    }

    private void calulTobbszoros() {
        int lengthOfSeries = buses.size()-1;
        long utolso = buses.get(lengthOfSeries);
        //  521 has to be divided by 37, 19 and 17 according to input data --> step interval 521*37*19*17
        // because all of them are prime numbers
        long minSzorzat = 521L*37L*19L*17L;
        System.out.println("MinSzorzat: " + minSzorzat);

        for(long level = Long.valueOf(lengthOfSeries); ; level++){
            long max = 100000000000000L;
            long szorzat = level*minSzorzat;
            if(max<szorzat){
                // Number deduced from the special input as well
                long minusz = 19L;
                if(joASzamsor(szorzat-minusz)){
                    System.out.println((szorzat-minusz));
                    break;
                }
            }
        }
    }

    private boolean joASzamsor(long alap) {
        for(int ii = 0; ii< buses.size(); ii++){
            int szam = buses.get(ii);
            if(szam!=-1){
                long maradek = (alap+Long.valueOf(ii)) %  Long.valueOf(szam);
                if(maradek == 0){
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private void compteBusses() {
        String bus = "";
        for(int i=0; i<listBus.length(); i++){
            if(listBus.charAt(i)==',' && bus.length()!=0){
                if(listBus.charAt(i-1)=='x'){
                    buses.add(-1);
                } else {
                    buses.add(Integer.valueOf(bus));
                }
                bus="";
            } else {
                bus = bus + listBus.charAt(i);
            }
        }
    }
}
