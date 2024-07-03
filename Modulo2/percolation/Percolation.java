import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private boolean mark[][]; // true => white, false => black
    private int cont;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0){
          throw new IllegalArgumentException();  
        }
        this.n = n;
        this.cont = 0;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.mark = new boolean[n][n];
    }

    private boolean verificar(int row, int col){
        if(row <= 0 || row > n || col <= 0 || col > n){
            return false;
        }
        return true;
    }

    private int formula(int row, int col){
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(!verificar(row, col)){
            throw new IllegalArgumentException();
        }
        else if(!isOpen(row, col)){
            int pos = formula(row, col), other;
            if(row == 1){
                this.uf.union(0, pos);   
            }
            else if(isOpen(row - 1, col)){
                other = formula(row - 1, col);
                this.uf.union(pos, other);
            }
            if(row == n){
                this.uf.union(pos, n * n + 1);
            }
            else if(isOpen(row + 1, col)){
                other = formula(row + 1, col);
                this.uf.union(pos, other);
            }
            if(col > 1 && isOpen(row, col - 1)){
                other = formula(row, col - 1);
                this.uf.union(pos, other);
            }
            if(col < n && isOpen(row, col + 1)){
                other = formula(row, col + 1);
                this.uf.union(pos, other);
            }
            this.mark[row - 1][col - 1] = true;
            this.cont++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(!verificar(row, col)){
            throw new IllegalArgumentException();
        }
        return this.mark[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(!verificar(row, col)){
            throw new IllegalArgumentException();
        }
        int pos = formula(row, col);
        return this.uf.find(0) == this.uf.find(pos);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.cont;
    }

    // does the system percolate?
    public boolean percolates(){
        return this.uf.find(0) == this.uf.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args){

    }
}