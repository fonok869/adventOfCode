package com.fmolnar.code.year2022.day23;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23 {

    Set<Point> allPoints = new HashSet<Point>();
    int roundNumber = 0;
    List<Point> allNeighborsPoints = new ArrayList<>();
    List<List<Point>> directions = new ArrayList<>();

    public void calculate() throws IOException {
        addAllNeighborsPoints();
        getDirections();
        List<String> lines = FileReaderUtils.readFile("/2022/day23/input.txt");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    allPoints.add(new Point(i, j));
                }
            }
        }

        int lepes = 20000;

        List<Point> allActualPointsToDestroy = new ArrayList<>(allPoints);
        List<Point> allNextPointsList;
        Set<Point> allNextPointsSet;
        List<Point> allActualPointsBefore = new ArrayList<>(allPoints);
        for (int i = 0; i < lepes; i++) {
            final Set<Point> allActualPoints = new HashSet<>(allActualPointsBefore);
            //plotAll(allActualPoints);
            final int round = i;
            allNextPointsList = allActualPoints.stream().map(p -> new Step(p, allNeighborsPoints, allActualPoints, round, directions).nextStep()).collect(Collectors.toList());
//            plotAll(new HashSet<>(allNextPointsList));
            allNextPointsSet = new HashSet<>(allNextPointsList);

            if(allNextPointsSet.containsAll(allActualPoints)){
                System.out.println("Round: " + (round+1));
                break;
            }

            if (allNextPointsSet.size() == allActualPoints.size() && allNextPointsSet.containsAll(allActualPoints)) {
                allActualPointsBefore = new ArrayList<>(allNextPointsList);
                continue;
            } else {
                // Only remove one
                allNextPointsList = onlyRemoveOnce(allNextPointsList, allNextPointsSet);
                final List<Point> allNextPointsListToKeep = new ArrayList<>(allNextPointsList);

                List<Point> doNotMoved = allActualPoints.stream().filter(p -> allNextPointsListToKeep.contains(new Step(p, allNeighborsPoints, allActualPoints, round, directions).nextStep())).collect(Collectors.toList());
                List<Point> doMoved = allActualPoints.stream().filter(p -> !allNextPointsListToKeep.contains(new Step(p, allNeighborsPoints, allActualPoints, round, directions).nextStep())).map(p->new Step(p, allNeighborsPoints, allActualPoints, round, directions).nextStep()).collect(Collectors.toList());
                allActualPointsBefore = new ArrayList<>();
                if(doMoved.size()==0){
                    System.out.println("Round: " + round);
                    break;
                }
                allActualPointsBefore.addAll(doNotMoved);
                allActualPointsBefore.addAll(doMoved);
                continue;
            }
        }
    }

    private List<Point> onlyRemoveOnce(List<Point> allNextPointsList, Set<Point> allNextPointsSet) {
        List<Point> notRemoved = new ArrayList<>();
        List<Point> removed = new ArrayList<>();
        for (int i = 0; i < allNextPointsList.size(); i++) {
            Point p = allNextPointsList.get(i);
            if (allNextPointsSet.contains(p) && !removed.contains(p)) {
                removed.add(p);
            } else {
                notRemoved.add(p);
            }
        }
        return notRemoved;
    }

    private void plotAll(Set<Point> allActualPoints) {
        int minX = allActualPoints.stream().mapToInt(Point::x).min().getAsInt();
        int maxX = allActualPoints.stream().mapToInt(Point::x).max().getAsInt();
        int minY = allActualPoints.stream().mapToInt(Point::y).min().getAsInt();
        int maxY = allActualPoints.stream().mapToInt(Point::y).max().getAsInt();

        System.out.println("------------------------------------");
        for (int j = 0; j <= 12; j++) {
            System.out.println();
            for (int i = 0; i <= 14; i++) {
                if (allActualPoints.contains(new Point(j, i))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
        }
        System.out.println();
        System.out.println("------------------------------------");
    }

    private void getDirections() {
        directions = new ArrayList<>();

        // North
        List<Point> norths = new ArrayList<>();
        norths.addAll(Arrays.asList(new Point(-1, 0), new Point(-1, -1), new Point(-1, 1)));
        directions.add(norths);

        // South
        List<Point> souths = new ArrayList<>();
        souths.addAll(Arrays.asList(new Point(1, 0), new Point(1, -1), new Point(1, 1)));
        directions.add(souths);

        // WEST
        List<Point> wests = new ArrayList<>();
        wests.addAll(Arrays.asList(new Point(0, -1), new Point(-1, -1), new Point(1, -1)));
        directions.add(wests);

        // EAST
        List<Point> easts = new ArrayList<>();
        easts.addAll(Arrays.asList(new Point(0, 1), new Point(-1, 1), new Point(1, 1)));
        directions.add(easts);

    }

    private void addAllNeighborsPoints() {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)) {
                    allNeighborsPoints.add(new Point(i, j));
                }
            }
        }
    }

    record Point(int y, int x) {
    }

    ;

    record Step(Point before, List<Point>allNeighbours, Set<Point>allPointsNow, int roundNumber,
                List<List<Point>>directions) {
        Point nextStep() {

            // AllNeighbors
            List<Point> pointsAllDirection = allNeighbours.stream().map(n -> new Point(n.y + before.y, n.x + before.x)).filter(p -> !allPointsNow.contains(p)).collect(Collectors.toList());
            if (pointsAllDirection.size() == 8) {
                return before;
            }

            for (int i = roundNumber; i < roundNumber + 4; i++) {
                List<Point> pointsToCheck = directions.get(i % 4);
                List<Point> pointsFree = pointsToCheck.stream().map(d -> new Point(d.y + before.y, d.x + before.x)).filter(p -> !allPointsNow.contains(p)).collect(Collectors.toList());
                if (pointsFree.size() == 3) {
                    return new Point(before.y + pointsToCheck.get(0).y, before.x + pointsToCheck.get(0).x);
                }
            }


            //System.out.println("Nem kellene itt lennie");
            return before;
        }
    }

    ;
}
