package com.fmolnar.code.year2021.day19;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Day19Test {

    @Test
    public void testintersection(){
        List<Integer> szamok = new ArrayList<>();
        szamok.addAll(Arrays.asList(1,2,3,3,3,4));

        List<Integer> szamok2 = new ArrayList<>();
        szamok2.addAll(Arrays.asList(1,2,3,3,4,5,6));

        Collection<Integer> integs = CollectionUtils.intersection(szamok, szamok2);
        System.out.println(integs);
    }
}
