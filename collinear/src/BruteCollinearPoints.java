import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Run:  javac-algs4 BruteCollinearPoints.java && java-algs4 BruteCollinearPoints
 */
public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    private int lineCount;
    private Point[][] linePoints;

    /**
     * Finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        final Point[] inPoints = this.getPoints(points);
        lineCount = 0;
        linePoints = new Point[0][2];

        Arrays.sort(inPoints);

        for (int i = 0; i < inPoints.length; ++i) {
            for (int k = i + 1; k < inPoints.length; ++k) {
                if (inPoints[i].compareTo(inPoints[k]) == 0) {
                    throw new IllegalArgumentException("Two points cannot be same");
                }
                this.setLineSegment(inPoints, i, k);
            }
        }

        lineSegments = new LineSegment[lineCount];
        int i = 0;
        for (Point[] line : this.linePoints) {
            if (line[0] != null && line[1] != null) {
                this.lineSegments[i++] = new LineSegment(line[0], line[1]);
            }
        }
    }

    /**
     * @param points
     */
    private Point[] getPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points cannot be null");
        }
        Point[] newPoints = new Point[points.length];
        int i = 0;
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Point cannot be null");
            }
            newPoints[i++] = point;
        }

        return newPoints;
    }

    /**
     * @param points
     * @param i
     * @param k
     */
    private void setLineSegment(Point[] points, int i, int k) {
        Point point1 = points[i];
        Point point2 = points[k];
        final Point startPoint = point1;
        final double slope = startPoint.slopeTo(point2);
        if (point1.compareTo(point2) > 0) {
            Point tmpPoint = point1;
            point1 = point2;
            point2 = tmpPoint;
        }

        int j = 0;
        for (int n = k+1; n < points.length; n++) {
            if (n == i || n == k) {
                continue;
            }
            if (slope == startPoint.slopeTo(points[n])) {
                if (points[n].compareTo(point1) < 0) {
                    point1 = points[n];
                }
                if (points[n].compareTo(point2) > 0) {
                    point2 = points[n];
                }
                j++;
                if (j > 1) {
                    this.addOrCreateLine(point1, point2, slope);
                    return;
                }
            }
        }
    }

    /**
     * Check the new points in the line or not, then add it into the line
     * @param startPoint
     * @param endPoint
     * @param slopeIn
     */
    private void addOrCreateLine(Point startPoint, Point endPoint, double slopeIn) {
        for (Point[] line : this.linePoints) {
            if (line[0] == null || slopeIn != line[0].slopeTo(line[1])) {
                continue;
            }
            Point[] tempPoints = {line[0], line[1], startPoint, endPoint};
            Arrays.sort(tempPoints);
            double slope = tempPoints[0].slopeTo(tempPoints[1]);
            if (
                    (slope == Double.NEGATIVE_INFINITY || slope == tempPoints[0].slopeTo(tempPoints[2]))
                            && tempPoints[0].slopeTo(tempPoints[2]) == tempPoints[0].slopeTo(tempPoints[3])
            ) {
                line[0] = tempPoints[0];
                line[1] = tempPoints[3];
                return;
            }
        }

        if (lineCount == this.linePoints.length) {
            if (this.linePoints.length == 0) {
                resize(4);
            }
            else {
                resize(this.linePoints.length + 4);
            }
        }
        this.linePoints[lineCount++] = new Point[]{startPoint, endPoint};
    }

    private void resize(int cap)
    {
        Point[][] temp = new Point[cap][2];
        int max = cap;
        if (this.linePoints.length < max) {
            max = this.linePoints.length;
        }
        for (int i = 0; i < max; ++i) {
            temp[i] = this.linePoints[i];
        }
        this.linePoints = temp;
    }

    /**
     * Return the number of line segments
     * @return
     */
    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    /**
     * Return the line segments
     * @return
     */
    public LineSegment[] segments() {
        final LineSegment[] temp = this.lineSegments;
        return temp;
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
        StdDraw.setPenColor(0, 0, 255);
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
        StdOut.println(collinear.numberOfSegments());
    }
}
