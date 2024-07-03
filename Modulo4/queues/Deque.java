import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last; 
    private int n;

    private class Node{
        Item item;
        Node before;
        Node after;
    }

    // construct an empty deque
    public Deque(){
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return this.n == 0;
    }

    // return the number of items on the deque
    public int size(){
        return this.n;
    }

    private void verifyItem(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
    }

    // add the item to the front
    public void addFirst(Item item){
        verifyItem(item);
        Node n = new Node();
        if(this.n == 0){
            n.item = item;
            this.first = n;
            this.last = n;
        }
        else{
            Node n2 = new Node();
            n = this.first;
            n2.item = item;

            this.first = n2;
            this.first.after = n;
            n.before = this.first;
        }
        this.n++;
    }

    // add the item to the back
    public void addLast(Item item){
        verifyItem(item);
        Node n = new Node();
        if(this.n == 0){
            n.item = item;
            this.first = n;
            this.last = n;
        }
        else{
            Node n2 = new Node();
            n = this.last;
            n2.item = item;

            this.last = n2;
            this.last.before = n;
            n.after = this.last;
        }
        this.n++;
    }

    private void verifyRemove(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        verifyRemove();
        Item i = this.first.item;
        if(this.size() == 1){
            this.first = null;
            this.last = null;
        }
        else{
            this.first = this.first.after;
            this.first.before = null;
        }
        this.n--;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast(){
        verifyRemove();
        Item i = this.last.item;
        if(this.size() == 1){
            this.first = null;
            this.last = null;
        }
        else{
            this.last = this.last.before;
            this.last.after = null;
        }
        this.n--;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new IteratorList();
    }

    private class IteratorList implements Iterator<Item>{

        private Node current = first;

        public boolean hasNext(){
            return current != null;
        }

        public Item next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Item i = current.item;
            current = current.after;
            return i;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> d = new Deque<Integer>();
        StdOut.println(d.isEmpty());
        d.addFirst(3);
        StdOut.println(d.isEmpty());
        d.addFirst(4);
        
        exibir(d);
        StdOut.println();

        d.addLast(9);
        exibir(d);
        StdOut.println(d.size());
        d.addLast(10);
        exibir(d);

        d.removeFirst();
        StdOut.println(d.size());
        exibir(d);

        d.removeLast();
        StdOut.println(d.size());
        exibir(d);
    }

    private static void exibir(Deque<Integer> d){
        for(Integer i: d){
            StdOut.println(i);
        }
        StdOut.println();
    }

}