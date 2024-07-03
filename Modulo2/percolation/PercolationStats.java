import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private double values[];
    private int trials;
    private int n;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){ // OK
        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.n = n;
        this.values = new double[trials];
        percolateTrials();
    }

    private void percolateTrials(){
        Percolation perc;
        for(int i = 0; i < trials; i++){
            perc = new Percolation(n);

            while(!perc.percolates()){ 
                perc.open(StdRandom.uniformInt(1, n + 1), StdRandom.uniformInt(1, n + 1));
            }
            values[i] = (double) perc.numberOfOpenSites()/(n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){ // OK
        return StdStats.mean(values);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){ // OK
        return StdStats.stddev(values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){ // OK
        double me = mean(), dev = stddev(), v;
        v = (1.96 * dev)/Math.sqrt(this.trials);
        return me - v;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){ // OK
        double me = mean(), dev = stddev(), v;
        v = (1.96 * dev)/Math.sqrt(this.trials);
        return me + v;
    }

   // test client (see below)
   public static void main(String[] args){ // OK
    PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    StdOut.println("mean                    = " + p.mean());
    StdOut.println("stddev                  = " + p.stddev());
    StdOut.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
   }
}
