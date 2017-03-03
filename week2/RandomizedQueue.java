import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Stack<Integer> dequeStack;
    private Item[] array;
    private int len = 10;
    private int n;
    private int index;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // noinspection unchecked
        array = (Item[]) new Object[len];
        dequeStack = new Stack<Integer>();
    }

    private class Stack<T> {
        private Node<T> first;     // top of stack
        private int n;                // size of the stack

        // helper linked list class
        private class Node<T> {
            private T item;
            private Node<T> next;
        }


        public Stack() {
            first = null;
            n = 0;
        }


        public boolean isEmpty() {
            return first == null;
        }


        public int size() {
            return n;
        }


        public void push(T item) {
            Node<T> oldfirst = first;
            first = new Node<T>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }


        public T pop() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            T item = first.item;        // save item to return
            first = first.next;            // delete first node
            n--;
            return item;                   // return the saved item
        }
    }


    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (n == len)
            resize(len * 2);
        if (dequeStack.isEmpty())
            array[index++] = item;
        else
            array[dequeStack.pop()] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0)
            throw new NoSuchElementException();
        if (n == len / 4)
            resize(len / 2);
        int randindex = StdRandom.uniform(0, len);
        while (array[randindex] == null)
            randindex = StdRandom.uniform(0, len);
        Item oldValue = array[randindex];
        array[randindex] = null;
        dequeStack.push(randindex);
        n--;
        return oldValue;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (n == 0)
            throw new NoSuchElementException();
        int randindex = StdRandom.uniform(0, len);
        while (array[randindex] == null)
            randindex = StdRandom.uniform(0, len);
        return array[randindex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int index;
        private int[] arr;

        public ListIterator() {
            arr = new int[n];
            for (int i = 0, j = 0; i < len; i++) {
                if (array[i] != null)
                    arr[j++] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return index < n;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return array[arr[index++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int length) {
        Item[] oldArray = array;
        array = (Item[]) new Object[length];
        if (length > this.len)
            System.arraycopy(oldArray, 0, array, 0, oldArray.length);
        else {
            int j = 0;
            for (int i = 0; i < this.len; i++) {
                if (oldArray[i] != null)
                    array[j++] = oldArray[i];
            }
            index = j;
            dequeStack = new Stack<Integer>();
        }
        this.len = length;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        for(Integer i:rq){
            StdOut.print(i);
        }
        StdOut.println();

        for (int i = 0; i < 5; i++) {
            StdOut.print(rq.dequeue());
        }
        StdOut.println();

        for(Integer i:rq){
            StdOut.print(i);
        }


    }
}