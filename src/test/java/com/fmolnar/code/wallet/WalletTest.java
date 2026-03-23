package com.fmolnar.code.wallet;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
*
* Given a Wallet containing Stocks, build a function that compute the value of wallet in a currency.

The Stocks have a quantity and a StockType. The StockType can be for example petroleum, Euros, bitcoins and Dollars.

To value the portfilio in a Currency you can use external api to provide rate exchanges (some are provided below).
 */
class WalletTest {

    @Test
    void shouldCalculateValueOfWalletEmpty() {
        Wallet wallet = new Wallet(null, List.of());

        assertThat(wallet.value(CurrencyEnum.EURO)).isEqualTo(0);
    }

    @Test
    void shouldCalculateValueOfWalletForOneStock() {
        Wallet wallet = new Wallet(null, List.of(new Stock(new Quantity(1), StockType.EURO)));

        assertThat(wallet.value(CurrencyEnum.EURO)).isEqualTo(1);
    }

    @Test
    void shouldCalculateValueOfWalletForTwoStock() {
        Wallet wallet = new Wallet(null, List.of(new Stock(new Quantity(1), StockType.EURO), new Stock(new Quantity(2), StockType.EURO)));

        assertThat(wallet.value(CurrencyEnum.EURO)).isEqualTo(3);
    }

    @Test
    void shouldCalculateValueOfWalletForTwoDifferentStock() {
        IRateProvider rateProvider = (currency, stocktype) -> 1.2;
        Wallet wallet = new Wallet(rateProvider, List.of(new Stock(new Quantity(1), StockType.EURO), new Stock(new Quantity(2), StockType.DOLLAR)));

        assertThat(wallet.value(CurrencyEnum.EURO)).isEqualTo(3.4);
    }


}
