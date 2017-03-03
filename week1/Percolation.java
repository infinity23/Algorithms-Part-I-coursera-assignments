import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf, uf2;
    private int n;
    private boolean[] arr;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        arr = new boolean[n * n];
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) * n + y;
    }


    private void checkBound(int row, int col) {
        if ((row < 1) || (row > n) || (col < 1) || (col > n))
            throw new IndexOutOfBoundsException();
    }

    private void connectIfOpen(int p, int row, int col) {
        if (row > n || col > n)
            return;
        int q = xyTo1D(row, col);
        if (arr[q - 1]) {
            uf.union(p, q);
            uf2.union(p, q);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBound(row, col);
        int p = xyTo1D(row, col);
        arr[p - 1] = true;
        if ((row > 1 && row < n) && (col > 1 && col < n)) {
            connectIfOpen(p, row + 1, col);
            connectIfOpen(p, row - 1, col);
            connectIfOpen(p, row, col - 1);
            connectIfOpen(p, row, col + 1);
        } else if (row == 1) {
            if (col == 1) {
                connectIfOpen(p, row + 1, col);
                connectIfOpen(p, row, col + 1);
                if (n == 1)
                    uf.union(1, 2);
            } else if (col == n) {
                connectIfOpen(p, row, col - 1);
                connectIfOpen(p, row + 1, col);
            } else {
                connectIfOpen(p, row + 1, col);
                connectIfOpen(p, row, col + 1);
                connectIfOpen(p, row, col - 1);
            }
            uf.union(p, 0);
            uf2.union(p, 0);
        } else if (row == n) {
            if (col == 1) {
                connectIfOpen(p, row - 1, col);
                connectIfOpen(p, row, col + 1);
            } else if (col == n) {
                connectIfOpen(p, row - 1, col);
                connectIfOpen(p, row, col - 1);
            } else {
                connectIfOpen(p, row - 1, col);
                connectIfOpen(p, row, col - 1);
                connectIfOpen(p, row, col + 1);
            }
            uf.union(p, n * n + 1);
        } else if (col == 1) {
            connectIfOpen(p, row - 1, col);
            connectIfOpen(p, row + 1, col);
            connectIfOpen(p, row, col + 1);
        } else if (col == n) {
            connectIfOpen(p, row - 1, col);
            connectIfOpen(p, row + 1, col);
            connectIfOpen(p, row, col - 1);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBound(row, col);
        return arr[xyTo1D(row, col) - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBound(row, col);
        return uf2.connected(0, xyTo1D(row, col));
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}