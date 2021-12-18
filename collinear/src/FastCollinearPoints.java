import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Run:  javac-algs4 FastCollinearPoints.java && java-algs4 FastCollinearPoints
 */
public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    private int lineCount;
    private Point[][] linePoints;

    /**
     * Finds all line segments containing 4 points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        final Point[] inPoints = this.getPoints(points);
        lineCount = 0;
        linePoints = new Point[0][2];

        Arrays.sort(inPoints);

        for (int i = 0; i < inPoints.length; ++i) {
            Point[] tempPoints = this.slicePoints(inPoints, i);
            Arrays.sort(tempPoints, inPoints[i].slopeOrder());
            int j = 0;
            while (j < tempPoints.length) {
                double slope = inPoints[i].slopeTo(tempPoints[j]);
                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("Two points cannot be same");
                }
                int n = 0;
                for (int m = j + 1; m < tempPoints.length && inPoints[i].slopeTo(tempPoints[m]) == slope; ++m) {
                    n++;
                }
                j += n;
                if (n > 1) {
                    this.addOrCreateLine(inPoints[i], tempPoints[j], slope);
                }
                j++;
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
     * Check the new points in the line or not, then add it into the line
     * @param startPoint
     * @param endPoint
     * @param slopeIn
     */
    private void addOrCreateLine(Point startPoint, Point endPoint, double slopeIn) {
        for (Point[] line : this.linePoints) {
            if (line[0] == null) {
                continue;
            }
            double slopeOld = line[0].slopeTo(line[1]);
            if (slopeIn != slopeOld) {
                continue;
            }

            double slope = line[0].slopeTo(startPoint);
            if ((slope == Double.NEGATIVE_INFINITY || slope == slopeOld) && slope == line[0].slopeTo(endPoint)) {
                if (line[1].compareTo(endPoint) < 0) {
                    line[1] = endPoint;
                }
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
     * @param points
     * @param i
     * @return Point[]
     */
    private Point[] slicePoints(Point[] points, int i) {
        Point[] tempPoints = new Point[points.length - i - 1];
        for (int j = 0; j < tempPoints.length; ++j) {
            tempPoints[j] = points[++i];
        }
        return tempPoints;
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
        LineSegment[] temp = this.lineSegments;
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
        StdOut.println(collinear.numberOfSegments());
    }
}
