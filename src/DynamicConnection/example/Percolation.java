package DynamicConnection.example;
import DynamicConnection.WeightedQuickUnion;

// Full example https://gist.github.com/geolffreym/ca869b97a46ec924d91f0c188d74170d
public class Percolation {
    private enum States {
        CLOSE,
        OPEN
    }

    private final int N;
    private final int TOP = 0;
    private int openBoxes = 0;

    private final States[][] boxes;
    private final WeightedQuickUnion unionFind;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.N = n; // Grid size
        this.boxes = new States[n][n];
        // Allow to Quick-Find holds virtual root node top and bottom
        // Add an extra object to top and bottom eg: 5 x 5 + 2
        // Not Zer0-based indexes in operations params row, col
        unionFind = new WeightedQuickUnion(n * n + 2);

        // Init Blocking all boxes
        for (int k = 0; k < this.N; k++) {
            for (int j = 0; j < this.N; j++) {
                boxes[k][j] = States.CLOSE;
            }
        }
    }


    private void virtualTopUnion(int row, int col) {
        // Populate top virtual component tree
        // Get the corresponding index for flat 1D
        int index = find1DIndexFrom2D(row, col);
        unionFind.union(index, this.TOP); // 0 root tree
    }

    private void virtualBottomUnion(int row, int col) {
        // Populate top virtual component tree
        // Get the corresponding index for flat 1D
        int index = find1DIndexFrom2D(row, col);
        // Add object to virtual bottom component this.N * this.N + 1
        // eg: 5 x 5 + 1 = 26 <= root for bottom
        unionFind.union(index, this.N * this.N + 1);
    }

    /**
     * Open box in grid (row,col) if not open, also add union
     *
     * @param row index in grid
     * @param col index in grid
     */
    public void open(int row, int col) {
        // Row and col are passed as non-zero based numbering
        boxes[row - 1][col - 1] = States.OPEN;
        if (row == 1) virtualTopUnion(row, col);
        if (row == this.N) virtualBottomUnion(row, col);

        unionDirection(row, col, row - 1, col); // Top union
        unionDirection(row, col, row + 1, col);// Bottom union
        unionDirection(row, col, row, col + 1); // Right union
        unionDirection(row, col, row, col - 1); // Left union
        openBoxes++;
    }

    /**
     * Is open site in (row, col) in grid?
     *
     * @param row index in grind
     * @param col index in grid
     * @return - true if box is OPEN state else return false
     * @throws IllegalArgumentException - if both 1 > row > N and 1 > col > N
     */
    private boolean isOpen(int row, int col) {
        if (row < 1 || row > this.N || col < 1 || col > this.N)
            throw new IllegalArgumentException();
        return boxes[row][col] == States.OPEN;
    }

    /**
     * Add union in diff directions for grid
     *
     * @param x1 from row
     * @param y1 from col
     * @param x2 to row
     * @param y2 to col
     */

    private void unionDirection(int x1, int y1, int x2, int y2) {
        // Avoid index over/under flow
        // If x2 underflow or x2 overflow or y2 underflow or y2 overflow
        if (x2 < 1 || x2 > this.N || y2 < 1 || y2 > this.N) return;
        if (!this.isOpen(x2, y2)) return;

        int from = this.find1DIndexFrom2D(x1, y1);
        int to = this.find1DIndexFrom2D(x2, y2);
        unionFind.union(from, to);
    }


    /**
     * Ss the site (row, col) full?
     *
     * @param row index in grid
     * @param col index in grid
     * @return - true if is object for row, col is connected to TOP else return false
     * @throws IllegalArgumentException - if both 1 > row > N and 1 > col > N
     */
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.N || col < 1 || col > this.N)
            throw new IllegalArgumentException();

        return unionFind.connected(find1DIndexFrom2D(row, col), TOP);
    }

    // return percolation threshold
    public double threshold() {
        return numberOfOpenSites() / Math.pow(this.N, 2);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openBoxes;
    }

    // does the system percolate?
    public boolean percolates() {
        // Is the bottom connected with top over open boxes?
        return unionFind.connected(TOP, this.N * this.N + 1);
    }

    /**
     * Locate index for 1D flat array from 2D corresponding array
     *
     * @param row in 2D - Not zero-based index
     * @param col in 2D - Not zero-based index
     * @return int projected 1D index from 2D array
     */
    private int find1DIndexFrom2D(int row, int col) {

        // Current N size eg. 5x5, get in 2D (col 2, row 2) = 7 in 1D
        // [
        //  0=> 0,1,2,3,4,
        //  1=> 5,6,7,8,9
        //  2=> ....
        //  3=> ....
        // ]
        return (this.N * (row - 1)) + col;
    }

    public static void main(String[] args) {

    }
}
