package com.fmolnar.code.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Challenge01 {

    public Day14Challenge01() {
    }

    String pattern = "(^mem\\[(\\d+)\\]\\s=\\s(\\d+)$)";
    Pattern patternToLine = Pattern.compile(pattern);

    List<String> lines = new ArrayList<>();
    Map<Long, Long> maps = new HashMap<>();
    List<String> bunch = new ArrayList<>();


    public void calculate() throws IOException {

        InputStream reader = getClass().getResourceAsStream("/day14/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            int counter = 0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (line.startsWith("mask")) {
                        if (counter != 0) {
                            szamolas();
                        }
                        bunch = new ArrayList<>();
                    }
                    counter++;
                    bunch.add(line);
                }
            }
            System.out.println("Osszeg: " + osszead());
        }
    }

    private long osszead() {
        long osszeg = 0L;
        for(Map.Entry<Long, Long> entry : maps.entrySet()){
            osszeg = osszeg + entry.getValue();
        }
        return osszeg;
    }

    private void szamolas() {
        String decimal = bunch.get(0).substring(7);
        Long index;
        Long value;
        for (int i = 1; i < bunch.size(); i++) {
            Matcher matcher = patternToLine.matcher(bunch.get(i));
            if (matcher.find()) {
                System.out.println("Group1 : " + matcher.group(1));
                index = Long.valueOf(matcher.group(2));
                value = Long.valueOf(matcher.group(3));
            } else {
                throw new RuntimeException("nagy gond van");
            }
            String binary = Long.toBinaryString(value);
            Long newNumber = calculWithMask(binary, decimal);
            addToMap(index, newNumber);
        }
    }

    private void addToMap(Long index, Long newNumber) {
        maps.put(index, newNumber);
    }

    private Long calculWithMask(String binary, String decimal) {
        String newSzam = "";
        for (int ii = 0; ii < decimal.length(); ii++) {
            char decBetu = decimal.charAt(decimal.length()-1-ii);
            char binaryBetu;
            if(ii<binary.length()){
                binaryBetu = binary.charAt(binary.length()-1-ii);
            } else {
                binaryBetu = '0';
            }
            if(decBetu == 'X'){
                newSzam = binaryBetu + newSzam;
            }else {
                newSzam = decBetu + newSzam;
            }
        }
        return  Long.parseLong(newSzam, 2);
    }


}
