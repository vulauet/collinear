import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
	private LineSegment[] ls;
	private int numSegments;

	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
	{
		if (points == null) throw new NullPointerException("Argument to the constructor is null");
		int size = points.length;
		numSegments = 0;
		LineSegment[] tmpLS = new LineSegment[size*(size-1)/6+1];
		for (int k=0; k<size; k++) if (points[k] == null) throw new NullPointerException("Invalid point");
		Point[] newPoints = points.clone();
		for (Point p : points) {
			Arrays.sort(newPoints, p.slopeOrder());
			for (int i = size-1; i>1; i--) {
				// System.out.print(i + " ");
				if (p.slopeTo(newPoints[i]) == p.slopeTo(newPoints[i-2])) {
					tmpLS[numSegments++] = new LineSegment(p, newPoints[i]);
					int j = i-2;
					while (p.slopeTo(newPoints[j-1]) == p.slopeTo(points[i])) j--;
					i = j;
				} 
			}
		//	newPoints = ArrayUtils.removeElement(newPoints, p);
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