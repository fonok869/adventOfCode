package com.fmolnar.code.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MakeLinkListTest {


    @Test
    void shouldReverseLinkedList() {
        assertThat(new MakeLinkList().reverseLinkedList(
                new Node(new Node(new Node(new Node(null, 2000), 1500), 100), 50)).getValue()).isEqualTo(2000);
    }
}