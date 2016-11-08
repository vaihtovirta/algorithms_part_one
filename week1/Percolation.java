import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int sideSize;
  private WeightedQuickUnionUF grid, bottomlessGrid;
  private int sitesSize;
  private boolean[] states;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n out of bound");
    }

    sideSize = n;
    sitesSize = sideSize * sideSize;
    grid = new WeightedQuickUnionUF(sitesSize + 2);
    bottomlessGrid = new WeightedQuickUnionUF(sitesSize + 1);
    states = new boolean[sitesSize + 2];

    // Initialize sites with closed state
    for (int i = 1; i <= sitesSize; i++) {
      states[i] = false;
    }

    // Initialize virtual top and bottom site with open state
    states[0] = true;
    states[sitesSize + 1] = true;
  }

  public boolean isFull(int row, int col) {
    checkBounds(row, col);

    if (!isOpen(row, col)) return false;

    int siteIndex = siteIndex(row, col);

    return bottomlessGrid.connected(0, siteIndex);
  }

  public boolean isOpen(int row, int col) {
    checkBounds(row, col);

    int siteIndex = siteIndex(row, col);

    return states[siteIndex];
  }

  public void open(int row, int col) {
    checkBounds(row, col);

    int currentIndex = siteIndex(row, col);
    if (!isOpen(row, col)) states[currentIndex] = true;

    int leftRow = row - 1;
    int rightRow = row + 1;
    int upCol = col - 1;
    int downCol = col + 1;

    if (row != 1 && isOpen(leftRow, col)) {
      unionGrids(currentIndex, leftRow, col);
    }

    if (row != sideSize && isOpen(rightRow, col)) {
      unionGrids(currentIndex, rightRow, col);
    }

    if (col != 1 && isOpen(row, upCol)) {
      unionGrids(currentIndex, row, upCol);
    }

    if (col != sideSize && isOpen(row, downCol)) {
      unionGrids(currentIndex, row, downCol);
    }

    if (isTopSite(currentIndex)) {
      grid.union(0, currentIndex);
      bottomlessGrid.union(0, currentIndex);
    }

    if (isBottomSite(currentIndex)) {
      grid.union(states.length - 1, currentIndex);
    }
  }

  public boolean percolates() {
    return grid.connected(0, states.length - 1);
  }

  private int siteIndex(int row, int col) {
    checkBounds(row, col);

    return (row - 1) * sideSize + col;
  }

  private void checkBounds(int row, int col) {
    if (row <= 0 || row > sideSize) {
      throw new IndexOutOfBoundsException("row out of bound");
    }

    if (col <= 0 || col > sideSize) {
      throw new IndexOutOfBoundsException("col out of bound");
    }
  }

  private boolean isBottomSite(int index) {
    return index >= (sideSize - 1) * sideSize + 1;
  }

  private boolean isTopSite(int index) {
    return index <= sideSize;
  }

  private void unionGrids(int currentIndex, int row, int col) {
    int siteIndex = siteIndex(row, col);

    grid.union(currentIndex, siteIndex);
    bottomlessGrid.union(currentIndex, siteIndex);
  }
}
