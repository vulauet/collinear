public class BruteCollinearPoints {
    private LineSegment[] ls;
    private int numOfSegments;
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        int len = points.length;
        numOfSegments = 0;
        ls = new LineSegment[len*(len-1)*(len-2)*(len-3)/24 + 1];
        for (int i=0; i<len-3; i++) {
            for (int j=1; i<len-2; j++) {
                for (int k=2; k<len-1; k++) {
                    if (points[i].slopeOrder(points[j], points[k]) != 0) continue;
                        for (int l=3; l<len; l++) {
                            if (points[i].slopeOrder(points[j], points[l]) == 0) 
                                    ls[numOfSegments++] = new LineSegment(points[i], points[l]);
                        }
                }
            }
         }
    }
   
   public int numberOfSegments()                 // the number of line segments
   {
       return numOfSegments;
   }
   
   public LineSegment[] segments()                // the line segments
   {
       return ls;
   }
   
   public static void main(String[] args) {
       In in = new In(args[0]);
       int N = in.readInt();
       Point[] points = new Point[N];
       for (int i = 0; i < N; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }
    
       StdDraw.show(0);
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (Point p : points) {
           p.draw();
       }
       StdDraw.show();
    
       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
   }
}
