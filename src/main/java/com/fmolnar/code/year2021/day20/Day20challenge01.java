package com.fmolnar.code.year2021.day20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Day20challenge01 {

    private List<String> lines = new ArrayList<>();
    private Map<Integer, Integer> numbers = new HashMap<>();
    private Set<Point> inputs1 = new HashSet<>();
    private Set<Point> inputs0 = new HashSet<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day20/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean input = false;
            int counter = 0;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (!input) {
                        for (int i = 0; i < line.length(); i++) {
                            char charToCheck = line.charAt(i);
                            if (charToCheck == '.') {
                                numbers.put(i, 0);
                            } else {
                                numbers.put(i, 1);
                            }
                        }
                    } else {
                        for (int i = 0; i < line.length(); i++) {
                            char charToCheck = line.charAt(i);
                            if (charToCheck == '#') {
                                inputs1.add(new Point(i, counter));
                            } else{
                                inputs0.add(new Point(i, counter));
                            }
                        }
                        counter++;
                    }
                    lines.add(line);
                } else {
                    input = true;
                }
            }
        }

        int numberStep = 2;
        for(int i=0; i<numberStep; i++){
            step(i);
        }

        System.out.println("Day20Challenge01: " + inputs1.size());
    }

    private void step(int step) {
        int xMin = Stream.of(inputs0, inputs1).flatMap(Collection::stream).mapToInt(p->p.x()).min().getAsInt();
        int xMax = Stream.of(inputs0, inputs1).flatMap(Collection::stream).mapToInt(p->p.x()).max().getAsInt();
        int yMin = Stream.of(inputs0, inputs1).flatMap(Collection::stream).mapToInt(p->p.y()).min().getAsInt();
        int yMax = Stream.of(inputs0, inputs1).flatMap(Collection::stream).mapToInt(p->p.y()).max().getAsInt();

        // We check one ligne and row further
        xMax = xMax + 1;
        yMax = yMax + 1;
        xMin = xMin - 1;
        yMin = yMin - 1;

        // Because of the input Data
        String actual = "0";
        if(step%2 == 1){
            actual =  "1";
        }

        Set<Point> newInput1 = new HashSet<>();
        Set<Point> newInput0 = new HashSet<>();

        for(int x=xMin; x<xMax+1; x++){
            for(int y=yMin; y<yMax+1; y++){
                String allNumber = "";
                for(int j=y-1; j<y+2; j++){
                    String line = "";
                    for(int i=x-1; i<x+2; i++){
                        if(inputs1.contains(new Point(i,j))){
                            line=line + "1";
                        } else if(inputs0.contains(new Point(i,j))) {
                            line=line + "0";
                        } else{
                            line = line + actual;
                        }
                    }
                    allNumber = allNumber + line;
                }
                Integer actualNumber = Integer.parseInt(allNumber,2);
                Integer intSzam = numbers.get(actualNumber);
                if(intSzam == 1){
                    newInput1.add(new Point(x,y));
                } else {
                    newInput0.add(new Point(x,y));
                }
            }
        }
        inputs0 = new HashSet<>(newInput0);
        inputs1 = new HashSet<Point>(newInput1);
    }

    public static record Point(int x, int y){

    }
}
