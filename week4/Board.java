import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] board;
//    private  int steps;

    private int abs(int a, int b) {
        return a - b > 0 ? a - b : b - a;
    }

    private void exch(int[][] arr, int a1, int a2, int b1, int b2) {
        int temp = arr[a1][a2];
        arr[a1][a2] = arr[b1][b2];
        arr[b1][b2] = temp;
    }

    private boolean boardquals(int[][] a1, int[][] a2) {
        if (a1.length != a2.length)
            return false;
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a1.length; j++) {
                if (a1[i][j] != a2[i][j])
                    return false;
            }
        }
        return true;
    }

    private Board() {
    }

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    board[i][j] = blocks.length * blocks.length - 1;
                    continue;
                }
                board[i][j] = blocks[i][j] - 1;
            }
        }
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of blocks out of place
    public int hamming() {
        int n = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != board.length * board.length - 1 && board[i][j] != i * board.length + j)
                    n++;
            }
        }
//        n += steps;
        return n;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int n = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != board.length * board.length - 1 && board[i][j] != i * board.length + j) {
                    int now = board[i][j];
                    int goal = i * board.length + j;
                    int dis = abs(now / board.length, goal / board.length) + abs(now % board.length, goal % board.length);
                    n += dis;
                }
            }
        }
        return n;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = new Board();
        int[][] arr = newcopy();
        int blank = board.length * board.length - 1;
        if (arr[0][0] != blank) {
            if (arr[0][1] != blank) {
                exch(arr, 0, 0, 0, 1);
            } else {
                exch(arr, 0, 0, 1, 0);
            }
        } else {
            exch(arr, 0, 1, 1, 0);
        }
        twin.board = arr;
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return y instanceof Board && boardquals(this.board, ((Board) y).board);
    }

    private int[][] newcopy() {
        int[][] copy = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board.length);
        }
        return copy;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        int blanki = -1;
        int blankj = -1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == board.length * board.length - 1) {
                    blanki = i;
                    blankj = j;
                    break;
                }
            }
        }

        int[][] copy = newcopy();
        if (blanki > 0) {
            exch(copy, blanki - 1, blankj, blanki, blankj);
            Board newBoard = new Board();
            newBoard.board = copy;
            stack.push(newBoard);
        }
        if (blanki < board.length - 1) {
            copy = newcopy();
            exch(copy, blanki + 1, blankj, blanki, blankj);
            Board newBoard = new Board();
            newBoard.board = copy;
            stack.push(newBoard);
        }
        if (blankj > 0) {
            copy = newcopy();
            exch(copy, blanki, blankj - 1, blanki, blankj);
            Board newBoard = new Board();
            newBoard.board = copy;
            stack.push(newBoard);
        }
        if (blankj < board.length - 1) {
            copy = newcopy();
            exch(copy, blanki, blankj + 1, blanki, blankj);
            Board newBoard = new Board();
            newBoard.board = copy;
            stack.push(newBoard);
        }

        return stack;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(board.length);
        sb.append('\n');
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(' ');
                if (board[i][j] == board.length * board.length - 1) {
                    sb.append(0);
                    continue;
                }
                sb.append(String.valueOf(board[i][j] + 1));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        Board board = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        Board twin = board.twin();
//        System.out.println(board);
        System.out.println(board.twin());
//        for (Board b : board.neighbors()) {
//            System.out.println(b);
//        }
        for (Board b : twin.neighbors()) {
            System.out.println(b);
        }
    }
}