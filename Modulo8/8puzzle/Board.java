import java.util.ArrayList;

public class Board {

    private final int[][] tiles;
    private final int n;
    private int x0, y0, manhattanP;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.tiles = tiles;
        this.n = tiles.length;
        this.manhattanP = -1;

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){
                if(this.tiles[i][j] == 0){
                    this.x0 = i;
                    this.y0 = j;
                }
            }
        }
    }

    private Board(Board b){
        this.tiles = new int[b.n][b.n];
        this.n = b.n;
        this.manhattanP = -1;

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){
                this.tiles[i][j] = b.tiles[i][j];

                if(this.tiles[i][j] == 0){
                    this.x0 = i;
                    this.y0 = j;
                }
            }
        }
        
    }
                                           
    // string representation of this board
    public String toString(){
        String res = "" + this.n;
        res += '\n';

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){
                res += this.tiles[i][j];

                if(j != this.n - 1){
                    res += ' ';
                }
            }
            res += '\n';
        }

        return res;
    }

    // board dimension n
    public int dimension(){
        return this.n;
    }

    // number of tiles out of place
    public int hamming(){
        int res = 0;

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){

                if(this.tiles[i][j] != (this.n * i + (j + 1)) && this.tiles[i][j] != 0){
                    res++;
                }
            }
        }

        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        if(this.manhattanP != -1){
            return this.manhattanP;
        }

        int res = 0, xp, yp;

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){

                if(this.tiles[i][j] != (this.n * i + (j + 1)) && this.tiles[i][j] != 0){
                    xp = (this.tiles[i][j] - 1)/this.n;
                    yp = (this.tiles[i][j] - 1) % this.n;

                    res += Math.abs(xp - i) + Math.abs(yp - j);
                }
            }
        }
        this.manhattanP = res;
        return res;
    }

    // is this board the goal board?
    public boolean isGoal(){
        int cont = 1, numSq = this.n * this.n;

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){

                if(cont == numSq){
                    cont = 0;
                }
                if(this.tiles[i][j] != cont){
                    return false;
                }
                cont++;

            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y == null){
            return false;
        }
        if(this.getClass() != y.getClass()){
            return false;
        }
        Board b = (Board) y;

        if(this.n != b.n){
            return false;
        }

        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.n; j++){

                if(this.tiles[i][j] != b.tiles[i][j]){
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ArrayList<Board> res = new ArrayList<>();

        if(this.x0 != 0){

            Board b = new Board(this);
            b.tiles[this.x0][this.y0] = b.tiles[this.x0 - 1][this.y0];
            b.tiles[this.x0 - 1][this.y0] = 0;
            
            b.x0 = b.x0 - 1;
            res.add(b);
        }
        if(this.x0 != this.n - 1){

            Board b = new Board(this);
            b.tiles[this.x0][this.y0] = b.tiles[this.x0 + 1][this.y0];
            b.tiles[this.x0 + 1][this.y0] = 0;
            
            b.x0 = b.x0 + 1;
            res.add(b);
        }
        if(this.y0 != 0){

            Board b = new Board(this);
            b.tiles[this.x0][this.y0] = b.tiles[this.x0][this.y0 - 1];
            b.tiles[this.x0][this.y0 - 1] = 0;

            b.y0 = b.y0 - 1;
            res.add(b);
        }
        if(this.y0 != this.n - 1){

            Board b = new Board(this);
            b.tiles[this.x0][this.y0] = b.tiles[this.x0][this.y0 + 1];
            b.tiles[this.x0][this.y0 + 1] = 0;

            b.y0 = b.y0 + 1;
            res.add(b);
        }

        return res;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){ 
        Board b = new Board(this);

        int x1, x2 = 0, y1, y2 = 0;
        if(b.tiles[0][0] != 0){
            x1 = 0;
            y1 = 0;
        }
        else{
            x1 = 1;
            y1 = 0;
        }

        if(b.tiles[0][1] != 0){
            x2 = 0;
            y2 = 1;
        }
        else{
            x1 = 1;
            y1 = 0;
        }
        return change(b, x1, x2, y1, y2);
    }

    private Board change(Board b, int x1, int x2, int y1, int y2){
        int aux = b.tiles[x1][y1];
        b.tiles[x1][y1] = b.tiles[x2][y2];
        b.tiles[x2][y2] = aux;
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int meuB [][] = new int[3][3];
        meuB[0][0] = 0;
        meuB[0][1] = 1;
        meuB[0][2] = 3;

        meuB[1][0] = 4;
        meuB[1][1] = 2;
        meuB[1][2] = 5;

        meuB[2][0] = 7;
        meuB[2][1] = 8;
        meuB[2][2] = 6;

        Board b = new Board(meuB);
        Board x = b.twin();
        Iterable<Board> it = b.neighbors();

        /*Iterable<Board> it = b.neighbors();
        for(Board y: it){
            y.toString();
        }*/
    }

}