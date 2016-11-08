import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] threshold;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Arguments out of bound");
    }

     threshold = new double[trials];

     int openCount = 0;

     for (int i = 0; i < trials; i++) {
       Percolation percolation = new Percolation(n);

       do {
          int row = StdRandom.uniform(1, n+1);
          int column  = StdRandom.uniform(1, n+1);

          if (percolation.isOpen(row, column)) continue;

          percolation.open(row, column);
          openCount++;
      } while (!percolation.percolates());

      threshold[i] = (double) openCount / (n * n);
      openCount = 0;
     }
   }

  public double mean() {
    return StdStats.mean(threshold);
  }

  public double stddev() {
    return StdStats.stddev(threshold);
  }

  public double confidenceLo() {
    return mean() - halfInterval();
  }

  public double confidenceHi() {
    return mean() + halfInterval();
  }

  private double halfInterval() {
    return 1.96 * stddev() / Math.sqrt(threshold.length);
  }

  public static void main(String[] args) {
      PercolationStats percolationstats = new PercolationStats(
        Integer.parseInt(args[0]),
        Integer.parseInt(args[1])
      );

      System.out.printf(
        "mean                         = %f%n",
        percolationstats.mean()
      );
      System.out.printf(
        "stddev                       = %f%n", percolationstats.stddev());
      System.out.printf(
        "95%% confidence Interval      = %f, %f%n",
        percolationstats.confidenceLo(),
        percolationstats.confidenceHi()
      );
  }
}
