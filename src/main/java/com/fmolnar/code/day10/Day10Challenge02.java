package com.fmolnar.code.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10Challenge02 {

    public Day10Challenge02() {
    }

    List<Integer> numbers = new ArrayList<>();
    List<Integer> differences = new ArrayList<>();
    Map<Long,Integer> onesLengthOccurrences = new HashMap<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day10/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            numbers.add(0);
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    numbers.add(Integer.valueOf(line));
                }
            }
        }

        Collections.sort(numbers);
        createListOfDifference();
        sumUpOnesSeriesLength();

        Double result = calulateResult();

        System.out.println("All options:" + result);
    }

    private Double calulateResult() {
        Double sum = 1.0;
        for(Long i = 1l; i< onesLengthOccurrences.size(); i++){
            Double integer = Double.valueOf(onesLengthOccurrences.get(i));
            if(i==1L){
                continue;
            }
            if(i==2L){
                integer = Math.pow(2, integer);
            }
            if(i==3L){
                integer = Math.pow(4, integer);
            }
            if(i==4L){
                integer = Math.pow(7, integer);
            }
            sum = sum*integer;
        }
        return sum;
    }

    private void sumUpOnesSeriesLength(){
        outerloop:
        for(int i = 0; i< differences.size(); i++){
            List<Integer> egyesek = new ArrayList<>();
            for(int j = i; j< differences.size(); j++){
                if((differences.get(j)!=1)){
                    saveEgyesek(egyesek);
                    egyesek = new ArrayList<>();
                    i = j;
                    break;
                }
                egyesek.add(differences.get(j));
                // Last element
                if(j== differences.size()-1){
                    saveEgyesek(egyesek);
                    break outerloop;
                }

            }
        }

    }

    private void saveEgyesek(List<Integer> egyesek) {
        Long hossz = egyesek.stream().count();
        if(onesLengthOccurrences.containsKey(hossz)){
            Integer elofordulas = onesLengthOccurrences.get(hossz);
            onesLengthOccurrences.put(hossz, elofordulas+1);
        }else {
            // Init
            onesLengthOccurrences.put(hossz,1);
        }

    }

    private void createListOfDifference(){
        Integer elozo = 0;
        for(Integer numb: numbers){
            Integer kulonbseg = numb-elozo;
            if(kulonbseg<=3){
                differences.add(kulonbseg);
            }
            elozo=numb;
        }
    }
}
