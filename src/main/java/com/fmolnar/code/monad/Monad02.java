package com.fmolnar.code.monad;

public class Monad02 {

    static String trim(String string){
        return string.trim();
    }

    static String toUpperCase(String string){
        return string.toUpperCase();
    }

    static String appendExclam(String string){
        return string.concat("!");
    }

    static String enthuse(String sentence){
        return appendExclam(toUpperCase(trim(sentence)));
    }

    public static void main(String[] args){
        System.out.println(enthuse(" Hello bob "));
    }
}
