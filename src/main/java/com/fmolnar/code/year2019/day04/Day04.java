package com.fmolnar.code.year2019.day04;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day04 {

    public void calculate() {
        int max = 673251;
        int min = 197487;
        List<String> ensemble = new ArrayList<>();

        int counter = 0;
        for (int i = min; i <= max; i++) {

            String actualInt = String.valueOf(i);

            Character before = null;
            boolean twoDigits = false;
            boolean increase = false;
            for (int j = 0; j < 6; j++) {
                Character now = actualInt.charAt(j);
                if (before == null) {
                    before = now;
                    continue;
                }

                if (Integer.valueOf(before) == Integer.valueOf(now)) {
                    twoDigits = true;
                    increase = true;
                    before = now;
                    continue;
                }

                if (Integer.valueOf(before) < Integer.valueOf(now)) {
                    increase = true;

                }
                if (Integer.valueOf(now) < Integer.valueOf(before)) {
                    increase = false;
                    break;
                }
                before = now;

            }

            if (twoDigits && increase) {
                ensemble.add(actualInt);
                counter++;
            }
        }

        int counterlimited = 0;
        for(String acts : ensemble){
            Map<Integer, Long> map = acts.chars()
                    .boxed()
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()));
            boolean found = false;
            for(Map.Entry<Integer, Long> entry : map.entrySet()){
                if(entry.getValue()==2){
                    found = true;
                    break;
                }
            }

            if(found){
                counterlimited++;
            }

        }

        System.out.println("First: " + counter);
        System.out.println("Second: " + (counterlimited));
    }
}
