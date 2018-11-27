/* *****************************************************************************
 *  Name: SAURABH JAIN
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int size;
    private final WeightedQuickUnionUF uf;
    private int opensite;

    public Percolation(int n)           // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.size = n;
        this.grid = new boolean[n + 1][n + 1];

        uf = new WeightedQuickUnionUF(size * size + 3);
        for (int i = 1; i <= size; i++) {
            uf.union(0, xyTo1D(1, i));
            uf.union(size * size + 1, xyTo1D(size, i));

        }
    }


    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!isOpen(row, col)) {

            this.grid[row][col] = true;
            this.opensite++;
            if (row + 1 <= size && isOpen(row + 1, col)) {
                this.uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col + 1 <= size && isOpen(row, col + 1)) {

                this.uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (row - 1 >= 1 && isOpen(row - 1, col)) {

                this.uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (col - 1 >= 1 && isOpen(row, col - 1)) {
                this.uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
    }


    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return this.grid[row][col];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException();
        }

        return isOpen(row, col) && uf.connected(0, xyTo1D(row, col));
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return opensite;
    }

    private int xyTo1D(int x, int y) {
        int ret = (x - 1) * size + y;
        return ret;
    }

    public boolean percolates()              // does the system percolate?
    {
        return numberOfOpenSites() > 0 && uf.connected(0, size * size + 1);
    }

    public static void main(String[] args)   // test client (optional)
    {
        // Percolation p = new Percolation(5);
        // System.out.println(p.numberOfOpenSites());
    }

}
