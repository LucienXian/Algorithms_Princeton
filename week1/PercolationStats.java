import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] res;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        assertPositive(n);
        assertPositive(trials);

        res = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            do {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                if (p.isOpen(x, y)) 
                    continue;
                p.open(x, y);
            } while (!p.percolates());

            res[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    private void assertPositive(int num) {
        if (num < 1) {
            throw new IllegalArgumentException();
        }
    }
    

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(res);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(res);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96 * stddev() / Math.sqrt(res.length);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96 * stddev() / Math.sqrt(res.length);
    }                 
    
    // test client (described below)
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.printf("mean                     = %f\n", p.mean());
        System.out.printf("stddev                   = %f\n", p.stddev());
        System.out.printf("95%% confidence Interval  = %f, %f\n", p.confidenceLo(), p.confidenceHi());
    }        
}