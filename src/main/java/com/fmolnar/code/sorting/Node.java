package com.fmolnar.code.sorting;

public
class Node {
    Node next;
    int value;

    public Node(Node next, int value) {
        this.next = next;
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }
}

