package com.fmolnar.code.leetcode.invalidtransaction;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    void shouldHave() {
        Solution s = new Solution();
        assertThat(s.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,100,beijing"}))
                .containsExactlyInAnyOrder("alice,20,800,mtv", "alice,50,100,beijing");
    }

    @Test
    void shouldHave2() {
        Solution s = new Solution();
        assertThat(s.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,100,mtv", "alice,51,100,frankfurt"}))
                .containsExactlyInAnyOrder("alice,20,800,mtv", "alice,50,100,mtv", "alice,51,100,frankfurt");
    }

    @Test
    void shouldHave3() {
        Solution s = new Solution();
        assertThat(s.invalidTransactions(new String[]{"alex,676,260,bangkok", "bob,656,1366,bangkok", "alex,393,616,bangkok", "bob,820,990,amsterdam", "alex,596,1390,amsterdam"}))
                .containsExactlyInAnyOrder("bob,656,1366,bangkok", "alex,596,1390,amsterdam");
    }
}