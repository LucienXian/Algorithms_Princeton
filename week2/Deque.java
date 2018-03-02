
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int count;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    // construct an empty deque
    public Deque() {
        count = 0;
    }

    // is the deque empty?       
    public boolean isEmpty() {
        return count == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Input item can't be null");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (last == null) {
            last = first;
        }
        else oldFirst.prev = first;

        count++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Input item can't be null");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (first == null) first = last;
        else oldLast.next = last;

        count++;
    } 
    // remove and return the item from the front
    public Item removeFirst() {
        if (count == 0)
            throw new java.util.NoSuchElementException("empty deque");
        
        Item item = first.item;
        
        if (count == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        count--;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (count == 0)
            throw new java.util.NoSuchElementException("empty deque");
        Item item = last.item;

        if (count == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        count--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> { 
        private Node current = first;
         
        public void remove() {
            throw new UnsupportedOperationException();
        }
         
        public boolean hasNext() {
            return current != null;
        }
         
        public Item next() {
            Item item = current.item;
            if (!hasNext()) { 
                throw new java.util.NoSuchElementException();
            }   
            current = current.next;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.removeLast();
        System.out.println(d.isEmpty());
    }
}