import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n, insertPosition;
    private Item [] a;

    // construct an empty randomized queue
    public RandomizedQueue(){
        this.n = 0;
        this.insertPosition = 0;
        this.a = (Item []) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return this.n == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return this.n;
    }

    private int resize(int cap){
        Item [] seq = (Item []) new Object[cap];
        int cont = 0;
        for(int i = 0; i < this.a.length; i++){
            if(this.a[i] != null){
                seq[cont] = this.a[i];
                cont++;
            }
        }
        this.a = seq;
        return cont;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        int pos = insertPosition;
        if(this.insertPosition >= this.a.length){
            pos = resize(this.a.length * 2);
        }
        this.a[pos] = item;
        this.insertPosition = pos + 1;
        this.n++;
    }

    private void verify(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
    }

    private int findRandom(){
        int val = this.insertPosition;
        if(insertPosition == 0){
            val = this.n;
        }
        int pos = StdRandom.uniformInt(0, val);
        while(a[pos] == null){
            pos = StdRandom.uniformInt(0, val);
        }
        return pos;
    }

    // remove and return a random item
    public Item dequeue(){
        verify();
        int pos = findRandom();

        Item i = a[pos];
        a[pos] = null;
        this.n--;

        if(this.n > 0 && this.n == this.a.length/4){
            this.insertPosition = resize(this.a.length/2);
        }
        return i;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        verify();
        int pos = findRandom();
        return this.a[pos];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new IteratorList();
    }

    private class IteratorList implements Iterator<Item>{

        private int cont = 0, quant = 0;
        private int listaItems [] = StdRandom.permutation(a.length);

        public boolean hasNext(){
            return this.quant < n;
        }

        public Item next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Item i = a[listaItems[this.cont]];
            while(i == null){
                cont++;
                i = a[listaItems[this.cont]];
            }
            this.cont++;
            this.quant++;
            return i;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println(rq.isEmpty());
        rq.enqueue(34);
        StdOut.println(rq.isEmpty());
        StdOut.println(rq.size());

        rq.enqueue(45);
        exibir(rq);

        StdOut.println(rq.dequeue());
        StdOut.println();
        exibir(rq);

        rq.enqueue(46);
        rq.enqueue(35);
        rq.enqueue(98);

        StdOut.println(rq.sample());
        StdOut.println();
        exibir(rq);
    }

    private static void exibir(RandomizedQueue<Integer> d){
        for(Integer i: d){
            StdOut.println(i);
        }
        StdOut.println();
    }
}