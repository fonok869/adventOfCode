package com.fmolnar.code.year2020.day16;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16Challenge02 {

    public static final String COMMA = ",";
    public static final String OR = "or";

    public Day16Challenge02() {
    }

    String patternSeat = "((\\d+)\\-(\\d+))";
    Pattern patternSeatLine = Pattern.compile(patternSeat);

    List<String> lines = new ArrayList<>();
    List<String> allValidRulesString = new ArrayList();
    Set<Integer> valids = new HashSet<>();
    Map<String, Set<Integer>> validRulesByFieldName = new HashMap<>();
    Map<Integer, Set<Integer>> columnContentByIdColumn = new HashMap<>();
    Map<Integer, Set<String>> potentialPlaces = new HashMap<>();
    Map<String, Integer> departures = new HashMap<>();
    String yourString = "";
    int hossz = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day16/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean nearBy = false;
            boolean your = false;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (line.contains(OR)) {
                        int index = line.indexOf(":");
                        String intervalles = line.substring(index);
                        int indexOr = intervalles.indexOf(OR);
                        String firstRule = intervalles.substring(2, indexOr - 1);
                        String secondRule = intervalles.substring(indexOr + 3);
                        Set<Integer> superposition = new HashSet<>();
                        superposition.addAll(transFormIntervalleToValidNumbers(firstRule));
                        superposition.addAll(transFormIntervalleToValidNumbers(secondRule));
                        validRulesByFieldName.put(line.substring(0, index), superposition);
                        allValidRulesString.add(firstRule);
                        allValidRulesString.add(secondRule);
                    }
                    if (nearBy) {
                        if (isValidLine(line)) {
                            lines.add(line);
                        }
                    }
                    if(your){
                        yourString = yourString  + line;
                        your = false;
                    }
                    if (line.contains("nearby")) {
                        transformValidRulesToValidNumbers();
                        nearBy = true;
                    }
                    if(line.contains("your")){
                        your = true;
                    }
                }
            }
        }
        hossz = lines.get(0).split(COMMA).length;
        initColumnContentByIdColumn();
        transformValidLinesToColumnContentByIdColumn();
        calculatePotentialFieldPlaces();
        selectTheCorrectFieldPlaces();
        selectDepartureFields();
        calculateTheProduct();
    }

    private void calculateTheProduct() {
        String[] yourValues = yourString.split(COMMA);
        long szorzat = 1;
        for(Map.Entry<String, Integer> departure: departures.entrySet()){
            szorzat = Long.valueOf(yourValues[departure.getValue()])*szorzat;
        }
        System.out.println("Product: " + szorzat);
    }

    private void selectDepartureFields() {
        for (Map.Entry<Integer, Set<String>> potential : potentialPlaces.entrySet()) {
            String coulmname = potential.getValue().iterator().next();
            if(coulmname.startsWith("departure")){
                departures.put(coulmname, potential.getKey());
            }
        }
    }

    private void selectTheCorrectFieldPlaces() {
        List<Integer> alreadySanitized = new ArrayList<>();
        int counter =0;
        while (counter < hossz) {
            for (Map.Entry<Integer, Set<String>> potential : potentialPlaces.entrySet()) {
                if (!alreadySanitized.contains(potential.getKey()) && potential.getValue().size() == 1) {
                    alreadySanitized.add(potential.getKey());
                    deleteFromAllOtherColumn(potential.getKey(), potential.getValue());
                }
            }
            counter++;
        }

    }

    private void deleteFromAllOtherColumn(Integer place, Set<String> value) {
        String validationRule = value.iterator().next();
        for (Map.Entry<Integer, Set<String>> potential : potentialPlaces.entrySet()) {
            if (potential.getKey() != place) {
                potential.getValue().remove(validationRule);
            }
        }
    }

    private void calculatePotentialFieldPlaces() {
        initPotentialPlaces();
        for (Map.Entry<String, Set<Integer>> validRuleByFieldName : validRulesByFieldName.entrySet()) {
            for (Map.Entry<Integer, Set<Integer>> column : columnContentByIdColumn.entrySet()) {
                if (validRuleByFieldName.getValue().containsAll(column.getValue())) {
                    potentialPlaces.get(column.getKey()).add(validRuleByFieldName.getKey());
                }
            }
        }
    }

    private void initPotentialPlaces() {
        for (int ii = 0; ii < columnContentByIdColumn.size(); ii++) {
            potentialPlaces.put(ii, new HashSet<>());
        }
    }

    private void initColumnContentByIdColumn() {
        for (int i = 0; i < hossz; i++) {
            columnContentByIdColumn.put(i, new HashSet<>());
        }
    }

    private void transformValidLinesToColumnContentByIdColumn() {
        for (String line : lines) {
            String[] valuesByLine = line.split(COMMA);
            for (int i = 0; i < hossz; i++) {
                columnContentByIdColumn.get(i).add(Integer.valueOf(valuesByLine[i]));
            }
        }
    }


    private boolean isValidLine(String line) {
        String[] values = line.split(COMMA);
        for (int ii = 0; ii < values.length; ii++) {
            Integer numbers = Integer.valueOf(values[ii]);
            if (!valids.contains(numbers)) {
                return false;
            }
        }
        return true;
    }

    private void transformValidRulesToValidNumbers() {
        for (int i = 0; i < allValidRulesString.size(); i++) {
            valids.addAll(transFormIntervalleToValidNumbers(allValidRulesString.get(i)));
        }
    }

    private Set<Integer> transFormIntervalleToValidNumbers(String rule) {
        Set<Integer> integers = new HashSet<>();
        Matcher matcher = patternSeatLine.matcher(rule);
        if (matcher.find()) {
            int kisebb = Integer.valueOf(matcher.group(2));
            int nagyobb = Integer.valueOf(matcher.group(3));
            for (int j = kisebb; j <= nagyobb; j++) {
                integers.add(j);
            }
        }
        return integers;
    }
}
