import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {

    private LineSegment[] lines;

    public BruteCollinearPoints(Point[] points){ // finds all line segments containing 4 points
        if(points == null){
            throw new IllegalArgumentException();
        }
        
        for(int i = 0; i < points.length; i++){
            verifyPoint(points[i]);
        }

        Point [] sq = Arrays.copyOf(points, points.length);
        verifyAllPoints(sq);

        this.lines = new LineSegment[0];

        if(sq.length > 3){
            operation(sq);
        }
    }

    private void verifyAllPoints(Point[] sq){
        Arrays.sort(sq);

        for(int i = 0; i < sq.length - 1; i++){
            if(sq[i].compareTo(sq[i + 1]) == 0){
                throw new IllegalArgumentException();
            }
        }
    }

    private void operation(Point[] sq){
        double s1, s2, s3;

        ArrayList<LineSegment> lin = new ArrayList<>();

        for(int i = 0; i < sq.length; i++){
            
            for(int j = i + 1; j < sq.length; j++){
                s1 = sq[i].slopeTo(sq[j]);
                

                for(int k = j + 1; k < sq.length; k++){
                    s2 = sq[i].slopeTo(sq[k]);

                    if(Double.compare(s1, s2) == 0){
    
                        for(int f = k + 1; f < sq.length; f++){
                            s3 = sq[i].slopeTo(sq[f]);

                            if(Double.compare(s1, s3) == 0){
                                lin.add(new LineSegment(sq[i], sq[f]));
                            }
                        }
    
                    }
    
                }
            }
        }

        this.lines = lin.toArray(new LineSegment[lin.size()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}