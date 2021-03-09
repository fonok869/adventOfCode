package com.fmolnar.code.day20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20Challenge01 {

    public static final char HASHTAG = '#';
    String title = "(^Tile\\s(\\d+)\\:$)";
    Pattern titlePattern = Pattern.compile(title);
    List<Tile> tiles = new ArrayList<>();
    List<Long> idsEdge = new ArrayList<>();
    Map<Long, Map<String, List<Tile>>> correspondant = new HashMap<>();
    Tile[][] tileExact = new Tile[12][12];
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day20/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean tileStart = false;
            long idTile = -1L;
            List<String> tile = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    Matcher id = titlePattern.matcher(line);
                    if (id.find()) {
                        idTile = Long.valueOf(id.group(2));
                        tileStart = true;
                        tile = new ArrayList<>();
                    } else if (tileStart) {
                        tile.add(line);
                    }
                } else {
                    tiles.add(new Tile(tile, idTile));
                }
            }
        }

        long number = 1;
        for (Tile tile : tiles) {
            correspondant.put(tile.getId(), new HashMap<>());
            int occurence = 0;
            for (Tile tileToCheck : tiles) {
                if (tile.getId() != tileToCheck.getId()) {
                    for (String tileActual : tile.getListOfChars()) {
                        if (tileToCheck.getListOfChars().contains(tileActual)) {
                            occurence++;
                        }
                    }
                }
            }
            if(occurence == 4){
                idsEdge.add(tile.getId());
                number = number * tile.getId();
                System.out.println(tile.getId());
            }
        }



        System.out.println("Eredmeny: " + number);
    }


    public class Tile {
        List<Character> left = new ArrayList<>();
        List<Character> right = new ArrayList<>();
        List<Character> up = new ArrayList<>();
        List<Character> down = new ArrayList<>();
        Map<String, List<Character>> chars = new HashMap<>();
        Set<String> listOfChars = new HashSet<>();
        List<String> arrays;
        long id;
        char[][] matrix;

        public Tile(List<String> arrays, long id) {
            this.arrays = arrays;
            this.id = id;
            transformMatrix();
            initSlides();
        }

        private void initSlides() {
            listOfChars.add(left.toString());
            listOfChars.add(up.toString());
            listOfChars.add(down.toString());
            listOfChars.add(right.toString());
            chars.put(LEFT, left);
            chars.put(UP, up);
            chars.put(DOWN, down);
            chars.put(RIGHT, right);
            Collections.reverse(left);
            listOfChars.add(left.toString());
            Collections.reverse(up);
            listOfChars.add(up.toString());
            Collections.reverse(down);
            listOfChars.add(down.toString());
            Collections.reverse(right);
            listOfChars.add(right.toString());
        }

        private void transformMatrix() {
            matrix = new char[arrays.size()][arrays.get(0).length()];
            for (int i = 0; i < arrays.size(); i++) {
                String line = arrays.get(i);
                for (int j = 0; j < line.length(); j++) {
                    matrix[i][j] = line.charAt(j);
                    comptage(i, line, j);
                }
            }
        }

        private void comptage(int i, String line, int j) {
            if (i == 0) {
                up.add(line.charAt(j));
            }
            if (arrays.size() - 1 == i) {
                down.add(line.charAt(j));
            }
            if (j == 0) {
                left.add(line.charAt(j));
            }
            if (j == line.length() - 1) {
                right.add(line.charAt(j));
            }
        }

        public List<Character> getLeft() {
            return left;
        }

        public List<Character> getRight() {
            return right;
        }

        public List<Character> getUp() {
            return up;
        }

        public List<Character> getDown() {
            return down;
        }

        public List<String> getArrays() {
            return arrays;
        }

        public long getId() {
            return id;
        }

        public Map<String, List<Character>> getChars() {
            return chars;
        }

        public Set<String> getListOfChars() {
            return listOfChars;
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "left=" + left +
                    ", right=" + right +
                    ", up=" + up +
                    ", down=" + down +
                    ", arrays=" + arrays +
                    ", id=" + id +
                    '}';
        }
    }

    //                    for (Map.Entry<String, List<Character>> entry : tile.getChars().entrySet()) {
//                        for (Map.Entry<String, List<Character>> entryToExamine : tileToCheck.getChars().entrySet()) {
//                            if (entry.getValue().equals(entryToExamine.getValue())) {
//                                Map<String, List<Tile>> actual = correspondant.get(tile.getId());
//                                List<Tile> tiles = actual.get(entry.getKey()) == null ? new ArrayList<>() : actual.get(entry.getKey());
//                                tiles.add(tileToCheck);
//                                actual.put(entry.getKey(), tiles);
//                            }
//                        }
//                    }

}
