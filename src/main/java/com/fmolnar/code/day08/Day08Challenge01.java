package com.fmolnar.code.day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08Challenge01 {

    public Day08Challenge01() {
    }

    List<Integer> alreadyVisited = new ArrayList<>();
    List<Long> accumulator = new ArrayList<>();
    List<String> instructions = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day08/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    instructions.add(line);
                }
            }
        }
        stepping();
        System.out.println("Accumulator level: " + accumulator.stream().mapToLong(s->s).sum());
    }

    String pattern = "(^[a-z]{3})\\s(\\+|\\-)([0-9]*)$";
    Pattern patternToLine = Pattern.compile(pattern);
    public void stepping(){
        Integer id = 0;
        while(!alreadyVisited.contains(id)){
            alreadyVisited.add(id);
            int newValue = calculateNextStep(id);
            id = newValue;
        }
    }

    public int calculateNextStep(int actual){
        String line = instructions.get(actual);
        Matcher matcher = patternToLine.matcher(line);
        String sign = null;
        String command = null;
        String lepesek = null ;

        if(matcher.find()){
            sign =  matcher.group(2);
            command = matcher.group(1);
            lepesek = matcher.group(3);
        }

        if("nop".equals(command)){
            return actual+1;
        }

        if("jmp".equals(command)){
            if("+".equals(sign)){
                actual = actual +  Integer.valueOf(lepesek);
            }
            else {
                actual = actual - Integer.valueOf(lepesek);
            }
            return actual;
        }

        if("acc".equals(command)){
            if("+".equals(sign)){
                accumulator.add(Long.valueOf(lepesek));
            }
            else {
                accumulator.add(Long.valueOf("-"+lepesek));
            }
            return actual+1;
        }

        return 0;
    }
}
