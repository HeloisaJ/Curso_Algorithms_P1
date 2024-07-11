import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable;
    private LinkedList<Board> answer;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null){
            throw new IllegalArgumentException();
        }

        this.answer = new LinkedList<>();
        this.solvable = false;
        solve(initial);
    }

    private void solve(Board board){
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twins = new MinPQ<SearchNode>();
        Iterable<Board> neighbors;

        SearchNode node2, node = new SearchNode(board, 0), t;
        t = new SearchNode(node.getBoard().twin(), 0);
        pq.insert(node);
        twins.insert(t);

        while(!pq.isEmpty()){
            node = pq.delMin();
            t = twins.delMin();

            if(node.board.isGoal()){
                makeAnswer(node);
                this.solvable = true;
                break;
            }
            if(node.board.twin().isGoal()){
                break;
            }

            neighbors = node.getBoard().neighbors();
            for(Board b: neighbors){
                node2 = new SearchNode(b, node.getMoves() + 1);
                node2.setLastBoard(node);

                if((node.getLastBoard() != null && !node2.getBoard().equals(node.lastBoard.getBoard())) || node.getLastBoard() == null){
                    pq.insert(node2);
                }
            }

            neighbors = t.getBoard().neighbors();
            for(Board b: neighbors){
                node2 = new SearchNode(b, t.getMoves() + 1);
                node2.setLastBoard(t);

                if((t.getLastBoard() != null && !node2.getBoard().equals(t.lastBoard.getBoard())) || t.getLastBoard() == null){
                    twins.insert(node2);
                }
            }
        }
    }

    private void makeAnswer(SearchNode minSolved){
        SearchNode sn = minSolved;
        
        while(sn != null){
            this.answer.addFirst(sn.getBoard());
            sn = sn.getLastBoard();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return this.answer.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(!this.solvable){
            return null;
        }
        return this.answer;
    }

    private class SearchNode implements Comparable<SearchNode>{

        private Board board;
        private int moves;
        private SearchNode lastBoard;

        public SearchNode(Board board, int moves){
            this.board = board;
            this.moves = moves;
            this.lastBoard = null;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getLastBoard() {
            return lastBoard;
        }

        public void setLastBoard(SearchNode lastBoard) {
            this.lastBoard = lastBoard;
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.board.manhattan() + this.moves) - (o.board.manhattan() + o.moves);
        }
        
    }

    // test client (see below) 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
    
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}