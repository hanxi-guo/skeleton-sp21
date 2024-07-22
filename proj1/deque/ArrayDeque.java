package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T> {
    private T[] array;
    private int size;

    //since I think nextfirst/nextlast is less convenient when resizing
    // use head and tail instead of it
    private int head;
    private int tail;
    private final int initSize = 8;
    private static final double MIN_USAGE_RATIO = 0.25;


    public ArrayDeque() {
        this.array = (T[]) new Object[initSize];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    // the most challenging method
    // A helper function for add /remove
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        // the new array start from head !!!
        // because there is a situation that The array ends are empty
        // and the stored elements are in the middle
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i) % array.length];
            // avoid the situation that array out of bounds
        }

        array = newArray;
        head = 0;
        tail = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            resize(size * 2);
        }

        head = (head - 1 + array.length) % array.length;
        array[head] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[tail % array.length] = item;
        tail = (tail + 1) % array.length;
        size++;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (T t : array) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int index = (head + 1) % array.length;
        T t = array[head];
        array[head] = null;
        head = index;
        size--;

        // downsize the array when usage ratio is too small
        if (size > initSize && size < array.length * MIN_USAGE_RATIO) {
            resize(array.length / 2);
        }
        return t;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        // tail = nextLast, so we need to remove the one before it
        int index = (tail - 1 + array.length) % array.length;
        T t = array[index];
        array[index] = null;
        tail = index;
        size--;

        // downsize the array when usage ratio is too small
        if (size > initSize && size < array.length * MIN_USAGE_RATIO) {
            resize(size);
        }
        return t;
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }

        // add head to correctly locate the index
        return array[index];
    }

    public Iterator<T> iterator() {
        return new Iterable();
    }

    private class Iterable implements Iterator<T> {
        private int index;

        public Iterable() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return array[index++];
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque) || ((Deque<?>) o).size() != this.size()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        ArrayDeque<?> other = (ArrayDeque<?>) o;
        for (int i = 0; i < this.size(); i++) {
            Object item = other.get(i);
            if (!(this.get(i).equals(item))) {
                return false;
            }
        }
        return true;
    }
}
