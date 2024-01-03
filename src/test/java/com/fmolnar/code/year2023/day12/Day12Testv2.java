package com.fmolnar.code.year2023.day12;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class Day12Testv2 {

    @Test
    public void test1(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("???.### 1,1,3"), false);

        assertThat(returnValue).isEqualTo(1L);
    }

    @Test
    public void test2(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of(".??..??...?##. 1,1,3"),false);

        assertThat(returnValue).isEqualTo(16384L);
    }

    @Test
    public void test3(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("?#?#?#?#?#?#?#? 1,3,1,6"), false);

        assertThat(returnValue).isEqualTo(1L);
    }

    @Test
    public void test4(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("????.#...#... 4,1,1"), false);

        assertThat(returnValue).isEqualTo(16L);
    }

    @Test
    public void test5(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("????.######..#####. 1,6,5"), false);

        assertThat(returnValue).isEqualTo(2500L);
    }

    @Test
    public void test6(){
        Day12v2 day12v2 = new Day12v2();
        long returnValue = day12v2.calculateFor1(List.of("?###???????? 3,2,1"), false);

        assertThat(returnValue).isEqualTo(506250L);
    }


}
