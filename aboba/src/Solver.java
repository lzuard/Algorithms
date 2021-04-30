import java.util.*;

public class Solver {

    private final List<Board> result = new ArrayList<>();
    private final Board board;

    private static class ITEM {

        private final ITEM prevBoard;
        private final Board board;

        private ITEM(ITEM prevBoard, Board board) {
            this.prevBoard = prevBoard;
            this.board = board;
        }

        public Board getBoard() {
            return board;
        }
    }

    public Solver(Board initial) {
        this.board=initial;

        if(notSolvable(board)) return;

        PriorityQueue<ITEM> priorityQueue = new PriorityQueue<>(10, Comparator.comparingInt(Solver::measure));
        priorityQueue.add(new ITEM(null, board));

        while (true){
            ITEM board = priorityQueue.poll();

            if(board.board.isGoal()) {
                itemToList(new ITEM(board, board.board));
                return;
            }

            for (Board board1 : board.board.neighbors()) {
                if (board1 != null && !containsInPath(board, board1))
                    priorityQueue.add(new ITEM(board, board1));
            }

        }
    }

    private static int measure(ITEM item){
        ITEM item2 = item;
        int c= 0;
        int measure = item.getBoard().getH();
        while (true){
            c++;
            item2 = item2.prevBoard;
            if(item2 == null) {
                return measure + c;
            }
        }
    }

    private void itemToList(ITEM item){
        ITEM item2 = item;
        while (true){
            item2 = item2.prevBoard;
            if(item2 == null) {
                Collections.reverse(result);
                return;
            }
            result.add(item2.board);
        }
    }

    private boolean containsInPath(ITEM item, Board board){
        ITEM item2 = item;
        while (true){
            if(item2.board.equals(board)) return true;
            item2 = item2.prevBoard;
            if(item2 == null) return false;
        }
    }

    public boolean notSolvable(Board board) {

        return (board.getN() + board.getZeroY()) % 2 != 0;
    }

    public int moves() {
        if(notSolvable(board)) return -1;
        return result.size() - 1;
    }

    public Iterable<Board> solution() {
        return result;
    }
}