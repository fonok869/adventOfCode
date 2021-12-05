package com.fmolnar.code.year2020.day23;

import java.util.ArrayList;
import java.util.List;

public class Day23Challenge01 {

    public static final int NINE = 9;

    public Day23Challenge01() {
    }

    String szam = "398254716";
    Integer lowestValue = -1;
    Integer highestValue = -1;
    List<Integer> numbers = new ArrayList<>();

    public void caulculate() {
        initNumbers();
        for (int j = 0; j < 100; j++) {
            System.out.println("Move : " + (j + 1));
            forward(j);
            System.out.println(" - ");
        }
        System.out.println(numbers);
        int index = numbers.indexOf(1);
        String toto = "";
        for (int i = (0 + index+1); i < index + NINE; i++) {
            toto = toto + numbers.get(i % NINE);
        }
        System.out.println("Solution: " + toto);


    }

    private void forward(int j) {
        int modulo = j % NINE;
        int cupsSelection = numbers.get(modulo);
        System.out.println("Cups :" + cupsSelection);
        int x = (j + 1) % NINE;
        int y = (j + 2) % NINE;
        int z = (j + 3) % NINE;
        System.out.println("Pick ups: " + numbers.get(x) +", "+ numbers.get(y)+", "+ numbers.get(z));
        List<Integer> newArray = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (i == x || i == y || z == i || modulo == i) {
                continue;
            }
            newArray.add(numbers.get(i));
        }
        int destination = selection(cupsSelection, newArray);
        System.out.println("Destination: " + destination);
        System.out.println("Numbers : " + showAfter1(numbers));
        List<Integer> relative = new ArrayList<>();
        createRelative(j, relative);
        int position = selectPosition(destination, relative);
        int kivonas = j% NINE;
        List<Integer> uj = rearrange(position, x - kivonas, y - kivonas, z - kivonas, relative);
        shiftNumbers(uj, kivonas, cupsSelection);
    }

    private String showAfter1(List<Integer> numbers) {
        int indexOf = numbers.indexOf(1);
        List<Integer> values = new ArrayList<>();
        for(int i = Math.max(indexOf-5,0); i< Math.min(indexOf+5,numbers.size()); i++){
            values.add(numbers.get(i));
        }
        return values.toString();
    }

    private void shiftNumbers(List<Integer> uj, int j, int cupsSelection) {
        //start from destination
        int indexOfCup = uj.indexOf(cupsSelection);
        List<Integer> ujIntegs = new ArrayList<>();
        for (int i = indexOfCup; i < (indexOfCup + NINE); i++) {
            ujIntegs.add(uj.get(Math.abs(NINE + i - j) % NINE));
        }
        numbers = ujIntegs;
    }

    private void createRelative(int j, List<Integer> relative) {
        for (int i = j; i < j + NINE; i++) {
            relative.add(numbers.get(i % NINE));
        }
    }

    private List<Integer> rearrange(int position, int x, int y, int z, List<Integer> relative) {
        List<Integer> integers = new ArrayList<>();
        List<Integer> numbersToChange = new ArrayList<>(relative);
        int size = Integer.valueOf(numbersToChange.size());
        for (int i = 0; i < size; i++) {
            if (i == (position% NINE)) {
                integers.add(numbersToChange.get(i));
                integers.add(numbersToChange.get((x + NINE) % NINE));
                integers.add(numbersToChange.get((y + NINE) % NINE));
                integers.add(numbersToChange.get((z + NINE) % NINE));
            } else if (i == ((x + NINE) % NINE) || i == ((y + NINE) % NINE) || i==((z + NINE) % NINE)) {
                continue;
            } else {
                integers.add(numbersToChange.get(i));
            }
        }
        return integers;
    }

    private Integer selectPosition(int dest, List<Integer> relative) {
        for (int i = 0; i < relative.size(); i++) {
            if (dest == relative.get(i)) {
                return i;
            }
        }
        return null;
    }

    private int selection(int cupsSelection, List<Integer> newArray) {
        int searchFor = cupsSelection - 1;
        if (searchFor < lowestValue) {
            searchFor = highestValue;
        }
        if (newArray.contains(searchFor)) {
            return searchFor;
        } else {
            return selection(searchFor, newArray);
        }
    }


    private void initNumbers() {
        for (int i = 0; i < szam.length(); i++) {
            numbers.add(Integer.valueOf(szam.charAt(i) - '0'));
        }
//        for(int j=10; j<1000001; j++){
//            numbers.add(j);
//        }
        highestValue = numbers.stream().mapToInt(s -> s).max().getAsInt();
        lowestValue = numbers.stream().mapToInt(s -> s).min().getAsInt();
    }
}
