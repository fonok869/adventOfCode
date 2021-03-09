package com.fmolnar.code.day23;

import java.util.ArrayList;
import java.util.List;

public class Day23Refacto01 {

    public Day23Refacto01() {
    }

    String szam = "398254716";
    Long lowestValue = -1L;
    Long highestValue = -1L;
    List<Long> numbers = new ArrayList<>();
    public static final int NINE = 9;

    public void caulculate() {
        initNumbers();
        for (int j = 0; j < 100; j++) {
            long firstNumber = numbers.get(j);
            List<Long> subList = new ArrayList<>();
            subList.add(numbers.get(j+1));
            subList.add(numbers.get(j+2));
            subList.add(numbers.get(j+3));
            while(true){

            }



        }
        writeOut();
    }
    public long nextNumber(long numberToDecrease, List<Long> sublist){
        long newValue = minusOne(numberToDecrease);
        while(sublist.contains(newValue)){
            newValue = minusOne(newValue);
        }
        return newValue;
    }

    private long minusOne(long numberToDecrease) {
        if(numberToDecrease==lowestValue){
            return highestValue;
        }
        return (numberToDecrease-1L);
    }

    private void writeOut() {
        System.out.println(numbers);
        int index = numbers.indexOf(1);
        String toto = "";
        for (int i = (0 + index+1); i < index + NINE; i++) {
            toto = toto + numbers.get(i % NINE);
        }
        System.out.println("Solution: " + toto);
    }

    private void initNumbers() {
        for (int i = 0; i < szam.length(); i++) {
            numbers.add(Long.valueOf(szam.charAt(i) - '0'));
        }
        highestValue = numbers.stream().mapToLong(s -> s).max().getAsLong();
        lowestValue = numbers.stream().mapToLong(s -> s).min().getAsLong();
    }
}
