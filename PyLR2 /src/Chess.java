import java.util.ArrayList;
import java.util.Arrays;

/** Класс, расставляющий на шахматном поле размером NxN N ферзей так, чтобы они не находились под боем друг друга **/
public class Chess {

    /** Возможные статусы ячеек **/
    private final int CELL_OPENED=0;
    private final int CELL_CLOSED=1;
    private final int CELL_WITH_FIGURE=2;


    /** Максимальное количество фигур **/
    private final int maxFigureCount;
    /** Текущее количество фиугр **/
    private int figureCount;
    /** Количество ячеек поля **/
    private final int fieldSize;
    /** Длина стороны поля **/
    private final int fieldLength;

    /** Массив ячеек поля **/
    private final  int[] cells;
    /** Коллекция неподходящих наборов **/
    private final  ArrayList<Integer[]> badCombination=new ArrayList<>();
    /** Набор текущих фиугр на поле **/
    private final ArrayList<Integer> figuresOnBoard=new ArrayList<>();


    /** Конструктор класса **/
    public Chess(int fieldLength, int maxFigureCount){
        this.fieldLength=fieldLength;
        fieldSize=fieldLength*fieldLength;
        this.maxFigureCount=maxFigureCount;

        figureCount=0;
        cells=new int[fieldSize];
        for(int i=0;i<fieldSize;i++){
            cells[i]=CELL_OPENED;
        }
    }

    /** Метод, закрывающий ячейки по диагонали слева направо **/
    private void closeLeftDiagonal(int startCell, int status){

        int currentCell=startCell;
        int nextCell = currentCell-fieldLength+1;

        int currentCol = currentCell%fieldLength;
        int nextCol = nextCell%fieldLength;

        //Going up
        while (currentCell>0 && nextCol==currentCol+1){
            currentCell=nextCell;
            nextCell=currentCell-fieldLength+1;

            currentCol=nextCol;
            nextCol=nextCell%fieldLength;
        }

        nextCell=currentCell+fieldLength-1;

        currentCol=currentCell%fieldLength;
        nextCol= nextCell%fieldLength;

        //Going down
        while(currentCell<fieldSize && nextCol==currentCol-1){
            if(cells[currentCell]!=CELL_WITH_FIGURE){
                cells[currentCell]=status;
            }
            currentCell=nextCell;
            nextCell=currentCell+fieldLength-1;

            currentCol=nextCol;
            nextCol=nextCell%fieldLength;
        }

        //last cell check
        if (currentCell<fieldSize && currentCell%fieldLength==0 && cells[currentCell]!=CELL_WITH_FIGURE){
            cells[currentCell]=status;
        }
    }

    /** Метод, закрывающий ячейки по диагонали справо налево **/
    private void closeRightDiagonal(int startCell, int status){
        int currentCell=startCell;
        int nextCell = currentCell-fieldLength-1;

        int currentCol = currentCell%fieldLength;
        int nextCol = currentCol-1;

        //Going up
        while (currentCell>0 && nextCol==currentCol-1){
            currentCell=nextCell;
            nextCell=currentCell-fieldLength-1;

            currentCol=nextCol;
            nextCol=nextCell%fieldLength;
        }
        if(currentCell<0){
            currentCell+=fieldLength+1;
        }

        nextCell=currentCell+fieldLength+1;

        currentCol=currentCell%fieldLength;
        nextCol= nextCell%fieldLength;

        //Going down
        while(currentCell<fieldSize && nextCol==currentCol+1){
            if(cells[currentCell]!=CELL_WITH_FIGURE){
                cells[currentCell]=status;
            }
            currentCell=nextCell;
            nextCell=currentCell+fieldLength+1;

            currentCol=nextCol;
            nextCol=nextCell%fieldLength;
        }

        //Last cell check
        if (currentCell<fieldSize && currentCell%fieldLength==7 && cells[currentCell]!=CELL_WITH_FIGURE){
            cells[currentCell]=status;
        }
    }

    /** Метод, закрывающий ячейки по вертикали **/
    private void closeVertical(int startCell, int status){

        int currentCell=startCell;

        //Поднимаемся выше по вертикали
        while(currentCell>0){
            currentCell-=fieldLength;
        }

        if(currentCell!=0) {
            currentCell += fieldLength;
        }

        //Спускаемся и закрываем все открытые ячейки
        while(currentCell<fieldSize){
            if(cells[currentCell]!=CELL_WITH_FIGURE){
                cells[currentCell]=status;

            }
            currentCell+=fieldLength;
        }
    }

    /** Метод, закрывающий ячейки по горизонатли **/
    private void closeHorizontal(int startCell, int status){
        int currentCell=startCell;

        //Определить строку
        int row=startCell/fieldLength;

        //Сдвигаемся влево внутри строки
        while(currentCell/fieldLength==row && currentCell>=0){
            currentCell--;
        }
        currentCell++;

        while(currentCell/fieldLength==row && currentCell<fieldSize){
            if(cells[currentCell]!=CELL_WITH_FIGURE){
                cells[currentCell]=status;
            }
            currentCell++;
        }
    }


    /** Открывает ячейки во все стороны от текущей **/
    public void openCells(int i){
        closeHorizontal(i,CELL_OPENED);
        closeVertical(i,CELL_OPENED);
        closeLeftDiagonal(i,CELL_OPENED);
        closeRightDiagonal(i,CELL_OPENED);
    }

    /** Закрывает ячейки во все стороны от текущей **/
    public void closeCells(int i){
        closeHorizontal(i,CELL_CLOSED);
        closeVertical(i,CELL_CLOSED);
        closeLeftDiagonal(i,CELL_CLOSED);
        closeRightDiagonal(i,CELL_CLOSED);
    }

    /** Проверяет на наличие комбинации в коллекции неподходящих комбинаций **/
    private boolean canTry(int index){

        int size=figuresOnBoard.size()+1;
        Integer[] combination = new Integer[size];

        if(figuresOnBoard.size()>0) {
            for (int i = 0; i < size - 1; i++) {
                combination[i] = figuresOnBoard.get(i);
            }
        }
        combination[size-1]=index;

        for (Integer[] integers : badCombination) {
            if (Arrays.equals(integers, combination)) {
                return false;
            }
        }
        return true;
    }

    /** Возвращается на один шаг назад и запоминает текущую комбинацию **/
    private boolean getBack(){
        //Добавление плохой комбинации
        int size=figuresOnBoard.size();
        Integer[] combination = new Integer[size];

        for(int i=0;i<size;i++){
            combination[i]=figuresOnBoard.get(i);
        }

        badCombination.add(combination);
        if (size==1 && combination[0]==maxFigureCount-1) {
            return true;
        }
        else{
            for (int i: figuresOnBoard){
                openCells(i);
            }

            cells[figuresOnBoard.get(figuresOnBoard.size() - 1)] = CELL_OPENED;
            figuresOnBoard.remove(figuresOnBoard.get(figuresOnBoard.size() - 1));
            figureCount--;
            for (int i: figuresOnBoard){
                closeCells(i);
            }
            return false;
        }
    }

    /** Основной метод, расставляющий фигуры **/
    private boolean putFigures(){
        int i;
        boolean noCellsLeft=false;

        while(figureCount<maxFigureCount && !noCellsLeft){

            i = getAvailableCell();
            System.out.println(figuresOnBoard);


            if (i>=0) {
                putNextFigure(i);
            } else{
                noCellsLeft=getBack();
            }
        }
        return noCellsLeft;
    }

    /** Ставит одну фигуру на поле **/
    private void putNextFigure(int index){
        cells[index]=CELL_WITH_FIGURE;
        figureCount++;
        figuresOnBoard.add(index);
        closeCells(index);
    }

    /** Получить первую доступную ячейку **/
    private int getAvailableCell(){
        int size =figuresOnBoard.size()-1;
        int index;
        if (size<0){
            index=0;
        }
        else{
            index=figuresOnBoard.get(size);
        }


        while (index<fieldSize){
            if(cells[index]==CELL_OPENED && canTry(index)){
                return index;
            }
        index++;
        }
        return -1;
    }

    /** Выводит на экран шахматное поле **/
    private void printChessTable(){
        for(int i=0;i<fieldSize;i++){
            if(i%fieldLength==0){
                System.out.print("\n");
            }

            switch (cells[i]) {
                case CELL_OPENED -> System.out.print("- ");
                case CELL_CLOSED -> System.out.print("x ");
                case CELL_WITH_FIGURE -> System.out.print("O ");
            }
        }
        System.out.print("\n");
    }

    /** Запускает перебор **/
    public void startApp(){
        boolean notDone=putFigures();
        if (!notDone) {
            System.out.println("\nШахматное поле (0-Фигура, х-ячейка под боем):");
            printChessTable();
            System.out.println("\nКоординаты фигур:");
            System.out.println(figuresOnBoard + "\n");
        }
        else{
            System.out.println("\nНевозможно поставить "+maxFigureCount+" фигур на поле "+fieldLength+"x"+fieldLength);
        }
    }
}
