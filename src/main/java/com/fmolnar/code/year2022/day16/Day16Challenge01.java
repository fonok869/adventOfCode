package com.fmolnar.code.year2022.day16;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Day16Challenge01 {

    List<Ins> ins = new ArrayList<>();
    Map<String, List<String>> leads = new HashMap<>();
    Map<String, Integer> rates = new ConcurrentHashMap<>();
    List<String> notZeroRates = new ArrayList<>();
    Map<String, Map<String, Integer>> notNullDistance = new ConcurrentHashMap<>();
    List<String> allPoints = new ArrayList<>();

    public void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2022/day16/input.txt");

        for (String line : lines) {
            String start = line.substring("Valve ".length(), line.indexOf("has") - 1);
            int rate = Integer.valueOf(line.substring((line.indexOf("=") + 1), line.indexOf(";")));
            List<String> destinations = Arrays.stream(line.substring(line.indexOf("valve") + "valve".length() + 1).split(",")).map(String::trim).collect(Collectors.toList());
            ins.add(new Ins(start, rate, destinations));
            leads.put(start, destinations);
            rates.put(start, rate);
            allPoints.add(start);
            if (rate != 0) {
                notZeroRates.add(start);
            }
        }

        // Dikstra
        notZeroRates.forEach(s -> {
            List<String> allOthers = new ArrayList<>(notZeroRates);
            allOthers.remove(s);
            String start = s;
            for (String destination : allOthers) {
                dijkstra(start, destination);
            }
        });

        // Dikstra
        notZeroRates.forEach(s -> {
            dijkstra("AA", s);
        });


        List<Integer> maxValues = new ArrayList<>();

        String startLetter = "AA";
        int sum = 0;
        int harminc = 30;
        List<String> alreadyVisited = new ArrayList<>();
        alreadyVisited.add(startLetter);
        calculateMaxValue(startLetter, harminc, sum, maxValues, alreadyVisited);

        System.out.println("Result (1) : " + maxValues.stream().mapToInt(s->s).max().getAsInt());
    }

    public void calculateMaxValue(String startLetter, int harminc, int sum, List<Integer> maxValues, List<String> alreadyVisited){
        Map<String, Integer> newPotentiels = notNullDistance.get(startLetter);
        for(Map.Entry<String, Integer> potentiel : newPotentiels.entrySet()){
            String nextLetter = potentiel.getKey();
            Integer lepes = potentiel.getValue();
            if(lepes<5) {
                if (!alreadyVisited.contains(nextLetter)) {
                    List<String> alVisited = new ArrayList<>(alreadyVisited);
                    alVisited.add(nextLetter);
                    int hamrminc1 = harminc - lepes - 1;
                    int rate = rates.get(nextLetter);
                    int actualSum = rate * hamrminc1 + sum;
                    if (hamrminc1 <= 0) {
                        continue;
                    }
                    maxValues.add(actualSum);
                    calculateMaxValue(nextLetter, hamrminc1, actualSum, maxValues, alVisited);
                } else {
                    int hamrminc1 = harminc - lepes;
                    if (hamrminc1 <= 0) {
                        continue;
                    }
                    maxValues.add(sum);
                    calculateMaxValue(nextLetter, hamrminc1, sum, maxValues, alreadyVisited);
                }
            }
        }
    }



    private int getSum(List<String> permutate, int harminc, int sum, String startLetter) {
        for (int i = 0; i < permutate.size(); i++) {
            String nextLetter = permutate.get(i);
            Integer lepes = notNullDistance.get(startLetter).get(nextLetter);
            harminc = harminc - lepes -1;
            int rate = rates.get(nextLetter);
            sum += rate * harminc;
            startLetter = nextLetter;
            if(harminc<=0){
                break;
            }
        }
        return sum;
    }

    private void dijkstra(String start, String destination) {
        Map<String, Integer> distances = new HashMap<>();
        allPoints.forEach(s -> {
                    distances.put(s, Integer.MAX_VALUE);
                }
        );
        distances.put(start, 0);
        List<String> alreadyVisited = new ArrayList<>();
        String acutal = start;
        while (true) {

            //
            final int distMinActual = distances.entrySet().stream().filter(s -> !alreadyVisited.contains(s.getKey()))
                    .map(Map.Entry::getValue).mapToInt(s -> s).min().getAsInt();
            acutal = distances.entrySet().stream().filter(s -> !alreadyVisited.contains(s.getKey()))
                    .filter(s -> s.getValue() == distMinActual).map(s -> s.getKey()).collect(Collectors.toList()).get(0);

            List<String> szomszedok = leads.get(acutal);

            szomszedok.forEach(s -> {
                Integer distanceActual = distances.get(s);
                if ((distMinActual + 1) < distanceActual) {
                    distances.put(s, (distMinActual + 1));
                }
            });

            alreadyVisited.add(acutal);


            if (destination.equals(acutal)) {
                Map<String, Integer> values = new HashMap<>();
                notNullDistance.putIfAbsent(start, new HashMap<>());
                Map<String, Integer> actual = notNullDistance.get(start);
                actual.put(acutal, distances.get(acutal));
                break;
            }
        }
    }

    record Ins(String start, int rate, List<String>destinations) {
    }

    ;
}
