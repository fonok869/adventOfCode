package com.fmolnar.code.year2020.day23;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day23Challenge02 {

    public static final int ITERATIONMAX = 10000000;
    public static final int MAXLINELENGTH = 1000000;

    public Day23Challenge02() {
    }

    String szam = "398254716";
    Long lowestValue = -1L;
    Long highestValue = -1L;
    LinkedList<Long> numbers = new LinkedList<>();

    public void caulculate() {
        long time1 = System.currentTimeMillis();
        initNumbers();
        for (int j = 0; j < ITERATIONMAX; j++) {
            if(j%10000 == 0){
                long time2 = System.currentTimeMillis() - time1;
                System.out.println("Perc: " + time2/60000L);
                System.out.println("Move : " + (j + 1));
            }
            forward(j % MAXLINELENGTH);
        }
        int index = numbers.indexOf(1L);
        System.out.println("1 Mellette: " + numbers.get((index+1)%MAXLINELENGTH));
        System.out.println("2 Mellette: " + numbers.get((index+2)%MAXLINELENGTH));
        System.out.println("Szorzat : " + numbers.get((index+1)%MAXLINELENGTH) * numbers.get((index+2)%MAXLINELENGTH));
        long time2 = System.currentTimeMillis() - time1;
        System.out.println("Millisec: " + time2);
    }

    private void forward(int j) {
        List<Long> new3Values = getNext3Values(0);
        long cupsSelection = numbers.get(0);
        long nextNumberToSearchFor = nextNumber(cupsSelection, new3Values);
        int indexToInsert = numbers.indexOf(nextNumberToSearchFor);
        numbers.addAll(indexToInsert + 1, new3Values);
        numbers.add(cupsSelection);
        removeFirst4Elements();
    }

    private void removeFirst4Elements() {
        for (int i = 0; i < 4; i++) {
            numbers.removeFirst();
        }
    }

    public long nextNumber(long numberToDecrease, List<Long> sublist) {
        long newValue = minusOne(numberToDecrease);
        while (sublist.contains(newValue)) {
            newValue = minusOne(newValue);
        }
        return newValue;
    }

    private long minusOne(long numberToDecrease) {
        if (numberToDecrease == lowestValue) {
            return highestValue;
        }
        return (numberToDecrease - 1L);
    }

    private List<Long> getNext3Values(int j) {
        List<Long> returnValue = new ArrayList<>();
        for (int i = j + 1; i <= j + 3; i++) {
            returnValue.add(Long.valueOf(numbers.get(i % MAXLINELENGTH)));
        }
        return returnValue;
    }


    private void initNumbers() {
        for (int i = 0; i < szam.length(); i++) {
            numbers.add(Long.valueOf(szam.charAt(i) - '0'));
        }
        for (long j = szam.length(); j < MAXLINELENGTH; j++) {
            numbers.add(j + 1);
        }
        highestValue = 1L;
        lowestValue = Long.valueOf(MAXLINELENGTH);
    }
}
