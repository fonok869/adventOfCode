package com.fmolnar.code.year2020.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23ChallengeV202 {

    public static final int ITERATIONMAX = 10000000;
    public static final int MAXLINELENGTH = 1000000;

    public Day23ChallengeV202() {
    }

    String szam = "398254716";
    Long lowestValue = -1L;
    Long highestValue = -1L;
    List<Long> numbers = new ArrayList<>();
    Map<Long, Long> valueNextValue = new HashMap<>();
    long lastMemmber = -1L;

    public void caulculate() {
        long time1 = System.currentTimeMillis();
        initNumbers();
        initMap();
        long nextValue = 3L;
        lastMemmber = MAXLINELENGTH;
        for (int j = 0; j < ITERATIONMAX; j++) {

//            System.out.println(j + " Szamsor: " + kiirod(nextValue));
//            System.out.println(j + " map: " + valueNextValue);
            if(j%10000 == 0){
                System.out.println(j);

            }

            nextValue = forward(nextValue);
        }
        long toto = valueNextValue.get(1L);
        long toto2 = valueNextValue.get(toto);
        System.out.println("1 Mellette: " + toto);
        System.out.println("2 Mellette: " + toto2);
        System.out.println("Szorzat : " + toto * toto2);
        long time2 = System.currentTimeMillis() - time1;
        System.out.println("Millisec: " + time2);
    }

    private String kiirod(Long nextValue) {
        String toto = nextValue.toString();
        Long szamToStart = nextValue;
        for (int i = 0; i < valueNextValue.size(); i++) {
            szamToStart = valueNextValue.get(szamToStart);
            toto = toto + ", " + szamToStart;
        }
        return toto;
    }

    private void initMap() {
        for (int i = 0; i < MAXLINELENGTH; i++) {
            long n1 = numbers.get(i);
            Long n2 = i + 1 == MAXLINELENGTH ? null : numbers.get(i + 1);
            valueNextValue.put(n1, n2);
        }
    }

    private long forward(long cupsSelection) {
        long first = valueNextValue.get(cupsSelection);
        long second = valueNextValue.get(first);
        long third = valueNextValue.get(second);
        long fourth = valueNextValue.get(third);
        long nextNumberToSearchFor = nextNumber(cupsSelection, Arrays.asList(first, second, third));
        // (2,4) --> (1, 4)
        Long newFirst = valueNextValue.get(nextNumberToSearchFor);
        //(3,8) --> (2,8)
        valueNextValue.put(nextNumberToSearchFor, first);
        // (2,4) --> (1, 4)
        if (newFirst == null) {
            lastMemmber = third;
        }
        valueNextValue.put(third, newFirst);


        // (100,3)
        valueNextValue.put(lastMemmber, cupsSelection);
        // (3, null)
        valueNextValue.put(cupsSelection, null);
        lastMemmber = cupsSelection;

        return fourth;
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
        System.out.println("Kezdi");
        for (int i = 0; i < szam.length(); i++) {
            numbers.add(Long.valueOf(szam.charAt(i) - '0'));
        }
        for (long j = szam.length(); j < MAXLINELENGTH; j++) {
            numbers.add(j + 1);
        }
        System.out.println("Elhagyta");
        lowestValue = 1L;
        highestValue = Long.valueOf(MAXLINELENGTH);
    }
}
