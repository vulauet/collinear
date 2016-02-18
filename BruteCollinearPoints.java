import java.util.Iterator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
	private LineSegment[] ls;
	private int numSegments;

	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if (points == null) throw new NullPointerException("Argument to the constructor is null");
		int size = points.length;
		System.out.println(size);
		numSegments = 0;
		//ls = new LineSegment[size*(size-1)/6+1];
		ls = new LineSegment[size*size*size*size];
		for (int i=0; i<size; i++) if (points[i] == null) throw new NullPointerException("Invalid point");
		for (int i=0; i<size-3; i++) {
			for (int j=i+1; j<size-2; j++) {
				for (int p=j+1; p<size-1; p++) {
					if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[p])) {
						for (int q=p+1; q<size; q++) {
							if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[q]) && !Arrays.asList(ls).contains(new LineSegment(points[p], points[q])))
								ls[numSegments++] = new LineSegment(points[p], points[q]);
							else if (Arrays.asList(ls).contains(new LineSegment(points[p], points[q])))
								System.out.println("existed");
						}
					}
				}
			}
		}
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