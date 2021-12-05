package com.fmolnar.code.year2020.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22Challenge02 {

    public Day22Challenge02() {
    }

    List<Integer> player1List = new ArrayList<>();
    List<Integer> player2List = new ArrayList<>();
    Set<List<Integer>> player1ListPassed = new HashSet<>();
    Set<List<Integer>> player2ListPassed = new HashSet<>();
    int counter = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day22/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean player1 = false;
            boolean player2 = false;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (line.startsWith("Player 1")) {
                        player1 = true;
                        continue;
                    }
                    if (line.startsWith("Player 2")) {
                        player2 = true;
                        player1 = false;
                        continue;
                    }
                    if (player1) {
                        player1List.add(Integer.valueOf(line));
                    }
                    if (player2) {
                        player2List.add(Integer.valueOf(line));
                    }
                }
            }
        }

        while (true) {
            if (player2List.isEmpty() || player1List.isEmpty()) {
                break;
            }
            playTheGame(player1List, player2List, player1ListPassed, player2ListPassed);
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
        for (int i = lineP2 - 1; -1 < i; i--) {
            szorzat = szorzat + counter * Long.valueOf(player2List.get(i));
            counter++;
        }
        System.out.println(szorzat);
    }

    private void osszeadasP1() {
        int lineP1 = player1List.size();
        long szorzat = 0L;
        long counter = 1L;
        for (int i = lineP1 - 1; -1 < i; i--) {
            szorzat = szorzat + counter * Long.valueOf(player1List.get(i));
            counter++;
        }
        System.out.println(szorzat);
    }

    private void playTheGame(List<Integer> player1List, List<Integer> player2List, Set<List<Integer>> player1ListPassed, Set<List<Integer>> player2ListPassed) {
        Integer p1 = player1List.get(0);
        Integer p2 = player2List.get(0);
        if (this.player1ListPassed.contains(player1List) || this.player2ListPassed.contains(player2List)) {
            addPlayerList1AndRemovePlayerList2(player1List, player2List, p1, p2);
            return;
        }
        List<Integer> ancientPlayerList1 = new ArrayList<>(player1List);
        List<Integer> ancientPlayerList2 = new ArrayList<>(player2List);
        counter++;
        System.out.println("------------------------------------------------------------");
        System.out.println("Counter: " + counter);
        System.out.println("PlayerList1 : " + player1List);
        System.out.println("PlayerList2 : " + player2List);
        System.out.println("SubGame: " + subGame(player1List, player2List, player1ListPassed, player2ListPassed));
        player1ListPassed.add(ancientPlayerList1);
        player2ListPassed.add(ancientPlayerList2);

    }

    private Lista subGame(List<Integer> player1List, List<Integer> player2List, Set<List<Integer>> player1ListPassed, Set<List<Integer>> player2ListPassed) {
        if (player1List.isEmpty()) {
            System.out.println("Player1 Lista ures --> WIN P2");
            return Lista.P2;
        } else if (player2List.isEmpty()) {
            System.out.println("Player2 Lista ures --> WIN P1");
            return Lista.P1;
        }

        if (player1ListPassed.contains(player1List) || player2ListPassed.contains(player2List)) {
            System.out.println("Vegtelen LOOP elkerules");
            System.out.println("player1ListPassed" + player1ListPassed);
            System.out.println("player1List" + player1List);
            System.out.println("player2ListPassed" + player2ListPassed);
            System.out.println("player2List" + player2List);
            return Lista.P1;
        }
        player1ListPassed.add(new ArrayList<>(player1List));
        player2ListPassed.add(new ArrayList<>(player2List));
        Integer p1 = player1List.get(0);
        Integer p2 = player2List.get(0);
        if ((p1 < player1List.size()) && (p2 < player2List.size())) {
            List<Integer> subPlayer1 = new ArrayList<>();
            List<Integer> subPlayer2 = new ArrayList<>();
            System.out.println("New Subgame ------------------------");
            for (int i = 1; i <= p1; i++) {
                subPlayer1.add(player1List.get(i));
            }
            for (int j = 1; j <= p2; j++) {
                subPlayer2.add(player2List.get(j));
            }
            System.out.println("SubList1 : " + subPlayer1);
            System.out.println("SubList2 : " + subPlayer2);
            Lista result = null;
            Set<List<Integer>> subPlayer1ListPassed = new HashSet<>();
            Set<List<Integer>> subPlayer2ListPassed = new HashSet<>();

            while (true) {
                result = subGame(subPlayer1, subPlayer2, subPlayer1ListPassed, subPlayer2ListPassed);
                if (result != null) {
                    break;
                }
            }
            if (result == Lista.P1) {
                addPlayerList1AndRemovePlayerList2(player1List, player2List, p1, p2);
                System.out.println("P1 WIN ");
                return Lista.P1;
            } else if (result == Lista.P2) {
                addPlayerList1AndRemovePlayerList2(player2List, player1List, p2, p1);
                System.out.println("P2 WIN ");
                return Lista.P2;
            }
            return null;

        }
        if (p1 > p2) {
            addPlayerList1AndRemovePlayerList2(player1List, player2List, p1, p2);
            System.out.println("P1 nagyobb-------------------");
            System.out.println("Player1 : " + player1List);
            System.out.println("Player2 : " + player2List);
        } else if (p2 > p1) {
            addPlayerList1AndRemovePlayerList2(player2List, player1List, p2, p1);
            System.out.println("P2 nagyobb-------------------");
            System.out.println("Player1 : " + player1List);
            System.out.println("Player2 : " + player2List);
        } else {
            throw new RuntimeException("Egyenoseg van");
        }
        return null;
    }

    private void addPlayerList1AndRemovePlayerList2(List<Integer> player1List, List<Integer> player2List, Integer p1, Integer p2) {
        player2List.remove(0);
        player1List.remove(0);
        player1List.add(p1);
        player1List.add(p2);
    }

    public enum Lista {
        P1,
        P2
    }
}
