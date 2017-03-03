import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private Node oldNode;
    private int n;

    private class Node {
        private Item item;
        private Node next;
        private Node before;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (first == null && last == null) {
            first = new Node();
            first.item = item;
            last = first;
            n++;
        } else {
            oldNode = first;
            first = new Node();
            first.item = item;
            first.next = oldNode;
            oldNode.before = first;
            n++;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (first == null && last == null) {
            first = new Node();
            first.item = item;
            last = first;
            n++;
        } else {
            oldNode = last;
            last = new Node();
            last.item = item;
            last.before = oldNode;
            oldNode.next = last;
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0)
            throw new NoSuchElementException();
        oldNode = first;
        first = first.next;
        if (first == null)
            last = null;
        else first.before = null;
        n--;
        return oldNode.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (n == 0)
            throw new NoSuchElementException();
        oldNode = last;
        last = last.before;
        if (last == null)
            first = null;
        else last.next = null;
        n--;
        return oldNode.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
    }
}

