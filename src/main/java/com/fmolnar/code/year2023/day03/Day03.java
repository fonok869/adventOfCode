package com.fmolnar.code.year2023.day03;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day03 {

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day03/input.txt");
        List<Integer> valids = new ArrayList<>();
        List<Point> directions = initdirections();
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (Character.isDigit(lines.get(i).charAt(j))) {
                    int jEnd = findEnd(i, j, lines, maxX);
                    String numberString = lines.get(i).substring(j, jEnd);
                    int number = Integer.valueOf(numberString);
                    boolean isValid = getIfValid(lines, i, j, jEnd, directions);
                    if(isValid){
                        valids.add(number);
                    }
                    j = jEnd;
                    System.out.println(number);
                }
            }
        }

        System.out.println("Valid : " + valids.stream().mapToInt(s->s).sum());
    }

    private static boolean getIfValid(List<String> lines, int i, int j, int jEnd, List<Point> directions) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();


        for (int pointerX = j; pointerX < jEnd; pointerX++) {
            for (Point direction : directions) {
                Point pointToCheck = new Point(i + direction.y, pointerX + direction.x);
                if(0<= pointToCheck.x && pointToCheck.x<maxX
                        && 0<= pointToCheck.y && pointToCheck.y < maxY){
                    Character letter = lines.get(pointToCheck.y).charAt(pointToCheck.x);
                    if(!(letter.equals('.') || Character.isDigit(letter))){
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private static List<Point> initdirections() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(-1, -1));
        points.add(new Point(-1, 0));
        points.add(new Point(-1, 1));
        points.add(new Point(0, -1));
        points.add(new Point(0, 1));
        points.add(new Point(1, -1));
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));
        return points;
    }


    private static int findEnd(int i, int j, List<String> lines, int xMax) {
        for (int k = j + 1; k < xMax; k++) {
            if (Character.isDigit(lines.get(i).charAt(k))) {

            } else {
                return k;
            }
        }
        return xMax - 1;
    }

    public record Point(int y, int x) {
    }
}
