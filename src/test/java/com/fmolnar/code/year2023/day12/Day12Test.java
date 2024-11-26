package com.fmolnar.code.year2023.day12;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class Day12Test {

    @Test
    public void test1(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("???.### 1,1,3"), true);

        assertThat(returnValue).isEqualTo(1L);
    }

    @Test
    public void test2(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of(".??..??...?##. 1,1,3"),true);

        assertThat(returnValue).isEqualTo(4L);
    }

    @Test
    public void test3(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("?#?#?#?#?#?#?#? 1,3,1,6"), true);

        assertThat(returnValue).isEqualTo(1L);
    }

    @Test
    public void test4(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("????.#...#... 4,1,1"), true);

        assertThat(returnValue).isEqualTo(1L);
    }

    @Test
    public void test5(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("????.######..#####. 1,6,5"), true);

        assertThat(returnValue).isEqualTo(4L);
    }

    @Test
    public void test6(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("?###???????? 3,2,1"), true);

        assertThat(returnValue).isEqualTo(10L);
    }

    @Test
    public void test7(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of(".#??#?.??????# 1,3,3,1"), true);

        assertThat(returnValue).isEqualTo(3);
    }

    @Test
    public void test8(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("??????##?#?.???????# 2,6,1,1,1"), true);

        assertThat(returnValue).isEqualTo(50);
    }

}
