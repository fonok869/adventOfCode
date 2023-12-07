package com.fmolnar.code.year2021.day19;

import com.fmolnar.code.FileReaderUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {

    private static final int MAX_LIMIT = 1000;
    private static final int MIN_SAME_DISTANCE = 55;
    private static final int MIN_SAME_POINTS = 12;
    int[][] rotationMatrix3DX = {{1, 0, 0}, {0, 0, -1}, {0, 1, 0}};
    int[][] rotationMatrix3DY = {{0, 0, 1}, {0, 1, 0}, {-1, 0, 0}};
    int[][] rotationMatrix3DZ = {{0, -1, 0}, {1, 0, 0}, {0, 0, 1}};
    private final List<Point> allDirections = getAllDirections();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2021/day19/input.txt");
        Pattern pattern = Pattern.compile("(--- scanner (\\d+) ---)");

        Map<Integer, Scanner> scanners = new HashMap<>();
        Map<Integer, ScannerResolved> resolvedScanners = new HashMap<>();
        boolean scannerMesure = false;
        int number = 0;
        Set<Point> points = new HashSet<>();
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                scannerMesure = false;
                scanners.put(number, new Scanner(number, points, getAllManhattanDistances(points)));
                continue;
            } else if (line.startsWith("--- scanner")) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    number = Integer.valueOf(matcher.group(2));
                    System.out.println("Number: " + number);
                }

                scannerMesure = true;
                points = new HashSet<>();
                continue;
            } else if (scannerMesure) {
                String[] values = line.split(",");
                points.add(new Point(Integer.valueOf(values[0]), Integer.valueOf(values[1]), Integer.valueOf(values[2])));
            }
        }
        scanners.put(number, new Scanner(number, points, getAllManhattanDistances(points)));


        Point originPoint = new Point(0, 0, 0);
        Scanner scannerZero = scanners.get(0);

        resolvedScanners.put(scannerZero.numero, new ScannerResolved(scannerZero.numero, originPoint, scannerZero.points, getAllManhattanDistances(scannerZero.points)));

        while (scanners.size() != resolvedScanners.size()) {
            for (int i = 0; i < scanners.size(); i++) {
                if (resolvedScanners.keySet().contains(i)) {
                    continue;
                }
                Scanner scannerToCheck = scanners.get(i);
                originsCompare:
                for (Map.Entry<Integer, ScannerResolved> scannerResolved : resolvedScanners.entrySet()) {
                    ScannerResolved scannerOrigin = scannerResolved.getValue();
                    if (MIN_SAME_DISTANCE <= atLeastSameDistance(scannerOrigin, scannerToCheck)) {
                        // Rotation
                        Set<Scanner> allRotatedVersion = getAllRotatedScanners(scannerToCheck);
                        for (Scanner rotatedScanner : allRotatedVersion) {
                            if (MIN_SAME_DISTANCE <= atLeastSameDistance(scannerOrigin, rotatedScanner)) {
                                // A pontokbol visszamenolegesen megkapunk beacon pontot
                                //es utana azokat kell kiprobalni
                                Set<Point> rotatedTruncatedPoints = rotatedScanner.points.stream().limit(rotatedScanner.points.size() - 11).collect(Collectors.toSet());
                                try {
                                    Set<Point> alreadyTestedPoint = new HashSet<>();
                                    rotatedTruncatedPoints.forEach(
                                            rotatedPoint -> {
                                                scannerOrigin.points.forEach(
                                                        scannerOnePoint -> {
                                                            Point diff = new Point(scannerOnePoint.x - rotatedPoint.x, scannerOnePoint.y - rotatedPoint.y, scannerOnePoint.z - rotatedPoint.z);

                                                            Point toSearch = diff;
                                                            if (alreadyTestedPoint.contains(toSearch)) {
                                                                return;
                                                            }
                                                            alreadyTestedPoint.add(toSearch);
                                                            if ((2 * MAX_LIMIT < Math.abs(scannerOrigin.beaconPoint.x - toSearch.x)) ||
                                                                    (2 * MAX_LIMIT < Math.abs(scannerOrigin.beaconPoint.y - toSearch.y)) ||
                                                                    (2 * MAX_LIMIT < Math.abs(scannerOrigin.beaconPoint.z - toSearch.z))) {
                                                                return;
                                                            }

                                                            Set<Point> allTransformedPointsLimit = getAllTransformedPoint(rotatedTruncatedPoints, toSearch);
                                                            allTransformedPointsLimit.forEach(
                                                                    point -> {
                                                                        if (scannerOrigin.points.contains(point)) {
                                                                            Set<Point> allTransformedPoints = getAllTransformedPoint(rotatedScanner.points, toSearch);
                                                                            Collection<Point> samePoints = CollectionUtils.intersection(scannerOrigin.points, allTransformedPoints);
                                                                            if (MIN_SAME_POINTS <= samePoints.size()) {
                                                                                //if (Objects.equals(scannerOrigin.beaconPoint, originPoint)) {
                                                                                resolvedScanners.put(rotatedScanner.numero, new ScannerResolved(rotatedScanner.numero, toSearch, allTransformedPoints, rotatedScanner.allManhattanDistances));
                                                                                throw new RuntimeException("Out");
                                                                            }

                                                                        }
                                                                    }
                                                            );

                                                        }
                                                );

                                            }

                                    );


                                } catch (RuntimeException exception) {
                                    break originsCompare;
                                }
                            }
                        }
                    }
                }
            }
        }

        Set<Point> allBeacons = new HashSet<>();
        Set<Point> allScannersPosition = new HashSet<>();
        for (Map.Entry<Integer, ScannerResolved> scannerResolved : resolvedScanners.entrySet()) {
            allBeacons.addAll(scannerResolved.getValue().points);
            allScannersPosition.add(scannerResolved.getValue().beaconPoint);
        }

        Set<Point> allScannerPosition2 = new HashSet<>(allScannersPosition);
        Set<Point> allScannerPosition3 = new HashSet<>(allScannersPosition);
        List<Integer> integs = new ArrayList<>();
        for (Point pointToCheck : allScannerPosition2){
            allScannerPosition3.remove(pointToCheck);
            for(Point manhattanPoint : allScannerPosition3){
                int xDiff = Math.abs(pointToCheck.x - manhattanPoint.x);
                int yDiff = Math.abs(pointToCheck.y - manhattanPoint.y);
                int zDiff = Math.abs(pointToCheck.z - manhattanPoint.z);
                integs.add((xDiff+yDiff+zDiff));
            }
        }

        System.out.println("First: " + allBeacons.size());
        System.out.println("Second: " + integs.stream().mapToInt(s -> s).max().getAsInt());
    }

    private Set<Scanner> getAllRotatedScanners(Scanner scannerToCheck) {

        Set<Scanner> allRotatedScanners = new HashSet<>();
        getAllRotatedStartWithX(allRotatedScanners, scannerToCheck, rotationMatrix3DX, rotationMatrix3DY, rotationMatrix3DZ);
        getAllRotatedStartWithX(allRotatedScanners, scannerToCheck, rotationMatrix3DY, rotationMatrix3DZ, rotationMatrix3DX);
        getAllRotatedStartWithX(allRotatedScanners, scannerToCheck, rotationMatrix3DZ, rotationMatrix3DX, rotationMatrix3DY);
        return allRotatedScanners;

    }

    private void getAllRotatedStartWithX(Set<Scanner> allRotatedScanners, Scanner scannerToCheck, int[][] rotMat1, int[][] rotMat2, int[][] rotMat3) {
        Set<Scanner> allRotatedXScanners = new HashSet<>();
        Set<Scanner> allRotatedXYScanners = new HashSet<>();
        Set<Scanner> allRotatedXYZScanners = new HashSet<>();
        allRotatedXScanners.addAll(getAllRotatedScannersWithMatrix(scannerToCheck, rotMat1));
        allRotatedXScanners.forEach(
                scanner -> allRotatedXYScanners.addAll(getAllRotatedScannersWithMatrix(scanner, rotMat2))
        );
        allRotatedXYScanners.forEach(
                scanner -> allRotatedXYZScanners.addAll(getAllRotatedScannersWithMatrix(scanner, rotMat3))
        );
        allRotatedXYZScanners.forEach(
                scanner -> {
                    allDirections.forEach(
                            point -> allRotatedScanners.add(getInverseScanner(scanner, point))
                    );
                }
        );
    }

    private List<Point> getAllDirections() {
        List<Point> allDirections = new ArrayList<>();
        for (int i = -1; i < 2; i = i + 2) {
            for (int j = -1; j < 2; j = j + 2) {
                for (int k = -1; k < 2; k = k + 2) {
                    allDirections.add(new Point(i, j, k));
                }
            }
        }
        return allDirections;
    }

    private Scanner getInverseScanner(Scanner scannerToCheck, Point transform) {
        Set<Point> newPoints = new HashSet<>();
        scannerToCheck.points.forEach(p ->
                newPoints.add(new Point(p.x * transform.x, p.y * transform.y, p.z * transform.z))
        );
        return new Scanner(scannerToCheck.numero, newPoints, getAllManhattanDistances(newPoints));
    }

    private List<Scanner> getAllRotatedScannersWithMatrix(Scanner scannerToCheck, int[][] rotationMatrix3D) {
        List<Scanner> rotatedScanners = new ArrayList<>();
        Set<Point> pointsOrigin = new HashSet<>(scannerToCheck.points);
        for (int i = 0; i < 3; i++) {
            List<Point> rotatedPoints = new ArrayList<>();
            pointsOrigin.forEach(p -> {
                Point newPoint = rotation(rotationMatrix3D, p);
                rotatedPoints.add(newPoint);
            });
            pointsOrigin = new HashSet<>(rotatedPoints);
            rotatedScanners.add(new Scanner(scannerToCheck.numero, pointsOrigin, getAllManhattanDistances(pointsOrigin)));
        }
        rotatedScanners.add(new Scanner(scannerToCheck.numero, scannerToCheck.points, scannerToCheck.allManhattanDistances));
        return rotatedScanners;
    }


    private Set<Point> getAllTransformedPoint(Set<Point> points, Point point) {
        Set<Point> newPoints = new HashSet<>();
        points.forEach(
                p -> newPoints.add(new Point(p.x + point.x, p.y + point.y, p.z + point.z))
        );
        return newPoints;
    }

    private int atLeastSameDistance(ScannerResolved scanner, Scanner reference) {
        return CollectionUtils.intersection(scanner.allManhattanDistances, reference.allManhattanDistances).size();
    }

    private List<Integer> getAllManhattanDistances(Set<Point> points) {
        List<Integer> distances = new ArrayList<>();
        List<Point> pointsToTreat = new ArrayList<>(points);
        List<Point> pointsToTreat2 = new ArrayList<>(points);
        pointsToTreat.forEach(p -> {
                    pointsToTreat2.remove(p);
                    pointsToTreat2.forEach(p2 -> distances.add(Math.abs(p2.x - p.x) + Math.abs(p2.y - p.y) + Math.abs(p2.z - p.z)));
                }
        );

        Collections.sort(distances);
        return distances;
    }

    private Point rotation(int[][] rotationMatrix3D, Point p) {
        int x = p.x * rotationMatrix3D[0][0] + p.y * rotationMatrix3D[0][1] + p.z * rotationMatrix3D[0][2];
        int y = p.x * rotationMatrix3D[1][0] + p.y * rotationMatrix3D[1][1] + p.z * rotationMatrix3D[1][2];
        int z = p.x * rotationMatrix3D[2][0] + p.y * rotationMatrix3D[2][1] + p.z * rotationMatrix3D[2][2];
        return new Point(x, y, z);
    }

    record ScannerResolved(int numero, Point beaconPoint, Set<Point>points, List<Integer>allManhattanDistances) {  } ;

    record Scanner(int numero, Set<Point>points, List<Integer>allManhattanDistances) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Scanner scanner = (Scanner) o;
            return numero == scanner.numero &&
                    CollectionUtils.isEqualCollection(points, scanner.points);
        }
        @Override
        public int hashCode() {
            return Objects.hash(numero, points);
        }
    };

    record Point(int x, int y, int z) {  };
}
