package com.fmolnar.code.year2022.day13;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Challenge02 {

    public static final String ADD_1 = "[[2]]";
    public static final String ADD_2 = "[[6]]";

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day13/input.txt");
        List<String> signals = lines.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        signals.add(ADD_1);
        signals.add(ADD_2);

        Collections.sort(signals, (o1, o2) -> comparatorSignal(o1, o2));

        int two = 0;
        int six = 0;

        for(int i=0; i<signals.size(); i++){
            if(ADD_1.equals(signals.get(i))) two = i+1;
            if(ADD_2.equals(signals.get(i))) six = i+1;
        }

        System.out.println("Second: " + two * six);
    }

    private int comparatorSignal(String s1, String s2){
        try {
            return Day13Utils.compareNodes(new ObjectMapper().readTree(s1), new ObjectMapper().readTree(s2)) ? -1 : 1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
