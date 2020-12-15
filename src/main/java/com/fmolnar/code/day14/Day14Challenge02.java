package com.fmolnar.code.day14;

import org.apache.commons.lang3.StringUtils;

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

public class Day14Challenge02 {

    public static final char X = 'X';

    public Day14Challenge02() {
    }

    String pattern = "(^mem\\[(\\d+)\\]\\s=\\s(\\d+)$)";
    Pattern patternToLine = Pattern.compile(pattern);

    Map<Long, Long> maps = new HashMap<>();
    List<String> bunch = new ArrayList<>();
    List<Integer> elofordulasList = new ArrayList<>();


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
        for (Map.Entry<Long, Long> entry : maps.entrySet()) {
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
                index = Long.valueOf(matcher.group(2));
                value = Long.valueOf(matcher.group(3));
            } else {
                throw new RuntimeException("nagy gond van");
            }
            String binary = Long.toBinaryString(index);
            String inBinary = calculWithMask(binary, decimal);
            addToMap(inBinary, value);
        }
    }

    private void addToMap(String inBinary, Long value) {
        int elofordulas = StringUtils.countMatches(inBinary, X);
        List<Integer> arryasOfX = new ArrayList<>();
        int index = inBinary.indexOf(X);
        while (index >= 0) {
            arryasOfX.add(index);
            index = inBinary.indexOf(X, index + 1);
        }
        List<String> egyesek = new ArrayList<>();
        egyesekSzamitas(egyesek, elofordulas);
        egyesekKicserelese(egyesek, inBinary, arryasOfX, value);
        elofordulasList.add(elofordulas);

    }

    private void egyesekKicserelese(List<String> egyesek, String inBinary,List<Integer> arryasOfX, Long value) {
        for(int ii=0; ii<egyesek.size(); ii++){
            String toModify = inBinary;
            for(int jj=0; jj<arryasOfX.size(); jj++){
                int index = arryasOfX.get(jj);
                char newChar = egyesek.get(ii).charAt(jj);
                toModify = toModify.substring(0, index) + newChar + toModify.substring(index + 1);
            }
            Long.parseLong(toModify, 2);
            maps.put(Long.parseLong(toModify, 2), value);
        }
    }

    private void egyesekSzamitas(List<String> array, int elofordulas) {
        Double hatvany = Math.pow(2,elofordulas);
        for(int i=0; i<Math.round(hatvany); i++){
            array.add(kiegeszites(Integer.toBinaryString(i), elofordulas));
        }
    }

    private String kiegeszites(String toBinaryString, int eloforduals) {
        int lengthString = toBinaryString.length();
        if(lengthString<eloforduals){
            for(int index = 0; index<(eloforduals-lengthString); index++){
                toBinaryString ='0' + toBinaryString;
            }
        }
        return toBinaryString;
    }


    private String calculWithMask(String binary, String decimal) {
        String newSzam = "";
        for (int ii = 0; ii < decimal.length(); ii++) {
            char decBetu = decimal.charAt(decimal.length() - 1 - ii);
            char binaryBetu;
            if (ii < binary.length()) {
                binaryBetu = binary.charAt(binary.length() - 1 - ii);
            } else {
                binaryBetu = '0';
            }
            if (decBetu == X) {
                newSzam = X + newSzam;
            } else if(decBetu == '1'){
                newSzam = '1' + newSzam;
            }  else {
                newSzam = binaryBetu + newSzam;
            }
        }
        return newSzam;
    }


}
