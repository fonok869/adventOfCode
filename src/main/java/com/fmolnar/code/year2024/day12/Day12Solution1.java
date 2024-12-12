package com.fmolnar.code.year2024.day12;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day12Solution1 {
    public void calculateDay12Year2024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day12/input.txt");

        Map<PointXY, String> mapper = AdventOfCodeUtils.getMapStringInput(lines);


        Map<String, List<PointXY>> areas = new HashMap<>();
        for (Map.Entry<PointXY, String> entry : mapper.entrySet()) {
            if (!areas.containsKey(entry.getValue())) {
                areas.put(entry.getValue(), new ArrayList<>());
            }
            areas.get(entry.getValue()).add(entry.getKey());
        }

        // Perimetre
        int osszeg = 0;
        List<Integer> szum = new ArrayList<>();

        Set<PointXY> pointsAlreadyChecked = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                PointXY actualPoint = new PointXY(x, y);
                if (pointsAlreadyChecked.contains(actualPoint)) {
                    continue;
                }
                pointsAlreadyChecked.add(actualPoint);
                String actualSymbol = mapper.get(actualPoint);
                List<PointXY> potentielsNeighbours = areas.get(actualSymbol);

                Set<PointXY> actualCluster = new HashSet<>();
                actualCluster.add(actualPoint);

                while (true) {

                    Set<PointXY> newCluster = new HashSet<>();
                    newCluster.addAll(actualCluster);

                    for (PointXY clusterPoint : actualCluster) {

                        for (PointXY direction : AdventOfCodeUtils.directionsNormals) {
                            PointXY pointToCheckDirection = clusterPoint.add(direction);
                            if (potentielsNeighbours.contains(pointToCheckDirection)) {
                                newCluster.add(pointToCheckDirection);
                            }
                        }
                    }

                    if (actualCluster.containsAll(newCluster) && newCluster.size() == actualCluster.size()) {
                        //vege

                        List<PointXY> pointXYSet = new ArrayList<>();
                        pointXYSet.addAll(actualCluster);

                        if (actualCluster.size() == 1) {
                            szum.add(4 * 1);
                        } else {
                            int area = pointXYSet.size();
                            int perimetre = 4 * pointXYSet.size();
                            for (int index = 0; index < pointXYSet.size(); index++) {
                                int actualElek = 0;
                                PointXY pointToCheck = pointXYSet.get(index);
                                for (int index2 = 0; index2 < pointXYSet.size(); index2++) {
                                    PointXY point = pointXYSet.get(index2);
                                    if (!point.equals(pointToCheck) && pointToCheck.szomszed(point)) {
                                        actualElek++;
                                    }
                                }

                                perimetre -= actualElek;

                            }
                            szum.add(area * (perimetre));
                        }

                        pointsAlreadyChecked.addAll(newCluster);
                        break;
                    } else {
                        actualCluster = new HashSet<>(newCluster);
                    }

                }
            }


            System.out.println(szum.stream().mapToInt(s -> s).sum());


        }


    }
}
