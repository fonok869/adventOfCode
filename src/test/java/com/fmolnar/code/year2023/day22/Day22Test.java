package com.fmolnar.code.year2023.day22;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day22Test {

    @Test
    public void testNewZX(){
        Day22.PointXYZ beginning = new Day22.PointXYZ(1,1,5);
        Day22.PointXYZ beginningTo = new Day22.PointXYZ(1,1,4);
        Day22.PointXYZ end = new Day22.PointXYZ(2,1,5);
        Day22.PointXYZ endTo = new Day22.PointXYZ(2,1,4);

        Day22.TeglaTest teglaTest = new Day22.TeglaTest(beginning, end, Day22.Direction.X);

        Day22.TeglaTest result = teglaTest.newZ(4);

        assertThat(result).isEqualTo(new Day22.TeglaTest(beginningTo, endTo, Day22.Direction.X));
    }

    @Test
    public void testNewZY(){
        Day22.PointXYZ beginning = new Day22.PointXYZ(1,0,5);
        Day22.PointXYZ beginningTo = new Day22.PointXYZ(1,0,4);
        Day22.PointXYZ end = new Day22.PointXYZ(1,2,5);
        Day22.PointXYZ endTo = new Day22.PointXYZ(1,2,4);

        Day22.TeglaTest teglaTest = new Day22.TeglaTest(beginning, end, Day22.Direction.Y);

        Day22.TeglaTest result = teglaTest.newZ(4);

        assertThat(result).isEqualTo(new Day22.TeglaTest(beginningTo, endTo, Day22.Direction.Y));
    }

    @Test
    public void testNewZZ(){
        Day22.PointXYZ beginning = new Day22.PointXYZ(1,0,4);
        Day22.PointXYZ beginningTo = new Day22.PointXYZ(1,0,2);
        Day22.PointXYZ end = new Day22.PointXYZ(1,0,6);
        Day22.PointXYZ endTo = new Day22.PointXYZ(1,0,4);

        Day22.TeglaTest teglaTest = new Day22.TeglaTest(beginning, end, Day22.Direction.Z);

        Day22.TeglaTest result = teglaTest.newZ(2);

        assertThat(result).isEqualTo(new Day22.TeglaTest(beginningTo, endTo, Day22.Direction.Z));
    }
}
