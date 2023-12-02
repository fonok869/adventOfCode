package com.fmolnar.code.year2023.day02;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02v2 {
    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day02/input.txt");
        List<Game> games = new ArrayList<>();
        for (String line : lines) {
            int semicolon = line.indexOf(':');
            // Game and ID
            String game = line.substring(0, semicolon);
            int gameId = Integer.valueOf(game.substring(game.indexOf(' ') + 1));
            // GameSet
            String[] gamers = line.substring(semicolon + 1).split(";");
            games.add(new Game(gameId, getAllGameSet(gamers)));
        }
        System.out.println("First: " + games.stream().filter(Game::canPossible).mapToInt(game -> game.id).sum());
        System.out.println("Second: " + games.stream().mapToLong(Game::fewers).sum());
    }

    private static List<GameSet> getAllGameSet(String[] gamers) {
        Pattern patternBlue = Pattern.compile("(\s(\\d+)\sblue)");
        Pattern patternRed = Pattern.compile("(\s(\\d+)\sred)");
        Pattern patternGreen = Pattern.compile("(\s(\\d+)\sgreen)");

        List<GameSet> sets = new ArrayList<>();
        Arrays.asList(gamers).stream().forEach(gamer -> {
            String[] colors = gamer.split(",");
            int blue = 0;
            int green = 0;
            int red = 0;
            for (String color : colors) {
                blue = getColor(color, patternBlue, blue);
                red = getColor(color, patternRed, red);
                green = getColor(color, patternGreen, green);
            }
            sets.add(new GameSet(red, green, blue));
        });
        return sets;
    }

    private static int getColor(String color, Pattern patternColor, int colorOcc) {
        Matcher matchColor = patternColor.matcher(color);
        if (matchColor.matches()) {
            return Integer.valueOf(matchColor.group(2));
        }
        return colorOcc;
    }

    public record Game(int id, List<GameSet> gameSets) {
        boolean canPossible() {
            int redAvaliable = 12;
            int greenAvaliable = 13;
            int blueAvaliable = 14;
            return gameSets.stream().allMatch(game -> game.red <= redAvaliable && game.green <= greenAvaliable && game.blue <= blueAvaliable);
        }

        long fewers() {
            long redfewest = gameSets.stream().mapToLong(game -> game.red).max().orElse(0l);
            long greenfewest = gameSets.stream().mapToLong(game -> game.green).max().orElse(0l);
            long bluefewest = gameSets.stream().mapToLong(game -> game.blue).max().orElse(0l);
            return bluefewest * greenfewest * redfewest;
        }
    }
    public record GameSet(int red, int green, int blue) {  }
}
