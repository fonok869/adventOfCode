package com.fmolnar.code.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day03Challenge01 {

    public Day03Challenge01() {
    }

    public void calculateDay301() throws IOException {
        getList();
    }

    public List<String> getList() throws IOException {
        List<String> passwordsValid = new ArrayList<>();
        int horizontalIndex = 0;
        int tree = 0;
        InputStream reader = getClass().getResourceAsStream ("/day03/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String lines;
            while ((lines = file.readLine()) != null) {
                if(lines.charAt(horizontalIndex) == '#'){
                    tree++;
                }
                horizontalIndex = (horizontalIndex + 3) % 31;
            }
        }
        System.out.println("Tree: " + tree);
        return passwordsValid;
    }

}
