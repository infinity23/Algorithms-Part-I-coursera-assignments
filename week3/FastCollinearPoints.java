import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] copy;
    private int n;

    // finds all line segments containing 4 or more copy
    public FastCollinearPoints(Point[] points) {
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
        int length = copy.length;
        LineSegment[] segments = new LineSegment[length];
        int h = 0;
        for (int i = 0; i < length - 3; i++) {
            Arrays.sort(copy, i + 1, length, copy[i].slopeOrder());
            circle:
            for (int j = i + 1; j < length - 2;) {
                int k = 1;
                while (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[j + k])) {
                    k++;
                    if (j + k == length)
                        break;
                }
                if (k > 2) {
                    for (int l = 0; l < i; l++) {
                        if (copy[l].slopeTo(copy[i]) == copy[l].slopeTo(copy[j])) {
                            j += k;
                            continue circle;
                        }
                    }
                    Arrays.sort(copy, j, j + k);
                    n++;
                    if (copy[j].compareTo(copy[i]) < 0) {
                        if (copy[j + k - 1].compareTo(copy[i]) > 0)
                            segments[h++] = new LineSegment(copy[j], copy[j + k - 1]);
                        else
                            segments[h++] = new LineSegment(copy[j], copy[i]);
                    } else {
                        segments[h++] = new LineSegment(copy[i], copy[j + k - 1]);
                    }
                }
                j += k;
            }
        }

        LineSegment[] trimSegs = new LineSegment[n];
        System.arraycopy(segments, 0, trimSegs, 0, n);
        return trimSegs;
    }


}