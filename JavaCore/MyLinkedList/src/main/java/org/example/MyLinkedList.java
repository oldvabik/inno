package org.example;

import java.util.NoSuchElementException;

public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    public MyLinkedList() {}

    public int size() {
        return size;
    }

    public void addFirst(E el) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, el, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    public void addLast(E el) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, el, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void add(int index, E el) {
        checkPositionIndex(index);

        if (index == 0) {
            addFirst(el);
        } else if (index == size) {
            addLast(el);
        } else {
            Node<E> succ = node(index);
            Node<E> pred = succ.prev;
            Node<E> newNode = new Node<>(pred, el, succ);
            succ.prev = newNode;
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    public E getFirst() {
        final Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException("List is empty");
        }
        return f.item;
    }

    public E getLast() {
        final Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException("List is empty");
        }
        return l.item;
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    public E removeFirst() {
        final Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException("List is empty");
        }
        final E el = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return el;
    }

    public E removeLast() {
        final Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException("List is empty");
        }
        final E el = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return el;
    }

    public E remove(int index) {
        checkElementIndex(index);

        final Node<E> node = node(index);
        final E el = node.item;
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return el;
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E el, Node<E> next) {
            this.prev = prev;
            this.item = el;
            this.next = next;
        }
    }
}