package com.fmolnar.code.kata;


import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class WordWrapTest {

    @Test
    public void shouldNotWrapWithoutSentece(){
        assertThat(WordWrapper.wrap(null, 10)).isNull();
    }

    @Test
    public void shouldWrapSentenceNotTooLong(){
        assertThat(WordWrapper.wrap("Toto", 4)).isEqualTo("Toto");
    }

    @Test
    public void shouldWrapInto2Words(){
        assertThat(WordWrapper.wrap("Bonjour", 4)).isEqualTo(adding("Bonj", "our"));
    }

    @Test
    public void shouldWrapIntoManyWords(){
        assertThat(WordWrapper.wrap("Bonjour", 2)).isEqualTo(adding("Bo","nj","ou","r"));
    }

    @Test
    public void shouldWrapBetweenWords(){
        System.out.println(WordWrapper.wrap("Once uppon a time in Hollywood", 14));
        assertThat(WordWrapper.wrap("Once uppon a time in Hollywood", 14)).isEqualTo(adding("Once uppon a", "time in", "Hollywood"));
    }

    @Test
    public void shouldWrapAfterWordsCase(){
        assertThat(WordWrapper.wrap("Bonjour chers participants", 13)).isEqualTo(adding("Bonjour chers", "participants"));
    }


    public static String adding(String ... parts){
        return Arrays.asList(parts).stream().collect(Collectors.joining("\n"));
    }


}
