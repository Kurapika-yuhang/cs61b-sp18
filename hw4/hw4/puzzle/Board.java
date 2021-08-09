package hw4.puzzle;

import java.util.LinkedList;
import java.util.List;

public class Board implements WorldState {

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    private int[][] tiles;
    private static final int BLANK = 0;
    private int N;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= size() || j < 0 || j >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        List<WorldState> neighbors = new LinkedList<>();

        int N = size();
        int[] blankIdx = findBlank();
        List<int[]> neighborIdx = findNeighborIdx(blankIdx);

        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = tileAt(i, j);
            }
        }

        for (int[] idx:neighborIdx) {
            newBoard[blankIdx[0]][blankIdx[1]] = newBoard[idx[0]][idx[1]];
            newBoard[idx[0]][idx[1]] = BLANK;
            neighbors.add(new Board(newBoard));
            newBoard[idx[0]][idx[1]] = newBoard[blankIdx[0]][blankIdx[1]];
            newBoard[blankIdx[0]][blankIdx[1]] = BLANK;
        }
        return neighbors;
    }

    private List<int[]> findNeighborIdx(int[] blankIdx) {
        /* return a list of the idx of blank's neighbors */
        List<int[]> neighbors = new LinkedList<>();
        int[][] moves = new int[][] {{0,1},{1,0},{0,-1},{-1,0}};
        for (int[] move:moves) {
            int row = blankIdx[0] + move[0];
            int col = blankIdx[1] + move[1];
            if (0 <= row && row <= size() - 1 && col >= 0 && col <= size() - 1) {
                neighbors.add(new int[]{row, col});
            }
        }
        return neighbors;
    }

    private int[] findBlank() {
        int[] res = new int[2];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] == BLANK) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        throw new IndexOutOfBoundsException("No BLANK!");
    }



    public int hamming() {
        int N = size();
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != i * N + j + 1) {
                    res += 1;
                }
            }
        }
        return res - 1;
    }

    public int manhattan() {
        int N = size();
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = tileAt(i, j);
                if (value == BLANK) {
                    continue;
                }
                int rowGoal = (value - 1) / N;
                int colGoal = (value - 1) % N;
                res += Math.abs(rowGoal - i) + Math.abs(colGoal - j);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object y) {

        if (y == null || !y.getClass().equals(this.getClass())) {
            return false;
        }

        Board board = (Board) y;
        if (board.size() != this.size()) {
            return false;
        }

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (this.tileAt(i, j) != board.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
}
