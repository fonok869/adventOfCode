package com.fmolnar.code;

import com.fmolnar.code.day01.Day01Challenge;
import com.fmolnar.code.day02.Day02Challenge;
import com.fmolnar.code.day02.Day02Challenge02;
import com.fmolnar.code.day03.Day03Challenge01;
import com.fmolnar.code.day03.Day03Challenge02;
import com.fmolnar.code.day04.Day04Challenge01;
import com.fmolnar.code.day04.Day04Challenge02;

import java.io.IOException;

/**
 * Launch day
 */
public class App {
    public static void main(String[] args) throws IOException {
        //DAY1
        Day01Challenge day01Challenge = new Day01Challenge();
        System.out.println("Day1 ------- ");
        day01Challenge.calculateDay1();

        //DAY2_01
        Day02Challenge day02Challenge01 = new Day02Challenge();
        System.out.println("Day2 ------- 01");
        day02Challenge01.calculateDay2();

        //DAY2_02
        Day02Challenge02 day02Challenge02 = new Day02Challenge02();
        System.out.println("Day2 ------- 02");
        day02Challenge02.calculateDay202();

        //DAY3_01
        Day03Challenge01 day03Challenge = new Day03Challenge01();
        System.out.println("Day3 ------- 01");
        day03Challenge.calculateDay301();

        //DAY3_02
        Day03Challenge02 day03Challenge2 = new Day03Challenge02();
        System.out.println("Day3 ------- 02");
        day03Challenge2.calculate();

        //DAY4_01
        Day04Challenge01 day04Challenge01 = new Day04Challenge01();
        System.out.println("Day4 ------- 01");
        day04Challenge01.calculateDay0401();

        //DAY4_02
        Day04Challenge02 day04Challenge02 = new Day04Challenge02();
        System.out.println("Day4 ------- 02");
        day04Challenge02.calculateDay0402();

    }


}
