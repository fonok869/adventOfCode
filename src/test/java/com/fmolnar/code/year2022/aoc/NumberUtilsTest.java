package com.fmolnar.code.year2022.aoc;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest {

    @Test
    public void testConvertToBase(){
        // Base of 5
        Assert.assertEquals("3111334140", NumberUtils.printInBase(6355545, 5));
        Assert.assertEquals("1", NumberUtils.printInBase(1, 5));
        Assert.assertEquals("2", NumberUtils.printInBase(2, 5));
        Assert.assertEquals("3", NumberUtils.printInBase(3, 5));
        Assert.assertEquals("4", NumberUtils.printInBase(4, 5));
        Assert.assertEquals("10", NumberUtils.printInBase(5, 5));

        // Base of 2
        Assert.assertEquals("1", NumberUtils.printInBase(1, 2));
        Assert.assertEquals("10", NumberUtils.printInBase(2, 2));
        Assert.assertEquals("11", NumberUtils.printInBase(3, 2));

        // Base of 7
        Assert.assertEquals("105010200", NumberUtils.printInBase(6355545, 7));

    }

    @Test
    public void testFromStringToNumber(){
        // Base of 5
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("3111334140",5), 6355545);
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("1",5), 1);
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("2",5), 2);
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("3",5), 3);
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("4",5), 4);
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("10", 5), 5);

        // Base of 2
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("10", 2), 2);
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("11", 2), 3);


        // Base of 7
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("105010200", 7), 6355545);

        // Base of 8
        Assert.assertEquals(NumberUtils.printValueDecimalFrom("30175131", 8), 6355545);
    }
}
