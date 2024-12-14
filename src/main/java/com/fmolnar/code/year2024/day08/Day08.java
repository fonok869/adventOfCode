package com.fmolnar.code.year2024.day08;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day08 {
    public void calculteDay8() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day08/input.txt");

        int xMax = lines.get(0).length();
        int yMax = lines.size();

        Map<PointXY, String> mapper = AdventOfCodeUtils.getMapStringInput(lines);

        Map<String, Set<PointXY>> setters = new HashMap<>();
        for (Map.Entry<PointXY, String> entry : mapper.entrySet()) {
            String symbol = entry.getValue();
            if (".".equals(symbol)) {
                continue;
            }
            if (setters.get(symbol) == null) {
                setters.put(symbol, new HashSet<>());
                setters.get(symbol).add(entry.getKey());
            } else {
                setters.get(symbol).add(entry.getKey());
            }
        }

        Set<PointXY> antinodesFirst = new HashSet<>();
        Set<PointXY> antinodesSecond = new HashSet<>();
        for (Map.Entry<String, Set<PointXY>> entry : setters.entrySet()) {
            Set<PointXY> esemenyek = entry.getValue();
            List<PointXY> lista = esemenyek.stream().toList();


            for (int i = 0; i < lista.size(); i++) {
                PointXY actualPoint = lista.get(i);
                for (int j = i + 1; j < lista.size(); j++) {
                    PointXY pointToCheck = lista.get(j);
                    //search for antinodesFirst
                    getAntinodes(pointToCheck, actualPoint, antinodesFirst, mapper, true);
                    getAntinodes(pointToCheck, actualPoint, antinodesSecond, mapper, false);
                }
            }
        }


        System.out.println("First: " + antinodesFirst.size());
        System.out.println("Second: " + antinodesSecond.size());
    }

    private void getAntinodes(PointXY pointToCheck, PointXY actualPoint, Set<PointXY> antinodes, Map<PointXY, String> mapper, boolean first) {
        // pointToCheck -> Actual
        int yDiff = pointToCheck.y() - actualPoint.y();
        int xDiff = pointToCheck.x() - actualPoint.x();

        for (int i = (first ? 1 : 0); i < (first ? 2 : 200); i++) {
            PointXY firstCandidate = new PointXY(pointToCheck.x() + xDiff * i, pointToCheck.y() + yDiff * i);
            PointXY secondeCandidate = new PointXY(actualPoint.x() + -1 * xDiff * i, actualPoint.y() + -1 * yDiff * i);

            if (mapper.containsKey(firstCandidate)) {
                antinodes.add(firstCandidate);
            }

            if (mapper.containsKey(secondeCandidate)) {
                antinodes.add(secondeCandidate);
            }
        }


    }
}
