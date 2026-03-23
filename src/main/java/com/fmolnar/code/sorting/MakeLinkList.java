package com.fmolnar.code.sorting;

public class MakeLinkList {


    Node reverseLinkedList(Node head) {
        Node current = head;
        Node next = null;
        Node previous = null;
        while (current != null) { // O(n) time
            // Store next
            next = current.next;
            // Reverse Current next
            current.next = previous;

            previous = current;

            current = next;
        }
        return previous; // O(n) time O(1) space
    }
}
