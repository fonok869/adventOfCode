package com.fmolnar.code.year2024.day05;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day05 {
    Map<Integer, Set<Integer>> pages = new HashMap<>();

    public void calculateDay05_2024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day05/input.txt");

        boolean instructionFirstPart = true;
        int sum1 = 0;
        int sum2 = 0;
        for (String line : lines) {
            if (instructionFirstPart && !line.isEmpty()) {
                int indexSeparator = line.indexOf('|');
                pages.compute(Integer.valueOf(line.substring(0, indexSeparator)), (k, v) -> v == null ? new HashSet<>() : v).add(Integer.valueOf(line.substring(indexSeparator + 1)));
            } else if (!line.isEmpty()) {
                List<Integer> pagesOrder = Arrays.asList(line.split(",")).stream().mapToInt(s -> Integer.valueOf(s)).boxed().toList();

                List<Integer> pagesV1 = getPagesOrder(pagesOrder);
                if (pagesV1.equals(pagesOrder)) {
                    sum2 += pagesOrder.get(pagesOrder.size() / 2);
                } else {
                    List<Integer> pagesV2 = new ArrayList<>();
                    while (!pagesV2.equals(pagesV1)) {
                        pagesV2 = new ArrayList<>(pagesV1);
                        pagesV1 = getPagesOrder(pagesV2);
                    }
                    sum1 += pagesV1.get(pagesV1.size() / 2);
                }
            }

            if (line.isEmpty()) {
                instructionFirstPart = false;
            }
        }
        System.out.println("First : " + sum1);
        System.out.println("Second: " + sum2);
    }

    private List<Integer> getPagesOrder(List<Integer> pagesOrder) {
        for (int actualPageIndex = 1; actualPageIndex < pagesOrder.size(); actualPageIndex++) {
            Integer actualPageNumber = pagesOrder.get(actualPageIndex);
            Set<Integer> afterPages = pages.get(actualPageNumber);
            if (afterPages != null) {
                for (int beforePageIndex = 0; beforePageIndex < actualPageIndex; beforePageIndex++) {
                    Integer actualBeforePage = pagesOrder.get(beforePageIndex);
                    if (afterPages.contains(actualBeforePage)) {
                        return createNewPages(pagesOrder, beforePageIndex, actualPageNumber, actualPageIndex, actualBeforePage);
                    }
                }
            }
        }
        return new ArrayList<>(pagesOrder);
    }

    private List<Integer> createNewPages(List<Integer> pagesOrder, int beforePageIndex, Integer actualPage, int actualIndex, Integer actualBeforePage) {
        List<Integer> newPages = new ArrayList<>();
        for (int copyPagesIndex = 0; copyPagesIndex < pagesOrder.size(); copyPagesIndex++) {
            if (copyPagesIndex == beforePageIndex) {
                newPages.add(actualPage);
            } else if (copyPagesIndex == actualIndex) {
                newPages.add(actualBeforePage);
            } else {
                newPages.add(pagesOrder.get(copyPagesIndex));
            }
        }
        return newPages;
    }
}
