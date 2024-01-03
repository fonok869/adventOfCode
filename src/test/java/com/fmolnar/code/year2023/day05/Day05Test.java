package com.fmolnar.code.year2023.day05;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day05Test {

    @Test
    public void shouldReturnNormal() {

        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(1, 5), new Day05v2.SeedRange(2, 6));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(6, 10), new Day05v2.SeedRange(7, 11));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line2, line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(0, 0);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isNull();
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(seedRange);
    }

    @Test
    public void shouldReturnNormalv2() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(1, 5), new Day05v2.SeedRange(2, 6));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(6, 10), new Day05v2.SeedRange(7, 11));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line2, line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(10, 20);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isNull();
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(seedRange);
    }

    @Test
    public void shouldReturnFullInside() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(1, 5), new Day05v2.SeedRange(2, 6));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(6, 10), new Day05v2.SeedRange(7, 11));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line2, line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(3, 4);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(4, 5));
        assertThat(notTouched).isEmpty();
    }

    @Test
    public void shouldReturnHalfBefore() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(6, 10), new Day05v2.SeedRange(7, 11));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line2, line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(0, 3);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(3, 4));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(0, 1));
    }

    @Test
    public void shouldReturnHalfAfter() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(6, 10), new Day05v2.SeedRange(7, 11));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line2, line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(4, 10);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(5, 6));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(6, 10));
    }

    @Test
    public void shouldReturnMiddle() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(0, 10);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(3, 6));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(6, 10));
        assertThat(notTouched.get(1)).isEqualTo(new Day05v2.SeedRange(0, 1));
    }

    @Test
    public void shouldReturnHalfBeforeLeftEdgeCase() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(6, 10), new Day05v2.SeedRange(7, 11));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line2, line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(0, 2);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();
        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(3, 3));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(0, 1));
    }

    @Test
    public void shouldReturnHalfBeforeRightEdgeCase() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(0, 5);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();

        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(3, 6));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(0, 1));
    }

    @Test
    public void shouldReturnHalfAfterLeftEdgeCase() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(2, 10);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();

        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(3, 6));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(6, 10));
    }

    @Test
    public void shouldReturnHalfAfterRightEdgeCase() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(5, 10);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();

        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(6, 6));
        assertThat(notTouched).isNotEmpty();
        assertThat(notTouched.get(0)).isEqualTo(new Day05v2.SeedRange(6, 10));
    }

    @Test
    public void shouldReturnMiddleLeftEdgeCase() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(2, 4);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();

        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(3, 5));
        assertThat(notTouched).isEmpty();
    }

    @Test
    public void shouldReturnMiddleRightEdgeCase() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(2, 5), new Day05v2.SeedRange(3, 6));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(3, 5);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();

        Day05v2.SeedRange result = solMapRange.getNewTranformedRanges(line1, seedRange, notTouched);
        assertThat(result).isEqualTo(new Day05v2.SeedRange(4, 6));
        assertThat(notTouched).isEmpty();
    }

    @Test
    public void shouldReturnCorrectValue() {
        Day05v2.SolLineRange line1 = new Day05v2.SolLineRange(new Day05v2.SeedRange(50, 97), new Day05v2.SeedRange(52, 99));
        Day05v2.SolLineRange line2 = new Day05v2.SolLineRange(new Day05v2.SeedRange(98, 99), new Day05v2.SeedRange(50, 51));
        Day05v2.SolMapRange solMapRange = new Day05v2.SolMapRange(Arrays.asList(line1, line2));
        Day05v2.SeedRange seedRange = new Day05v2.SeedRange(55, 68);
        List<Day05v2.SeedRange> notTouched = new ArrayList<>();

        Collection<Day05v2.SeedRange> result = solMapRange.calculatenewRanges(seedRange);
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next()).isEqualTo(new Day05v2.SeedRange(57, 70));
    }
}


