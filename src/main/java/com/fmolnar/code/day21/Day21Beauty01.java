package com.fmolnar.code.day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day21Beauty01 {

    public Day21Beauty01() {
    }

    private List<String> lines = new ArrayList<>();
    private List<List<String>> foods = new ArrayList<>();
    private List<List<String>> allergens = new ArrayList<>();
    private Set<String> foodsSet = new HashSet<>();
    private Set<String> allergensSet = new HashSet<>();
    private Map<String, Set<String>> allergenFood = new HashMap<>();
    private Map<String, String> finalSolution = new HashMap<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day21/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    List<String> food = new ArrayList<>();
                    List<String> allergen = new ArrayList<>();
                    int firstIndex = line.indexOf("(");
                    String foodsString = line.substring(0, firstIndex - 1);
                    String allergenString = line.substring(firstIndex + "contains".length() + 2, line.length() - 1);
                    addAllItems(foodsString, food);
                    addAllItems(allergenString.replace(",", ""), allergen);
                    foods.add(food);
                    foodsSet.addAll(food);
                    allergens.add(allergen);
                    allergensSet.addAll(allergen);
                }
            }
        }

        while(true){
            iterateByAllergen();
            modifyAllergenFood();
            if(allergens.stream().filter(s->s.isEmpty()).count() == foods.size()){
                break;
            }
        }
        createList();
        selection();

        System.out.println("Sum: " + foods.stream().mapToInt(s-> s.size()).sum());
    }

    private void createList() {

        List<String> allergensToOrder = new ArrayList<>(allergensSet);
        Collections.sort(allergensToOrder);
        String solution = "";
        for(String allergen : allergensToOrder){
            solution = solution  +finalSolution.get(allergen) + ",";
        }
        System.out.println(solution);
    }

    private void modifyAllergenFood() {
        for(Map.Entry<String, Set<String>> allergenPair:  allergenFood.entrySet()){
            if(allergenPair.getValue().size() == 1){
                String allergenToRemove = allergenPair.getKey();
                String foodToRemove = allergenPair.getValue().iterator().next();
                finalSolution.put(allergenToRemove,foodToRemove);
                for(int i=0; i<foods.size(); i++){
                    System.out.println("Allergens elotte: " + allergens.get(i));
                    foods.get(i).remove(foodToRemove);
                    allergens.get(i).remove(allergenToRemove);
                    System.out.println("Allergens utana: " + allergens.get(i));
                }
            }
        }
    }

    private void iterateByAllergen() {
        for(String allegen : allergensSet){
            Set<String> foodsOnly = new HashSet<>();
            for(int i=0; i<allergens.size();i++){
                List<String> allergensActual = allergens.get(i);
                if(allergensActual.contains(allegen)){
                    List<String> foodsActual = foods.get(i);
                    foodsOnly = selectFoodsSame(foodsActual, i, foodsOnly);
                }
                if(foodsOnly.size() ==1){
                    break;
                }
            }
            allergenFood.put(allegen, foodsOnly);
        }
    }

    private void selectionTwo() {
        for(int i=0; i<allergens.size();i++){
            List<String> allergenActual = allergens.get(i);
            if(allergenActual.size() == 2) {
                String allergen1 = allergenActual.get(0);
                String allergen2 = allergenActual.get(1);
                Set<String> communFoods = new HashSet<>();
                if(allergenFood.get(allergen1)!=null){
                    communFoods.addAll(allergenFood.get(allergen1));
                }
                if(allergenFood.get(allergen2)!= null){
                    communFoods.addAll(allergenFood.get(allergen2));
                }
                communFoods = selectFoodsSame(null,i, communFoods);
                System.out.println("CommonFood" + communFoods);
            }
        }
    }

    private void removeFromAll() {

    }

    private void selection() {
        for (int i = 0; i < allergens.size(); i++) {
            List<String> actualAllergens = allergens.get(i);
            if (actualAllergens.size() == 1) {
                searchForIt(i);
            }
        }
    }

    private void searchForIt(int i) {
        List<String> actualAllergens = allergens.get(i);
        String allergenOnly = actualAllergens.get(0);
        List<String> actualFood = foods.get(i);
        Set<String> foodsSame = new HashSet<>();
        outerloop:
        for (int w = 0; w < allergens.size(); w++) {
            if (w != i) {
                List<String> allergensToCheck = allergens.get(w);
                if (allergensToCheck.size() == 2 && allergensToCheck.contains(allergenOnly)) {
                    foodsSame = (selectFoodsSame(actualFood, w, foodsSame));
                    for (int y = 0; y < allergens.size(); y++) {
                        List<String> allergensToCheckLevel2 = allergens.get(y);
                        if (allergensToCheckLevel2.size() == 3 && allergensToCheckLevel2.containsAll(allergensToCheck)) {
                            foodsSame = selectFoodsSame(actualFood, y, foodsSame);
                            if (foodsSame.size() == 1) {
                                //allergenFood.put(allergenOnly, foodsSame.iterator().next());
                                break outerloop;
                            }
                            if(foodsSame.size() == 0){
                                throw new RuntimeException("Error");
                            }
                        }

                    }
                    if (foodsSame.size() == 1) {
                        //allergenFood.put(allergenOnly, foodsSame.iterator().next());
                        break outerloop;
                    }
                    if(foodsSame.size() == 0){
                        throw new RuntimeException("Error");
                    }
                }
            }
        }
        System.out.println(allergenOnly + foodsSame);
        allergenFood.put(allergenOnly, foodsSame);
    }

    private Set<String> selectFoodsSame(List<String> actualFood, int w, Set<String> foodsSet) {
        if (foodsSet.isEmpty()) {
            return actualFood.stream().filter(s -> foods.get(w).contains(s)).collect(Collectors.toSet());
        } else {
            return foodsSet.stream().filter(s -> foods.get(w).contains(s)).collect(Collectors.toSet());
        }
    }

    private void addAllItems(String foodsString, List<String> food) {
        while (true) {
            int firstIndex = foodsString.indexOf(' ');
            if (firstIndex == -1) {
                food.add(foodsString);
                break;
            }
            food.add(foodsString.substring(0, firstIndex));
            foodsString = foodsString.substring(firstIndex + 1);
        }

    }
}
