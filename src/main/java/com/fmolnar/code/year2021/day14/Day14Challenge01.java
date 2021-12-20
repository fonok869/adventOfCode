package com.fmolnar.code.year2021.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day14Challenge01 {

    private List<String> lines = new ArrayList<>();

    String alap = "";
    Set<String> allLetters = new HashSet<>();
    private Map<String, String> polys = new HashMap<>();
    private Map<String, Integer> lettersOccurence = new HashMap<>();
    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day14/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counter =0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if(counter==0){
                        alap = line;
                    }
                    else {
                        String[] args = line.split(" -> ");
                        polys.put(args[0], args[1]);
                    }
                    lines.add(alap);
                    counter++;
                }
            }
        }

        int step = 40;
        for(int i=0; i<step;i++){
            growing();
        }
        for(String charToCjeck : allLetters){
            lettersOccurence.put(charToCjeck, (int) alap.chars().mapToObj(s->s).filter(s->charToCjeck.charAt(0) == s).count());
        }

        List<Integer> integs = lettersOccurence.entrySet().stream().map(s->s.getValue()).collect(Collectors.toList());
        int max= integs.stream().mapToInt(i->i).max().getAsInt();
        int min= integs.stream().mapToInt(i->i).min().getAsInt();
        System.out.println("Sum: " + (max-min));
    }

    private void growing() {
        String newpolymer = "";
        for(int i=0;i<alap.length()-1;i++){
            String code = alap.substring(i, i+2);
            String newPol = polys.get(code);
            newpolymer = newpolymer + (i==0 ? alap.substring(i,i+1): "" )+ newPol + alap.substring(i+1, i+2);
            allLetters.add(alap.substring(i,i+1));
            allLetters.add(newPol);
            allLetters.add(alap.substring(i+1,i+2));
        }
        alap = newpolymer;
    }
}
