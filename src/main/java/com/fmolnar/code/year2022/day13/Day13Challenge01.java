package com.fmolnar.code.year2022.day13;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13Challenge01 {

    public void calculate() throws IOException{
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day13/input.txt");
        List<Pairs> parises = new ArrayList<>();
        String s1 = "";
        String s2 = "";
        for (int i = 0; i < lines.size(); i++) {
            if (i % 3 == 0) {
                s1 = lines.get(i);
            } else if (i % 3 == 1) {
                s2 = lines.get(i);
            } else {
                parises.add(new Pairs(s1, s2));
            }
        }
        parises.add(new Pairs(s1, s2));

        int sum = 0;
        for (int j = 0; j < parises.size(); j++) {
            JsonNode n1 = new ObjectMapper().readTree(parises.get(j).s1());
            JsonNode n2 = new ObjectMapper().readTree(parises.get(j).s2());
            if (Day13Utils.compareNodes(n1, n2)) {
                sum += j + 1;
            }
        }

        System.out.println("First: " + sum);
    }
    record Pairs(String s1, String s2) { };
}
