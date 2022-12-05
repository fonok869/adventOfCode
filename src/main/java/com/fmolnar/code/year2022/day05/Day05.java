package com.fmolnar.code.year2022.day05;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Day05 {

    public static final String MOVE = "move";
    public static final String FROM = "from";
    public static final String TO = "to";

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day05/input.txt");

        List<String> linesTop = new ArrayList<>();
        List<LinkedList> priorities1 = new ArrayList<>();
        List<LinkedList> priorities2 = new ArrayList<>();
        boolean firstPart = true;
        boolean secondPart = false;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                firstPart = false;
                calculatePriorityQueus(linesTop, priorities2);
                calculatePriorityQueus(linesTop, priorities1);
                secondPart = true;
            } else if (firstPart) {
                linesTop.add(line);
            } else if (secondPart) {
                doStep(line, priorities1, priorities2);
            }
        }
        System.out.print("First Solution: ");

        for (LinkedList priority1 : priorities1) {
            System.out.print(priority1.removeLast());
        }
        System.out.println(" ");

        System.out.println("---------------------");
        System.out.print("Second Solution: ");

        for (LinkedList priority2 : priorities2) {
            System.out.print(priority2.removeLast());
        }
    }

    private void doStep(String line, List<LinkedList> priorities1, List<LinkedList> priorities2) {
        int number = Integer.valueOf(line.substring(MOVE.length(), line.indexOf(FROM)).trim());
        int column = Integer.valueOf(line.substring(line.indexOf(FROM) + FROM.length(), line.indexOf(TO)).trim());
        int destination = Integer.valueOf(line.substring((line.indexOf(TO) + TO.length())).trim());
        List<String> sameOrder = new ArrayList<>();
        for (int order = 0; order < number; order++) {
            String p2 = (String) priorities2.get(column - 1).removeLast();
            String p1 = (String) priorities1.get(column - 1).removeLast();
            sameOrder.add(p2);
            priorities1.get(destination - 1).add(p1);
        }

        for (int orderTo = 0; orderTo < number; orderTo++) {
            priorities2.get(destination - 1).add(sameOrder.get(number - orderTo - 1));
        }

    }

    private void calculatePriorityQueus(List<String> linesTop, List<LinkedList> priorities) {
        int lineNumber = Arrays.asList(linesTop.get(linesTop.size() - 1).split(" ")).stream().filter(Objects::nonNull).filter(StringUtils::isNotEmpty).mapToInt(t -> Integer.parseInt(t)).max().getAsInt();

        for (int i = 0; i < lineNumber; i++) {
            priorities.add(new LinkedList());
        }

        for (int j = linesTop.size() - 2; -1 < j; j--) {
            String lineToTreat = linesTop.get(j);
            int row = 0;
            for (int step = 0; step < lineToTreat.length(); step = step + 4) {
                String sub = lineToTreat.substring(step, Math.min(step + 4, lineToTreat.length() - 1));
                String letter = sub.substring(1, 2);
                if (!" ".equals(letter)) {
                    priorities.get(row).add(letter);
                }
                row++;
            }
        }
    }


}
