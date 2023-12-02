package com.fmolnar.code.monad;

import java.util.function.Function;

public class ResultOrError<R> {

    static <R> ResultOrError<R> bind(ResultOrError<R> resultOrError, Function<R, ResultOrError<R>> function){
        if(resultOrError.isResult()){
            return function.apply(resultOrError.getResult());
        }
        return resultOrError;
    }

    private final R result;
    private final SimpleError error;

    public ResultOrError(R result) {
        this.result = result;
        this.error = null;
    }

    public ResultOrError(SimpleError error) {
        this.result = null;
        this.error = error;
    }

    public R getResult() {
        return result;
    }

    public SimpleError getError() {
        return error;
    }

    public boolean isResult() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

    @Override
    public String toString() {
        if(isResult()){
            return "Result: " + getResult();
        } else {
            return "Error: " + error.getInfo();
        }

    }
}
