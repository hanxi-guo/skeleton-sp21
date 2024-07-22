package deque;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {
    private class Node<Generic> {
        Generic item;
        Node<Generic> previous;
        Node<Generic> next;


        public Node(Generic item) {
            this.item = item;
            this.previous = null;
            this.next = null;
        }
    }

    private Node<T> sentinel = new Node<>(null);
    private Integer size;

    @Override
    public void addFirst(T item) {
        Node<T> addNode = new Node<>(item);
        addNode.previous = sentinel;
        addNode.next = sentinel.next;

        //ensures that the previous last node’s next reference
        // is updated to point to the new added node
        sentinel.previous.next = addNode;
        sentinel.next = addNode;
        //do not need sentinel.next.previous = addNode;
        // since addNode has been assigned to the sentinel.next

        //update the size of the circular linked list
        size++;
    }

    @Override
    //almost same as addFirst because of circular
    public void addLast(T item) {
        Node<T> addNode = new Node<>(item);
        sentinel.previous = addNode;
        addNode.next = sentinel;
        addNode.previous = sentinel.previous;

        sentinel.previous = addNode;
        size++;
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        // cannot directly modify the deque when iterate
        Node<T> current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        Node<T> firstNode = sentinel.next;
        if (isEmpty()) {
            return null;
        }
        // remove the pointers to that object.
        sentinel.next = firstNode.next;
        sentinel.next.previous = sentinel;
        size--;
        return firstNode.item;
    }

    @Override
    public T removeLast() {
        Node<T> lastNode = sentinel.previous;
        if (isEmpty()) {
            return null;
        }
        // remove the pointers to that object.
        sentinel.previous = lastNode.previous;
        lastNode.previous.next = sentinel;
        size--;
        return lastNode.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }


    public Iterator<T> iterator() {
        return new Iterable();
    }

    private class Iterable implements Iterator<T> {
        private int followIndex;
        private Node<T> current = sentinel.next;

        Iterable() {
            followIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return followIndex < size;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                T nextItem = current.item;
                followIndex++;
                current = current.next;
                return nextItem;
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

    public LinkedListDeque() {
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null; // Index out of bounds
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node<T> node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }
}