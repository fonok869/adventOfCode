package com.fmolnar.code.year2023.day11;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day11 {

    public static void main(String[] args) throws IOException {
        calculate();
    }


    public static void calculate() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2023/day11/input.txt");

        Set<Point> expansions = new HashSet<>();

        List<List<Character>> map = new ArrayList<>();
        for (String line : lines) {
            List<Character> charToPut = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                charToPut.add(line.charAt(i));
            }
            map.add(charToPut);
            if (charToPut.stream().allMatch(s -> s == '.')) {
                expansions.add(new Point(0, map.size()-1));
                List<Character> extra = new ArrayList<>(charToPut);
                //map.add(extra);
            }
        }

        List<Integer> addFugg = new ArrayList<>();
        for (int i = 0; i < map.get(0).size(); i++) {
            List<Character> fugg = new ArrayList<>();
            for (int j = 0; j < map.size(); j++) {
                List<Character> charToExamine = map.get(j);
                fugg.add(charToExamine.get(i));
            }
            if (fugg.stream().allMatch(s -> s == '.')) {
                addFugg.add(i);
            }
        }
        Collections.sort(addFugg);
        int eltolas = 0;
        for (Integer fuggolegesEltolas : addFugg) {
            expansions.add(new Point(fuggolegesEltolas + eltolas, 0));
            //eltolas++;
        }
        Collections.reverse(addFugg);
//        for (Integer integerToAdd : addFugg) {
//            for (int i = 0; i < map.size(); i++) {
//                map.get(i).add(integerToAdd, '.');
//            }
//        }

        Set<Point> points = new HashSet<>();

        for (int i = 0; i < map.size(); i++) {
            List<Character> charsToCheck = map.get(i);
            for (int j = 0; j < charsToCheck.size(); j++) {
                if (charsToCheck.get(j) == '#') {
                    points.add(new Point(j, i));
                }
            }
        }

        long sum = 0l;
        long szorzas = 1000000l-1l;
        Set<Point> pointsToReduce = new HashSet<>(points);
        for (Point pointActuel : points) {
            pointsToReduce.remove(pointActuel);
            for (Point pointToCompare : pointsToReduce) {
                long total = Long.valueOf(Math.abs(pointActuel.x - pointToCompare.x)) + Math.abs(pointActuel.y - pointToCompare.y);
                long x = getAllXStep(pointActuel, pointToCompare, expansions);
                long y = getAllYStep(pointActuel, pointToCompare, expansions);
                sum = sum + total + (Long.valueOf(x+y) * szorzas);
            }
        }


        System.out.println("Toto: " + sum);

    }

    private static long getAllYStep(Point pointActuel, Point pointToCompare, Set<Point> expansion) {
        int beginning = pointActuel.y;
        int end = pointToCompare.y;
        if (pointActuel.y > pointToCompare.y) {
            beginning = pointToCompare.y;
            end = pointActuel.y;
        }
        long multiplication = 0;
        for (int index = beginning; index <= end; index++) {
            if (expansion.contains(new Point(0, index))) {
                multiplication++;
            }
        }

        return multiplication;
    }

    private static long getAllXStep(Point pointActuel, Point pointToCompare, Set<Point> expansion) {
        int beginning = pointActuel.x;
        int end = pointToCompare.x;
        if (pointActuel.x > pointToCompare.x) {
            beginning = pointToCompare.x;
            end = pointActuel.x;
        }
        long multiplication = 0;
        for (int index = beginning; index <= end; index++) {
            if (expansion.contains(new Point(index, 0))) {
                multiplication++;
            }
        }

        return multiplication;
    }

    record Point(int x, int y) {
    }
}

