package com.fmolnar.code.year2021.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day22Challenge02Beauty {

    public static final String commandX = "((-?\\d{1,6})..(-?\\d{1,6}))";
    private Pattern matcherX = Pattern.compile(commandX);

    public static final String commandY = "((-?\\d{1,6})..(-?\\d{1,6}))";
    private Pattern matcherY = Pattern.compile(commandY);

    public static final String commandZ = "((-?\\d{1,6})..(-?\\d{1,6}))";
    private Pattern matcherZ = Pattern.compile(commandZ);


    List<Rectangle> rectanglesOns = new ArrayList<>();
    List<Rectangle> rectangles = new ArrayList<>();


    //on x=10..12,y=10..12,z=10..12
    //on x=11..13,y=11..13,z=11..13

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day22/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String xGroup = line.substring(line.indexOf('x'), line.indexOf('y'));
                    String yGroup = line.substring(line.indexOf('y'), line.indexOf('z'));
                    String zGroup = line.substring(line.indexOf('z'));

                    Matcher matchX = matcherX.matcher(xGroup);
                    int x1 = 0;
                    int x2 = 0;
                    int y1 = 0;
                    int y2 = 0;
                    int z1 = 0;
                    int z2 = 0;

                    if (matchX.find()) {
                        x1 = Integer.valueOf(matchX.group(2));
                        x2 = Integer.valueOf(matchX.group(3));
                    }

                    Matcher matchY = matcherY.matcher(yGroup);
                    if (matchY.find()) {
                        y1 = Integer.valueOf(matchY.group(2));
                        y2 = Integer.valueOf(matchY.group(3));
                    }

                    Matcher matchZ = matcherZ.matcher(zGroup);
                    if (matchZ.find()) {
                        z1 = Integer.valueOf(matchZ.group(2));
                        z2 = Integer.valueOf(matchZ.group(3));
                    }


                    if (line.startsWith("on")) {
                        rectangles.add(new Rectangle(true, x1, x2, y1, y2, z1, z2));
                    } else {
                        rectangles.add(new Rectangle(false, x1, x2, y1, y2, z1, z2));
                    }
                }
            }
        }
        int counter = 0;
        long timeStamp = System.currentTimeMillis();
        for (Rectangle rectangleActualOne : rectangles) {
            List<Rectangle> rectangleOnesToTest = new ArrayList<>(rectanglesOns);

            System.out.println("Counter: " + counter++ + " Size of rectangleOnesToTest: " + rectangleOnesToTest.size() + " on: " + rectangleActualOne.on);
            System.out.println("Eltelt ido [s]: " + ((System.currentTimeMillis() - timeStamp) /1000));
            List<Rectangle> rectanlesOnesRemain = new ArrayList<>();
            final List<Rectangle> rectanglesActualRemain = new ArrayList<>(Collections.singleton(rectangleActualOne));

            rectangleOnesToTest.forEach(r -> {
                // Rectangles lehet szilankos
                // inside --> split
                List<Rectangle> newActuals = new ArrayList<>();
                rectanglesActualRemain.forEach(rectangleActual -> {
                    if (doesNotHaveCommonPoints(rectangleActual, r)) {
                        //System.out.println("doesNotHaveCommonPoints(rectangleActual, r)");
                        // Ha nincs kozos pont on-off mindegy --> elrakjuk (csak on-t szamoljuk)
                        rectanlesOnesRemain.add(r);
                    } else if (isInsidePointInR(r, rectangleActual)) {
                        // Mivel on-okon dolgozunk --> azokbol kihasitunk --> mindegy elrakjuk oket
                        //System.out.println("isInsidePointInR(r, rectangleActual)");
                        List<Point> pointsInside = pointInsideInR(rectangleActual, r);
                        // 1 pont
                        if (pointsInside.size() == 1) {
                            List<Point> borderPoints = calculateIntersectionPoints(r, rectangleActual, pointsInside);
                            Rectangle rectangleToRemove = calcultateRectangleToExclure(r.on, pointsInside.get(0), borderPoints);
                            List<Rectangle> rectanglesRestFromR = r.removeRectangleWithoutBorne(rectangleToRemove);
                            rectanlesOnesRemain.addAll(rectanglesRestFromR);
                        } // 2 pont
                        else if (pointsInside.size() == 2) {
                            List<Point> borderPoints = calculateIntersectionPoints(r, rectangleActual, pointsInside);
                            borderPoints.addAll(pointsInside);
                            Rectangle rectangleToRemove = r.calcultateRectangleToExclure(borderPoints);
                            List<Rectangle> rectanglesRestFromR = r.removeRectangleWithoutBorne(rectangleToRemove);
                            rectanlesOnesRemain.addAll(rectanglesRestFromR);
                        } // 4 pont
                        else if (pointsInside.size() == 4) {
                            List<Point> borderPoints = calculateIntersectionPoints(r, rectangleActual, pointsInside);
                            borderPoints.addAll(pointsInside);
                            Rectangle rectangleToRemove = r.calcultateRectangleToExclure(borderPoints);
                            List<Rectangle> rectanglesRestFromR = r.removeRectangleWithoutBorne(rectangleToRemove);
                            rectanlesOnesRemain.addAll(rectanglesRestFromR);
                        } // 8 pont
                        else if (pointsInside.size() == 8) {
                            List<Point> borderPoints = calculateIntersectionPoints(r, rectangleActual, pointsInside);
                            borderPoints.addAll(pointsInside);
                            Rectangle rectangleToRemove = r.calcultateRectangleToExclure(borderPoints);
                            List<Rectangle> rectanglesRestFromR = r.removeRectangleWithoutBorne(rectangleToRemove);
                            rectanlesOnesRemain.addAll(rectanglesRestFromR);
                        }

                    } // rectangleActual benne vannak pontok
                    else if (isInsidePointInR(rectangleActual, r)) {
                        // Azon dolgozunk, hogy amit epp vizsgalunk rectangelActual abbol ki kell hasitani
                        // Unicitas van, ha valahol van atfedes kihasithatunk, mert mashol nem lesz
                        List<Point> pointsInside = pointInsideInR(r, rectangleActual);

                        //System.out.println("!!!!isInsidePointInR(rectangleActual, r) - size: " + pointsInside.size());

                        // 1 pont
                        if (pointsInside.size() == 1) {
                            List<Point> borderPoints = calculateIntersectionPoints(rectangleActual, r, pointsInside);
                            Rectangle rectangleToRemove = calcultateRectangleToExclure(rectangleActual.on, pointsInside.get(0), borderPoints);
                            if (rectangleToRemove.on) {
                                rectanlesOnesRemain.add(rectangleToRemove);
                            }
                            List<Rectangle> rectanglesRestFromR = rectangleActual.removeRectangleWithoutBorne(rectangleToRemove);
                            newActuals.addAll(rectanglesRestFromR);
                        } // 2 pont
                        else if (pointsInside.size() == 2) {
                            List<Point> borderPoints = calculateIntersectionPoints(rectangleActual, r, pointsInside);
                            borderPoints.addAll(pointsInside);
                            Rectangle rectangleToRemove = rectangleActual.calcultateRectangleToExclure(borderPoints);
                            if (rectangleToRemove.on) {
                                rectanlesOnesRemain.add(rectangleToRemove);
                            }
                            List<Rectangle> rectanglesRestFromR = rectangleActual.removeRectangleWithoutBorne(rectangleToRemove);
                            newActuals.addAll(rectanglesRestFromR);
                        } // 4 pont
                        else if (pointsInside.size() == 4) {
                            List<Point> borderPoints = calculateIntersectionPoints(rectangleActual, r, pointsInside);
                            borderPoints.addAll(pointsInside);
                            Rectangle rectangleToRemove = rectangleActual.calcultateRectangleToExclure(borderPoints);
                            if (rectangleToRemove.on) {
                                rectanlesOnesRemain.add(rectangleToRemove);
                            }
                            List<Rectangle> rectanglesRestFromR = rectangleActual.removeRectangleWithoutBorne(rectangleToRemove);
                            newActuals.addAll(rectanglesRestFromR);
                        } // 8 pont
                        else if (pointsInside.size() == 8) {
                            List<Point> borderPoints = calculateIntersectionPoints(rectangleActual, r, pointsInside);
                            borderPoints.addAll(pointsInside);
                            Rectangle rectangleToRemove = rectangleActual.calcultateRectangleToExclure(borderPoints);
                            if (rectangleToRemove.on) {
                                rectanlesOnesRemain.add(rectangleToRemove);
                            }
                            List<Rectangle> rectanglesRestFromR = rectangleActual.removeRectangleWithoutBorne(rectangleToRemove);
                            newActuals.addAll(rectanglesRestFromR);
                        }

                    } // rectangleActual élek belemennek r-be --> kihasitas
                    else if (doesEnterToR(rectangleActual, r)) {
                        List<Point> intersectionPoints = doesEnterToRAndGetPoints(rectangleActual, r);
                        if (intersectionPoints.size() == 4) {
//                            System.out.println("RectangleActual : " + rectangleActual);
//                            System.out.println("r : " + r);
//                            System.out.println("--- 4-ben van Intersection points");
//                            intersectionPoints.forEach(System.out::println);
                            List<Point> borderPoints = calculateIntersectionPoints(r, rectangleActual, intersectionPoints);
                           // System.out.println("--- BorderPoints ");
                            //borderPoints.forEach(System.out::println);
                            borderPoints.addAll(intersectionPoints);
                            Rectangle rectangleToRemove = r.calcultateRectangleToExclure(borderPoints);
                            List<Rectangle> rectanglesRestFromR = r.removeRectangleWithoutBorne(rectangleToRemove);
                            rectanlesOnesRemain.addAll(rectanglesRestFromR);
                        }
                        // Kesz Rectangle
                        if (intersectionPoints.size() == 8) {
                            //System.out.println("--- 8-ben van Intersection points");
                            //intersectionPoints.forEach(System.out::println);
                            Rectangle rectangleToRemove = calcultateRectangleToExclure(rectangleActual.on, intersectionPoints);
                            List<Rectangle> rectanglesRestFromR = r.removeRectangleWithoutBorne(rectangleToRemove);
                            rectanlesOnesRemain.addAll(rectanglesRestFromR);
                        }
                        //System.out.println("!!!!!!!!!!isInsidePointInR(rectangleActual, r): " + intersectionPoints.size());
                    } else if (doesEnterToR(r, rectangleActual)) {// r bol élek -->  R actualba
                        System.out.println("!!!!!!!!!!doesEnterToR(r, rectangleActual)");
                    } else {
                        throw new RuntimeException("Not covered");
                    }
                });
                // Uj szilankos csekkeles (unicitas)
                if (!newActuals.isEmpty()) {
                    rectanglesActualRemain.clear();
                    rectanglesActualRemain.addAll(newActuals);
                }

            });

            rectanglesActualRemain.forEach(actual -> {
                if (actual.on) {
                    rectanlesOnesRemain.add(actual);
                }
            });


            // Ha siman nincs semmi es onnan akkor jo hozzadjuk
            rectanglesOns = new ArrayList<>(rectanlesOnesRemain);
            System.out.println("Day22Challenge02: " + rectanglesOns.stream().mapToLong(s -> s.size()).sum());
        }

    }

    // 608785599994040261
    // 2758514936282235
    //       2005776293634

    private boolean doesNotHaveCommonPoints(Rectangle rectangleActual, Rectangle r) {
        return !(doesEnterToR(rectangleActual, r) || doesEnterToR(r, rectangleActual));
    }

    private List<Point> doesEnterToRAndGetPoints(Rectangle rectangleActual, Rectangle r) {
        // A 8 csucspont nem lehet benne
        // 4 csucspont sem
        List<Point> pointsInteserctionInsideR = new ArrayList<>();

        parcourBorneXElore(rectangleActual, r, pointsInteserctionInsideR);
        parcourBorneXVissza(rectangleActual, r, pointsInteserctionInsideR);

        parcourBorneYElore(rectangleActual, r, pointsInteserctionInsideR);
        parcourBorneYVissza(rectangleActual, r, pointsInteserctionInsideR);

        parcourBorneZElore(rectangleActual, r, pointsInteserctionInsideR);
        parcourBorneZVissza(rectangleActual, r, pointsInteserctionInsideR);


        // Suppression of doubles
        Set<Point> points = pointsInteserctionInsideR.stream().collect(Collectors.toSet());

        System.out.println("Sout:  " + (isInsidePointInR(rectangleActual, r)));

        System.out.println("Points inside number: " + points.size());

        return new ArrayList<>(points);
    }


    private void parcourBorneXElore(Rectangle rectangleActual, Rectangle r, List<Point> pointsInteserctionInsideR) {
        boolean xInside1 = false;
        boolean xInside2 = false;
        boolean xInside3 = false;
        boolean xInside4 = false;
        for (int x = rectangleActual.x1; x <= rectangleActual.x2; x++) {
            Point x1P = new Point(x, rectangleActual.y1, rectangleActual.z1);
            xInside1 = isxPointInside(pointsInteserctionInsideR, xInside1, r, x1P);

            Point x2P = new Point(x, rectangleActual.y1, rectangleActual.z2);
            xInside2 = isxPointInside(pointsInteserctionInsideR, xInside2, r, x2P);

            Point x3P = new Point(x, rectangleActual.y2, rectangleActual.z1);
            xInside3 = isxPointInside(pointsInteserctionInsideR, xInside3, r, x3P);

            Point x4P = new Point(x, rectangleActual.y2, rectangleActual.z2);
            xInside4 = isxPointInside(pointsInteserctionInsideR, xInside4, r, x4P);
        }
    }

    private void parcourBorneYElore(Rectangle rectangleActual, Rectangle r, List<Point> pointsInteserctionInsideR) {
        boolean yInside1 = false;
        boolean yInside2 = false;
        boolean yInside3 = false;
        boolean yInside4 = false;
        for (int y = rectangleActual.y1; y <= rectangleActual.y2; y++) {
            Point x1P = new Point(rectangleActual.x1, y, rectangleActual.z1);
            yInside1 = isxPointInside(pointsInteserctionInsideR, yInside1, r, x1P);

            Point x2P = new Point(rectangleActual.x1, y, rectangleActual.z2);
            yInside2 = isxPointInside(pointsInteserctionInsideR, yInside2, r, x2P);

            Point x3P = new Point(rectangleActual.x2, y, rectangleActual.z1);
            yInside3 = isxPointInside(pointsInteserctionInsideR, yInside3, r, x3P);

            Point x4P = new Point(rectangleActual.x2, y, rectangleActual.z2);
            yInside4 = isxPointInside(pointsInteserctionInsideR, yInside4, r, x4P);
        }
    }

    private void parcourBorneZElore(Rectangle rectangleActual, Rectangle r, List<Point> pointsInteserctionInsideR) {
        boolean zInside1 = false;
        boolean zInside2 = false;
        boolean zInside3 = false;
        boolean zInside4 = false;
        for (int z = rectangleActual.z1; z <= rectangleActual.z2; z++) {
            Point x1P = new Point(rectangleActual.x1, rectangleActual.y1, z);
            zInside1 = isxPointInside(pointsInteserctionInsideR, zInside1, r, x1P);

            Point x2P = new Point(rectangleActual.x1, rectangleActual.y2, z);
            zInside2 = isxPointInside(pointsInteserctionInsideR, zInside2, r, x2P);

            Point x3P = new Point(rectangleActual.x2, rectangleActual.y1, z);
            zInside3 = isxPointInside(pointsInteserctionInsideR, zInside3, r, x3P);

            Point x4P = new Point(rectangleActual.x2, rectangleActual.y2, z);
            zInside4 = isxPointInside(pointsInteserctionInsideR, zInside4, r, x4P);
        }
    }

    private boolean isxPointInside(List<Point> pointsInteserctionInsideR, boolean inside, Rectangle r, Point p) {
        if (inside != r.isInside(p) && inside == false) {
            pointsInteserctionInsideR.add(p);
            inside = true;
        }
        return inside;
    }

    private void parcourBorneXVissza(Rectangle rectangleActual, Rectangle r, List<Point> pointsInteserctionInsideR) {
        boolean xInside1 = false;
        boolean xInside2 = false;
        boolean xInside3 = false;
        boolean xInside4 = false;
        for (int x = rectangleActual.x2; rectangleActual.x1 <= x; x--) {
            Point x1P = new Point(x, rectangleActual.y1, rectangleActual.z1);
            xInside1 = isxPointInside(pointsInteserctionInsideR, xInside1, r, x1P);

            Point x2P = new Point(x, rectangleActual.y1, rectangleActual.z2);
            xInside2 = isxPointInside(pointsInteserctionInsideR, xInside2, r, x2P);

            Point x3P = new Point(x, rectangleActual.y2, rectangleActual.z1);
            xInside3 = isxPointInside(pointsInteserctionInsideR, xInside3, r, x3P);

            Point x4P = new Point(x, rectangleActual.y2, rectangleActual.z2);
            xInside4 = isxPointInside(pointsInteserctionInsideR, xInside4, r, x4P);
        }
    }

    private void parcourBorneYVissza(Rectangle rectangleActual, Rectangle r, List<Point> pointsInteserctionInsideR) {
        boolean yInside1 = false;
        boolean yInside2 = false;
        boolean yInside3 = false;
        boolean yInside4 = false;
        for (int y = rectangleActual.y2; rectangleActual.y1 <= y; y--) {
            Point x1P = new Point(rectangleActual.x1, y, rectangleActual.z1);
            yInside1 = isxPointInside(pointsInteserctionInsideR, yInside1, r, x1P);

            Point x2P = new Point(rectangleActual.x1, y, rectangleActual.z2);
            yInside2 = isxPointInside(pointsInteserctionInsideR, yInside2, r, x2P);

            Point x3P = new Point(rectangleActual.x2, y, rectangleActual.z1);
            yInside3 = isxPointInside(pointsInteserctionInsideR, yInside3, r, x3P);

            Point x4P = new Point(rectangleActual.x2, y, rectangleActual.z2);
            yInside4 = isxPointInside(pointsInteserctionInsideR, yInside4, r, x4P);
        }
    }

    private void parcourBorneZVissza(Rectangle rectangleActual, Rectangle r, List<Point> pointsInteserctionInsideR) {
        boolean zInside1 = false;
        boolean zInside2 = false;
        boolean zInside3 = false;
        boolean zInside4 = false;
        for (int z = rectangleActual.z2; rectangleActual.z1 <= z; z--) {
            Point x1P = new Point(rectangleActual.x1, rectangleActual.y1, z);
            zInside1 = isxPointInside(pointsInteserctionInsideR, zInside1, r, x1P);

            Point x2P = new Point(rectangleActual.x1, rectangleActual.y2, z);
            zInside2 = isxPointInside(pointsInteserctionInsideR, zInside2, r, x2P);

            Point x3P = new Point(rectangleActual.x2, rectangleActual.y1, z);
            zInside3 = isxPointInside(pointsInteserctionInsideR, zInside3, r, x3P);

            Point x4P = new Point(rectangleActual.x2, rectangleActual.y2, z);
            zInside4 = isxPointInside(pointsInteserctionInsideR, zInside4, r, x4P);
        }
    }


    private boolean doesEnterToR(Rectangle rectangleActual, Rectangle r) {
        boolean xInside = false;
        for (int x = rectangleActual.x1; x <= rectangleActual.x2; x++) {
            xInside = xInside || r.isInside(new Point(x, rectangleActual.y1, rectangleActual.z1));
            xInside = xInside || r.isInside(new Point(x, rectangleActual.y1, rectangleActual.z2));
            xInside = xInside || r.isInside(new Point(x, rectangleActual.y2, rectangleActual.z1));
            xInside = xInside || r.isInside(new Point(x, rectangleActual.y2, rectangleActual.z2));
            if (xInside) {
                break;
            }
        }

        boolean yInside = false;
        for (int y = rectangleActual.y1; y <= rectangleActual.y2; y++) {
            yInside = yInside || r.isInside(new Point(rectangleActual.x1, y, rectangleActual.z1));
            yInside = yInside || r.isInside(new Point(rectangleActual.x2, y, rectangleActual.z1));
            yInside = yInside || r.isInside(new Point(rectangleActual.x1, y, rectangleActual.z2));
            yInside = yInside || r.isInside(new Point(rectangleActual.x2, y, rectangleActual.z2));
            if (yInside) {
                break;
            }
        }

        boolean zInside = false;
        for (int z = rectangleActual.z1; z <= rectangleActual.z2; z++) {
            zInside = zInside || r.isInside(new Point(rectangleActual.x1, rectangleActual.y1, z));
            zInside = zInside || r.isInside(new Point(rectangleActual.x1, rectangleActual.y2, z));
            zInside = zInside || r.isInside(new Point(rectangleActual.x2, rectangleActual.y1, z));
            zInside = zInside || r.isInside(new Point(rectangleActual.x2, rectangleActual.y2, z));
            if (zInside) {
                break;
            }
        }

        return xInside || yInside || zInside;
    }

    protected List<Rectangle> sanitaize0Rectangles(List<Rectangle> rectanglesToSanitize) {
        List<Rectangle> sanitized = new ArrayList<>();
        // Rectangles with 0 volume --> sanitized
        rectanglesToSanitize.forEach(r -> {
            if (r.x1 != r.x2 && r.y1 != r.y2 && r.z1 != r.z2) {
                sanitized.add(r);
            }
        });
        return sanitized;
    }

    private List<Rectangle> removeRectangleWithoutBorne(Rectangle rVesszo, Rectangle r) {
        // TODO 1 réteggel kisebb téglatest kell
        List<Rectangle> rRestants = new ArrayList<>();
        rRestants.add(new Rectangle(r.on, r.x1, Math.max(r.x1, rVesszo.x1 - 1), r.y1, r.y2, r.z1, r.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, r.x2, r.y1, r.y2, r.z1, Math.max(rVesszo.z1 - 1, r.z1)));
        rRestants.add(new Rectangle(r.on, Math.min(rVesszo.x2 + 1, r.x2), r.x2, r.y1, r.y2, rVesszo.z1, r.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, rVesszo.x2, r.y1, r.y2, Math.min(rVesszo.z2 + 1, r.z2), r.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, rVesszo.x2, r.y1, Math.max(rVesszo.y1 - 1, r.y1), rVesszo.z1, rVesszo.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, rVesszo.x2, Math.min(rVesszo.y2 + 1, r.y2), r.y2, rVesszo.z1, rVesszo.z2));
        return rRestants;
    }

    private List<Rectangle> removeRectangleWithBorne(Rectangle rVesszo, Rectangle r) {
        // TODO 1 réteggel kisebb téglatest kell
        List<Rectangle> rRestants = new ArrayList<>();
        rRestants.add(new Rectangle(r.on, r.x1, rVesszo.x1, r.y1, r.y2, r.z1, r.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, r.x2, r.y1, r.y2, r.z1, rVesszo.z1));
        rRestants.add(new Rectangle(r.on, rVesszo.x2, r.x2, r.y1, r.y2, rVesszo.z1, r.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, rVesszo.x2, r.y1, r.y2, rVesszo.z2, r.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, rVesszo.x2, r.y1, rVesszo.y1, rVesszo.z1, rVesszo.z2));
        rRestants.add(new Rectangle(r.on, rVesszo.x1, rVesszo.x2, rVesszo.y2, r.y2, rVesszo.z1, rVesszo.z2));
        return rRestants;
    }

    private List<Point> calculateIntersectionPoints(Rectangle r, Rectangle rectangleActual, List<Point> pointsInside) {
        List<Point> pointsBorder = new ArrayList<>();
        for (Point point : pointsInside) {
            determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(r.x1(), point.y(), point.z()));
            determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(r.x2(), point.y(), point.z()));
            determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), r.y1(), point.z()));
            determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), r.y2(), point.z()));
            determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), point.y(), r.z1()));
            determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), point.y(), r.z2()));
        }
        return pointsBorder;
    }

    protected Rectangle calcultateRectangleToExclure(boolean on, List<Point> calculateIntersectionPoints) {
        Set<Integer> xLines = new HashSet<>();
        Set<Integer> yLines = new HashSet<>();
        Set<Integer> zLines = new HashSet<>();
        for (Point pointToExamine : calculateIntersectionPoints) {
            xLines.add(pointToExamine.x);
            yLines.add(pointToExamine.y);
            zLines.add(pointToExamine.z);
        }
        if (xLines.size() != 2 || yLines.size() != 2 || zLines.size() != 2) {
            throw new RuntimeException("Nincs eleg adat a teglatest kiszamitasahoz");
        }

        List<Integer> xLinesA = new ArrayList<>(xLines);
        List<Integer> yLinesA = new ArrayList<>(yLines);
        List<Integer> zLinesA = new ArrayList<>(zLines);
        Collections.sort(xLinesA);
        Collections.sort(yLinesA);
        Collections.sort(zLinesA);
        return new Rectangle(on, xLinesA.get(0), xLinesA.get(1), yLinesA.get(0), yLinesA.get(1), zLinesA.get(0), zLinesA.get(1));
    }

    protected Rectangle calcultateRectangleToExclure(boolean on, Point point, List<Point> calculateIntersectionPoints) {
        int i = point.x();
        int j = point.y();
        int k = point.z();
        boolean iPassed = false;
        boolean jPassed = false;
        boolean kPassed = false;
        for (Point point1 : calculateIntersectionPoints) {
            if (point1.x() != i && !iPassed) {
                i = point1.x();
                iPassed = true;
            }
            if (point1.y() != j && !jPassed) {
                j = point1.y();
                jPassed = true;
            }
            if (point1.z() != k && !kPassed) {
                k = point1.z();
                kPassed = true;
            }
        }
        int x1 = point.x < i ? point.x : i;
        int x2 = point.x < i ? i : point.x;
        int y1 = point.y < j ? point.y : j;
        int y2 = point.y < j ? j : point.y;
        int z1 = point.z < k ? point.z : k;
        int z2 = point.z < k ? k : point.z;

        return new Rectangle(on, x1, x2, y1, y2, z1, z2);
    }


    private void determineCalculateBorder(Rectangle rectangleActual, Rectangle r, List<Point> pointsBorder, Point pointInside) {
        if (rectangleActual.isInside(pointInside) && r.isInside(pointInside)) {
            pointsBorder.add(pointInside);
        }
    }

    protected List<Point> pointInsideInR(Rectangle rectangleActual, Rectangle r) {
        Set<Point> points = new HashSet<>();
        for (int i : Arrays.asList(rectangleActual.x1(), rectangleActual.x2())) {
            for (int j : Arrays.asList(rectangleActual.y1(), rectangleActual.y2())) {
                for (int k : Arrays.asList(rectangleActual.z1(), rectangleActual.z2())) {
                    if (r.isInside(new Point(i, j, k))) {
                        points.add(new Point(i, j, k));
                    }
                }
            }
        }
        return new ArrayList<>(points);

    }

    protected boolean isInsidePointInR(Rectangle r, Rectangle rectangleActual) {
        // Do not have point inside
        if (!isOneXInside(r, rectangleActual.x1(), rectangleActual.x2())) {
            return false;
        }

        if (!isOneYInside(r, rectangleActual.y1(), rectangleActual.y2())) {
            return false;
        }

        if (!isOneZInside(r, rectangleActual.z1(), rectangleActual.z2())) {
            return false;
        }

        return true;
    }

    private boolean isOneZInside(Rectangle r1, int r2z1, int r2z2) {
        return isZInside(r1, r2z1) || isZInside(r1, r2z2);
    }

    private boolean isOneYInside(Rectangle r1, int r2y1, int r2y2) {
        return isYInside(r1, r2y1) || isYInside(r1, r2y2);
    }

    protected boolean isOneXInside(Rectangle r1, int r2x1, int r2x2) {
        return isXInside(r1, r2x1) || isXInside(r1, r2x2);
    }

    protected static boolean isXInside(Rectangle r1, int x) {
        return r1.x1 <= x && x <= r1.x2;
    }

    protected static boolean isYInside(Rectangle r1, int y) {
        return r1.y1 <= y && y <= r1.y2;
    }

    private static boolean isZInside(Rectangle r1, int z) {
        return r1.z1 <= z && z <= r1.z2;
    }

    public static record Rectangle(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {

        public Rectangle newRectangle(int x1Plus, int x2Plus, int y1Plus, int y2Plus, int z1Plus, int z2Plus) {
            return new Rectangle(this.on(), x1() + x1Plus, x2() + x2Plus, y1() + y1Plus, y2() + y2Plus, z1() + z1Plus, z2() + z2Plus);
        }

        protected Rectangle calcultateRectangleToExclure(List<Point> calculateIntersectionPoints) {
            Set<Integer> xLines = new HashSet<>();
            Set<Integer> yLines = new HashSet<>();
            Set<Integer> zLines = new HashSet<>();
            calculateIntersectionPoints.forEach(pointToExamine ->
            {
                xLines.add(pointToExamine.x);
                yLines.add(pointToExamine.y);
                zLines.add(pointToExamine.z);
            });
            if (xLines.size() != 2 || yLines.size() != 2 || zLines.size() != 2) {
                throw new RuntimeException("Nincs eleg adat a teglatest kiszamitasahoz");
            }

            List<Integer> xLinesA = new ArrayList<>(xLines);
            List<Integer> yLinesA = new ArrayList<>(yLines);
            List<Integer> zLinesA = new ArrayList<>(zLines);
            Collections.sort(xLinesA);
            Collections.sort(yLinesA);
            Collections.sort(zLinesA);
            return new Rectangle(on, xLinesA.get(0), xLinesA.get(1), yLinesA.get(0), yLinesA.get(1), zLinesA.get(0), zLinesA.get(1));
        }

        private List<Rectangle> removeRectangleWithoutBorne(Rectangle rVesszo) {
            // TODO 1 réteggel kisebb téglatest kell
            List<Rectangle> rRestants = new ArrayList<>();
            rRestants.add(new Rectangle(on, x1, Math.max(x1, rVesszo.x1 - 1), y1, y2, z1, z2));
            rRestants.add(new Rectangle(on, rVesszo.x1, x2, y1, y2, z1, Math.max(rVesszo.z1 - 1, z1)));
            rRestants.add(new Rectangle(on, Math.min(rVesszo.x2 + 1, x2), x2, y1, y2, rVesszo.z1, z2));
            rRestants.add(new Rectangle(on, rVesszo.x1, rVesszo.x2, y1, y2, Math.min(rVesszo.z2 + 1, z2), z2));
            rRestants.add(new Rectangle(on, rVesszo.x1, rVesszo.x2, y1, Math.max(rVesszo.y1 - 1, y1), rVesszo.z1, rVesszo.z2));
            rRestants.add(new Rectangle(on, rVesszo.x1, rVesszo.x2, Math.min(rVesszo.y2 + 1, y2), y2, rVesszo.z1, rVesszo.z2));
            List<Rectangle> sanitized = new ArrayList<>();
            // Rectangles with 0 volume --> sanitized
            rRestants.forEach(r -> {
                if (r.x1 != r.x2 && r.y1 != r.y2 && r.z1 != r.z2) {
                    sanitized.add(r);
                }
            });
            return sanitized;
        }

        public long size() {
            return 1L * (x2 - x1) * (y2 - y1) * (z2 - z1);
        }

        public boolean isInside(Point p) {
            return isXInside(this, p.x()) && isYInside(this, p.y()) && isZInside(this, p.z());
        }

        @Override
        public String toString() {
            return "new Rectangle(" + on + ", " + x1 + ", " + x2 + ", " + y1 + ", " + y2 + ", " + z1 + ", " + z2 + "),";
        }
    }

    public static record Point(int x, int y, int z) {

    }
}
