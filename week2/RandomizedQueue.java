import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count;
    private Item[] items;
    // construct an empty randomized queue
    public RandomizedQueue() {
        count = 0;
        items = (Item[]) new Object[2];
    }

    private void resize(int n) {
        Item[] tmp = (Item[]) new Object[n];
        for (int i = 0; i < count; i++)
            tmp[i] = items[i];
        items = tmp;
    }

    // is the randomized queue empty?         
    public boolean isEmpty() {
        return count == 0;
    }
    // return the number of items on the randomized queue              
    public int size() {
        return count;
    } 
    // add the item                      
    public void enqueue(Item item) {
        if (item == null) 
            throw new java.lang.IllegalArgumentException("item can't be null");
        if (this.count == items.length)
            resize(2*items.length);
        items[count++] = item;
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        if (count == items.length / 4) resize(items.length/2);
        int index = StdRandom.uniform(this.count);
        Item ret = items[index];
        items[index] = items[--count];
        items[count] = null;

        return ret;
    } 
    // return a random item (but do not remove it)                
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return items[StdRandom.uniform(this.count)];
    }
     // return an independent iterator over items in random order                 
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int i;
        private int[] randomIndices;
        public ArrayIterator() {
            i = 0;
            randomIndices = new int[count];
            for (int j = 0; j < count; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        @Override
        public boolean hasNext() {
            return i < count;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return items[randomIndices[i++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println("size: "+queue.size());
        System.out.println("iterator");
        for (int i : queue) {
            System.out.println(i);
        }
        System.out.println("sample:" + queue.sample());
        System.out.println("dequeue");
        while (!queue.isEmpty()) System.out.println(queue.dequeue());
        System.out.println("size: "+queue.size());
    }
}