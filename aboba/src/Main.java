public class Main {

    public static void main(String[] args) {
        int[][] blocks = new int[][]{{1, 2, 3, 0}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 4}};
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        System.out.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            System.out.println(board);
    }
}
