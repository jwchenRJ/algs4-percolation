/* *****************************************************************************
 *  Name: Jeffrey W. Chen
 *  Date:
 *  Description: Assignment 1b for Cos 226
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] record;
    private Percolation ps;
    private int T;


    // perform T trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int T) {
        if (n <= 0 || T <= 0) throw new java.lang.IllegalArgumentException("args must be + int");

        record = new double[T]; // record count for each trial

        for (int i = 0; i < T; i++) { // run perc 'T 'times
            Percolation p = new Percolation(n);
            int count = 0; // track number of steps to achieve perc
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
                count++;
            }
            double frac = (double) count / (n * n);
            record[i] = frac;
        }
    }

    public double mean() {
        return StdStats.mean(record);
    }

    public double stddev() {
        return StdStats.stddev(record);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(record.length));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(record.length));
    }

    public static void main(String[] args) {// test client
        PercolationStats ps = new PercolationStats(20, 100);
        StdOut.print("mean = " + ps.mean() + "\n");
        StdOut.print("std dev = " + ps.stddev() + "\n");
        StdOut.print("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}
