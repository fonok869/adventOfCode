package com.fmolnar.code.year2020.day20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20Challenge02 {

    public static final char HASHTAG = '#';
    String title = "(^Tile\\s(\\d+)\\:$)";
    Pattern titlePattern = Pattern.compile(title);
    List<Tile> tiles = new ArrayList<>();
    Map<Long, Tile> mapsIdTile = new HashMap<>();
    Set<Long> idsEdge = new HashSet<>();
    Map<Long, Map<String, List<Tile>>> correspondant = new HashMap<>();
    Map<Long, Set<Long>> tileMatchOtherTile = new HashMap<>();
    private int TILE_MATRIX_MAX_SIZE = 3;
    private int MAX_TILE_SIZE = 10;
    Tile[][] tileExact = new Tile[TILE_MATRIX_MAX_SIZE][TILE_MATRIX_MAX_SIZE];
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private List<Character> UP_TO_MATCH = new ArrayList<>();
    private Long UP_TILE = null;
    private Long DOWN_TILE = null;
    private List<Character> LEFT_TO_MATCH = new ArrayList<>();
    private Long LEFT_TILE = null;
    private Long RIGHT_TILE = null;
    private Set<Long> tileAlreadyPositionned = new HashSet<>();
    private Map<Integer, List<Character>> sorok = new HashMap<>();
    private Map<Integer, List<Character>> sorokOrigin = new HashMap<>();
    private static final String DRAGON_1 = "                   #";
    private static final String DRAGON_2 = "#    ##    ##    ###";
    private static final String DRAGON_3 = " #  #  #  #  #  #   ";


    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day20/inputtest.txt");
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
                    mapsIdTile.put(idTile, new Tile(tile, idTile));
                }
            }
        }

        long number = 1;
        for (Tile tile : tiles) {
            correspondant.put(tile.getId(), new HashMap<>());
            int occurence = 0;
            for (Tile tileToCheck : tiles) {
                if (tile.getId() != tileToCheck.getId()) {
                    for (String tileActualEdgeChars : tile.getListOfEdgeChars()) {
                        if (tileToCheck.getListOfEdgeChars().contains(tileActualEdgeChars)) {
                            addToMap(tile, tileToCheck);
                            occurence++;
                        }
                    }
                }
            }
            if (occurence == 4) {
                idsEdge.add(tile.getId());
            }
        }


        calculateEdge();
        showAllMatrix();
        truncateAllLines();

        System.out.println("Eredmeny: " + number);
    }

    private void truncateAllLines() {

        System.out.println("Truncate;");
        int maxLength = (MAX_TILE_SIZE * TILE_MATRIX_MAX_SIZE) - (TILE_MATRIX_MAX_SIZE * 2);
        char[][] finalImage = new char[maxLength][maxLength];

        for (int i = 0; i < TILE_MATRIX_MAX_SIZE; i++) {
            for (int j = 0; j < TILE_MATRIX_MAX_SIZE; j++) {
                for (int ii = 1; ii < MAX_TILE_SIZE - 1; ii++) {
                    List<Character> characterListSor = sorok.get((i * MAX_TILE_SIZE) + ii);
                    if (characterListSor == null) {
                        characterListSor = new ArrayList<>();
                    }
                    for (int jj = 1; jj < MAX_TILE_SIZE - 1; jj++) {
                        Character character = tileExact[i][j].getMatrix()[ii][jj];
                        characterListSor.add(character);
                    }
                    sorok.put((i * MAX_TILE_SIZE) + ii, characterListSor);
                }
            }
        }

        List<List<Character>> toExlpoit = new ArrayList<>();
        for (Map.Entry<Integer, List<Character>> mapper : sorok.entrySet()) {
            System.out.println(mapper.getKey() + ".sor : " + " meret: " + mapper.getValue().size() + " " + mapper.getValue().toString());
            toExlpoit.add(mapper.getValue());
        }

        for (int p = 0; p < toExlpoit.size(); p++) {
            for (int q = 0; q < maxLength; q++) {
                finalImage[p][q] = toExlpoit.get(p).get(q);
            }
        }

        toShow(finalImage);
        changeDirection(finalImage); // 8
    }

    private void showAllMatrix() {
        for (int i = 0; i < TILE_MATRIX_MAX_SIZE; i++) {
            for (int j = 0; j < TILE_MATRIX_MAX_SIZE; j++) {
                for (int ii = 0; ii < MAX_TILE_SIZE; ii++) {
                    List<Character> characterListSor = sorokOrigin.get((i * MAX_TILE_SIZE) + ii);
                    if (characterListSor == null) {
                        characterListSor = new ArrayList<>();
                    }
                    for (int jj = 0; jj < MAX_TILE_SIZE; jj++) {
                        Character character = tileExact[i][j].getMatrix()[ii][jj];
                        characterListSor.add(character);
                    }
                    characterListSor.add(' ');
                    sorokOrigin.put((i * MAX_TILE_SIZE) + ii, characterListSor);
                }
            }
        }
        int maxLine = TILE_MATRIX_MAX_SIZE * MAX_TILE_SIZE;
        for (int k = 0; k < maxLine; k++) {
            if (k % 10 == 0) {
                System.out.println("");
            }
            System.out.println(k + ".sor : " + sorokOrigin.get(k));
        }
    }

    private void truncateLines() {
        System.out.println("Truncate;");
        int maxLength = (MAX_TILE_SIZE * TILE_MATRIX_MAX_SIZE) - ((TILE_MATRIX_MAX_SIZE - 2) * 2 + 2);
        char[][] finalImage = new char[maxLength][maxLength];

        for (int i = 0; i < TILE_MATRIX_MAX_SIZE; i++) {
            for (int j = 0; j < TILE_MATRIX_MAX_SIZE; j++) {
                for (int ii = 0; ii < MAX_TILE_SIZE; ii++) {
                    List<Character> characterListSor = sorok.get((i * MAX_TILE_SIZE) + ii);
                    if (characterListSor == null) {
                        characterListSor = new ArrayList<>();
                    }
                    for (int jj = 0; jj < MAX_TILE_SIZE; jj++) {
                        Character character = tileExact[i][j].getMatrix()[ii][jj];
                        if (isCorner(i, j, ii, jj, character, characterListSor)) {
                        } else if (isSlice(i, j, ii, jj, character, characterListSor)) {
                        } else if (kozepso(i, j, ii, jj, character, characterListSor)) { // kozepso
                        }
                    }
                    sorok.put((i * MAX_TILE_SIZE) + ii, characterListSor);
                }
            }
        }

        for (int k = 0; k < (MAX_TILE_SIZE * TILE_MATRIX_MAX_SIZE); k++) {
            System.out.println(k + ".sor : " + sorok.get(k).size() + " meret: " + sorok.get(k));
        }

        List<List<Character>> toExlpoit = new ArrayList<>();
        for (int k = 0; k < (MAX_TILE_SIZE * TILE_MATRIX_MAX_SIZE); k++) {
            List<Character> chars2 = sorok.get(k);
            if (chars2 != null && !chars2.isEmpty()) {
                toExlpoit.add(chars2);
            }
        }

        for (int p = 0; p < maxLength; p++) {
            for (int q = 0; q < maxLength; q++) {
                finalImage[p][q] = toExlpoit.get(p).get(q);
            }
        }

        toShow(finalImage);
        changeDirection(finalImage);

    }

    private void changeDirection(char[][] finalImage) {
        char[][] imageToShow = finalImage;
        int counter = 0;
        while (counter < 18) {
            System.out.println("Counter : " + counter);
            if (testDragonSuccess(imageToShow)) {
                return;
            }
            if (counter % 9 == 8) {
                imageToShow = flipYAxe(pivot90(flipYAxe(pivot90(imageToShow))));
            } else if (counter % 9 == 4) {
                imageToShow = flipYAxe(imageToShow);
            } else {
                imageToShow = pivot90(imageToShow);
            }
            counter++;
        }
    }

    private boolean testDragonSuccess(char[][] imageToShow) {
        int lengthDragon = DRAGON_1.length();
        int profondeur = 3;
        int counterDragon = 0;
        for (int i = 0; i < imageToShow.length - profondeur; i++) {
            for (int j = 0; j < imageToShow[0].length - lengthDragon; j++) {
                counterDragon = counterDragon + (isFoundDragon(i, j, imageToShow) ? 1 : 0);
            }
        }
        if(0<counterDragon){
            int matrixHashtag = countMatrixHashtag(imageToShow);
            int dragonHashtag = 15 * counterDragon;
            System.out.println("Eredmeny Hashtag : " + (matrixHashtag - dragonHashtag));
            return true;
        }
        return false;
    }

    private int countMatrixHashtag(char[][] imageToShow) {
        int counterHashtag = 0;
        for (int i = 0; i < imageToShow.length ; i++) {
            for (int j = 0; j < imageToShow[0].length ; j++) {
                if(imageToShow[i][j] == HASHTAG){
                    counterHashtag ++;
                }
            }
        }
        return counterHashtag;
    }

    private boolean isFoundDragon(int i, int j, char[][] imageToShow) {
        boolean isDragon1Good = true;
        for (int jj = 0; jj < DRAGON_1.length(); jj++) {
            if (DRAGON_1.charAt(jj) != HASHTAG || (DRAGON_1.charAt(jj) == HASHTAG && DRAGON_1.charAt(jj) == imageToShow[i][j + jj])) {
                isDragon1Good = true && isDragon1Good;
            } else {
                return false;
            }
        }

        boolean isDragon2Good = true;
        for (int jj = 0; jj < DRAGON_2.length(); jj++) {
            if (DRAGON_2.charAt(jj) != HASHTAG || (DRAGON_2.charAt(jj) == HASHTAG && DRAGON_2.charAt(jj) == imageToShow[i + 1][j + jj])) {
                isDragon2Good = true && isDragon2Good;
            } else {
                return false;
            }
        }

        boolean isDragon3Good = true;
        for (int jj = 0; jj < DRAGON_3.length(); jj++) {
            if (DRAGON_3.charAt(jj) != HASHTAG || (DRAGON_3.charAt(jj) == HASHTAG && DRAGON_3.charAt(jj) == imageToShow[i + 2][j + jj])) {
                isDragon3Good = true && isDragon3Good;
            } else {
                return false;
            }
        }

        return true;
    }

    private boolean kozepso(int i, int j, int ii, int jj, Character lettre, List<Character> characterList) {
        if (0 < i && i < TILE_MATRIX_MAX_SIZE - 1 && 0 < j && j < TILE_MATRIX_MAX_SIZE - 1) {
            // kozepso
            if (0 < jj && jj < (MAX_TILE_SIZE - 1) && 0 < ii && ii < (MAX_TILE_SIZE - 1)) {
                characterList.add(lettre);
            }
            return true;
        }
        return false;
    }

    private boolean isSlice(int i, int j, int ii, int jj, Character lettre, List<Character> characterList) {
        if (i == 0 && 0 < j && j < TILE_MATRIX_MAX_SIZE - 1) {
            // fenti negyzet
            if (0 < jj && jj < MAX_TILE_SIZE - 1 && ii < MAX_TILE_SIZE - 1) {
                characterList.add(lettre);
            }
            return true;
        }
        if (i == TILE_MATRIX_MAX_SIZE - 1 && 0 < j && j < TILE_MATRIX_MAX_SIZE - 1) {
            // bal also negyzet
            if (0 < jj && jj < MAX_TILE_SIZE - 1 && 0 < ii) {
                characterList.add(lettre);
            }
            return true;
        }
        if (0 < i && i < TILE_MATRIX_MAX_SIZE - 1 && j == TILE_MATRIX_MAX_SIZE - 1) {
            // jobb oldalso
            if (0 < ii && ii < MAX_TILE_SIZE - 1 && 0 < jj) {
                characterList.add(lettre);
            }
            return true;
        }
        if (0 < i && i < TILE_MATRIX_MAX_SIZE - 1 && j == 0) {
            // bal oldalso negyzet
            if (0 < ii && ii < MAX_TILE_SIZE - 1 && jj < MAX_TILE_SIZE - 1) {
                characterList.add(lettre);
            }
            return true;
        }
        return false;
    }

    private boolean isCorner(int i, int j, int ii, int jj, Character lettre, List<Character> characterList) {
        if (i == 0 && j == 0) {
            if (ii < MAX_TILE_SIZE - 1 && jj < MAX_TILE_SIZE - 1) {
                characterList.add(lettre);
            }
            return true;
        }
        if (i == TILE_MATRIX_MAX_SIZE - 1 && j == 0) {
            if (0 < ii && jj < MAX_TILE_SIZE - 1) {
                characterList.add(lettre);
            }
            return true;
        }
        if (i == 0 && j == TILE_MATRIX_MAX_SIZE - 1) {
            if (ii < MAX_TILE_SIZE - 1 && 0 < jj) {
                characterList.add(lettre);
            }
            return true;
        }
        if (i == TILE_MATRIX_MAX_SIZE - 1 && j == TILE_MATRIX_MAX_SIZE - 1) {
            if (0 < ii && 0 < jj) {
                characterList.add(lettre);
            }
            return true;
        }
        return false;
    }

    private void calculateEdge() {
        for (int j = 0; j < TILE_MATRIX_MAX_SIZE; j++) {
            for (int i = 0; i < TILE_MATRIX_MAX_SIZE; i++) {
                if (i == 0 && j == 0) {
                    Long idsToStart = idsEdge.iterator().next(); // 1951L
                    Tile corner = mapsIdTile.get(idsToStart);
                    Set<Long> szomszedok = tileMatchOtherTile.get(corner.getId());
                    int counter = 0;
                    while (true) {
                        System.out.println("Counter " + counter);
                        Set<String> oldalak = new HashSet<>();
                        for (Long szomszed : szomszedok) {
                            oldalak.add(caulculateCorrespondance(corner, szomszed));
                        }
                        if (oldalak.containsAll(Arrays.asList(DOWN, RIGHT))) {
                            corner = new Tile(corner.getMatrix(), corner.getId());
                            putTileToMatrix(corner, i, j);
                            break;
                        }
                        corner = changeTile(corner, counter);
                        counter++;
                    }
                } else if (j == 0) {
                    Tile corner = mapsIdTile.get(DOWN_TILE);
                    Set<Long> szomszedok = tileMatchOtherTile.get(corner.getId());
                    int counter = 0;
                    while (true) {
                        System.out.println("Counter " + counter);
                        Set<String> oldalak = new HashSet<>();
                        for (Long szomszed : szomszedok) {
                            oldalak.add(caulculateCorrespondance(corner, szomszed));
                            if (oldalak.contains("")) {
                                break;
                            }
                        }
                        if ((oldalak.containsAll(Arrays.asList(UP, RIGHT, DOWN)) && i != TILE_MATRIX_MAX_SIZE - 1) ||
                                (oldalak.containsAll(Arrays.asList(UP, RIGHT)) && i == TILE_MATRIX_MAX_SIZE - 1)) {
                            putTileToMatrix(corner, i, j);
                            break;
                        }
                        corner = changeTile(corner, counter);
                        counter++;
                    }
                } else if (j != 0) {
                    Tile corner = mapsIdTile.get(RIGHT_TILE);
                    Set<Long> szomszedok = tileMatchOtherTile.get(corner.getId());
                    int counter = 0;
                    while (true) {
                        System.out.println("Counter " + counter);
                        Set<String> oldalak = new HashSet<>();
                        for (Long szomszed : szomszedok) {
                            oldalak.add(caulculateCorrespondance(corner, szomszed));
                            if (oldalak.contains("")) {
                                break;
                            }
                        }
                        if ((oldalak.containsAll(Arrays.asList(LEFT, RIGHT, DOWN)) && i == 0 && j != TILE_MATRIX_MAX_SIZE - 1) ||
                                (oldalak.containsAll(Arrays.asList(UP, RIGHT, DOWN, LEFT)) && j != TILE_MATRIX_MAX_SIZE - 1 && i != 0) ||
                                (oldalak.containsAll(Arrays.asList(UP, RIGHT, LEFT)) && i == TILE_MATRIX_MAX_SIZE - 1) ||
                                (oldalak.containsAll(Arrays.asList(UP, LEFT, DOWN)) && j == TILE_MATRIX_MAX_SIZE - 1) ||
                                (oldalak.containsAll(Arrays.asList(LEFT, DOWN)) && j == TILE_MATRIX_MAX_SIZE - 1 && i == 0) ||
                                (oldalak.containsAll(Arrays.asList(UP, LEFT)) && j == TILE_MATRIX_MAX_SIZE - 1 && i == TILE_MATRIX_MAX_SIZE - 1)) {
                            putTileToMatrix(corner, i, j);
                            break;
                        }
                        corner = changeTile(corner, counter);
                        counter++;
                    }

                }
                System.out.println("Tile i:" + i + " j: " + j);
                toShow(tileExact[i][j].getMatrix());
            }
        }

    }


    private Tile changeTile(Tile corner, int counter) {
        if (counter % 9 == 8) {
            corner = new Tile(flipYAxe(pivot90(flipYAxe(pivot90(corner.getMatrix())))), corner.getId());
        } else if (counter % 9 == 4) {
            corner = new Tile(flipYAxe(corner.getMatrix()), corner.getId());
        } else {
            corner = new Tile(pivot90(corner.getMatrix()), corner.getId());
        }
        return corner;
    }

    private void putTileToMatrix(Tile corner, int i, int j) {
        tileAlreadyPositionned.add(corner.getId());
        UP_TO_MATCH = new ArrayList<>();
        if (i != TILE_MATRIX_MAX_SIZE - 1 && j != TILE_MATRIX_MAX_SIZE - 1) {
            for (int jj = 0; jj < MAX_TILE_SIZE; jj++) {
                UP_TO_MATCH.add(corner.getMatrix()[MAX_TILE_SIZE - 1][jj]);
            }
            calculateNextUP(corner);
        } else if (i == TILE_MATRIX_MAX_SIZE - 1) {
            UP_TILE = null; // kulonben  checknel baj van
            fillOutLeftSide(j);
        }

        if (0 < j && i < TILE_MATRIX_MAX_SIZE - 1) {
            LEFT_TO_MATCH = new ArrayList<>();
            Tile tileFenti = tileExact[i + 1][j - 1];
            for (int ii = 0; ii < MAX_TILE_SIZE; ii++) {
                LEFT_TO_MATCH.add(tileFenti.getMatrix()[ii][MAX_TILE_SIZE - 1]);
            }
            calculateNextLeft(tileFenti);
        }
        tileExact[i][j] = corner;
    }

    private void fillOutLeftSide(int j) {
        Tile tileFenti = tileExact[0][j];
        LEFT_TO_MATCH = new ArrayList<>();
        for (int ii = 0; ii < MAX_TILE_SIZE; ii++) {
            LEFT_TO_MATCH.add(tileFenti.getMatrix()[ii][MAX_TILE_SIZE - 1]);
        }
        calculateNextLeft(tileFenti);
    }

    private void calculateNextLeft(Tile corner) {
        Set<Long> szomszedok = tileMatchOtherTile.get(corner.getId());
        for (Long szomszed : szomszedok) {
            if (mapsIdTile.get(szomszed).getListOfEdgeChars().contains(LEFT_TO_MATCH.toString())) {
                LEFT_TILE = corner.getId();
                RIGHT_TILE = szomszed;
                break;
            }
        }
    }

    private void calculateNextUP(Tile corner) {
        Set<Long> szomszedok = tileMatchOtherTile.get(corner.getId());
        for (Long szomszed : szomszedok) {
            if (mapsIdTile.get(szomszed).getListOfEdgeChars().contains(UP_TO_MATCH.toString())) {
                UP_TILE = corner.getId();
                DOWN_TILE = szomszed;
                break;
            }
        }
    }

    private String caulculateCorrespondance(Tile tile, Long szomszedToCheck) {
        Tile szomszed = mapsIdTile.get(szomszedToCheck);
        String direction = "";
        if ((LEFT_TILE == null && szomszed.getListOfEdgeChars().contains(tile.getLeft().toString()) ||
                (LEFT_TILE != null && LEFT_TILE == szomszed.getId() && LEFT_TO_MATCH.toString().contains(tile.getLeft().toString())))) {
            direction = LEFT;
        }

        if ((UP_TILE == null && szomszed.getListOfEdgeChars().contains(tile.getUp().toString())) ||
                (UP_TILE != null && UP_TILE == szomszed.getId() && UP_TO_MATCH.toString().equals(tile.getUp().toString()))) {
            direction = UP;
        }

        if (szomszed.getListOfEdgeChars().contains(tile.getDown().toString())) {
            direction = DOWN;
        }

        if (szomszed.getListOfEdgeChars().contains(tile.getRight().toString())) {
            direction = RIGHT;
        }
        return direction;
    }

    private char[][] pivot90(char[][] toPivot) {
        toShow(toPivot);
        int hossz = toPivot.length;
        char[][] newMatrix = new char[hossz][hossz];
        for (int i = 0; i < hossz; i++) { // y
            for (int j = 0; j < hossz; j++) { // x
                newMatrix[j][i] = toPivot[(-1 * i) + hossz - 1][j];
            }
        }
        System.out.println("After: \n \n ");
        toShow(newMatrix);
        return newMatrix;
    }

    private char[][] flipYAxe(char[][] toFlipY) {
        System.out.println("Before: \n \n ");
        toShow(toFlipY);
        int hossz = toFlipY.length;
        char[][] newMatrix = new char[hossz][hossz];
        for (int i = 0; i < hossz; i++) { // y
            for (int j = 0; j < hossz; j++) { // x
                newMatrix[j][i] = toFlipY[j][(-1 * i) + hossz - 1];
            }
        }
        System.out.println("After: \n \n ");
        toShow(newMatrix);
        return newMatrix;
    }

    private void toShow(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            String line = "";
            for (int j = 0; j < matrix[0].length; j++) {
                line = line + matrix[i][j];
            }
            System.out.println(line);
        }
    }


    private void addToMap(Tile tile, Tile tileToCheck) {
        Set<Long> longs = tileMatchOtherTile.get(tile.getId());
        if (longs == null) {
            longs = new HashSet<>();
        }
        longs.add(tileToCheck.getId());
        tileMatchOtherTile.put(tile.getId(), longs);
    }


    public class Tile {
        List<Character> left = new ArrayList<>();
        List<Character> right = new ArrayList<>();
        List<Character> up = new ArrayList<>();
        List<Character> down = new ArrayList<>();
        Map<String, List<Character>> chars = new HashMap<>();
        Set<String> listOfEdgeChars = new HashSet<>();
        List<String> arrays;
        long id;
        char[][] matrix;

        public Tile(char[][] matrix, long id) {
            this.matrix = matrix;
            this.id = id;
            fillSlides();
            initSlides();
        }

        private void fillSlides() {
            int hossz = matrix.length;
            for (int i = 0; i < hossz; i++) {
                for (int j = 0; j < hossz; j++) {
                    char symbol = matrix[i][j];
                    if (i == 0) {
                        up.add(symbol);
                    }
                    if (hossz - 1 == i) {
                        down.add(symbol);
                    }
                    if (j == 0) {
                        left.add(symbol);
                    }
                    if (j == hossz - 1) {
                        right.add(symbol);
                    }
                }
            }

        }

        public Tile(List<String> arrays, long id) {
            this.arrays = arrays;
            this.id = id;
            transformMatrix();
            initSlides();
        }

        private void initSlides() {
            listOfEdgeChars.add(left.toString());
            listOfEdgeChars.add(up.toString());
            listOfEdgeChars.add(down.toString());
            listOfEdgeChars.add(right.toString());
            chars.put(LEFT, left);
            chars.put(UP, up);
            chars.put(DOWN, down);
            chars.put(RIGHT, right);
            Collections.reverse(left);
            listOfEdgeChars.add(left.toString());
            Collections.reverse(up);
            listOfEdgeChars.add(up.toString());
            Collections.reverse(down);
            listOfEdgeChars.add(down.toString());
            Collections.reverse(right);
            listOfEdgeChars.add(right.toString());
            Collections.reverse(left);
            Collections.reverse(up);
            Collections.reverse(down);
            Collections.reverse(right);

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
            char symbol = line.charAt(j);
            if (i == 0) {
                up.add(symbol);
            }
            if (arrays.size() - 1 == i) {
                down.add(symbol);
            }
            if (j == 0) {
                left.add(symbol);
            }
            if (j == line.length() - 1) {
                right.add(symbol);
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

        public Set<String> getListOfEdgeChars() {
            return listOfEdgeChars;
        }

        public char[][] getMatrix() {
            return matrix;
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

}
