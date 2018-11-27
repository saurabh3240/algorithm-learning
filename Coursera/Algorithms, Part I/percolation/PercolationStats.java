/* *****************************************************************************
 *  Name: SAURABH
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double C = 1.96;
    private static double meanTrials;
    private static double stdTrials;
    private final double[] res;
    private final int trials;


    public PercolationStats(int n,
                            int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int[] blockedSites = new int[n * n];
        this.trials = trials;
        res = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            for (int x = 0; x < n * n; x++) {
                blockedSites[x] = x;
            }
            StdRandom.shuffle(blockedSites);

            int j = 0;
            while (!p.percolates()) {
                int toOpen = blockedSites[j++];
                int row = toOpen / n + 1;
                int col = toOpen - (row - 1) * n + 1;

                p.open(row, col);
            }

            res[i] = (double) j / (n * n);
        }
        meanTrials = mean();
        stdTrials = stddev();

    }


    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(res);
    }

    public double stddev()                // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(res);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return meanTrials - C * stdTrials / Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return meanTrials + C * stdTrials / Math.sqrt(trials);
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");

    }

}
