package com.fmolnar.code.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day03Challenge02 {

    public Day03Challenge02() {
    }

    public List<String> calculate() throws IOException {
        List<String> passwordsValid = new ArrayList<>();
        int horizontalIndex1To1 = 0;
        int horizontalIndex1To3 = 0;
        int horizontalIndex1To5 = 0;
        int horizontalIndex1To7 = 0;
        int horizontalIndex2To1 = 0;
        int tree1T1 = 0;
        int tree1T3 = 0;
        int tree1T5 = 0;
        int tree1T7 = 0;
        int tree2T1 = 0;
        int row = 0;
        InputStream reader = getClass().getResourceAsStream ("/day03/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String lines;
            while ((lines = file.readLine()) != null) {

                if(lines.charAt(horizontalIndex1To1) == '#'){
                    tree1T1++;
                }
                if(lines.charAt(horizontalIndex1To3) == '#'){
                    tree1T3++;
                }
                if(lines.charAt(horizontalIndex1To5) == '#'){
                    tree1T5++;
                }
                if(lines.charAt(horizontalIndex1To7) == '#'){
                    tree1T7++;
                }
                if((row%2 == 0) && (lines.charAt(horizontalIndex2To1) == '#')){
                    tree2T1++;
                }

                if((row%2 == 0)){
                    horizontalIndex2To1 = (horizontalIndex2To1 + 1) % 31;
                }

                horizontalIndex1To1 = (horizontalIndex1To1 + 1) % 31;
                horizontalIndex1To3 = (horizontalIndex1To3 + 3) % 31;
                horizontalIndex1To5 = (horizontalIndex1To5 + 5) % 31;
                horizontalIndex1To7 = (horizontalIndex1To7 + 7) % 31;
                row++;
            }
        }
        System.out.println("Tree11: " + tree1T1);
        System.out.println("Tree13: " + tree1T3);
        System.out.println("Tree15: " + tree1T5);
        System.out.println("Tree17: " + tree1T7);
        System.out.println("Tree21: " + tree2T1);
        System.out.println("Result : " + Long.valueOf(tree1T1)*Long.valueOf(tree1T3)*Long.valueOf(tree1T5)*Long.valueOf(tree1T7)*Long.valueOf(tree2T1));
        return passwordsValid;
    }
}
