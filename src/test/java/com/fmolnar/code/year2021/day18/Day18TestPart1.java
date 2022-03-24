package com.fmolnar.code.year2021.day18;

import org.junit.Assert;
import org.junit.Test;

public class Day18TestPart1 {

    @Test
    public void testExample(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[[[[[9,8],1],2],3],4]");
        boolean isTrue = "[[[[0,9],2],3],4]".equals(line.getLine());
        Assert.assertTrue( "[[[[0,9],2],3],4]",isTrue);
    }

    @Test
    public void testExample11(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[[[[[9,1],10],2],3],4]");
        Assert.assertTrue( "[[[[0,11],2],3],4]","[[[[0,9],2],3],4]".equals(line.getLine()));
    }

    @Test
    public void testExample2(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[7,[6,[5,[4,[3,2]]]]]");
        Assert.assertTrue("[7,[6,[5,[7,0]]]]","[7,[6,[5,[7,0]]]]".equals(line.getLine()));
    }

    @Test
    public void testExample21(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[7,[6,[5,[11,[3,2]]]]]");
        Assert.assertTrue("[7,[6,[5,[7,0]]]]","[7,[6,[[5,5],[9,0]]]]".equals(line.getLine()));
    }

    @Test
    public void testExample2124(){
        Day18Challenge01 day18Challenge01 = new Day18Challenge01();
        long value = day18Challenge01.calculMagnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
        Assert.assertTrue("3488", value == 3488);
    }

    @Test
    public void testExample212(){
        Day18Challenge01 day18Challenge01 = new Day18Challenge01();
        long value = day18Challenge01.calculMagnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]");
        Assert.assertTrue("1137", value == 1137);
    }

    @Test
    public void testExample218(){
        Day18Challenge01 day18Challenge01 = new Day18Challenge01();
        long value = day18Challenge01.calculMagnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]");
        Assert.assertTrue("791", value == 791);
    }

    @Test
    public void testExample242(){
        Day18Challenge01 day18Challenge01 = new Day18Challenge01();
        long value = day18Challenge01.calculMagnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]");
        Assert.assertTrue("445", value == 445);
    }

    @Test
    public void testExample251(){
        Day18Challenge01 day18Challenge01 = new Day18Challenge01();
        long value = day18Challenge01.calculMagnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        Assert.assertTrue("1384", value == 1384);
    }

    @Test
    public void testExample250(){
        Day18Challenge01 day18Challenge01 = new Day18Challenge01();
        long value = day18Challenge01.calculMagnitude("[[1,2],[[3,4],5]]");
        Assert.assertTrue("143", value == 143);
    }

    @Test
    public void testExample3(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[[6,[5,[4,[3,2]]]],1]");
        Assert.assertTrue("[[6,[5,[7,0]]],3]", "[[6,[5,[7,0]]],3]".equals(line.getLine()));
    }

    @Test
    public void testExample4(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        Assert.assertTrue("[[3,[2,[8,0]]],[9,[5,[7,0]]]]","[[3,[2,[8,0]]],[9,[5,[7,0]]]]".equals(line.getLine()));
    }

    @Test
    public void testExample5(){
        Day18Challenge01.Line line = new Day18Challenge01.Line("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        Assert.assertTrue("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]".equals(line.getLine()));
    }
}
