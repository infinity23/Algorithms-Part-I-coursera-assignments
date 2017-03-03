import java.util.Arrays;

public class BruteCollinearPoints {
    private int n;
    private Point[] copy;

    // finds all line segments containing 4 copy
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException();
        for (Point p : points)
            if (p == null)
                throw new NullPointerException();
        Point[] copy = new Point[points.length];
        System.arraycopy(points, 0, copy, 0, points.length);
        Arrays.sort(copy);
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].slopeTo(copy[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();
        }

        this.copy = copy;
    }

    // the number of line segments
    public int numberOfSegments() {
        return n;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[copy.length];
        int h = 0;
        for (int i = 0; i < copy.length - 3; i++)
            for (int j = i + 1; j < copy.length - 2; j++) {
                double s1 = copy[i].slopeTo(copy[j]);
                for (int k = j + 1; k < copy.length - 1; k++) {
                    double s2 = copy[i].slopeTo(copy[k]);
                    for (int l = k + 1; l < copy.length; l++) {
                        double s3 = copy[i].slopeTo(copy[l]);
                        if (s1 == s2 && s1 == s3) {
                            n++;
                            segments[h++] = new LineSegment(copy[i], copy[l]);
                        }
                    }
                }
            }
        LineSegment[] trimSegs = new LineSegment[n];
        System.arraycopy(segments, 0, trimSegs, 0, n);
        return trimSegs;
    }
}
