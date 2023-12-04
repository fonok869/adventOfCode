package com.fmolnar.code.year2023.day04;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 {

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day04/input.txt");

        double sumOfTotal = 0;
        Map<Integer, Integer> numbersOfCards = new HashMap<>();
        IntStream.rangeClosed(1, lines.size()).forEach(index -> numbersOfCards.put(index, 1));

        int card = 1;
        for (String line : lines) {
            final int cardIndex = card;
            String winningPart = line.substring(line.indexOf(':') + 1, line.indexOf('|'));
            String myPart = line.substring(line.indexOf('|') + 1);

            List<Integer> winningNumbers = Arrays.asList(winningPart.split(" ")).stream().filter(StringUtils::isNotBlank).map(String::trim)
                    .map(Integer::valueOf).collect(Collectors.toList());

            List<Integer> myNumbers = Arrays.asList(myPart.split(" ")).stream().filter(StringUtils::isNotBlank).map(String::trim)
                    .map(Integer::valueOf).collect(Collectors.toList());

            int matchedNumbers = winningNumbers.stream().filter(szam -> myNumbers.contains(szam)).collect(Collectors.toList()).size();

            Integer numberOfActualTicket = numbersOfCards.get(card) == null ? 1 : numbersOfCards.get(card);

            IntStream.rangeClosed(1, matchedNumbers)
                    .forEach(index -> numbersOfCards.compute(cardIndex + index, (key, value) -> (value == null ? 1 : value) + numberOfActualTicket));

            double winningScore = matchedNumbers > 2 ? Math.pow(2, (matchedNumbers - 1)) : matchedNumbers;
            sumOfTotal += winningScore;
            card++;
        }
        System.out.println("First: " + sumOfTotal);
        System.out.println("Second: " + numbersOfCards.entrySet().stream().map(Map.Entry::getValue).mapToInt(i -> i).sum());
    }
}
