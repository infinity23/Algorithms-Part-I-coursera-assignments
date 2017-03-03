import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D aSet : set) {
            aSet.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        SET<Point2D> rangeSet = new SET<>();
        for (Point2D p : set) {
            if (rect.contains(p))
                rangeSet.add(p);
        }
        return rangeSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        double mindist = 2;
        Point2D nearestPoint = null;
        for (Point2D point : set) {
            if (point.distanceSquaredTo(p) < mindist) {
                nearestPoint = point;
                mindist = point.distanceSquaredTo(p);
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        PointSET kdTree = new PointSET();
        Point2D p1 = new Point2D(0.8, 0.5);
        Point2D p2 = new Point2D(0.7, 0.4);
        Point2D p3 = new Point2D(0.6, 0.3);
        Point2D p4 = new Point2D(0.5, 0.2);
        Point2D p5 = new Point2D(0.1, 0.1);
        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        Iterable<Point2D> queue = kdTree.range(new RectHV(0, 0, 0.7, 1));
        for (Point2D p : queue) {
            System.out.println(p);
        }
        System.out.println("nearest:");
        System.out.println(kdTree.nearest(new Point2D(0.51, 0.21)));
    }
}