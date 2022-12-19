package com.fmolnar.code.year2022.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    String instructions;
    int maxX = 7;
    int maxY = 200;
    int[][] matrix = new int[maxX][maxY];
    int maxHeight = maxY - 4;
    List<List<Integer>> roundsUp = new ArrayList<>();
    List<Long> maxHeights = new ArrayList<>();
    List<Long> dueReorganisation = new ArrayList<>();
    List<Long> actualGasList = new ArrayList<>();
    final int limit = 20;

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day17/input.txt");
        List<List<Point>> forms = new ArrayList<>();
        forms.add(createLineHorizzontal());
        forms.add(createCross());
        forms.add(createL());
        forms.add(createLineVertical());
        forms.add(createSquare());

        for (String line : lines) {
            instructions = line;
        }

        System.out.println("Length: " + instructions.length());
        int cardinality = instructions.length() * 5;
        long actualGas = 0;
        for (int i = 0; i < 4 * cardinality; i++) {
            actualGas = stepForm(forms.get(i % 5), actualGas);
            calculYMax(actualGas);
            if (maxHeight <= limit) {
                reorganisation();
            }
//            if (i % 100 == 0) {
//                System.out.println("i: " + i + " MAxHeight: " + (maxY - (maxHeight + 4)));
//            }
        }

//        System.out.println("MAxHeight: " + (maxY - (maxHeight + 4)));
//        long values = Long.valueOf((maxY - (maxHeight + 4))) + dueReorganisation.stream().mapToLong(s -> Long.valueOf(s)).sum();
//        System.out.println("Height: " + values);


        List<Integer> diffs = new ArrayList<>();
        List<Long> heightDiffs = new ArrayList<>();
        for (int i = 0; i < cardinality; i++) {
            List<Integer> l1 = roundsUp.get(i);
            for (int j = cardinality; j < 2 * cardinality; j++) {
                List<Integer> l2 = roundsUp.get(j);
                if (l1.equals(l2)) {
                    int diff = j - i;
                    diffs.add(diff);
                    long maxHeightJ = maxHeights.get(j);
                    long maxHeightI = maxHeights.get(i);
                    heightDiffs.add(maxHeightJ - maxHeightI);
                }
            }
        }

        //i : 1071
        long diffMax = 65170l ;
        long diffHeight = 97812;
        long diffGasUnit = 383458l;
//
//
        long numberRocks = 1000000000000L;
        long stepped = numberRocks / Long.valueOf(diffMax);
        long rested = numberRocks - (stepped * diffMax);
        long diffGasAll = stepped * diffGasUnit;
        long heightStepped = stepped * diffHeight;
//
//        // Minden init
        matrix = new int[maxX][maxY];
        maxHeight = maxY - 4;
        maxHeights = new ArrayList<>();
        roundsUp = new ArrayList<>();
        dueReorganisation = new ArrayList<>();

        long actualGas2 = 0;
        for (int i = 0; i < rested; i++) {
            if(i == 1071){
                actualGas2 = (actualGas2 + diffGasAll) % instructions.length();
            }

            actualGas2 = stepForm(forms.get(i % 5), actualGas2);
            //show();
            calculYMax(actualGas2);
            if (maxHeight <= limit) {
                reorganisation();
            }
        }
//
        System.out.println("MAxHeight: " + (maxY - (maxHeight + 4) + heightStepped + dueReorganisation.stream().mapToLong(s->s).sum()));
        int counter = 0;

        List<Integer> cycles = new ArrayList<>();
        loop: for(int i =0; i<cardinality; i++){
            List<Integer> l1 = roundsUp.get(i);
            for(int j=cardinality; j<2*cardinality; j++){
                List<Integer> l2 = roundsUp.get(j);
                if(l1.equals(l2)){
                    int diff = (j-i);
                    //System.out.println(l1);
                    //System.out.println(l2);
                    for(int k=2*cardinality; k<3*cardinality; k++) {
                        List<Integer> l3 = roundsUp.get(j);
                        if (l1.equals(l3)) {
                            int diff2 = (k - j);
                            if(diff == diff2 && diff>cardinality) {
                                //System.out.println("i : " + i + " j: " + j + " k: " + k + " diff1: " + diff + " diff2: " + (k - j) + " diff3: " + (k - i));


                                if(roundsUp.get(k+diff2).equals(l3)){
                                    counter++;
                                    cycles.add(diff);
                                    System.out.println("Heights: (1)" + maxHeights.get(i) + " (j) " + maxHeights.get(j) + " (k): " + maxHeights.get(k) + " (z): " + maxHeights.get(k+diff2));
                                    System.out.println("i : " + i + " j: " + j + " k: " + k + " diff1: " + diff + " diff2: " + (k - j) + " diff3: " + (k - i));
                                    System.out.println("Actal gas (i) : " + actualGasList.get(i) + " j: " + actualGasList.get(j) + " k: " + actualGasList.get(k) + " diff k-j: " + (actualGasList.get(k) - actualGasList.get(j)) + " diff j-i: " + (actualGasList.get(j) - actualGasList.get(i)));

                                }
                            }
                        }
                    }


                }
            }
        }
        System.out.println("Gas Actual: " + actualGas);
        System.out.println("Nombre: " + counter);
        System.out.println("Max: " + cycles.stream().mapToInt(s->s).max().getAsInt());

        System.out.println("MAxHeight: " + (maxY - (maxHeight + 4)));
    }

    private void reorganisation() {
        List<Integer> maxs = new ArrayList<>();
        for (int x = 0; x < maxX; x++) {
            int yMax = maxY;
            for (int y = maxY - 1; 0 <= y; y--) {
                if (matrix[x][y] == 1) {
                    if (y < yMax) {
                        yMax = y;
                    }
                }
            }
            maxs.add(yMax);
        }
        final int max = maxs.stream().mapToInt(s -> s).max().getAsInt();
        //show();
        int diff = maxY - max - limit;

        if(diff<0){
            throw new RuntimeException("Nem lehet kisebb 0-nal");
        }
        int[][] newMatrix = new int[maxX][maxY];
        for (int y = maxY - 1; diff <= y; y--) {
            for (int x = 0; x < maxX; x++) {
                newMatrix[x][y] = matrix[x][y - (diff)];
            }
        }
        //show2(newMatrix);
        dueReorganisation.add(Long.valueOf(diff));
        maxHeight += diff;
        matrix = newMatrix;
    }


    private void calculYMax(long actualGas) {
        List<Integer> maxs = new ArrayList<>();
        for (int x = 0; x < maxX; x++) {
            int yMax = maxY;
            for (int y = maxY - 1; 0 <= y; y--) {
                if (matrix[x][y] == 1) {
                    if (y < yMax) {
                        yMax = y;
                    }
                }
            }
            maxs.add(yMax);
        }
        final int min = maxs.stream().mapToInt(s -> s).min().getAsInt();
        final int max = maxs.stream().mapToInt(s -> s).max().getAsInt();
        maxHeight = min - 4;
        List<Integer> relatives = new ArrayList<>();
        for (int maxValue : maxs) {
            relatives.add(Math.abs(max - maxValue));
        }

        maxHeights.add((Long.valueOf(maxY - min)) + dueReorganisation.stream().mapToLong(s->s).sum());
        actualGasList.add(actualGas);

        roundsUp.add(relatives);
    }

    private void show2(int[][] matrix) {
        for (int y = 0; y < maxY; y++) {
            System.out.println();
            for (int x = 0; x < maxX; x++) {
                System.out.print(matrix[x][y]);
            }
        }
        System.out.println();
        System.out.println("---------------------------------------------");
    }

    private void show() {
        for (int y = 0; y < maxY; y++) {
            System.out.println();
            for (int x = 0; x < maxX; x++) {
                System.out.print(matrix[x][y]);
            }
        }
        System.out.println();
        System.out.println("---------------------------------------------");
    }

    private long stepForm(List<Point> alakzat, long actualGas) {
        Point actualPoint = new Point(2, maxHeight);
        for (int i = 0; i < 100; i++) {
            //System.out.println("i: " + i);
            int[][] vierge = matrix;
            if (i != 0) {
                vierge = calculateVierge(actualPoint, alakzat);
            }
            // Oldalra lepes
            if ((i % 2) == 0) {

                // Balra lepes
                if (instructions.charAt((int) (actualGas % ((long) instructions.length()))) == '<') {
                    Point step = new Point(-1, 0);
                    if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                        actualPoint = addTwoPoint(actualPoint, step);
                        matrix = draw(actualPoint, alakzat, vierge);
                        //show();
                    } // Vege nem tud lepni
                    else {

                    }
                    actualGas += 1;
                }
                // Jobbra lepes
                else if (instructions.charAt((int) (actualGas % (long)instructions.length())) == '>') {
                    Point step = new Point(1, 0);
                    if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                        actualPoint = addTwoPoint(actualPoint, step);
                        matrix = draw(actualPoint, alakzat, vierge);
                        //show();
                    } // Vege nem tud lepni
                    else {

                    }
                    actualGas += 1;
                } else {
                    System.out.println("Nem kellene itt lennie 1");
                }
            }// Lefele lepes
            else {
                Point step = new Point(0, 1);
                if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                    actualPoint = addTwoPoint(actualPoint, step);
                    matrix = draw(actualPoint, alakzat, vierge);
                    //show();
                } // Vege nem tud lepni
                else {
                    return actualGas;
                }
            }

        }
        // aktualis Gas limit
        return actualGas;

    }

    private int[][] draw(Point newPoint, List<Point> alakzat, int[][] vierge) {
        List<Point> pointsCalculated = alakzat.stream().map(s -> addTwoPoint(s, newPoint)).collect(Collectors.toList());
        for (Point pointer : pointsCalculated) {
            vierge[pointer.x][pointer.y] = 1;
        }
        return vierge;
    }


    private boolean canGoToDirection(List<Point> alakzat, Point actualPoint, int[][] vierge, Point point) {
        Point newPoint = addTwoPoint(actualPoint, point);
        List<Point> pointsCalculated = alakzat.stream().map(s -> addTwoPoint(s, newPoint)).collect(Collectors.toList());

        for (Point pointToCheck : pointsCalculated) {
            if (0 <= pointToCheck.x && pointToCheck.x < maxX && 0 <= pointToCheck.y && pointToCheck.y < maxY) {
                if (vierge[pointToCheck.x][pointToCheck.y] == 0) {
                    // we can move
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;

    }

    private int[][] calculateVierge(Point actualPoint, List<Point> points) {
        List<Point> pointsCalculated = points.stream().map(s -> addTwoPoint(s, actualPoint)).collect(Collectors.toList());
        int[][] matrixVierge = new int[maxX][maxY];
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                Point pointToCheck = new Point(x, y);
                if (!pointsCalculated.contains(pointToCheck)) {
                    matrixVierge[x][y] = matrix[x][y];
                }
            }
        }
        return matrixVierge;
    }

    Point addTwoPoint(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }


    private List<Point> createSquare() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, -1));
        points.add(new Point(1, 0));
        points.add(new Point(1, -1));
        return points;
    }

    private List<Point> createLineVertical() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, -1));
        points.add(new Point(0, -2));
        points.add(new Point(0, -3));
        return points;
    }

    private List<Point> createL() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(2, 0));
        points.add(new Point(2, -1));
        points.add(new Point(2, -2));
        return points;
    }

    private List<Point> createCross() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 0));
        points.add(new Point(0, -1));
        points.add(new Point(1, -1));
        points.add(new Point(1, -2));
        points.add(new Point(2, -1));
        return points;
    }

    private List<Point> createLineHorizzontal() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(2, 0));
        points.add(new Point(3, 0));
        return points;
    }

    record Point(int x, int y) {

    }
}
