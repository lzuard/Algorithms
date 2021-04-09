public class ChessCell {
    public int x;
    public int y;

    public ChessCell(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean equals(Object obj){
        if (obj!=null && obj.getClass()==getClass()){
            ChessCell cell = (ChessCell)obj;

            if (this.x==cell.x && this.y==this.y)
                return true;

            else
                return false;
        }
        else
            return false;
    }
}
