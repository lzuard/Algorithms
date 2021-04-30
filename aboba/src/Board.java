import java.util.HashSet;
import java.util.Set;

public class Board {
    private final int[][] blocks;
    private int zeroY;
    private int zeroX;
    private int h;
    private int N;

    public int getBlocksLength() {
        return blocks.length;
    }
    public int getH() {
        return h;
    }
    public int getZeroY(){
        return zeroY;
    }
    public int getN(){
        return N;
    }


    public Board(int[][] blocks) {
        this.blocks = deepCopy(blocks);

        int[] kArr=new int[blocks.length*blocks[0].length];

        h = 0;
        int temp=0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if(blocks[i][j] == 0){
                    zeroY = i;
                    zeroX = j;
                }
                else if (blocks[i][j] != (i*blocks.length + j + 1)) {
                    h += 1;
                }
                kArr[temp]=blocks[i][j];
                temp++;
            }
        }
        for(int i=0;i<kArr.length-1;i++){
            for(int j=i+1;j<kArr.length;j++){
                if(kArr[i]>kArr[j]){
                    N++;
                }
            }
        }
    }

    /** Публичные методы **/
    public boolean isGoal() {  //   если все на своем месте, значит это искомая позиция
        return h == 0;
    }

    public Iterable<Board> neighbors() {
        Set<Board> boardList = new HashSet<>();
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY, zeroX + 1));
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY, zeroX - 1));
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY - 1, zeroX));
        boardList.add(change(getNewBlock(), zeroY, zeroX, zeroY + 1, zeroX));

        return boardList;
    }

    /** Приватные методы */
    private int[][] getNewBlock() { //  опять же, для неизменяемости
        return deepCopy(blocks);
    }

    private Board change(int[][] blocks2, int y1, int x1, int y2, int x2) {

        if (y2 > -1 && y2 < blocks.length && x2 > -1 && x2 < blocks.length) {
            int t = blocks2[y2][x2];
            blocks2[y2][x2] = blocks2[y1][x1];
            blocks2[y1][x1] = t;
            return new Board(blocks2);
        }
        else return null;
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            result[i] = new int[original[i].length];
            System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }


    /** Перезаписанные Object методы */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (board.getBlocksLength() != blocks.length) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int[] block : blocks) {
            for (int j = 0; j < blocks.length; j++) {
                s.append(String.format("%2d ", block[j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}