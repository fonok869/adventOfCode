package com.fmolnar.code.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day22Beauty02 {

    public Day22Beauty02() {
    }


    List<List<Integer>> playersTogether = new ArrayList<>();
    public void calculate() throws IOException {
        List<Integer> player1List = new ArrayList<>();
        List<Integer> player2List =new ArrayList<>();
        List<List<Integer>> player1ListAlreadyPassed = new ArrayList<>();
        List<List<Integer>> player2ListAlreadyPassed = new ArrayList<>();
        InputStream reader = getClass().getResourceAsStream("/day22/input.txt");
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
           playTheGame(player1List, player2List, player1ListAlreadyPassed, player2ListAlreadyPassed);
            if(player2List.isEmpty() || player1List.isEmpty()){
                break;
            }
        }

        osszeadasP1(player1List);
        osszeadasP2(player2List);
        System.out.println("P1" + player1List);
        System.out.println("P2" + player2List);
    }

    private void osszeadasP2(List<Integer> player2List) {
        int lineP2 = player2List.size();
        long szorzat = 0L;
        long counter = 1L;
        for(int i = lineP2-1; -1 <i; i--){
            szorzat =  szorzat + counter * Long.valueOf(player2List.get(i));
            counter++;
        }
        System.out.println(szorzat);
    }

    private void osszeadasP1(List<Integer> player1List) {
        int lineP1 = player1List.size();
        long szorzat = 0L;
        long counter = 1L;
        for(int i = lineP1-1; -1 <i; i--){
            szorzat =  szorzat + counter * Long.valueOf(player1List.get(i));
            counter++;
        }
        System.out.println(szorzat);
    }

    private Player playTheGame(List<Integer> player1List, List<Integer> player2List, List<List<Integer>> player1ListAlreadyPassed, List<List<Integer>> player2ListAlreadyPassed) {
        if(player1ListAlreadyPassed.contains(player1List) && player2ListAlreadyPassed.contains(player2List)){
            return Player.P1;
        }
        if(player1List.isEmpty()){
            return Player.P2;
        }
        if(player2List.isEmpty()){
            return Player.P1;
        }
        player1ListAlreadyPassed.add(new ArrayList<>(player1List));
        player2ListAlreadyPassed.add(new ArrayList<>(player2List));

        Integer p1 = player1List.get(0);
        Integer p2 = player2List.get(0);
        if((p1<player1List.size()) && (p2<player2List.size())){
            List<Integer> subPlayer1 = initSubPlayer(player1List, p1);
            List<Integer> subPlayer2 = initSubPlayer(player2List, p2);
            List<List<Integer>> subPlayer1Passed = new ArrayList<>();
            List<List<Integer>> subPlayer2Passed = new ArrayList<>();

            Player result = null;
            while(true){
                result = playTheGame(subPlayer1, subPlayer2, subPlayer1Passed, subPlayer2Passed);
                if(result!=null){
                    break;
                }
            }
            if(result == Player.P1){
                addList1AndRemoveFromList2(player1List, player2List);
            }
            if(result == Player.P2){
                addList1AndRemoveFromList2(player2List, player1List);
            }
        } else if (p1>p2){
            addList1AndRemoveFromList2(player1List,player2List);
        } else if(p2>p1){
            addList1AndRemoveFromList2(player2List,player1List);
        } else {
            throw new RuntimeException("Egyenoseg van");
        }
        return null;
    }

    private void addList1AndRemoveFromList2(List<Integer> player1List, List<Integer> player2List){
        Integer p1 = player1List.get(0);
        Integer p2 = player2List.get(0);
        player1List.add(p1);
        player1List.add(p2);
        player1List.remove(0);
        player2List.remove(0);
    }

    private List<Integer> initSubPlayer(List<Integer> playerList, int p1){
        List<Integer> subPlayer = new ArrayList<>();
        for(int i=1; i<=p1; i++){
            subPlayer.add(playerList.get(i));
        }
        return subPlayer;
    }

    public enum Player{
        P1,
        P2
    }
}
