package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.QuickUnionUF;

import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        this.maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        marked[s] = true;
        announce();

        while (!queue.isEmpty() && !targetFound) {
            int v = queue.poll();
            for (int u : maze.adj(v)) {
                if (! marked[u]) {
                    marked[u] = true;
                    queue.add(u);
                    edgeTo[u] = v;
                    distTo[u] = distTo[v] + 1;
                    announce();

                    if (u == t) {
                        targetFound = true;
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

