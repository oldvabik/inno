package org.example;

import org.example.MyLinkedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

public class MyLinkedListTest {

    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    void testSize() {
        assertEquals(0, list.size());

        list.addFirst(1);
        assertEquals(1, list.size());

        list.addLast(2);
        assertEquals(2, list.size());

        list.removeFirst();
        assertEquals(1, list.size());
    }

    @Test
    void testAddFirst() {
        list.addFirst(1);
        assertEquals(1, list.getFirst());
        assertEquals(1, list.size());

        list.addFirst(2);
        assertEquals(2, list.getFirst());
        assertEquals(1, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testAddLast() {
        list.addLast(1);
        assertEquals(1, list.getLast());
        assertEquals(1, list.size());

        list.addLast(2);
        assertEquals(2, list.getLast());
        assertEquals(1, list.getFirst());
        assertEquals(2, list.size());
    }

    @Test
    void testAddAtIndex() {
        list.add(0, 1);
        assertEquals(1, list.getFirst());
        assertEquals(1, list.size());

        list.add(0, 2);
        assertEquals(2, list.getFirst());
        assertEquals(1, list.getLast());
        assertEquals(2, list.size());

        list.add(2, 3);
        assertEquals(3, list.getLast());
        assertEquals(3, list.size());

        list.add(1, 4);
        assertEquals(2, list.get(0));
        assertEquals(4, list.get(1));
        assertEquals(1, list.get(2));
        assertEquals(3, list.get(3));
        assertEquals(4, list.size());
    }

    @Test
    void testAddAtIndexInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 1));
    }

    @Test
    void testGetFirst() {
        assertThrows(NoSuchElementException.class, () -> list.getFirst());

        list.addFirst(1);
        assertEquals(1, list.getFirst());

        list.addFirst(2);
        assertEquals(2, list.getFirst());
    }

    @Test
    void testGetLast() {
        assertThrows(NoSuchElementException.class, () -> list.getLast());

        list.addLast(1);
        assertEquals(1, list.getLast());

        list.addLast(2);
        assertEquals(2, list.getLast());
    }

    @Test
    void testGet() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testGetInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

        list.addFirst(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void testRemoveFirst() {
        list.addLast(1);
        list.addLast(2);

        assertEquals(1, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals(2, list.getFirst());

        assertEquals(2, list.removeFirst());
        assertEquals(0, list.size());
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    }

    @Test
    void testRemoveLast() {
        list.addLast(1);
        list.addLast(2);

        assertEquals(2, list.removeLast());
        assertEquals(1, list.size());
        assertEquals(1, list.getLast());

        assertEquals(1, list.removeLast());
        assertEquals(0, list.size());
        assertThrows(NoSuchElementException.class, () -> list.removeLast());
    }

    @Test
    void testRemove() {
        try {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            var removeMethod = list.getClass().getDeclaredMethod("remove", int.class);
            removeMethod.setAccessible(true);

            assertEquals(2, removeMethod.invoke(list, 1));
            assertEquals(2, list.size());
            assertEquals(1, list.get(0));
            assertEquals(3, list.get(1));

            assertEquals(1, removeMethod.invoke(list, 0));
            assertEquals(1, list.size());
            assertEquals(3, list.getFirst());

            assertEquals(3, removeMethod.invoke(list, 0));
            assertEquals(0, list.size());

        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    void testRemoveInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

        list.addFirst(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
    }

    @Test
    void testComplexScenario() {
        assertEquals(0, list.size());

        list.addFirst(10);
        assertEquals(1, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(10, list.getLast());

        list.addLast(20);
        assertEquals(2, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(20, list.getLast());

        list.add(1, 15);
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(15, list.get(1));
        assertEquals(20, list.get(2));

        assertEquals(10, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(15, list.getFirst());

        assertEquals(20, list.removeLast());
        assertEquals(1, list.size());
        assertEquals(15, list.getLast());
    }

    @Test
    void testSingleElementList() {
        list.addFirst(1);

        assertEquals(1, list.getFirst());
        assertEquals(1, list.getLast());
        assertEquals(1, list.get(0));
        assertEquals(1, list.size());

        assertEquals(1, list.removeFirst());
        assertEquals(0, list.size());

        list.addLast(2);
        assertEquals(1, list.size());
        assertEquals(2, list.removeLast());
        assertEquals(0, list.size());
    }

    @Test
    void testEmptyListExceptions() {
        assertThrows(NoSuchElementException.class, () -> list.getFirst());
        assertThrows(NoSuchElementException.class, () -> list.getLast());
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
        assertThrows(NoSuchElementException.class, () -> list.removeLast());
    }
}