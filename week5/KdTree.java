import edu.princeton.cs.algs4.*;


public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        Node(Point2D p) {
            this.p = p;
        }
    }

    private Node root;
    private RectHV unit;
    private int n;
    private Point2D nearest;
    private double distance;


    // construct an empty set of points
    public KdTree() {
        distance = 2;
        unit = new RectHV(0, 0, 1, 1);
    }

    // is the set empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // number of points in the set
    public int size() {
        return n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        root = insert(root, p, unit, true);
        n++;
    }

    private Node insert(Node x, Point2D p, RectHV r, boolean byx) {
        int cmp;
        if (x == null) {
            Node node = new Node(p);
            node.rect = r;
            return node;
        }
        if (x.p.equals(p)) {
            n--;
            return x;
        }
        if (byx) {
            cmp = Point2D.X_ORDER.compare(p, x.p);
            if (cmp < 0)
                x.lb = insert(x.lb, p, new RectHV(r.xmin(), r.ymin(), x.p.x(), r.ymax()), false);
            if (cmp >= 0)
                x.rt = insert(x.rt, p, new RectHV(x.p.x(), r.ymin(), r.xmax(), r.ymax()), false);

            return x;
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.p);
            if (cmp < 0)
                x.lb = insert(x.lb, p, new RectHV(r.xmin(), r.ymin(), r.xmax(), x.p.y()), true);
            if (cmp >= 0)
                x.rt = insert(x.rt, p, new RectHV(r.xmin(), x.p.y(), r.xmax(), r.ymax()), true);
            return x;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean byx) {
        int cmp;
        if (x == null)
            return false;
        if (byx)
            cmp = Point2D.X_ORDER.compare(p, x.p);
        else
            cmp = Point2D.Y_ORDER.compare(p, x.p);
        if (cmp < 0)
            return contains(x.lb, p, !byx);
        else if (cmp > 0)
            return contains(x.rt, p, !byx);
        else if (!x.p.equals(p))
            return contains(x.rt, p, !byx);
        else
            return true;

    }

    // StdDraw all points to standard StdDraw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node x, boolean byx) {
        if (x == null)
            return;
        StdDraw.setPenColor(Draw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x.p.x(), x.p.y());
        if (byx) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(Draw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(Draw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        draw(x.lb, !byx);
        draw(x.rt, !byx);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, queue);
        return queue;
    }

    private void range(Node x, RectHV r, Queue<Point2D> q) {
        if (x == null)
            return;
        if (!x.rect.intersects(r))
            return;
        if (r.contains(x.p))
            q.enqueue(x.p);
        range(x.lb, r, q);
        range(x.rt, r, q);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        nearest(root, p);
        distance = 2;
        return nearest;
    }

    private void nearest(Node x, Point2D p) {
        if (x == null)
            return;
        if (x.rect.distanceSquaredTo(p) >= distance)
            return;
        if (x.p.distanceSquaredTo(p) < distance) {
            nearest = x.p;
            distance = x.p.distanceSquaredTo(p);
        }
        nearest(x.lb, p);
        nearest(x.rt, p);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.8, 0.5);
        Point2D p2 = new Point2D(0.7, 0.5);
        Point2D p3 = new Point2D(0.6, 0.3);
        Point2D p4 = new Point2D(0.5, 0.2);
        Point2D p5 = new Point2D(0.1, 0.1);
        Point2D p6 = new Point2D(0.9, 0.1);
        Point2D p7 = new Point2D(0.92, 0.1);
        Point2D p8 = new Point2D(0.95, 0.2);
        Point2D p9 = new Point2D(0.99, 0.3);
        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        kdTree.insert(p6);
        kdTree.insert(p7);
        kdTree.insert(p8);
        kdTree.insert(p9);
        kdTree.draw();
        Iterable<Point2D> queue = kdTree.range(new RectHV(0, 0, 0.7, 1));
        for (Point2D p : queue) {
            System.out.println(p);
        }
        System.out.println("nearest:");
        System.out.println(kdTree.nearest(new Point2D(0.51, 0.21)));
        System.out.println(kdTree.contains(new Point2D(0.6, 0.5)));

    }
}
