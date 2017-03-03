import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Board initial;
    private BoardState goal;

    private class BoardState implements Comparable<BoardState> {
        private Board board;
        private int moves;
        private BoardState previous;

        BoardState(Board board) {
            this.board = board;
        }

        private int priority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(BoardState o) {
            return priority() < o.priority() ? -1 : (priority() > o.priority() ? 1 : 0);
        }
    }

    private BoardState solvableAlgorithm() {
        MinPQ<BoardState> pq1 = new MinPQ<>();
        MinPQ<BoardState> pq2 = new MinPQ<>();
        BoardState bs1 = new BoardState(initial);
        BoardState bs2 = new BoardState(initial.twin());
        pq1.insert(bs1);
        pq2.insert(bs2);

        BoardState bs = null;
        while (!pq1.isEmpty() && !pq2.isEmpty()) {
            bs1 = pq1.delMin();
            bs2 = pq2.delMin();

            if (bs1.board.isGoal()) {
                return bs1;
            } else if (bs2.board.isGoal())
                return null;

            for (Board b : bs1.board.neighbors()) {
                if (bs1.previous != null && b.equals(bs1.previous.board))
                    continue;
                bs = new BoardState(b);
                bs.moves = bs1.moves + 1;
                bs.previous = bs1;
                pq1.insert(bs);
            }

            for (Board b : bs2.board.neighbors()) {
                if (bs2.previous != null && b.equals(bs2.previous.board))
                    continue;
                bs = new BoardState(b);
                bs.moves = bs2.moves + 1;
                bs.previous = bs2;
                pq2.insert(bs);
            }

        }
        return null;
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        this.initial = initial;
        goal = solvableAlgorithm();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return goal.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> stack = new Stack<>();
        BoardState bs = goal;
        while (bs != null) {
            stack.push(bs.board);
            bs = bs.previous;
        }
        return stack;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        Board board = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        Solver solver = new Solver(board);
        if (!solver.isSolvable()) {
            System.out.println("不可解");
            return;
        }

        for (Board b : solver.solution()) {
            System.out.println(b);
        }

    }
}