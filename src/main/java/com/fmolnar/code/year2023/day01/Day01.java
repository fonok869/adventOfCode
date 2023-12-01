package com.fmolnar.code.year2023.day01;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day01/input.txt");

        Integer value = 0;
        List<Integer> osszegek = new ArrayList<>();
        int first = -1;
        int second = 0;
        List<Character> numbers = new ArrayList<>();
        numbers.add('0');
        numbers.add('1');
        numbers.add('2');
        numbers.add('3');
        numbers.add('4');
        numbers.add('5');
        numbers.add('6');
        numbers.add('7');
        numbers.add('8');
        numbers.add('9');
        List<String> diitsLetters = new ArrayList<>();
        diitsLetters.add("one");
        diitsLetters.add("two");
        diitsLetters.add("six");
        diitsLetters.add("four");
        diitsLetters.add("five");
        diitsLetters.add("nine");
        diitsLetters.add("seven");
        diitsLetters.add("eight");
        diitsLetters.add("three");

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (numbers.contains(line.charAt(i))) {
                    if (first == -1) {
                        first = Integer.valueOf(line.substring(i, i + 1));
                    } else {
                        if (i == line.length() - 1) {
                            second = Integer.valueOf(line.substring(i));
                        } else {
                            second = Integer.valueOf(line.substring(i, i + 1));
                        }
                    }
                } else {
                    String word = line.substring(i);
                    if (i < line.length() - 2) {
                        if (word.startsWith("one")) {
                            if (first == -1) {
                                first = 1;
                            } else {
                                second = 1;
                            }
                            i = i + 2;
                        } else if (word.startsWith("two")) {

                            if (first == -1) {
                                first = 2;
                            } else {
                                second = 2;
                            }
                            i = i + 2;
                        } else if (word.startsWith("six")) {

                            if (first == -1) {
                                first = 6;
                            } else {
                                second = 6;
                            }
                            i = i + 2;
                        }


                    }
                    if (i < line.length() - 3) {
                        if (word.startsWith("four")) {
                            if (first == -1) {
                                first = 4;
                            } else {
                                second = 4;
                            }
                            i = i + 3;
                        } else if (word.startsWith("five")) {

                            if (first == -1) {
                                first = 5;
                            } else {
                                second = 5;
                            }
                            i = i + 3;
                        } else if (word.startsWith("nine")) {

                            if (first == -1) {
                                first = 9;
                            } else {
                                second = 9;
                            }
                            i = i + 3;
                        }
                        else if (word.startsWith("zero")) {

                            if (first == -1) {
                                first = 0;
                            } else {
                                second = 0;
                            }
                            i = i + 3;
                        }


                    }
                    if (i < line.length() - 4) {
                        if (word.startsWith("three")) {
                            if (first == -1) {
                                first = 3;
                            } else {
                                second = 3;
                            }
                            i = i + 4;
                        } else if (word.startsWith("seven")) {

                            if (first == -1) {
                                first = 7;
                            } else {
                                second = 7;
                            }
                            i = i + 4;
                        } else if (word.startsWith("eight")) {

                            if (first == -1) {
                                first = 8;
                            } else {
                                second = 8;
                            }
                            i = i + 4;
                        }

                    }

                }
            }

            Integer szam = first * 10 + second;
            if (second == -1) {
                szam = first * 10 + first;
            }
            if(szam<0){
                System.out.println(line + " : " + szam);
            }
            osszegek.add(szam);
            first = -1;
            second = -1;


        }

        Integer sum = osszegek.stream()
                .reduce(0, Integer::sum);


        System.out.println("Totot: " + sum);
    }
}
