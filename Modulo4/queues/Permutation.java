import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]), cont = 0;
        String s;
        while(!StdIn.isEmpty()){
            s = StdIn.readString();
            rq.enqueue(s);
        }
        for(String res : rq){
            StdOut.println(res);
            cont++;
            if(cont == k){
                break;
            }
        }
    }
 }