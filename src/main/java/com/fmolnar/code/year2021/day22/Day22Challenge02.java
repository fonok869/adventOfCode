package com.fmolnar.code.year2021.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22Challenge02 {

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

        int counter = 1;
        for (Rectangle rectangleActual : rectangles) {
            System.out.println("Sor: " + counter);
            counter++;
            List<Rectangle> rectangleListOn = new ArrayList<>(rectanglesOns);
            List<Rectangle> rectanglesStay = new ArrayList<>();
            for (Rectangle r : rectangleListOn) {
                if (doesNotHaveCommonPart(rectangleActual, r)) {
                    rectanglesStay.add(r);
                    //System.out.println("Pas de correspondance");
                } else {
                    List<Point> pointsInside = pointInsideInR(rectangleActual, r);

                    System.out.println("Inside: " + pointsInside.size());
                    if (pointsInside.size() == 0) {
                        System.out.println("0 Points");
                        // Inverse
                        List<Point> pointsInsideInverse = pointInsideInR(r,rectangleActual);
                        System.out.println("Inverse: " + pointsInsideInverse.size());
                    } else if (pointsInside.size() == 1) {
                        System.out.println("1 Points");
                        Rectangle rectangleToRemove = calcultateRectangleToExclure(r.on, pointsInside.get(0), calculateIntersectionPoints(rectangleActual, r, pointsInside.get(0)));
                        List<Rectangle> removeRectangleWithoutBorne = removeRectangleWithoutBorne(r.on, rectangleToRemove, r);
                        rectanglesStay.addAll(sanitaize0Rectangles(removeRectangleWithoutBorne));
                    } else if (pointsInside.size() == 2) {
                        System.out.println("2 Points");
                        List<Rectangle> rectangleStayStep2 = getRectanglesForStep2(rectangleActual, r, pointsInside);
                        rectanglesStay.addAll(sanitaize0Rectangles(rectangleStayStep2));
                    } else if (pointsInside.size() == 4) {
                        System.out.println("4 Points");
                    } else if (pointsInside.size() == 8) {
                        System.out.println("8 Points");
                    } else {
                        throw new RuntimeException("Points size: " + pointsInside.size());
                    }


                }
            }
            if(rectangleActual.on){
                rectanglesStay.add(rectangleActual);
            }
            rectanglesOns = new ArrayList<>(rectanglesStay);
        }
        System.out.println("Day22Challenge02: " + rectanglesOns.stream().mapToLong(s->(Math.abs(s.x2()-s.x1())) * Math.abs(s.y2()-s.y1()) * Math.abs(s.z2()-s.z1())).sum());
    }

    protected List<Rectangle> sanitaize0Rectangles(List<Rectangle> rectanglesToSanitize){
        List<Rectangle> sanitized = new ArrayList<>();
        // Rectangles with 0 volume --> sanitized
        rectanglesToSanitize.forEach(r->{
            if(r.x1!=r.x2 && r.y1!=r.y2 && r.z1!=r.z2){
                sanitized.add(r);
            }
        });
        return sanitized;
    }




    protected List<Rectangle> getRectanglesForStep2(Rectangle rectangleActual, Rectangle r, List<Point> pointsInside) {
        List<Rectangle> rectangleStayStep2 = new ArrayList<>();
        Point p1 = pointsInside.get(0);
        Point p2 = pointsInside.get(1);
        List<Point> interSectionsP1 = calculateIntersectionPoints(rectangleActual, r, p1);
        List<Point> interSectionsP2 = calculateIntersectionPoints(rectangleActual, r, p2);
        Point p1Intersection = null;
        Rectangle rectangleToKeepWithoutBorne = null;
        if (p1.x != p2.x && p1.y == p2.y && p1.z == p2.z) {
            if (p1.x < p2.x) {
                p1Intersection = new Point(r.x2, p1.y, p1.z);
                Rectangle rectangleToKeep = getSmallRectangle(r.on, interSectionsP2, p2, p1Intersection);
                rectangleToKeepWithoutBorne = rectangleToKeep.newRectangle(1, 0, 0, 0, 0, 0);
            } else {
                p1Intersection = new Point(r.x1, p1.y, p1.z);
                Rectangle rectangleToKeep = getSmallRectangle(r.on, interSectionsP2, p2, p1Intersection);
                rectangleToKeepWithoutBorne = rectangleToKeep.newRectangle(0, -1, 0, 0, 0, 0);
            }
        } else if (p1.y != p2.y && p1.x == p2.x && p1.z == p2.z) {
            if (p1.y < p2.y) {
                p1Intersection = new Point(p1.x, r.y2, p1.z);
                Rectangle rectangleToKeep = getSmallRectangle(r.on, interSectionsP2, p2, p1Intersection);
                rectangleToKeepWithoutBorne = rectangleToKeep.newRectangle(0, 0, 1, 0, 0, 0);
            } else {
                p1Intersection = new Point(p1.x, r.y1, p1.z);
                Rectangle rectangleToKeep = getSmallRectangle(r.on, interSectionsP2, p2, p1Intersection);
                rectangleToKeepWithoutBorne = rectangleToKeep.newRectangle(0, 0, 0, -1, 0, 0);
            }
        } else if (p1.z != p2.z && p1.x == p2.x && p1.y == p2.y) {
            if (p1.z < p2.z) {
                p1Intersection = new Point(p1.x, p1.y, r.z2);
                Rectangle rectangleToKeep = getSmallRectangle(r.on, interSectionsP2, p2, p1Intersection);
                rectangleToKeepWithoutBorne = rectangleToKeep.newRectangle(0, 0, 0, 0, 1, 0);
            } else {
                p1Intersection = new Point(p1.x, p1.y, r.z1);
                Rectangle rectangleToKeep = getSmallRectangle(r.on, interSectionsP2, p2, p1Intersection);
                rectangleToKeepWithoutBorne = rectangleToKeep.newRectangle(0, 0, 0, 0, 0, -1);
            }
        }
        interSectionsP1.add(p1Intersection);
        rectangleStayStep2.add(rectangleToKeepWithoutBorne);
        Rectangle rectangleToRemove1 = calcultateRectangleToExclure(r.on, p1, interSectionsP1);
        List<Rectangle> removeRectangleWithoutBorne1 = removeRectangleWithoutBorne(r.on, rectangleToRemove1, r);
        rectangleStayStep2.addAll(removeRectangleWithoutBorne1);
        return rectangleStayStep2;
    }

    private Rectangle getSmallRectangle(boolean isOn, List<Point> interSectionsP2, Point p2, Point p1Intersection) {
        interSectionsP2.add(p1Intersection);
        return calcultateRectangleToExclure(isOn, p2, interSectionsP2);
    }

    protected List<Rectangle> removeRectangleWithoutBorne(boolean isOn, Rectangle rectangleToRemove, Rectangle r) {
        List<Integer> xCoordinates = Arrays.asList(rectangleToRemove.x1, rectangleToRemove.x2, r.x1, r.x2);
        List<Integer> yCoordinates = Arrays.asList(rectangleToRemove.y1, rectangleToRemove.y2, r.y1, r.y2);
        List<Integer> zCoordinates = Arrays.asList(rectangleToRemove.z1, rectangleToRemove.z2, r.z1, r.z2);
        Collections.sort(xCoordinates);
        Collections.sort(yCoordinates);
        Collections.sort(zCoordinates);
        boolean isX1Inside = xCoordinates.get(0) == xCoordinates.get(1);
        boolean isY1Inside = yCoordinates.get(0) == yCoordinates.get(1);
        boolean isZ1Inside = zCoordinates.get(0) == zCoordinates.get(1);
        // 2 Azonos van
        int xMiddle = isX1Inside ? xCoordinates.get(2) : xCoordinates.get(1);
        int yMiddle = isY1Inside ? yCoordinates.get(2) : yCoordinates.get(1);
        int zMiddle = isZ1Inside ? zCoordinates.get(2) : zCoordinates.get(1);

        return findRectanglesWithoutBorne(isOn, r, isX1Inside, isY1Inside, isZ1Inside, xMiddle, yMiddle, zMiddle);
    }

    protected List<Rectangle> removeRectangle(boolean isOn, Rectangle rectangleToRemove, Rectangle r) {
        List<Integer> xCoordinates = Arrays.asList(rectangleToRemove.x1, rectangleToRemove.x2, r.x1, r.x2);
        List<Integer> yCoordinates = Arrays.asList(rectangleToRemove.y1, rectangleToRemove.y2, r.y1, r.y2);
        List<Integer> zCoordinates = Arrays.asList(rectangleToRemove.z1, rectangleToRemove.z2, r.z1, r.z2);
        Collections.sort(xCoordinates);
        Collections.sort(yCoordinates);
        Collections.sort(zCoordinates);
        boolean isX1Inside = xCoordinates.get(0) == xCoordinates.get(1);
        boolean isY1Inside = yCoordinates.get(0) == yCoordinates.get(1);
        boolean isZ1Inside = zCoordinates.get(0) == zCoordinates.get(1);
        // 2 Azonos van
        int xMiddle = isX1Inside ? xCoordinates.get(2) : xCoordinates.get(1);
        int yMiddle = isY1Inside ? yCoordinates.get(2) : yCoordinates.get(1);
        int zMiddle = isZ1Inside ? zCoordinates.get(2) : zCoordinates.get(1);

        return findRectangles(isOn, r, isX1Inside, isY1Inside, isZ1Inside, xMiddle, yMiddle, zMiddle);
    }

    private List<Rectangle> findRectanglesWithoutBorne(boolean isOn, Rectangle r, boolean isX1Inside, boolean isY1Inside, boolean isZ1Inside, int xMiddle, int yMiddle, int zMiddle) {
        List<Rectangle> rectangles = new ArrayList<>();
        if (isX1Inside) {
            rectangles.add(new Rectangle(isOn, Math.min(xMiddle + 1, r.x2), r.x2, r.y1, r.y2, r.z1, r.z2));
            if (isY1Inside) {
                rectangles.add(new Rectangle(isOn, r.x1, xMiddle, Math.min(yMiddle + 1,r.y2) , r.y2, r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, yMiddle, Math.min(zMiddle + 1,r.z2) , r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, yMiddle, r.z1, Math.max(zMiddle - 1, r.z1)));
                }
            } else {
                rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, Math.max(yMiddle - 1,r.y1) , r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, yMiddle, r.y2, Math.min(zMiddle + 1, r.z2), r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, yMiddle, r.y2, r.z1, Math.max(zMiddle - 1, r.z1)));
                }
            }
        } else {
            rectangles.add(new Rectangle(isOn, r.x1, Math.max(xMiddle - 1, r.x1), r.y1, r.y2, r.z1, r.z2));
            if (isY1Inside) {
                rectangles.add(new Rectangle(isOn, xMiddle, r.x2, Math.min(yMiddle + 1, r.y2), r.y2, r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, yMiddle, Math.min(zMiddle + 1, r.z2), r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, yMiddle, r.z1, Math.max(zMiddle - 1, r.z1)));
                }
            } else {
                rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, Math.max(yMiddle - 1, r.y1), r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, yMiddle, r.y2, Math.min(zMiddle + 1, r.z2), r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, yMiddle, r.y2, r.z1, Math.max(zMiddle - 1, r.z1)));
                }
            }
        }
        return rectangles;
    }

    private List<Rectangle> findRectangles(boolean isOn, Rectangle r, boolean isX1Inside, boolean isY1Inside, boolean isZ1Inside, int xMiddle, int yMiddle, int zMiddle) {
        List<Rectangle> rectangles = new ArrayList<>();
        if (isX1Inside) {
            rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, r.y2, r.z1, r.z2));
            if (isY1Inside) {
                rectangles.add(new Rectangle(isOn, r.x1, xMiddle, yMiddle, r.y2, r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, yMiddle, zMiddle, r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, yMiddle, r.z1, zMiddle));
                }
            } else {
                rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, yMiddle, r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, yMiddle, r.y2, zMiddle, r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, r.x1, xMiddle, yMiddle, r.y2, r.z1, zMiddle));
                }
            }
        } else {
            rectangles.add(new Rectangle(isOn, r.x1, xMiddle, r.y1, r.y2, r.z1, r.z2));
            if (isY1Inside) {
                rectangles.add(new Rectangle(isOn, xMiddle, r.x2, yMiddle, r.y2, r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, yMiddle, zMiddle, r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, yMiddle, r.z1, zMiddle));
                }
            } else {
                rectangles.add(new Rectangle(isOn, xMiddle, r.x2, r.y1, yMiddle, r.z1, r.z2));
                if (isZ1Inside) {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, yMiddle, r.y2, zMiddle, r.z2));
                } else {
                    rectangles.add(new Rectangle(isOn, xMiddle, r.x2, yMiddle, r.y2, r.z1, zMiddle));
                }
            }
        }
        return rectangles;
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

    protected List<Point> pointInsideInR(Rectangle rectangleActual, Rectangle r) {
        List<Point> points = new ArrayList<>();
        for (int i : Arrays.asList(rectangleActual.x1(), rectangleActual.x2())) {
            for (int j : Arrays.asList(rectangleActual.y1(), rectangleActual.y2())) {
                for (int k : Arrays.asList(rectangleActual.z1(), rectangleActual.z2())) {
                    if (r.isInside(new Point(i, j, k))) {
                        points.add(new Point(i, j, k));
                    }
                }
            }
        }
        return points;

    }

    protected boolean doesNotHaveCommonPart(Rectangle r1, Rectangle r2) {
        return !doesHaveCommonPart(r1, r2);
    }

    protected boolean doesHaveCommonPart(Rectangle r1, Rectangle r2) {
        return doesHaveInsidePart(r1, r2) || doesHaveInsidePart(r2, r1);
    }

    protected boolean doesHaveInsidePart(Rectangle r1, Rectangle r2) {
        // Do not have point inside
        if (!isOneXInside(r1, r2.x1(), r2.x2())) {
            return false;
        }

        if (!isOneYInside(r1, r2.y1(), r2.y2())) {
            return false;
        }

        if (!isOneZInside(r1, r2.z1(), r2.z2())) {
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

    protected List<Point> calculateIntersectionPoints(Rectangle rectangleActual, Rectangle r, Point point) {
        List<Point> pointsBorder = new ArrayList<>();
        determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(r.x1(), point.y(), point.z()));
        determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(r.x2(), point.y(), point.z()));
        determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), r.y1(), point.z()));
        determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), r.y2(), point.z()));
        determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), point.y(), r.z1()));
        determineCalculateBorder(rectangleActual, r, pointsBorder, new Point(point.x(), point.y(), r.z2()));
        return pointsBorder;
    }


    private void determineCalculateBorder(Rectangle rectangleActual, Rectangle r, List<Point> pointsBorder, Point pointInside) {
        if (rectangleActual.isInside(pointInside) && r.isInside(pointInside)) {
            pointsBorder.add(pointInside);
        }
    }

public static record Rectangle(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {

    public Rectangle newRectangle(int x1Plus, int x2Plus, int y1Plus, int y2Plus, int z1Plus, int z2Plus) {
        return new Rectangle(this.on(), x1() + x1Plus, x2() + x2Plus, y1() + y1Plus, y2() + y2Plus, z1() + z1Plus, z2() + z2Plus);
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
