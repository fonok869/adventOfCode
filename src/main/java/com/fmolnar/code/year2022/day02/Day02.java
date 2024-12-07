package com.fmolnar.code.year2022.day02;

import com.fmolnar.code.AdventOfCodeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class Day02 {


    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day02/input.txt");

        int scoreA = 0;
        int scoreB = 0;
        for (String line : lines) {
            scoreA = calculateA(scoreA, line);
            scoreB = calculateB(scoreB, line);
        }

        System.out.println("First: " + scoreA);
        System.out.println("Second: " + scoreB);
    }

    private int calculateA(int score, String line) {
        if (StringUtils.isNotEmpty(line)) {
            if("A Y".equals(line)){
                score +=8;
            } else if(("B X").equals(line)){
                score +=1;
            }else if(("C Z").equals(line)){
                score +=6;
            }else if(("A X").equals(line)){
                score +=4;
            } else if(("A Z").equals(line)){
                score +=3;
            } else if(("B Y").equals(line)){
                score +=5;
            } else if(("B Z").equals(line)){
                score +=9;
            } else if(("C X").equals(line)){
                score +=7;
            } else if(("C Y").equals(line)){
                score +=2;
            }
        }
        return score;
    }

    private int calculateB(int score, String line) {
        if (StringUtils.isNotEmpty(line)) {
            if("A Y".equals(line)){
                score +=4;
            } else if(("B X").equals(line)){
                score +=1;
            }else if(("C Z").equals(line)){
                score +=7;
            }else if(("A X").equals(line)){
                score +=3;
            } else if(("A Z").equals(line)){
                score +=8;
            } else if(("B Y").equals(line)){
                score +=5;
            } else if(("B Z").equals(line)){
                score +=9;
            } else if(("C X").equals(line)){
                score +=2;
            } else if(("C Y").equals(line)){
                score +=6;
            }
        }
        return score;
    }
}
