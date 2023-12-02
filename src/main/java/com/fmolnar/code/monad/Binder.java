package com.fmolnar.code.monad;

import java.util.function.Function;

import static com.fmolnar.code.monad.ResultOrError.bind;
import static com.fmolnar.code.monad.StringFunctions.toUpperCase;

public class Binder {

    public static ResultOrError enthuse(String sentence){
        ResultOrError trimmed = bind(new ResultOrError(sentence), StringFunctions::trim);

        ResultOrError upperCased = bind(trimmed, StringFunctions::toUpperCase);

        ResultOrError appended = bind(upperCased, StringFunctions::appendExclam);

        return appended;
    }

//    public static ResultOrError enthuse2(String sentence){
//        ResultOrError trimmed = bind(new ResultOrError(sentence), s->StringFunctions.trim(s));
//
//        ResultOrError upperCased = bind(trimmed,s->toUpperCase(s));
//
//        return bind(upperCased, s->StringFunctions.appendExclam(s));
//    }

    public static ResultOrError enthuse3(String sentence){
        return bind(bind(bind(new ResultOrError(sentence), StringFunctions::trim), StringFunctions::toUpperCase), StringFunctions::appendExclam);
    }

    public static void main(String[] args){
        String bob= " hello bob ";
        System.out.println(enthuse(bob));
        //System.out.println(enthuse2(bob));
        System.out.println(enthuse3(bob));
    }
}
