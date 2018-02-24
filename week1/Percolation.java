
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF backwash;
    private final int N;
    private boolean[] states;
    private final int SIZE;
    private int numOfsites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = n;
        this.numOfsites = 0;
        this.SIZE = n * n;
        uf = new WeightedQuickUnionUF(this.SIZE+2);
        backwash = new WeightedQuickUnionUF(this.SIZE+1);
        states = new boolean[this.SIZE+2];
        for (int i = 1; i <= this.SIZE; i++)
            states[i] = false;
        states[0] = true;
        states[this.SIZE+1] = true;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = toIndex(row, col);

        if (!isOpen(row, col)) numOfsites = numOfsites + 1;
        states[index] = true;

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(index, toIndex(row - 1, col));
            backwash.union(index, toIndex(row - 1, col));
        }

        if (row < N && isOpen(row + 1, col)) {
            uf.union(index, toIndex(row + 1, col));
            backwash.union(index, toIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(index, toIndex(row, col - 1));
            backwash.union(index, toIndex(row, col - 1));
        }

        if (col < N && isOpen(row, col + 1)) {
            uf.union(index, toIndex(row, col + 1));
            backwash.union(index, toIndex(row, col + 1));
        }

        // top
        if (index <= this.N) {
            uf.union(0, index);
            backwash.union(0, index);
        }

        // bottom
        if (index > SIZE - N) {
            uf.union(SIZE + 1, index);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return states[toIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = toIndex(row, col);
        return isOpen(row, col) && uf.connected(0, index) && backwash.connected(0, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.numOfsites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, SIZE + 1);
    }
    
    private void assertInRange(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N) {
            throw new IllegalArgumentException();
        }
    }

    private int toIndex(int row, int col) {
        assertInRange(row, col);
        return (row - 1) * N + col;
    }

}