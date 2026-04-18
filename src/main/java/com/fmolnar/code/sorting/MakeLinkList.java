package com.fmolnar.code.sorting;

public class MakeLinkList {


    Node reverseLinkedList(Node head) {

        Node current = head;
        Node next = null;
        Node previous = null;
        while (current != null) {
            next = current.next;
            current.next = previous;

            previous = current;
            current = next;
        }
        return previous;
    }
}
