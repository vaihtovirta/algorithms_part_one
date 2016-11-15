import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] stack;
    private int stackSize;

    public RandomizedQueue() {
        stack = (Item[]) new Object[10];
        stackSize = 0;
    }

    public boolean isEmpty() {
        return stackSize == 0;
    }

    public int size() {
        return stackSize;
    }

    public void enqueue(Item item) {
        checkIfItemNull(item);

        if (stackSize == stack.length) resize(2 * stack.length);

        stack[stackSize++] = item;

        int randomItemIndex = StdRandom.uniform(0, size());
        swapWithLast(randomItemIndex, stackSize - 1);
    }

    public Item dequeue() {
        checkIfEmpty();

        Item item = stack[stackSize - 1];
        stack[stackSize-1] = null;
        stackSize--;

        if (stackSize > 0 && stackSize == stack.length/4) resize(stack.length/2);

        return item;
    }

    public Item sample() {
        checkIfEmpty();

        Item item = stack[StdRandom.uniform(0, size())];

        return item;
    }

    public Iterator<Item> iterator() {
        return new RandomStackIterator();
    }

    private void checkIfEmpty() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
    }

    private void swapWithLast(int swapIndex, int lastIndex) {
        Item lastItem = stack[lastIndex];
        stack[lastIndex] = stack[swapIndex];
        stack[swapIndex] = lastItem;
    }

    private class RandomStackIterator implements Iterator<Item> {
        private int[] lookupTable;
        private int current;
        private int initSize;

        public RandomStackIterator() {
            lookupTable = new int[stackSize];

            for (int i = 0; i < stackSize; i++) {
                lookupTable[i] = i;
            }

            StdRandom.shuffle(lookupTable);
            current = 0;
            initSize = stackSize;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            if (stackSize != initSize) throw new NoSuchElementException();

            return (initSize != current);
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (stackSize != initSize) throw new NoSuchElementException();

            return stack[lookupTable[current++]];
        }
    }

    private void resize(int capacity) {
        Item[] tempStack = (Item[]) new Object[capacity];

        for (int i = 0; i < stackSize; i++) {
            tempStack[i] = stack[i];
        }

        stack = tempStack;
    }

    private void checkIfItemNull(Item item) {
        if (item == null) { throw new java.lang.NullPointerException(); }
    }
}