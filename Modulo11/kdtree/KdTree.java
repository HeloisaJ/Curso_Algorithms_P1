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
        if(!this.isEmpty()){
            RectHV rec = new RectHV(0, 0, 1, 1);
            Node res = searchNearest(p, root, root, rec, 0);
            return res.point;
        }
        return null;
    }

    private Node searchNearest(Point2D p, Node n, Node closest, RectHV rec, int level){
        if(n == null){
            return closest;
        }

        double dist = closest.point.distanceSquaredTo(p);

        if(dist < rec.distanceSquaredTo(p) && !closest.point.equals(n.point)){
            return closest;
        }

        if(n.point.distanceSquaredTo(p) < dist){
            closest = n;
        }

        RectHV rR, rL;
        if(level == 0 || level % 2 == 0){
            rL = new RectHV(rec.xmin(), rec.ymin(), n.point.x(), rec.ymax());
            rR = new RectHV(n.point.x(), rec.ymin(), rec.xmax(), rec.ymax());
        }
        else{
            rL = new RectHV(rec.xmin(), rec.ymin(), rec.xmax(), n.point.y());
            rR = new RectHV(rec.xmin(), n.point.y(), rec.xmax(), rec.ymax());
        }

        double distRL = rL.distanceSquaredTo(p), distRR = rR.distanceSquaredTo(p);
        
        if(distRL < distRR){
            closest = searchNearest(p, n.left, closest, rL, level + 1);
            closest = searchNearest(p, n.right, closest, rR, level + 1);
        }
        else{
            closest = searchNearest(p, n.right, closest, rR, level + 1);
            closest = searchNearest(p, n.left, closest, rL, level + 1);
        }

        return closest;
    }
 
    public static void main(String[] args){ // unit testing of the methods (optional) 
        KdTree k = new KdTree();
        k.insert(new Point2D(0.4375, 0.0));
        k.insert(new Point2D(0.625, 0.3125));
        k.insert(new Point2D(0.9375, 0.0625));
        k.insert(new Point2D(0.375, 0.9375));
        k.insert(new Point2D(0.25, 0.375));
        k.insert(new Point2D(0.5, 0.875));
        k.insert(new Point2D(0.0625, 1.0));
        k.insert(new Point2D(0.5625, 0.75));
        k.insert(new Point2D(1.0, 0.8125));
        k.insert(new Point2D(0.8125, 0.125));
        StdOut.print(k.nearest(new Point2D(0.3125, 0.25)));
    }
 }