/* *****************************************************************************
 *  Name: Jeffrey Wu Chen
 *  Date:
 *  Description: Assignment 1a for Cos 226
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wqu;
    private int edge; // size of the grid axis
    private boolean[][] grid; // boolean array ... n.b. initialize F
    private int top = 0;
    private int bottom;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        edge = n; // site number of boxes
        // set the bottom as the last
        bottom = edge * edge + 1;
        grid = new boolean[edge][edge];
        // intialize a WQU of equal size to array... note the extra 2
        wqu = new WeightedQuickUnionUF(edge * edge + 2);
    }

    // grid site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row - 1][col - 1] = true; // set location to 'T'... check index

        // if on top or bottom attach to the 'top' node...same for bottom
        if (row == 1) {
            wqu.union(xyTo1D(row, col), top);
        }
        if (row == edge) {
            wqu.union(xyTo1D(row, col), top);
        }

        // now edges and corners
        // if no left most, connect with left
        if (col > 1 && isOpen(row, col - 1)) {
            wqu.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        // right
        if (col < edge && isOpen(row, col + 1)) {
            wqu.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        // if not top most, connect with above
        if (row > 1 && isOpen(row - 1, col)) {
            wqu.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        // bottom
        if (row < edge && isOpen(row + 1, col)) {
            wqu.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    // is the position (i,j) grid
    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1];
    }

    // Transform linearly so wqu can work...
    private int xyTo1D(int i, int j) {
        return edge * (i - 1) + j;
    }

    // print grid


    public boolean isFull(int i, int j) {

        if (i > 0 && j > 0 && i <= edge && j <= edge) {
            return wqu.connected(top, xyTo1D(i, j));
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    // number of open sites
    public int numberOfOpenSites() {
        int counts = 0;
        // counting True via dbl for loop
        for (int i = 0; i < edge; i++) {
            for (int j = 0; j < edge; j++) {
                if (grid[i][j]) {
                    counts += 1;
                }
            }
        }
        return counts;
    }

    // does it percolate?
    public boolean percolates() {
        return wqu.connected(top, bottom);
    }

    public static void main(String[] args) {
        // testing out Percolation
        int n = 5;
        int i = 2;
        int j = 2;
        Percolation p = new Percolation(n);
        System.out.print(p.isOpen(i, j));
        p.open(i, j);
        System.out.print(p.isOpen(i, j));
        System.out.print(p.isOpen(i + 1, j + 2));
        System.out.print(n);
        System.out.print(p.numberOfOpenSites());
        // p.printGrid();
    }
}
