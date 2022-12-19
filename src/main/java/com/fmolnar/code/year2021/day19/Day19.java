package com.fmolnar.code.year2021.day19;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day19 {

    List<List<Point>> points = new ArrayList<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2021/day19/input.txt");

        List<Point> oneEnsemble = new ArrayList<>();
        for(String line : lines){
            if("--- scanner 0 ---".equals(line)){
                continue;
            }
            String[] values = line.split(",");
            oneEnsemble.add(new Point(Integer.valueOf(values[0]), Integer.valueOf(values[1]), Integer.valueOf(values[2])));

            if(StringUtils.isEmpty(line)){
                points.add(oneEnsemble);
                oneEnsemble = new ArrayList<>();
            }
        }

        System.out.println("Totot");
    }

    record Point(int x, int y, int z){

    }
}
