package com.fmolnar.code.year2021.day21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day21Challenge01 {

    private List<String> lines = new ArrayList<>();

//    int player1Position = 4;
//    int player2Position = 8;

    int player1Position = 9;
    int player2Position = 10;

    public void calculate() throws IOException {

        int beginnning = 1;
        int diceMax = 100;
        int board = 10;
        int maxWin = 1000;
        boolean firstPlayer = true;
        int player1TotalScore = 0;
        int player2TotalScore = 0;

        int i = 0;
        for (; ; i++) {
            int first = (beginnning % diceMax) == 0 ? diceMax : (beginnning % diceMax);
            int second = ((beginnning + 1) % diceMax) == 0 ? diceMax : ((beginnning + 1) % diceMax);
            int third = ((beginnning + 2) % diceMax) == 0 ? diceMax : ((beginnning + 2) % diceMax);
            int total = (first + second + third) % board == 0 ? board : (first + second + third) % board;
            if (firstPlayer) {
                player1Position = (player1Position + total) % board == 0 ? board : (player1Position + total);
                player1TotalScore += (player1Position % board) == 0 ? board : (player1Position % board);
                if (maxWin <= player1TotalScore) {
                    beginnning += 2;
                    break;
                }
                firstPlayer = false;
            } else {
                player2Position = (player2Position + total) % board == 0 ? board : (player2Position + total);
                player2TotalScore += (player2Position % board) == 0 ? board : (player2Position % board);
                if (maxWin <= player2TotalScore) {
                    beginnning += 2;
                    break;
                }
                firstPlayer = true;
            }
            beginnning += 3;
        }
        int sumLooser = (firstPlayer ? player2TotalScore : player1TotalScore);
        System.out.println("Day21Challenge01: " + sumLooser * (beginnning));
    }
}
