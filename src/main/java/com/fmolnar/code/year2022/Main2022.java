package com.fmolnar.code.year2022;

import com.fmolnar.code.year2021.day22.Day22Challenge02;
import com.fmolnar.code.year2022.day25.Day25;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class Main2022 {

    public static void main(String[] args) throws IOException {
        Day25 day25 = new Day25();
        day25.calculate();

        System.out.println("Before");
        PointerToto p = new PointerToto("hello");
        System.out.println("After");
    }

    record PointerToto(String cucc){
        PointerToto{
            System.out.println(StringUtils.isEmpty(cucc));
        }
    }
}
