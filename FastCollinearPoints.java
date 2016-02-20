import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
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

	// private boolean existed(LineSegment[] ls, Point a, Point b) {
	// 	LineSegment ls1 = new LineSegment(a, b);
	// 	LineSegment ls2 = new LineSegment(b, a);
	// 	for (LineSegment l : ls) {
	// 		if (l != null) {
	// 			if (l.toString().equals(ls1.toString())) return true;
	// 			if (l.toString().equals(ls2.toString())) return true;
	// 		}
	// 	}
	// 	return false;
	// }

	private boolean existed(Point[] points, int current, double slope) {
		for (int i = 0; i < current; i++) if (points[i].slopeTo(points[current]) == slope) return true;
		return false;
	}

	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
	{
		if (points == null) throw new NullPointerException("Argument to the constructor is null");
		int size = points.length;
		if (points[size-1] == null) throw new NullPointerException("Invalid point");
		for (int k = 0; k < size-1; k++) {
			if (points[k] == null) throw new NullPointerException("Invalid point");
			for (int f = k+1; f < size; f++) {
				if (points[k].compareTo(points[f]) == 0) throw new IllegalArgumentException("Duplicate point");
			} 
		}
		numSegments = 0;
		LineSegment[] tmpLS = new LineSegment[size*(size-1)/12+1];
		// LineSegment[] endLS = new LineSegment[size*(size-1)/12+1]; 
		//Point[] tmpPoints = Arrays.copyOf(points, size);
		Point[] tmpPoints = new Point[size];
		for (int i=0; i<size; i++) tmpPoints[i] = points[i];
		Point[] newPoints = new Point[size];
		sort(tmpPoints);
		for (int k = 0; k < size-3; k++) {
			Point p = tmpPoints[k];	
			for (int i = 0; i < size; i++) newPoints[i] = tmpPoints[i];
			Arrays.sort(newPoints, k+1, size, p.slopeOrder());
			for (int i = size-1; i>k+2; i--) {
				if (p.slopeTo(newPoints[i]) == p.slopeTo(newPoints[i-2])) {
					// if (!existed(endLS, newPoints[i-1], newPoints[i])) {
					double st = p.slopeTo(newPoints[i]);
					if (!existed(newPoints, k, st)) {
						// endLS[numSegments] = new LineSegment(newPoints[i-1], newPoints[i]);
						tmpLS[numSegments++] = new LineSegment(p, newPoints[i]);
					}
					int j = i-2;
					while (p.slopeTo(newPoints[j-1]) == st) j--;
					i = j;
				}
			}
		}
		ls = new LineSegment[numSegments];
		for (int i=0; i<numSegments; i++) ls[i] = tmpLS[i];
	}

	public int numberOfSegments() { return numSegments; }       // the number of line segments
	public LineSegment[] segments() { return ls; }					// the line segments               

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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	}
}