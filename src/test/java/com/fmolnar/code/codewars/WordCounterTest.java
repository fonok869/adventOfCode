package com.fmolnar.code.codewars;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class WordCounterTest {

    @Test
    public void sampleTests() {
        assertThat(Arrays.asList("e", "d", "a")).isEqualTo(TopWords.top3("a a a  b  c c  d d d d  e e e e e"));
        assertThat(Arrays.asList("e", "ddd", "aa")).isEqualTo(TopWords.top3("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e"));
        assertThat(Arrays.asList("won't", "wont")).isEqualTo(TopWords.top3("  //wont won't won't "));
        assertThat(Arrays.asList("e")).isEqualTo(TopWords.top3("  , e   .. "));
        assertThat(Arrays.asList()).isEqualTo(TopWords.top3("  ...  "));
        assertThat(Arrays.asList()).isEqualTo(TopWords.top3("  '  "));
        assertThat(Arrays.asList()).isEqualTo(TopWords.top3("  '''  "));
        assertThat(Arrays.asList("a", "of", "on")).isEqualTo(TopWords.top3(Stream
                .of("In a village of La Mancha, the name of which I have no desire to call to",
                        "mind, there lived not long since one of those gentlemen that keep a lance",
                        "in the lance-rack, an old buckler, a lean hack, and a greyhound for",
                        "coursing. An olla of rather more beef than mutton, a salad on most",
                        "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra",
                        "on Sundays, made away with three-quarters of his income.")
                .collect(Collectors.joining("\n"))));
    }
}
