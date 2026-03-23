package com.fmolnar.code.wallet;

public interface IRateProvider {
    double getRate(CurrencyEnum currencyEnum, StockType stockType);
}
