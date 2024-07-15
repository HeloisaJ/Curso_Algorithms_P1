import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private Node root;
    private int sizeKd;

    private class Node{

        private Point2D point;
        private Node left;
        private Node right;
        
        public Node(Point2D p){
            this.point = p;
            this.left = null;
            this.right = null;
        }
    }

    public KdTree(){ // construct an empty set of points 
        this.root = null;
        this.sizeKd = 0;
    }

    public boolean isEmpty(){ // is the set empty? 
        return this.root == null;
    }

    public int size(){ // number of points in the set 
        return this.sizeKd;
    }

    private void verifyIfNull(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
    }

    public void insert(Point2D p){ // add the point to the set (if it is not already in the set)
        Node n = this.root;
        verifyIfNull(p);

        if(!this.contains(p)){
            this.root = insert(p, n, 0);
        }
    }

    private Node insert(Point2D p, Node n, int level){

        if(n == null){
            Node node = new Node(p);
            this.sizeKd++;
            return node;
        }
        if(level == 0 || level % 2 == 0){
            if(p.x() < n.point.x()){
                n.left = insert(p, n.left, level + 1);
            }
            else{
                n.right = insert(p, n.right, level + 1);
            }
        }
        else{
            if(p.y() < n.point.y()){
                n.left = insert(p, n.left, level + 1);
            }
            else{
                n.right = insert(p, n.right, level + 1);
            }
        }

        return n;
    }

    public boolean contains(Point2D p){ // does the set contain point p? 
        int level = 0;
        Node n = this.root;
        verifyIfNull(p);

        while(n != null){
            if(n.point.equals(p)){
                return true;
            }
            else if(level == 0 || level % 2 == 0){
                n = compareValues(p.x(), n.point.x(), n);
            }
            else{
                n = compareValues(p.y(), n.point.y(), n);
            }
            level++;
        }

        return false;
    }

    private Node compareValues(double p, double vn, Node n){
        if(p < vn){
            n = n.left;
        }
        else{
            n = n.right;
        }
        return n;
    }

    public void draw(){ // draw all points to standard draw 
        if(!this.isEmpty()){
            draw(this.root, 1, 0, 1, 0, 0);
        }
    }

    private void draw(Node n, double maxX, double minX, double maxY, double minY, int level){
        if(n == null){
            return;
        }

        if(level == 0 || level % 2 == 0){ // vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.point.x(), minY, n.point.x(), maxY);

            draw(n.left, n.point.x(), minX, maxY, minY, level + 1);
            draw(n.right, maxX, n.point.x(), maxY, minY, level + 1);
        }
        else{ // horizontal
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, n.point.y(), maxX, n.point.y());

            draw(n.left, maxX, minX, n.point.y(), minY, level + 1);
            draw(n.right, maxX, minX, maxY, n.point.y(), level + 1);
        }
        StdDraw.setPenColor();
        n.point.draw();
    }

    public Iterable<Point2D> range(RectHV rect){ // all points that are inside the rectangle (or on the boundary) 
        if(rect == null){
            throw new IllegalArgumentException();
        }
        LinkedList<Point2D> pointsInside = new LinkedList<>();
        searchRect(rect, root, 1, 0, 1, 0, pointsInside, 0);
        return pointsInside;
    }

    private void searchRect(RectHV rect, Node n, double maxX, double minX, double maxY, double minY, LinkedList<Point2D> pointsInside, int level){
        if(n == null){
            return;
        }

        RectHV re = new RectHV(minX, minY, maxX, maxY);
        if(!rect.intersects(re)){
            return;
        }
        if(rect.contains(n.point)){
            pointsInside.add(n.point);
        }

        if(level == 0 || level % 2 == 0){
            searchRect(rect, n.left, n.point.x(), minX, maxY, minY, pointsInside, level + 1);
            searchRect(rect, n.right, maxX, n.point.x(), maxY, minY, pointsInside, level + 1);
        }
        else{
            searchRect(rect, n.left, maxX, minX, n.point.y(), minY, pointsInside, level + 1);
            searchRect(rect, n.right, maxX, minX, maxY, n.point.y(), pointsInside, level + 1);
        }
    }

    public Point2D nearest(Point2D p){ // a nearest neighbor in the set to point p; null if the set is empty 
        verifyIfNull(p);
        return p;
    }
 
    public static void main(String[] args){ // unit testing of the methods (optional) 
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }
    }
 }