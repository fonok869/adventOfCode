package com.fmolnar.code.year2024.day12;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.Direction;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day12Solution2 {
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
                            List<Integer> twoCornersList = new ArrayList<>();
                            List<PointXY> corners = new ArrayList<>();
                            int area = pointXYSet.size();

                            for (int index = 0; index < pointXYSet.size(); index++) {
                                int actualElek = 0;
                                PointXY pointToCheck = pointXYSet.get(index);


                                for (int index2 = 0; index2 < pointXYSet.size(); index2++) {
                                    PointXY point = pointXYSet.get(index2);
                                    if (!point.equals(pointToCheck) && pointToCheck.szomszed(point)) {
                                        actualElek++;
                                    }
                                }

                                PointXY northPoint = pointToCheck.add(Direction.UP.toPoint());
                                PointXY southPoint = pointToCheck.add(Direction.DOWN.toPoint());
                                PointXY westPoint = pointToCheck.add(Direction.LEFT.toPoint());
                                PointXY eastPoint = pointToCheck.add(Direction.RIGHT.toPoint());

                                if (actualElek == 1) {
                                    twoCornersList.add(2);
                                } else if (actualElek == 2) {
                                    if (actualCluster.containsAll(Set.of(northPoint, westPoint)) ||
                                            actualCluster.containsAll(Set.of(northPoint, eastPoint)) ||
                                            actualCluster.containsAll(Set.of(southPoint, westPoint)) ||
                                            actualCluster.containsAll(Set.of(southPoint, eastPoint))) {
                                        corners.add(pointToCheck);
                                    }
                                }

                                // rightUp
                                PointXY rightUp = pointToCheck.add(Direction.UPRIGHT.toPoint());
                                if (!actualCluster.contains(rightUp) && actualCluster.containsAll(Set.of(northPoint, eastPoint))) {
                                    corners.add(rightUp);
                                }


                                // rightDown
                                PointXY rightDown = pointToCheck.add(Direction.RIGHTDOWN.toPoint());
                                if (!actualCluster.contains(rightDown) && actualCluster.containsAll(Set.of(eastPoint, southPoint))) {
                                    corners.add(rightDown);
                                }

                                // leftUp
                                PointXY leftUp = pointToCheck.add(Direction.UPLEFT.toPoint());
                                if (!actualCluster.contains(leftUp) && actualCluster.containsAll(Set.of(northPoint, westPoint))) {
                                    corners.add(leftUp);
                                }

                                // leftDown
                                PointXY leftDown = pointToCheck.add(Direction.LEFTDOWN.toPoint());
                                if (!actualCluster.contains(leftDown) && actualCluster.containsAll(Set.of(southPoint, westPoint))) {
                                    corners.add(leftDown);
                                }
                            }
                            int cornerNumber = twoCornersList.stream().mapToInt(s -> s).sum() + corners.size();
                            szum.add(area * cornerNumber);
                        }
                        pointsAlreadyChecked.addAll(newCluster);
                        break;
                    } else {
                        actualCluster = new HashSet<>(newCluster);
                    }

                }
            }
        }

        System.out.println("Second: " + szum.stream().mapToInt(s -> s).sum());
    }

}
