import java.util.HashMap;
import java.util.Scanner;

/** Отвечает за взаимодействие с пользователем**/
public class Conversation {

    /** Объект для считывания данных с консоли **/
    private Scanner in = new Scanner(System.in);

    /** Ссылка на объект управления программой **/
    private Interactions interactions;

    /** Установить ссылку на объект управления программой **/
    public void setInteractions(Interactions interactions){
        this.interactions=interactions;
    }

    /** Вывод сообщения об успехе выполнения **/
    public void saySuccess(){
        System.out.println("Команда выполнена\n");
    }

    /** Вступительный текст **/
    public void sayHello(){

        System.out.println("\n");
        System.out.println("Программа для работы с наборами слов");
        System.out.println("Введите \"Help\", чтобы увидеть список команд");
        System.out.println("\n");
    }

    /** Cообщение об неккоректном вводе **/
    public void invalidInput(){
        System.out.println("\n");
        System.out.println("Ошибка, неккоректный ввод.");
        System.out.println("Пожалуйста, прочтите условие еще раз и повторите попытку");
        System.out.println("\n");
    }

    public int getFieldSize(){
        System.out.println("Введите длину поля");
        String strAnswer = in.nextLine();
        try{
            int answer=Integer.parseInt(strAnswer);
            return answer;
        }
        catch (Exception e){
            invalidInput();
            getFieldSize();
        }
        return 0;
    }

    public int getFigureCount(){
        System.out.println("Количество фигур");
        String strAnswer = in.nextLine();
        try{
            int answer=Integer.parseInt(strAnswer);
            return answer;
        }
        catch (Exception e){
            invalidInput();
            getFieldSize();
        }
        return 0;
    }

    /** Спрашивает пользователя какой метод рехеширования выбрать **/
    public void getRehashMethod(){

        boolean notReady=true;
        while (notReady) {
            System.out.println("Выберите способ рехеширования:\n1.Простое \n2.Случайное\n3.Метод цепочек");
            String answer = in.nextLine();
            switch (answer) {
                case "1":
                    interactions.setRehashMethod(Hash.REHASH_SIMPLE);
                    notReady=false;
                    break;
                case "2":
                    interactions.setRehashMethod(Hash.REHASH_RANDOM);
                    notReady=false;
                    break;
                case "3":
                    interactions.setRehashMethod(Hash.REHASH_CHAINS);
                    notReady=false;
                    break;
                default:
                    invalidInput();
                    break;
            }
        }
    }
    /** Спрашивает пользователя какой метод поиска выбрать **/
    public void getSeacrhMethod(){
        boolean notReady=true;
        while (notReady){
            System.out.println("Выберите способ поиска:\n1.Простой \n2.Бинарный\n3.Интерполяционный\n4.Бинарное дерево\n5.Фибоначиев");
            String answer = in.nextLine();

             switch (answer){
                 case "1":
                     interactions.setSearchMethod(Search.SIMPLE_SEARCH);
                     notReady=false;
                     break;
                 case "2":
                     interactions.setSearchMethod(Search.BINARY_SEARCH);
                     notReady=false;
                     break;
                 case "3":
                     interactions.setSearchMethod(Search.INTERPOLATION_SEARCH);
                     notReady=false;
                     break;
                 case "4":
                     interactions.setSearchMethod(Search.TREE_SEARCH);
                     notReady=false;
                     break;
                 case "5":
                     interactions.setSearchMethod(Search.FIBONACCI_SEARCH);
                     notReady=false;
                     break;
                 default:
                     invalidInput();
                     break;
             }
        }
    }

    /**  Вывести всю коллекцию на экран **/
    public void printCollection(){
        HashMap<Integer,String>map=interactions.getMap();
        int count=1;
        for(int key :map.keySet()){
            System.out.println(count+".\t"+key+"\t"+map.get(key));
            count++;
        }
    }
    /** Список команд **/
    public void printHelp(){
        System.out.println("Для работы с программой вы можете использовать следующие команды:");
        System.out.println("Help - Вывести список команд");
        System.out.println("Generate - Создать набор случайных слов");
        System.out.println("Print - Вывести набор слов на экран");
        System.out.println("Add - Добавить слово в набор");
        System.out.println("Delete - Удалить слово из набора");
        System.out.println("Find - Найти номер слова в наборе");
        System.out.println("Chess- Запустить программу с шахматами");
    }

    /** Получить размер коллекции **/
    public int getSize(){
        String answerStr;
        int answer;
        while (true){
            try{
                System.out.println("Введите размер набора");
                answerStr=in.nextLine();
                answer = Integer.parseInt(answerStr);
                break;
            }catch (Exception e){
                invalidInput();
            }
        }
        return answer;
    }

    /** Получить слово **/
    public String getWord(){
        System.out.println("Введите значение:");
        String answer = in.nextLine();
        return answer;
    }

    /** Вывод сообщения, что элемент не был найден **/
    public void notFound(){
        System.out.println("Значение не найдено в наборе");
    }

    /** Вывод сообщения, что элемент есть в бинарном дереве **/
    public void foundInTree(){
        System.out.println("Элемент присутсвует в наборе");
    }
    /** Вывод сообщения, что элемент был найден **/
    public void found(int index){

        int hash=interactions.getHashList().get(index);

        HashMap<Integer,String> map=interactions.getMap();
        int number=0;
        for (int key:map.keySet()){
            number++;
            if(key==hash){
                break;
            }
        }

        System.out.println("Значение найдено в наборе с под номером "+number);
    }

    /** Получить новую команду **/
    public void getCommand(){
        System.out.println("Введите комнаду:");
        String value =in.nextLine();
        switch (value){
            case "Help":
                interactions.setCommand(CMD.HELP);
                break;
            case "Start":
                interactions.setCommand(CMD.CHESS);
                break;
            case "Stop":
                interactions.setCommand(CMD.STOP);
                break;
            case "Generate":
                interactions.setCommand(CMD.GENERATE);
                break;
            case "Print":
                interactions.setCommand(CMD.PRINT);
                break;
            case "Add":
                interactions.setCommand(CMD.ADD);
                break;
            case "Find":
                interactions.setCommand(CMD.FIND);
                break;
            case "Delete":
                interactions.setCommand(CMD.DELETE);
                break;
            case "Chess":
                interactions.setCommand(CMD.CHESS);
                break;
            default:
                invalidInput();
                getCommand();
                break;
        }

    }
}
