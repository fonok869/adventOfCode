package com.fmolnar.code.year2021.day22;

import static com.fmolnar.code.year2021.day22.Day22Challenge02.Rectangle;
import static com.fmolnar.code.year2021.day22.Day22Challenge02.Point;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnitParamsRunner.class)
public class Day22TestPart2 {


    @Parameters
    private Object[] parametersToCommonTest() {
        return new Object[]{
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 1, 10, 1, 10, 1, 10), true},
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 1, 1, 1, 9, 9, 9), true},
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 5, 15, 0, 10, 0, 10), true},
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 0, 10, 5, 15, 0, 10), true},
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 0, 10, 0, 10, 5, 15), true},// rectangle couvre
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, -10, 20, 5, 15, 5, 15), true},// rectangle couvre
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 5, 6, 5, 6, 5, 15), true},// frite inside
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 8, 20, 8, 20, 5, 15), true},// 1 point inside
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                new Rectangle(true, 4, 8, 4, 8, 1, 9), true}};// rectangle inside
    }

    @Test
    @Parameters(method = "parametersToCommonTest")
    public void testdoesNotHaveCommonPart(Rectangle r1, Rectangle r2, boolean isCommonPart) {
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        if (isCommonPart) {
            Assert.assertTrue(day22Challenge02.doesNotHaveCommonPart(r1, r2));
        } else {
            Assert.assertFalse(day22Challenge02.doesNotHaveCommonPart(r1, r2));
        }
    }

    @Parameters
    private Object[] parametersPointsInside() {
        return new Object[]{
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 1, 10, 1, 10, 1, 10), 1},// Masodik teglatestben mennyi pont van az elsobol
                new Object[]{new Rectangle(true, 5, 15, 5, 15, 5, 15),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 1},
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 2, 8, 2, 8, 2, 8), 0},
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 5, 15),
                        new Rectangle(true, 2, 8, 2, 8, 2, 8), 0},
                new Object[]{new Rectangle(true, 4, 6, 3, 12, 5, 15),
                        new Rectangle(true, 2, 8, 2, 8, 2, 8), 2},
                new Object[]{new Rectangle(true, 4, 6, 4, 6, 5, 15),
                        new Rectangle(true, 2, 8, 2, 8, 2, 8), 4},
                new Object[]{new Rectangle(true, 4, 6, 4, 6, 4, 6),
                        new Rectangle(true, 2, 8, 2, 8, 2, 8), 8}};
    }

    @Test
    @Parameters(method = "parametersPointsInside")
    public void tesPointsInside(Rectangle r1, Rectangle r2, int nbPoints){
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        Assert.assertSame(nbPoints, day22Challenge02.pointInsideInR(r1, r2).size());
    }

    @Parameters
    private Object[] paramsOnePointInside() {
        return new Object[]{
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 1, 11, 1, 11, 1, 11), new Point(10,10,10), 3},// Masodik teglatestben mennyi pont van az elsobol
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 5, 15, 5, 15, 5, 15), new Point(10,10,10), 3}};}

    @Test
    @Parameters(method = "paramsOnePointInside")
    public void testcalculateIntersectionPoints1Point(Rectangle r1, Rectangle r2, Point point, int nbPoints){
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        Assert.assertSame(nbPoints, day22Challenge02.calculateIntersectionPoints(r1, r2, point).size());
    }

    @Parameters
    private Object[] paramsOnePointInsideToExclure() {
        return new Object[]{
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 1, 11, 1, 11, 1, 11), new Point(10,10,10),
                        new Rectangle(true, 1,10,1,10,1,10)},// Masodik teglatestben mennyi pont van az elsobol
                new Object[]{new Rectangle(true, 0, 10, 0, 10, 0, 10),
                        new Rectangle(true, 5, 15, 5, 15, 5, 15), new Point(10,10,10),
                        new Rectangle(true, 5,10,5,10,5,10)}};}

    @Test
    @Parameters(method = "paramsOnePointInsideToExclure")
    public void testcalculateIntersectionPoints1PointExclure(Rectangle r1, Rectangle r2, Point point, Rectangle rectangleToHave){
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        Assert.assertTrue(rectangleToHave.equals(day22Challenge02.calcultateRectangleToExclure(r1.on(), point, day22Challenge02.calculateIntersectionPoints(r1, r2, point))));
    }

    @Parameters
    private Object[] rectangleToRemove() {
        return new Object[]{
                new Object[]{new Rectangle(true, 0, 5, 0, 5, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 5, 10, 0, 10, 0, 10),
                        new Rectangle(true, 0, 5, 5, 10, 0, 10),
                        new Rectangle(true, 0, 5, 0, 5, 5, 10))},// Masodik teglatestben mennyi pont van az elsobol
                new Object[]{new Rectangle(true, 5, 10, 0, 5, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 0, 5, 0, 10, 0, 10),
                        new Rectangle(true, 5, 10, 5, 10, 0, 10),
                        new Rectangle(true, 5, 10, 0, 5, 5, 10))},
                new Object[]{new Rectangle(true, 0, 5, 5, 10, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 5, 10, 0, 10, 0, 10),
                        new Rectangle(true, 0, 5, 0, 5, 0, 10),
                        new Rectangle(true, 0, 5, 5, 10, 5, 10))},
                new Object[]{new Rectangle(true, 0, 5, 0, 5, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 5, 10, 0, 10, 0, 10),
                        new Rectangle(true, 0, 5, 5, 10, 0, 10),
                        new Rectangle(true, 0, 5, 0, 5, 0, 5))},
                new Object[]{new Rectangle(true, 5, 10, 5, 10, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 0, 5, 0, 10, 0, 10),
                        new Rectangle(true, 5, 10, 0, 5, 0, 10),
                        new Rectangle(true, 5, 10, 5, 10, 5, 10))},
                new Object[]{new Rectangle(true, 5, 10, 5, 10, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 0, 5, 0, 10, 0, 10),
                        new Rectangle(true, 5, 10, 0, 5, 0, 10),
                        new Rectangle(true, 5, 10, 5, 10, 0, 5))},
                new Object[]{new Rectangle(true, 0, 5, 5, 10, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 5, 10, 0, 10, 0, 10),
                        new Rectangle(true, 0, 5, 0, 5, 0, 10),
                        new Rectangle(true, 0, 5, 5, 10, 0, 5))},
                new Object[]{new Rectangle(true, 5, 10, 0, 5, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                Arrays.asList(new Rectangle(true, 0, 5, 0, 10, 0, 10),
                        new Rectangle(true, 5, 10, 5, 10, 0, 10),
                        new Rectangle(true, 5, 10, 0, 5, 0, 5))}};}

    @Test
    @Parameters(method = "rectangleToRemove")
    public void testRemoveRectangle(Rectangle rectToRemove, Rectangle r, int nbRestant, List<Rectangle> rectanglesExpected){
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        List<Rectangle> restants = day22Challenge02.removeRectangle(true, rectToRemove, r);
        Assert.assertEquals(nbRestant, restants.size());
        Assert.assertTrue(rectanglesExpected.containsAll(restants));
        restants.forEach(System.out::println);
    }

    @Parameters
    private Object[] rectangleToRemoveWithoutBorne() {
        return new Object[]{
                new Object[]{new Rectangle(true, 0, 5, 0, 5, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 6, 10, 0, 10, 0, 10),
                                new Rectangle(true, 0, 5, 6, 10, 0, 10),
                                new Rectangle(true, 0, 5, 0, 5, 6, 10))},// Masodik teglatestben mennyi pont van az elsobol
                new Object[]{new Rectangle(true, 5, 10, 0, 5, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 0, 4, 0, 10, 0, 10),
                                new Rectangle(true, 5, 10, 6, 10, 0, 10),
                                new Rectangle(true, 5, 10, 0, 5, 6, 10))},
                new Object[]{new Rectangle(true, 0, 5, 5, 10, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 6, 10, 0, 10, 0, 10),
                                new Rectangle(true, 0, 5, 0, 4, 0, 10),
                                new Rectangle(true, 0, 5, 5, 10, 6, 10))},
                new Object[]{new Rectangle(true, 0, 5, 0, 5, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 6, 10, 0, 10, 0, 10),
                                new Rectangle(true, 0, 5, 6, 10, 0, 10),
                                new Rectangle(true, 0, 5, 0, 5, 0, 4))},
                new Object[]{new Rectangle(true, 5, 10, 5, 10, 0, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 0, 4, 0, 10, 0, 10),
                                new Rectangle(true, 5, 10, 0, 4, 0, 10),
                                new Rectangle(true, 5, 10, 5, 10, 6, 10))},
                new Object[]{new Rectangle(true, 5, 10, 5, 10, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 0, 4, 0, 10, 0, 10),
                                new Rectangle(true, 5, 10, 0, 4, 0, 10),
                                new Rectangle(true, 5, 10, 5, 10, 0, 4))},
                new Object[]{new Rectangle(true, 0, 5, 5, 10, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 6, 10, 0, 10, 0, 10),
                                new Rectangle(true, 0, 5, 0, 4, 0, 10),
                                new Rectangle(true, 0, 5, 5, 10, 0, 4))},
                new Object[]{new Rectangle(true, 5, 10, 0, 5, 5, 10),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), 3,
                        Arrays.asList(new Rectangle(true, 0, 4, 0, 10, 0, 10),
                                new Rectangle(true, 5, 10, 6, 10, 0, 10),
                                new Rectangle(true, 5, 10, 0, 5, 0, 4))}};}

    @Test
    @Parameters(method = "rectangleToRemoveWithoutBorne")
    public void testRemoveRectangleWithoutBorne(Rectangle rectToRemove, Rectangle r, int nbRestant, List<Rectangle> rectanglesExpected){
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        List<Rectangle> restants = day22Challenge02.removeRectangleWithoutBorne(true, rectToRemove, r);
        Assert.assertEquals(nbRestant, restants.size());
        Assert.assertTrue(rectanglesExpected.containsAll(restants));
        restants.forEach(System.out::println);
    }

    @Parameters
    private Object[] stepTwo() {
        return new Object[]{
                new Object[]{new Rectangle(true, 2, 8, -5, 5, -5, 5),
                        new Rectangle(true, 0, 10, 0, 10, 0, 10), Arrays.asList(new Point(2,5,5), new Point(8,5,5))}};}


    @Test
    @Parameters(method = "stepTwo")
    public void testStep2(Rectangle rectAct, Rectangle r, List<Point> pointsInside){
        Day22Challenge02 day22Challenge02 = new Day22Challenge02();
        List<Rectangle> restants = day22Challenge02.getRectanglesForStep2(rectAct, r, pointsInside);
//        Assert.assertEquals(rectanglesExpected, restants.size());
//        Assert.assertTrue(rectanglesExpected.containsAll(restants));
        System.out.println(restants);
    }


}

