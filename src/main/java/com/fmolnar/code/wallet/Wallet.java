package com.fmolnar.code.wallet;

import java.util.List;

public record Wallet(IRateProvider rateProvider, List<Stock> stocks) {

    public double value(CurrencyEnum currency) {
        if (stocks.isEmpty()) {
            return 0;
        }

        return stocks.stream().mapToDouble(
                stock -> {
                    if (!stock.stockType().name().equalsIgnoreCase(currency.name())) {
                        return stock.quantity().value() * rateProvider.getRate(currency, stock.stockType());
                    } else {
                        return stock.quantity().value();
                    }
                }).sum();
    }
}
