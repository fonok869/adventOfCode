package com.fmolnar.code.year2023.day23;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23Remake {

    public static final Point UP = new Point(0, -1);
    public static final Point RIGHT = new Point(1, 0);
    public static final Point LEFT = new Point(-1, 0);
    public static final Point DOWN = new Point(0, 1);
    public static final Point START = new Point(1, 0);
    public static Point END;
    private Set<Point> notForest = new HashSet<>();
    private Set<Point> vertices = new HashSet<>();
    private Set<Point> right = new HashSet<>();
    private Set<Point> left = new HashSet<>();
    private Set<Point> up = new HashSet<>();
    private Set<Point> down = new HashSet<>();
    private Set<Point> directions = Set.of(DOWN, UP, RIGHT, LEFT);
    private Map<Point, List<AccessVertices>> verticesMap = new HashMap<>();
    private int xMax, yMax;
    Map<Point, Set<Point>> directionMap;
    Set<Integer> maxs = new HashSet<>();

    public static void main(String[] args) throws IOException {
        new Day23Remake().calculate();
    }

    private void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2023/day23/input.txt");
        xMax = lines.get(0).length();
        yMax = lines.size();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int index = 0; index < line.length(); index++) {
                char c = line.charAt(index);
                if (c == '.') {
                    notForest.add(new Point(index, y));
                }

                if (c == '>') {
                    right.add(new Point(index, y));
                    notForest.add(new Point(index, y));
                }

                if (c == '<') {
                    left.add(new Point(index, y));
                    notForest.add(new Point(index, y));
                }

                if (c == 'v') {
                    down.add(new Point(index, y));
                    notForest.add(new Point(index, y));
                }

                if (c == '^') {
                    up.add(new Point(index, y));
                    notForest.add(new Point(index, y));
                }
            }
        }

        directionMap = Map.of(RIGHT, down, LEFT, Stream.of(down, right).flatMap(Collection::stream).collect(Collectors.toSet()), UP, Stream.of(down, right).flatMap(Collection::stream).collect(Collectors.toSet()), DOWN, left);

        // Start
        vertices.add(START);

        // End
        END = new Point(xMax - 2, yMax - 1);
        vertices.add(END);

        calculateVertices();

        callculateVerticesMap();

        List<Point> points = new ArrayList<>();
        points.add(START);

        doDFS(START, points);

        System.out.println("Max: " + maxs.stream().mapToInt(s -> s).max().getAsInt());

        System.out.println("tata");

    }

    private void callculateVerticesMap() {
        for (Point vertice : vertices) {
            lepegetes(vertice);
        }
    }

    private void lepegetes(Point vertice) {
        Map<Point, List<Point>> sets = new HashMap<>();
        for (Point direction : directions) {
            Point p = new Point(vertice.x() + direction.x(), vertice.y() + direction.y());
            List<Point> pointsAlreadyVisited = new ArrayList<>();
            pointsAlreadyVisited.add(vertice);
            sets.put(p, pointsAlreadyVisited);


            if (notForest.contains(p)) {

//                if (directionMap.get(direction).contains(p)) {
//                    pointsAlreadyVisited.clear();
//                    continue;
//                }
                pointsAlreadyVisited.add(p);
                lepes(p, pointsAlreadyVisited);
            }
        }

        System.out.println("Vertice : " + vertice);
        List<AccessVertices> accessVertices = new ArrayList<>();
        for (Map.Entry<Point, List<Point>> entry : sets.entrySet()) {
            List<Point> pointsVisited = entry.getValue();
            if (pointsVisited.size() > 1) {
                accessVertices.add(new AccessVertices(pointsVisited.get(pointsVisited.size() - 1), pointsVisited.size() - 1));
                System.out.println("AccessVertices : " + new AccessVertices(pointsVisited.get(pointsVisited.size() - 1), pointsVisited.size() - 1));
            }
        }

        verticesMap.put(vertice, accessVertices);


    }

    private void doDFS(Point actual, List<Point> pointsAlreadyVisisted) {
        if (actual.equals(END)) {
            calculateDistance(pointsAlreadyVisisted);
            return;
        }
        for (AccessVertices access : verticesMap.get(actual)) {
            if (pointsAlreadyVisisted.contains(access.point())) {
                continue;
            }
            List<Point> alredyCopy = new ArrayList<>(pointsAlreadyVisisted);
            alredyCopy.add(access.point());
            doDFS(access.point(), alredyCopy);
        }

    }

    private void calculateDistance(List<Point> pointsAlreadyVisisted) {
        int sum = 0;
        for (int index = 0; index < pointsAlreadyVisisted.size() - 1; index++) {
            for (AccessVertices access : verticesMap.get(pointsAlreadyVisisted.get(index))) {
                if (access.point().equals(pointsAlreadyVisisted.get(index + 1))) {
                    sum += access.length();
                    break;
                }
            }
        }
        maxs.add(sum);

    }


    private List<Point> lepes(Point actualPoint, List<Point> pointsAlreadyVisited) {
        for (Point direction : directions) {
            Point newPoint = new Point(actualPoint.x() + direction.x(), actualPoint.y() + direction.y());
            if (pointsAlreadyVisited.contains(newPoint)) {
                continue;
            }

            if (vertices.contains(newPoint)) {
                pointsAlreadyVisited.add(newPoint);
                return List.of();
            }

            if (notForest.contains(newPoint)) {
//                if (directionMap.get(direction).contains(newPoint)) {
//                    pointsAlreadyVisited.clear();
//                    return List.of();
//                }
                pointsAlreadyVisited.add(newPoint);
                return lepes(newPoint, pointsAlreadyVisited);
            }
        }
        return pointsAlreadyVisited;
    }


    private void calculateVertices() {

        notForest.forEach(point -> {
            int presence = 0;
            for (Point direction : directions) {
                Point p = new Point(point.x() + direction.x(), point.y() + direction.y());
                if (notForest.contains(p)) {
                    presence++;
                }
            }
            if (2 < presence) {
                vertices.add(point);
            }

        });


    }
}

record Point(int x, int y) {
};

record AccessVertices(Point point, int length) {
}
