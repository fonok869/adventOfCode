package com.fmolnar.code.year2023.day02;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day02 {

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day02/input.txt");
        List<Game> games = new ArrayList<>();
        for (String line : lines) {
            int semicolon = line.indexOf(':');
            String game = line.substring(0, semicolon);
            int gameId = Integer.valueOf(game.substring(game.indexOf(' ') + 1));

            String rest = line.substring(semicolon + 1);
            String[] gamers = rest.split(";");

            List<GameSet> sets = getAllGameSet(gamers);
            games.add(new Game(gameId, sets));
        }

        int sumFirst = 0;
        for(Game game : games){
            if(game.canPossible()){
                sumFirst+= game.id;
            }
        }

        System.out.println("First: " + sumFirst);

        long sumSecond = 0;
        for(Game game : games){
            sumSecond += game.fewers();
        }

        System.out.println("Second: " + sumSecond);

    }

    private static List<GameSet> getAllGameSet(String[] gamers) {
        List<GameSet> sets = new ArrayList<>();
        for (String gamer : gamers) {
            String[] colors = gamer.split(",");
            int blue = 0;
            int green = 0;
            int red = 0;
            for (String color : colors) {
                // Blue
                if (color.contains("blue")) {
                    blue = Integer.valueOf(color.substring(0, color.indexOf("blue")).trim());
                }

                // Red
                if (color.contains("red")) {
                    red = Integer.valueOf(color.substring(0, color.indexOf("red")).trim());
                }

                // Green
                if (color.contains("green")) {
                    green = Integer.valueOf(color.substring(0, color.indexOf("green")).trim());
                }
            }
            sets.add(new GameSet(red, green, blue));

        }

        return sets;
    }

    public record Game(int id, List<GameSet> gameSets) {
        boolean canPossible() {
            int redAvaliable = 12;
            int greenAvaliable = 13;
            int blueAvaliable = 14;

            boolean possible = true;
            for (GameSet game : gameSets) {
                if(game.red<=redAvaliable && game.green<=greenAvaliable && game.blue<=blueAvaliable){

                } else {
                    possible = false;
                    break;
                }
            }


            return possible;
        }

        long fewers() {
            long redfewest = 0;
            long greenfewest = 0;
            long bluefewest = 0;

            for (GameSet game : gameSets) {
                if(redfewest<=game.red){
                    redfewest = game.red;
                }

                if(greenfewest<=game.green){
                    greenfewest = game.green;
                }

                if(bluefewest<=game.blue){
                    bluefewest = game.blue;
                }
            }


            return bluefewest*greenfewest*redfewest;
        }
    }


    public record GameSet(int red, int green, int blue) {
    }


}
