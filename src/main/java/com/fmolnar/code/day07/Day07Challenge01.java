package com.fmolnar.code.day07;

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

public class Day07Challenge01 {

    private static final String BAGS_CONTAIN = "bags contain";
    public static final String BAG = "bag";

    private Map<String, List<String>> codeRules = new HashMap<>();

    private Set<String> typeToSearch;

    private Set<String> typeToRemove = null;

    private Set<String> allColoredBagsAlreadySearched = new HashSet<>();

    public Day07Challenge01() {
        typeToSearch =  new HashSet<>();
        typeToSearch.add("shiny gold");
    }

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day07/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                makeBagsComposition(line);
            }
        }

        int oldSearchedColorsNumber = 0;
        int newLySearchedColorsNumber = 1;
        // if we do not add more colors --> we gathered all of them
        while (oldSearchedColorsNumber!= newLySearchedColorsNumber){
            oldSearchedColorsNumber = allColoredBagsAlreadySearched.size();
            calculateCucc();
            newLySearchedColorsNumber = allColoredBagsAlreadySearched.size();
        }

        System.out.println("Bag Colors: " + (allColoredBagsAlreadySearched.size()-1)); //-1 for shiny gold bag

    }


    public void makeBagsComposition(String line){
        int index = line.indexOf(BAGS_CONTAIN);
        String bagType = line.substring(0, index-1);
        String[] toto = line.substring(index + BAGS_CONTAIN.length()).split(",");
        List<String> bagsType = new ArrayList<>();

        for(int i=0; i<toto.length; i++){
            String element = toto[i];
            for(int j=0; j<element.length(); j++){
                if(Character.isDigit(element.charAt(j))){
                    // if a bag can contain more than 9 bags from the same color
                    int indexBag = element.indexOf(BAG);
                    if(Character.isDigit(element.charAt(j+1))){
                        bagsType.add(element.substring(4, indexBag-1));
                    }else{
                        bagsType.add(element.substring(3, indexBag-1));
                    }
                }
            }
        }
        codeRules.put(bagType, bagsType);
    }

    public int calculateCucc(){
        allColoredBagsAlreadySearched.addAll(typeToSearch);
        typeToRemove = new HashSet<>(typeToSearch);
        Set<String> newTypeToSearch = new HashSet<>();
        int counter = 0;
        for(Map.Entry<String, List<String>>  mapper : codeRules.entrySet()){
            for (String bagType : mapper.getValue()){
                if(typeToSearch.contains(bagType)){
                    newTypeToSearch.add(mapper.getKey());
                    counter++;
                    break;
                }
            }
        }
        // All searched colored bags have to be searched only once
        typeToSearch.removeAll(typeToRemove);
        // Add newly found colored bags to search
        typeToSearch.addAll(newTypeToSearch);
        return counter;
    }
}
