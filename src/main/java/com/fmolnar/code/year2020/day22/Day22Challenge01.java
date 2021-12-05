package com.fmolnar.code.year2020.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day22Challenge01 {

    public Day22Challenge01() {
    }

    List<Integer> player1List = new ArrayList<>();
    List<Integer> player2List =new ArrayList<>();
    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day22/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean player1 = false;
            boolean player2 = false;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if(line.startsWith("Player 1")){
                        player1 = true;
                        continue;
                    }
                    if(line.startsWith("Player 2")){
                        player2 = true;
                        player1 = false;
                        continue;
                    }
                    if(player1){
                        player1List.add(Integer.valueOf(line));
                    }
                    if(player2){
                        player2List.add(Integer.valueOf(line));
                    }
                }
            }
        }

        while(true){
            if(player2List.isEmpty() || player1List.isEmpty()){
                break;
            }
            playTheGame();
        }

        osszeadasP1();
        osszeadasP2();
        System.out.println("P1" + player1List);
        System.out.println("P2" + player2List);
    }

    private void osszeadasP2() {
        int lineP2 = player2List.size();
        long szorzat = 0L;
        long counter = 1L;
        for(int i = lineP2-1; -1 <i; i--){
            szorzat =  szorzat + counter * Long.valueOf(player2List.get(i));
            counter++;
        }
        System.out.println(szorzat);
    }

    private void osszeadasP1() {
        int lineP1 = player1List.size();
        long szorzat = 0L;
        long counter = 1L;
        for(int i = lineP1-1; -1 <i; i--){
            szorzat =  szorzat + counter * Long.valueOf(player1List.get(i));
            counter++;
        }
        System.out.println(szorzat);
    }

    private void playTheGame() {
        Integer p1 = player1List.get(0);
        Integer p2 = player2List.get(0);
        if(p1>p2){
            player2List.remove(0);
            player1List.remove(0);
            player1List.add(p1);
            player1List.add(p2);
        } else if(p2>p1){
            player1List.remove(0);
            player2List.remove(0);
            player2List.add(p2);
            player2List.add(p1);
        } else {
            throw new RuntimeException("Egyenoseg van");
        }
    }
}
