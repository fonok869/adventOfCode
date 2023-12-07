package com.fmolnar.code.monad;

public class StringFunctions {

    public static ResultOrError trim(String string) {
        String result = string.trim();
        if (result.isEmpty()) {
            return new ResultOrError(new SimpleError("String must conatin non-space characters."));
        }

        return new ResultOrError(result);
    }

    public static ResultOrError toUpperCase(String string) {
//        if (!string.matches("[a-zA-Z\s]+")) {
//            return new ResultOrError(new SimpleError("String must contains only letters and spaces"));
//        }
        return new ResultOrError(string.toUpperCase());
    }

    public static ResultOrError appendExclam(String string) {
        if (string.length() > 20) {
            return new ResultOrError(new SimpleError("Can not be more than 20 characters"));
        }

        return new ResultOrError(string.concat("!"));
    }




}
