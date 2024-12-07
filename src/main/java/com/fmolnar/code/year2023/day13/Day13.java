package com.fmolnar.code.year2023.day13;

import com.fmolnar.code.AdventOfCodeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Day13 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day13/input.txt");

        List<List<Character>> maps = new ArrayList<>();
        List<Character> previous = new ArrayList<>();
        List<Integer> potentielViyszintes = new ArrayList<>();
        int sum = 0;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                sum += checkAllLines(maps);
                maps = new ArrayList<>();
                continue;
            }
            List<Character> chars = line.chars().mapToObj(s -> (char) s).collect(Collectors.toList());
            maps.add(chars);
        }
        sum += checkAllLines(maps);
        System.out.println(sum);
    }

    private static int checkAllLines(List<List<Character>> maps) {
        // Vizszintes
        int sum = 0;
        boolean vizszintes = false;
        for (int i = 0; i < maps.size() - 1; i++) {
            List<Character> elso = maps.get(i);
            List<Character> masodik = maps.get(i + 1);
            if (masodik.equals(elso)) {
                for(int j=0; j<=Math.min(i, maps.size()-1-(i+1)); j++){
                    List<Character> elsoV = maps.get(i-j);
                    List<Character> masodikV = maps.get(i + 1+j);
                    if(elsoV.equals(masodikV)){
                        vizszintes = true;
                    }
                    else {
                        vizszintes = false;
                        break;
                    }
                }
                if(vizszintes){
                    sum+= (i+1)*100;
                }
            }
        }

        // Fuggoleges
        List<List<Character>> charFuggoleges = transoformMap(maps);

        boolean fuggoleges = false;
        for (int i = 0; i < charFuggoleges.size() - 1; i++) {
            List<Character> elso = charFuggoleges.get(i);
            List<Character> masodik = charFuggoleges.get(i + 1);
            if (masodik.equals(elso)) {
                for(int j=0; j<=Math.min(i, charFuggoleges.size()-1-(i+1)); j++){
                    List<Character> elsoV = charFuggoleges.get(i-j);
                    List<Character> masodikV = charFuggoleges.get(i + 1+j);
                    if(elsoV.equals(masodikV)){
                        fuggoleges = true;
                    }
                    else {
                        fuggoleges = false;
                        break;
                    }
                }
                if(fuggoleges){
                    sum+= (i+1);
                }
            }
        }

        return sum;
    }

    private static List<List<Character>> transoformMap(List<List<Character>> maps) {
        List<List<Character>> newMap = new ArrayList<>();
        for(int i=0; i<maps.get(0).size(); i++){
            List<Character> newChars = new ArrayList<>();
            for(int j=0; j<maps.size(); j++){
                newChars.add(maps.get(j).get(i));
            }
            newMap.add(newChars);
        }
        return newMap;
    }
}
