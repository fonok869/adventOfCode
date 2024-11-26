package com.fmolnar.code.year2023.day19;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fmolnar.code.year2023.day19.Day19v2.RangeX;
import static com.fmolnar.code.year2023.day19.Day19v2.RangeM;
import static com.fmolnar.code.year2023.day19.Day19v2.RangeA;
import static com.fmolnar.code.year2023.day19.Day19v2.RangeS;
import static com.fmolnar.code.year2023.day19.Day19v2.LineRange;
import static org.assertj.core.api.Assertions.assertThat;

public class Day19v2Test {

    @Test
    public void testSimpleXRangeCutMiddleOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "x", 2555);

        assertThat(result.get(0).rangeX()).isEqualTo(new RangeX(1,2554));
        assertThat(result.get(1).rangeX()).isEqualTo(new RangeX(2555,4000));
    }

    @Test
    public void shouldRetunrRangeXZeroLongOKKisebb(){
        RangeX rangeX = new RangeX(2000,4000);
        LineRange lineRange = new LineRange(rangeX, getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "x", 1555);

        assertThat(result.get(0)).isNull();
        assertThat(result.get(1).rangeX()).isEqualTo(new RangeX(2000,4000));
    }

    @Test
    public void shouldNotModifyRangeXOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "x", 4500);

        assertThat(result.get(0).rangeX()).isEqualTo(getRangeXNormal());
        assertThat(result.get(1)).isNull();
    }

    @Test
    public void testSimpleMRangeCutMiddleOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "m", 2555);

        assertThat(result.get(0).rangeM()).isEqualTo(new RangeM(1,2554));
        assertThat(result.get(1).rangeM()).isEqualTo(new RangeM(2555,4000));
    }

    @Test
    public void shouldRetunrRangeMZeroLongOKKisebb(){
        RangeM rangeM = new RangeM(2000,4000);
        LineRange lineRange = new LineRange(getRangeXNormal(), rangeM, getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "m", 1555);

        assertThat(result.get(0)).isNull();
        assertThat(result.get(1).rangeM()).isEqualTo(new RangeM(2000,4000));
    }

    @Test
    public void shouldNotModifyRangeMOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "m", 4500);

        assertThat(result.get(0).rangeM()).isEqualTo(getRangeMNormal());
        assertThat(result.get(1)).isNull();
    }

    @Test
    public void testSimpleARangeCutMiddleOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "a", 2555);

        assertThat(result.get(0).rangeA()).isEqualTo(new RangeA(1,2554));
        assertThat(result.get(1).rangeA()).isEqualTo(new RangeA(2555,4000));
    }

    @Test
    public void shouldRetunrRangeAZeroLongOKKisebb(){
        RangeA rangeA = new RangeA(2000,4000);
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), rangeA, getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "a", 1555);

        assertThat(result.get(0)).isNull();
        assertThat(result.get(1).rangeA()).isEqualTo(new RangeA(2000,4000));
    }

    @Test
    public void shouldNotModifyRangeAOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "a", 4500);

        assertThat(result.get(0).rangeA()).isEqualTo(getRangeANormal());
        assertThat(result.get(1)).isNull();
    }

    @Test
    public void testSimpleSRangeCutMiddleOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "s", 2555);

        assertThat(result.get(0).rangeS()).isEqualTo(new RangeS(1,2554));
        assertThat(result.get(1).rangeS()).isEqualTo(new RangeS(2555,4000));
    }

    @Test
    public void shouldRetunrRangeSZeroLongOKKisebb(){
        RangeS rangeS = new RangeS(2000,4000);
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), rangeS);

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "s", 1555);

        assertThat(result.get(0)).isNull();
        assertThat(result.get(1).rangeS()).isEqualTo(new RangeS(2000,4000));
    }

    @Test
    public void shouldNotModifyRangeSOKKisebb(){
        LineRange lineRange = new LineRange(getRangeXNormal(), getRangeMNormal(), getRangeANormal(), getRangeSNormal());

        List<LineRange> result = Day19v2.getFromLineRangeOKKisebb(lineRange, "s", 4500);

        assertThat(result.get(0).rangeS()).isEqualTo(getRangeSNormal());
        assertThat(result.get(1)).isNull();
    }




    RangeX getRangeXNormal(){
        return new RangeX(1,4000);
    }

    RangeM getRangeMNormal(){
        return new RangeM(1,4000);
    }

    RangeA getRangeANormal(){
        return new RangeA(1,4000);
    }

    RangeS getRangeSNormal(){
        return new RangeS(1,4000);
    }
}
