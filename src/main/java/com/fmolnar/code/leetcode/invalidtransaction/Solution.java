package com.fmolnar.code.leetcode.invalidtransaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

    record Transaction(String name, int time, int amount, String city) {
        @Override
        public String toString() {
            return name + "," + time + "," + amount + "," + city;
        }
    }

    public List<String> invalidTransactions(String[] transactions) {
        List<String> transactionsOrdered = Arrays.asList(transactions).stream().toList();
        Map<String, Set<Transaction>> lastTransactions = new HashMap<>();
        Set<String> suspectedTransactions = new HashSet<>();
        for (String transactionString : transactionsOrdered) {
            String[] trans = transactionString.split(",");
            Transaction transaction = new Transaction(trans[0], Integer.parseInt(trans[1]), Integer.parseInt(trans[2]), trans[3]);
            if (1000 < transaction.amount) {
                suspectedTransactions.add(transactionString);
            }


            if (lastTransactions.get(transaction.name) != null) {
                for (Transaction sameName : lastTransactions.get(transaction.name)) {
                    if (Math.abs(transaction.time - sameName.time) <= 60 &&
                            (!transaction.city.equalsIgnoreCase(sameName.city))) {
                        suspectedTransactions.add(sameName.toString());
                        suspectedTransactions.add(transactionString);
                    }

                }
            }
            lastTransactions.putIfAbsent(transaction.name, new HashSet<>());
            lastTransactions.get(transaction.name).add(transaction);
        }
        return transactionsOrdered.stream().filter(suspectedTransactions::contains).toList();

    }
}
