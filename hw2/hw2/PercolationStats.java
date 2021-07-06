package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int N;  // N-by-N grid
    private int T;  // T independent experiments
    private double[] thresholds;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.T = T;

        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            // 一次实验 算出一个threshold的值
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                p.open(row, col);
            }
            thresholds[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
