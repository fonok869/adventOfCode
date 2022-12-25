package com.fmolnar.code.year2022.day25;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.List;

public class Day25 {

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day25/input.txt");

        for(String line : lines){
            System.out.println("Lines: " + line);
        }

        for(int i=0; i<lines.size(); i++){
            String line = lines.get(i);

        }

        System.out.println("Totot");
    }
}
