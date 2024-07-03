import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String s, res = "";
        double p, i = 1.0;
        while(!StdIn.isEmpty()){
            s = StdIn.readString();
            p = 1.0/i;
            if(StdRandom.bernoulli(p)){
                res = s;
            }
            i++;
        }
        StdOut.println(res);
    }
}