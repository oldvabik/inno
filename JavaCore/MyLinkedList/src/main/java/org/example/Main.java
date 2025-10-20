package org.example;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.addFirst("Apple");
        list.addLast("Cherry");
        list.add(1, "Banana");
        list.addLast("Date");

        System.out.println("First: " + list.getFirst());
        System.out.println("Last: " + list.getLast());
        System.out.println("Index 2: " + list.get(2));

        System.out.println("Removed first: " + list.removeFirst());
        System.out.println("Removed index 1: " + list.remove(1));
        System.out.println("Removed last: " + list.removeLast());

        System.out.println("Final size: " + list.size());
        System.out.println("Final element: " + list.get(0));
    }
}