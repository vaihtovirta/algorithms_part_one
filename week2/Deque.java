import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int stackSize;
    private Node first, last;

    private class Node {
        private Item item;
        private Node next, previous;
    }


    public Deque() {
        stackSize = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return stackSize < 1;
    }

    public int size() {
        return stackSize;
    }

    public void addFirst(Item item) {
        checkIfItemNull(item);

        Node oldFirst = first;
        first = new Node();
        first.item = item;

        if (isEmpty()) {
            first.next = null;
            first.previous = null;
            last = first;
        } else {
            first.next = oldFirst;
            first.previous = null;

            if (oldFirst != null) {
                oldFirst.previous = first;
            }
        }

        stackSize++;
    }

    public void addLast(Item item) {
        checkIfItemNull(item);

        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = null;
            first = last;
        } else {
            Node newLast = new Node();
            newLast.item = item;
            newLast.previous = last;
            last.next = newLast;
            last = newLast;
        }

        stackSize++;
    }

    public Item removeFirst() {
        checkIfEmpty();

        Item item = first.item;

        if (stackSize == 1) {
            first.item = null;
            first.next = null;
            first.previous = null;
            first = null;
            last = null;
        } else {
            Node newFirst = first.next;
            newFirst.previous = null;
            first.item = null;
            first = newFirst;
        }

        stackSize--;
        return item;
    }

    public Item removeLast() {
        checkIfEmpty();

        Item item = last.item;
        last.item = null;

        if (last.previous != null) {
            Node newLast = last.previous;
            newLast.next = null;
            last = newLast;
        }

        stackSize--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void checkIfEmpty() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
    }

    private void checkIfItemNull(Item item) {
        if (item == null) { throw new java.lang.NullPointerException(); }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(1);
        StdOut.println(deque.removeLast());
        deque.addFirst(3);
        StdOut.println(deque.removeLast());
        StdOut.println("isEmpty: " + deque.isEmpty());
    }
}