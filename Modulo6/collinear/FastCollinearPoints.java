import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

    private LineSegment[] lines;

    public FastCollinearPoints(Point[] points){ // finds all line segments containing 4 or more points
        if(points == null){
            throw new IllegalArgumentException();
        }

        Point [] sq = Arrays.copyOf(points, points.length);
        verifyAllPoints(sq);

        this.lines = new LineSegment[0];

        if(sq.length > 3){
            operation(sq);
        }

    }

    private void operation(Point[] sq){ // This code is actually not working as espected
        int quantS;
        Arrays.sort(sq);
        Point [] cp = Arrays.copyOf(sq, sq.length);
        double s1, s2;
        Point p1, p2;

        ArrayList<LineSegment> lin = new ArrayList<LineSegment>();

        for(int i = 0; i < sq.length; i++){

            quantS = 2;
            Arrays.sort(cp, sq[i].slopeOrder());

            p1 = cp[1];
            s1 = p1.slopeTo(sq[i]);

            for(int j = 2; j < cp.length; j++){

                p2 = cp[j];
                s2 = p2.slopeTo(sq[i]);

                if(s1 != s2){
                    if(quantS >= 4){
                        lin.add(new LineSegment(sq[i], p1));
                    }
                    quantS = 2;
                }
                else if(sq[i].compareTo(p2) < 0){
                    quantS++;
                }
                else{
                    quantS = 2;
                }
                p1 = p2;
            }

            if(quantS >= 4){
                lin.add(new LineSegment(sq[i], p1));
            }
        }

        this.lines = lin.toArray(new LineSegment[lin.size()]);
    }

    private void verifyAllPoints(Point[] points){

        for(int i = 0; i < points.length; i++){
            verifyPoint(points[i]);
        }

        Arrays.sort(points);

        for(int i = 0; i < points.length - 1; i++){
            if(points[i].compareTo(points[i + 1]) == 0){
                throw new IllegalArgumentException();
            }
        }
    }

    private void verifyPoint(Point p){
        if(p == null){
            throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments(){ // the number of line segments
        return this.lines.length;
    }

    public LineSegment[] segments(){ // the line segments
        return this.lines;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}