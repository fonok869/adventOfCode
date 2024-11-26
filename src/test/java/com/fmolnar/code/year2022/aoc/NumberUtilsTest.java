package com.fmolnar.code.year2022.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberUtilsTest {

    @Test
    public void testConvertToBase(){
        // Base of 5
        assertEquals("3111334140", NumberUtils.printInBase(6355545, 5));
       assertEquals("1", NumberUtils.printInBase(1, 5));
       assertEquals("2", NumberUtils.printInBase(2, 5));
       assertEquals("3", NumberUtils.printInBase(3, 5));
       assertEquals("4", NumberUtils.printInBase(4, 5));
       assertEquals("10", NumberUtils.printInBase(5, 5));

        // Base of 2
       assertEquals("1", NumberUtils.printInBase(1, 2));
       assertEquals("10", NumberUtils.printInBase(2, 2));
       assertEquals("11", NumberUtils.printInBase(3, 2));

        // Base of 7
       assertEquals("105010200", NumberUtils.printInBase(6355545, 7));

    }

    @Test
    public void testFromStringToNumber(){
        // Base of 5
       assertEquals(NumberUtils.printValueDecimalFrom("3111334140",5), 6355545);
       assertEquals(NumberUtils.printValueDecimalFrom("1",5), 1);
       assertEquals(NumberUtils.printValueDecimalFrom("2",5), 2);
       assertEquals(NumberUtils.printValueDecimalFrom("3",5), 3);
       assertEquals(NumberUtils.printValueDecimalFrom("4",5), 4);
       assertEquals(NumberUtils.printValueDecimalFrom("10", 5), 5);

        // Base of 2
       assertEquals(NumberUtils.printValueDecimalFrom("10", 2), 2);
       assertEquals(NumberUtils.printValueDecimalFrom("11", 2), 3);


        // Base of 7
       assertEquals(NumberUtils.printValueDecimalFrom("105010200", 7), 6355545);

        // Base of 8
       assertEquals(NumberUtils.printValueDecimalFrom("30175131", 8), 6355545);
    }
}
