package com.fmolnar.code.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16Challenge01 {

    public static final String OR = "or";
    public static final String NEARBY = "nearby";

    public Day16Challenge01() {
    }

    String patternSeat = "((\\d+)\\-(\\d+))";
    Pattern patternSeatLine = Pattern.compile(patternSeat);

    List<String> lines = new ArrayList<>();
    List<String> validationRules= new ArrayList();
    Set<Integer> valids = new HashSet<>();
    List<Integer> invalidNumbers = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day16/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean nearBy = false;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if(line.contains(OR)){
                        getRule(line);
                    }
                    if(nearBy){
                        lines.add(line);
                    }
                    if(line.contains(NEARBY)){
                        nearBy = true;
                    }
                }
            }
        }
        transformRuleToValidNumbers();
        checkLines();
        System.out.println("Sum: " + invalidNumbers.stream().mapToInt(s->s).sum());
    }

    private void getRule(String line) {
        int index = line.indexOf(":");
        String intervalles = line.substring(index);
        int indexOr = intervalles.indexOf(OR);
        validationRules.add(intervalles.substring(2,indexOr-1));
        validationRules.add(intervalles.substring(indexOr+3));
    }

    private void checkLines() {
        for(String line : lines){
            String[] values = line.split(",");
            for(int ii=0; ii< values.length; ii++){
                Integer numbers = Integer.valueOf(values[ii]);
                if (!valids.contains(numbers)){
                    invalidNumbers.add(numbers);
                }
            }
        }
    }

    private void transformRuleToValidNumbers() {
        validationRules.forEach(
             rule ->{
                 Matcher matcher = patternSeatLine.matcher(rule);
                 if(matcher.find()){
                     int smaller = Integer.valueOf(matcher.group(2));
                     int bigger = Integer.valueOf(matcher.group(3));
                     for(int j=smaller; j<=bigger; j++){
                         valids.add(j);
                     }
                 }
             }
        );
    }


}
