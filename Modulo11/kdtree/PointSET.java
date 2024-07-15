import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> points;

    public PointSET(){ // construct an empty set of points 
        this.points = new SET<>();
    }

    public boolean isEmpty(){ // is the set empty? 
        return this.points.isEmpty();
    }

    public int size(){ // number of points in the set 
        return this.points.size();
    }

    public void insert(Point2D p){ // add the point to the set (if it is not already in the set)
        verifyIfNull(p);
        if(!this.contains(p)){
            this.points.add(p);
        }
    }

    public boolean contains(Point2D p){ // does the set contain point p? 
        verifyIfNull(p);
        return this.points.contains(p);
    }

    private void verifyIfNull(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
    }

    public void draw(){ // draw all points to standard draw 
        for(Point2D p: this.points){
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect){ // all points that are inside the rectangle (or on the boundary) 
        if(rect == null){
            throw new IllegalArgumentException();
        }
        
        LinkedList<Point2D> pointsInside = new LinkedList<>();
        for(Point2D p: this.points){
            if(rect.contains(p)){
                pointsInside.add(p);
            }
        }
        return pointsInside;
    }

    public Point2D nearest(Point2D p){ // a nearest neighbor in the set to point p; null if the set is empty 
        Point2D closest = null;
        double smallestDist = Double.MAX_VALUE, actual;
        verifyIfNull(p);

        for(Point2D po: this.points){

            actual = po.distanceTo(p);
            if(actual < smallestDist){
                smallestDist = actual;
                closest = po; 
            }
        }
        return closest;
    }
 
    public static void main(String[] args){ // unit testing of the methods (optional) 

    }
 }