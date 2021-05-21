import java.util.ArrayList;
import java.util.List;

/** Отвечает за поиск элементов **/
public class Search {

    /** Методы поиска **/
    public static final int SIMPLE_SEARCH=0;
    public static final int BINARY_SEARCH=1;
    public static final int TREE_SEARCH=2;
    public static final int FIBONACCI_SEARCH=3;
    public static final int INTERPOLATION_SEARCH=4;


    /** Ссылка на объект класса управления **/
    private static Interactions interactions;

    /** Установка ссылка на объект класса управления **/
    public static void setInteraction(Interactions obj){
        interactions=obj;
    }

    /** Главный метод поиска, запускает остальные **/
    public static int find (int value, int method){

        List<Integer> list = interactions.getHashList();

        int low=0;
        int high=list.size()-1;

        int index=0;

        switch (method){
            case SIMPLE_SEARCH:
                index=simpleSearch(value,list);
                break;
            case BINARY_SEARCH:
                index=binarySearch(value,low,high,list);
                break;
            case INTERPOLATION_SEARCH:
                index=interpolationSearch(value,list);
                break;
            case FIBONACCI_SEARCH:
                index=fibonacciSearch(value,list);
                break;
            case TREE_SEARCH:
                index=treeSearch(value,list);
                break;
        }
        return index;
    }


    /** Метод поиска бинарным деревом, инициализирует его, заполняет и вызывает метод поиска  **/
    public static int treeSearch(int value,List<Integer> list){
        BST_class tree = new BST_class();
        for(int i=0;i<list.size();i++){
            tree.insert(list.get(i));
        }
        boolean found=tree.search(value);
        if(found)
            return-100;
        else
            return -1;
    }

    /** Простой перебор элементов **/
    private static int simpleSearch(int value, List<Integer> list){
        int index=-1;

        for (int i=0;i<list.size();i++){
            if(list.get(i)==value){
                index=i;
                break;
            }
        }
        return index;
    }

    /** Бинарный поиск **/
    private static int binarySearch(int value, int low, int high, List<Integer> list){
        int index = -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (list.get(mid) < value) {
                low = mid + 1;
            } else if (list.get(mid) > value) {
                high = mid - 1;
            } else if (list.get(mid) == value) {
                index = mid;
                break;
            }
        }
        return index;
    }


    /** Интерполяционный поиск **/
    private static int interpolationSearch(int value, List<Integer> list){
        int startIndex = 0;
        int lastIndex = (list.size() - 1);


        while ((startIndex <= lastIndex) && (value >= list.get(startIndex)) && (value <= list.get(lastIndex))) {
            // используем формулу интерполяции для поиска возможной лучшей позиции для существующего элемента
            int pos = startIndex + (((lastIndex-startIndex) / (list.get(lastIndex)-list.get(startIndex)))* (value - list.get(startIndex)));

            if (list.get(pos) == value)
                return pos;

            if (list.get(pos) < value)
                startIndex = pos + 1;

            else
                lastIndex = pos - 1;
        }
        return -1;
    }

    /** Фибоначиев поиск **/
    public static int fibonacciSearch(int value, List<Integer> list)
    {
        int listSize=list.size();
        /* Initialize fibonacci numbers */
        int fibMMm2 = 0; // (m-2)'th Fibonacci No.
        int fibMMm1 = 1; // (m-1)'th Fibonacci No.
        int fibM = fibMMm2 + fibMMm1; // m'th Fibonacci

        /* fibM is going to store the smallest
        Fibonacci Number greater than or equal to listSize */
        while (fibM < listSize) {
            fibMMm2 = fibMMm1;
            fibMMm1 = fibM;
            fibM = fibMMm2 + fibMMm1;
        }

        // Marks the eliminated range from front
        int offset = -1;

        /* while there are elements to be inspected.
        Note that we compare list[fibMm2] with value.
        When fibM becomes 1, fibMm2 becomes 0 */
        while (fibM > 1) {
            // Check if fibMm2 is a valid location
            int i = Math.min(offset + fibMMm2, listSize - 1);

            /* If value is greater than the value at
            index fibMm2, cut the subarray array
            from offset to i */
            if (list.get(i) < value) {
                fibM = fibMMm1;
                fibMMm1 = fibMMm2;
                fibMMm2 = fibM - fibMMm1;
                offset = i;
            }

            /* If value is less than the value at index
            fibMm2, cut the subarray after i+1 */
            else if (list.get(i) > value) {
                fibM = fibMMm2;
                fibMMm1 = fibMMm1 - fibMMm2;
                fibMMm2 = fibM - fibMMm1;
            }

            /* element found. return index */
            else
                return i;
        }

        /* comparing the last element with value */
        if (fibMMm1 == 1 && list.get(listSize-1) == value)
            return listSize-1;

        /*element not found. return -1 */
        return -1;
    }
}
