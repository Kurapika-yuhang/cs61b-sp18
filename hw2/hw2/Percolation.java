package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;

public class Percolation {
    private int N;
    private boolean[][] openFlag;
    private int numOfOpenSites;
    private WeightedQuickUnionUF sites; // 记录是不是full
    private WeightedQuickUnionUF sitesWithoutBottom; // 不记录bottom的union 防止backwash
    private int top;
    private int bottom;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked

        this.N = N;
        top = N*N;
        bottom = N*N + 1;
        numOfOpenSites = 0;

        // openFlag initialization：all false
        openFlag = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                openFlag[i][j] = false;
            }
        }

        // sites initialization: 第一行与top连，最后一行与bottom连 （有backwash 只为了方便isfull（）为const time）
        sites = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) {
            sites.union(top,xyTo1D(0, i));
        }

        for (int i = 0; i < N; i++) {
            sites.union(bottom,xyTo1D(N - 1, i));
        }

        // sitesWithoutBottom initialization: 第一行与top连 没有bottom（防止backwash）
        sitesWithoutBottom = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            sitesWithoutBottom.union(top,xyTo1D(0, i));
        }
    }




    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        validate(row, col);
        if (!isOpen(row, col)) {
            return false;
        }

        return sitesWithoutBottom.connected(top, xyTo1D(row, col));
    }
    public int numberOfOpenSites() {
        // number of open sites
        return numOfOpenSites;
    }
    public boolean percolates() {
        // does the system percolate?
        return sites.connected(top, bottom);
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        openFlag[row][col] = true;
        numOfOpenSites += 1;
        unionAround(row, col);
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        validate(row, col);
        return openFlag[row][col];
    }


    private void unionAround(int row, int col) {
        validate(row, col);
        int[][] dirArray = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
        for (int[] dir: dirArray) {
            int rowAround = row + dir[0];
            int colAround = col + dir[1];
            if (isValidRange(rowAround,colAround) && isOpen(rowAround,colAround)) {
                sites.union(xyTo1D(row,col), xyTo1D(rowAround, colAround));
                sitesWithoutBottom.union(xyTo1D(row,col), xyTo1D(rowAround, colAround));
            }
        }
    }

    private int xyTo1D(int row, int col) {
        return N * row + col;
    }

    private boolean isValidRange(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    private void validate(int row, int col) {
        if (!isValidRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
        // use for unit testing (not required)
        Percolation percolation = new Percolation(2);
        System.out.println(percolation.percolates());  // false
        percolation.open(0,0);
        percolation.open(1,0);
        System.out.println(percolation.percolates());  // true
    }
}
