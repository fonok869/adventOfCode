package com.fmolnar.code.day23;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day23ChallengeV201 {

    public Day23ChallengeV201() {
    }

    String szam = "389125467";
    Long lowestValue = -1L;
    Long highestValue = -1L;
    LinkedList<Long> numbers = new LinkedList<>();


    public void caulculate() {
        initNumbers();
        for (int j = 0; j < 100; j++) {
            System.out.println("Move : " + (j + 1));
            System.out.println("Numbers: " + numbers);
            forward(j%9);
            System.out.println(" ");
        }
        System.out.println(numbers);
        int index = numbers.indexOf(1L);
        String toto = "";
        for (int i = (index + 1); i < index + 9; i++) {
            toto = toto + numbers.get(i % 9);
        }
        System.out.println("Solution: " + toto);


    }

    private void forward(int j) {
        List<Long> new3Values = getNext3Values(0);
        long cupsSelection = numbers.get(0);
        removeFirst4Elements();
        long searchFor = nextNumber(cupsSelection, new3Values);
        int indexToInsert = numbers.indexOf(searchFor);
        numbers.addAll(indexToInsert+1, new3Values);
        numbers.add(cupsSelection);
    }

    private void removeFirst4Elements() {
        for(int i=0;i<4; i++){
            numbers.removeFirst();
        }
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

    private List<Long> getNext3Values(int j) {
        List<Long> returnValue = new ArrayList<>();
        for(int i = j+1; i<=j+3; i++){
            returnValue.add(Long.valueOf(numbers.get(i%9)));
        }
        return returnValue;
    }


    private void initNumbers() {
        for (int i = 0; i < szam.length(); i++) {
            numbers.add(Long.valueOf(szam.charAt(i) - '0'));
        }
        highestValue = numbers.stream().mapToLong(s -> s).max().getAsLong();
        lowestValue = numbers.stream().mapToLong(s -> s).min().getAsLong();
    }

    public class FerencLinkedList{

    }
}
