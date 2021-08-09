package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private List<WorldState> solution;
    private HashMap<WorldState, Integer> expMap;

    private class SearchNode {

        private WorldState state;
        private int moves;  // the number of moves made to reach this world state from the initial state.
        private SearchNode prev;    // a reference to the previous search node.

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int exp1 = getExpDist(o1.state);
            int exp2 = getExpDist(o2.state);
            int priority1 = exp1 + o1.moves;
            int priority2 = exp2 + o2.moves;

            return priority1 - priority2;
        }
    }

    public Solver(WorldState initial) {
        /*
        * Constructor which solves the puzzle, computing
                 everything necessary for moves() and solution() to
                 not have to solve the problem again. Solves the
                 puzzle using the A* algorithm. Assumes a solution exists.
        * */
        solution = new LinkedList<>();
        expMap = new HashMap<>();

        SearchNode currNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());

        pq.insert(currNode);

        while (!pq.isEmpty()) {
            currNode = pq.delMin();
            // if find the goal, end.
            if (currNode.state.isGoal()) {
                break;
            }

            // explore each neighbor state
            for(WorldState state: currNode.state.neighbors()) {
                SearchNode node = new SearchNode(state, currNode.moves + 1, currNode);
                // critical optimization
                if (currNode.prev != null && state.equals(currNode.prev.state)) {
                    // skip and go to the next neighbor
                    continue;
                }
                pq.insert(node);
            }
        }

        // record solution
        Stack<WorldState> stateStack = new Stack<>();
        for (SearchNode n = currNode; n != null; n = n.prev) {
            stateStack.push(n.state);
        }

        while (!stateStack.isEmpty()) {
            solution.add(stateStack.pop());
        }
    }

    private int getExpDist(WorldState state) {
        /* get the expected distance from the given state to the goal with caching */
        if (!expMap.containsKey(state)) {
            expMap.put(state, state.estimatedDistanceToGoal());
        }
        return expMap.get(state);
    }

    public int moves() {
        /**
         * Returns the minimum number of moves to solve the puzzle starting
         * at the initial WorldState.
         * */
        return solution.size() - 1;
    }

    public Iterable<WorldState> solution() {
        /*
        * Returns a sequence of WorldStates from the initial WorldState
                 to the solution.
        * */
        return solution;
    }
}
