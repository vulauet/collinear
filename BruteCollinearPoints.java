import java.util.Iterator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
	private LineSegment[] ls;
	private int numSegments;

	private void sort(Point[] points) {
		for (int i=0; i<points.length-1; i++) {
			for (int j=i+1; j<points.length; j++) {
				if (points[i].compareTo(points[j]) < 0) {
					Point tmp = points[i];
					points[i] = points[j];
					points[j] = tmp;
				}
			}
		}
	}

	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if (points == null) throw new NullPointerException("Argument to the constructor is null");
		int size = points.length;
		numSegments = 0;
		LineSegment[] tmpLS = new LineSegment[size*(size-1)/6+1];
		for (int k=0; k<size; k++) if (points[k] == null) throw new NullPointerException("Invalid point");
		sort(points);
		for (int i=0; i<size-3; i++) {
			for (int j=i+1; j<size-2; j++) {
				for (int p=j+1; p<size-1; p++) {
					if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[p])) {
						for (int q=p+1; q<size; q++) {
							if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[q])) {
								tmpLS[numSegments++] = new LineSegment(points[i], points[q]);
							}
						}
					}
				}
			}
		}
		ls = new LineSegment[numSegments];
		for (int i=0; i<numSegments; i++) ls[i] = tmpLS[i];
	}

	public           int numberOfSegments() { return numSegments; }        // the number of line segments
	public LineSegment[] segments() { return ls; }               // the line segments

	public static void main(String[] args) {

	    // read the N points from a file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    Point[] points = new Point[N];
	    for (int i = 0; i < N; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.show(0);
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
	}
}