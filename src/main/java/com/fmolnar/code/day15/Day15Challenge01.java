package com.fmolnar.code.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15Challenge01 {

    public Day15Challenge01() {

        firstLetter.put(0L,1);
        firstLetter.put(3L,2);
        firstLetter.put(6L,3);
        szamok.add(0L);
        szamok.add(3L);
        szamok.add(6L);
        //15,5,1,4,7,0
//        szamok.add(15L);
//        szamok.add(5L);
//        szamok.add(1L);
//        szamok.add(4L);
//        szamok.add(7L);
//        szamok.add(0L);
    }

    long lastNumber = 6L;

    Map<Long, Integer> differences = new HashMap<>();

    Map<Long, Integer> firstLetter = new HashMap();

    List<Long> szamok = new ArrayList<>();

    public void calculate2(){
        for(int i=szamok.size()-1; i<30000000;i++){
            long lastNumber = szamok.get(i);
            checkConditions(lastNumber);
        }
        System.out.println(szamok.get(30000000-1));
    }

    private void checkConditions(long lastNumber) {
        if(firstNumber(lastNumber)){
            szamok.add(0L);
        } else {
            long newNumber = calculDiff(lastNumber);
            szamok.add(newNumber);
        }
    }

    private long calculDiff(long lastNumber) {
        int index1=szamok.size();
        int index2 = -1;
        for(int jj=szamok.size()-2; 0<=jj; jj--){
            if(szamok.get(jj)== lastNumber){
                index2 = jj+1;
                break;
            }
        }
        return index1-index2;
    }

    private boolean firstNumber(long lastNumber) {
        List<Long> ujSzamok = new ArrayList<>();
        for(int ii=0;ii<szamok.size()-1; ii++) {
            ujSzamok.add(szamok.get(ii));
        }
        return !ujSzamok.contains(lastNumber);
    }

    public void calculate(){
        for(int index=firstLetter.size()+1; index<2020; index++){
            // Second time
            if(firstLetter.containsKey(lastNumber) && !differences.containsKey(lastNumber)){
                lastNumber = 0L;
                checkIfHadAlreadyOccurennce(lastNumber, index);
                firstLetter.put(lastNumber,index);
            } else if(firstLetter.containsKey(lastNumber) && differences.containsKey(lastNumber)){

                int lastOccurence = firstLetter.get(lastNumber);
                int diff = index-lastOccurence;
                lastNumber = diff;
            }

        }

    }

    public void checkIfHadAlreadyOccurennce(long lastNumber, int position){
        if(firstLetter.containsKey(lastNumber)){
            int position1 = firstLetter.get(lastNumber);
            differences.put(lastNumber, position-position1);
        }
    }
}
