import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
	private LineSegment[] ls;
	private int numSegments;

	// private boolean existed(Point[] points, int current, double slope) {
	// 	for (int i = 0; i < current; i++) if (points[i].slopeTo(points[current]) == slope) return true;
	// 	return false;
	// }

	private boolean existed(double[] slope, int current, int index) {
		for (int i = 0; i < current; i++) if (slope[i] == slope[index]) return true;
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
		Point[] tmpPoints = new Point[size];
		for (int i=0; i<size; i++) tmpPoints[i] = points[i];
		Point[] newPoints = new Point[size];
		Arrays.sort(tmpPoints);
		double[] slope = new double[size];
		for (int k = 0; k < size-3; k++) {
			Point p = tmpPoints[k];	
			for (int i = 0; i < size; i++) newPoints[i] = tmpPoints[i];
			Arrays.sort(newPoints, k+1, size, p.slopeOrder());

			for (int i = 0; i < size; i++) slope[i] = p.slopeTo(newPoints[i]);
			for (int i = size-1; i > k+2; i--) {
				if (slope[i] == slope[i-2]) {
					if (!existed(slope, k, i)) tmpLS[numSegments++] = new LineSegment(p, newPoints[i]);
					int j =  i - 2;
					while (slope[j-1] == slope[i]) j--;
					i = j;
				}
			}

			// for (int i = 0; i < size-k-1; i++) slope[i] = p.slopeTo(newPoints[k+i+1]);
			// for (int i = size-k-2; i >= 2; i--) {
			// 	if (slope[i] == slope[i-2]) {
			// 		if (!existed(newPoints, k, slope[i])) tmpLS[numSegments++] = new LineSegment(p, newPoints[k+i+1]);
			// 		int j = i - 2;
			// 		while (j > 0 && slope[j-1] == slope[i]) j--;
			// 		i = j;
			// 	}
			// }

			// for (int i = size-1; i>k+2; i--) {
			// 	if (p.slopeTo(newPoints[i]) == p.slopeTo(newPoints[i-2])) {
			// 		double st = p.slopeTo(newPoints[i]);
			// 		if (!existed(newPoints, k, st)) 
			// 			tmpLS[numSegments++] = new LineSegment(p, newPoints[i]);
			// 		int j = i-2;
			// 		while (p.slopeTo(newPoints[j-1]) == st) j--;
			// 		i = j;
			// 	}
			// }
		}
		ls = new LineSegment[numSegments];
		for (int i=0; i<numSegments; i++) ls[i] = tmpLS[i];
		// System.out.println(numSegments + " " + size);
	}

	public int numberOfSegments() { return numSegments; }       // the number of line segments
	public LineSegment[] segments() 					// the line segments               
	{
		LineSegment[] newLS = new LineSegment[numSegments];
		for (int i = 0; i < numSegments; i++) newLS[i] = ls[i]; 
		return newLS; 
	}

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