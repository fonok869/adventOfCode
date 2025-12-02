package com.fmolnar.code.year2025.day02;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Challenge2025Test {

    @Test
    void shouldPrint11() {
        assertThat(new IdRanges(11L, 22L).getPolyandromNumbers()).containsExactlyInAnyOrder(11L, 22L);
    }

    @Test
    void shouldPrint123() {
        assertThat(new IdRanges2(11L, 22L).getPolyandromNumbers()).containsExactlyInAnyOrder(11L, 22L);
    }

    @Test
    void shouldPrintV211() {
        assertThat(new IdRanges2(998L, 1012L).getPolyandromNumbers()).containsExactlyInAnyOrder(999L, 1010L);
    }

    @Test
    void shouldPrint99() {
        assertThat(new IdRanges2(95L, 115L).getPolyandromNumbers()).containsExactlyInAnyOrder(99L, 111L);
    }

    @Test
    void shouldPrint10() {
        assertThat(new IdRanges2(998L, 1012L).getPolyandromNumbers()).containsExactlyInAnyOrder(999L, 1010L);
    }

    @Test
    void shouldPrint111() {
        assertThat(new IdRanges2(4L, 19L).getPolyandromNumbers()).containsExactlyInAnyOrder(11L);
    }

    @Test
    void shouldPrint968() {
        //assertThat(new IdRanges(968753L, 1053291L).getPolyandromNumbers()).isEqualTo(List.of(968968L));
    }


}