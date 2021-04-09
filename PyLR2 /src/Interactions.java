import java.util.*;

/** Управляющий класс. Внутри этого класса осуществляется запуск всех методов для выполнения команд пользователя **/
public class Interactions {

    /** Ссылка на созданный объект класса */
    private Interactions interactions;

    /** Ссылка на консольную команду **/
    private CMD command;

    /** Объект генератора слов **/
    private WordGenerator generator;

    /** Объект взаимодействия с пользователем **/
    private Conversation user;

    private Hash hash;


    /** Лист хешей для организации поиска **/
    private ArrayList<Integer> hashList;

    /** Метод рехеширования **/
    private int rehashMethod;

    /** Метод поиска **/
    private int searchMethod;

    /** Вернуть лист хешей **/
    public ArrayList<Integer> getHashList(){ return hashList;}

    /** Вернуть коллекцию **/
    public HashMap<Integer, String> getMap() {
        return hash.getTable();
    }

    /** Установить текущую команду **/
    public void setCommand(CMD command){
        this.command=command;
    }

    /** Установить метод рехеширования **/
    public void setRehashMethod(int rehashMethod){
        this.rehashMethod=rehashMethod;
    }

    /** Установить метод поиска **/
    public void setSearchMethod(int searchMethod){
        this.searchMethod=searchMethod;
    }

    /** Конструктор класса **/
    public Interactions(){
        hashList = new ArrayList<>();
        command = CMD.CHESS;
        user = new Conversation();
        generator = new WordGenerator();
        hash = new Hash(0,1024);
    }


    private void updateList(){
        HashMap<Integer,String> map =hash.getTable();
        hashList.clear();
        for (int i: map.keySet()){
            hashList.add(i);
        }
        Collections.sort(hashList);
    }

    /** Ищет строку в наборе. Получает хэш строки, вызывает метод поиска и метод вывода результата **/
    private void findValue(String value){
        int hsh=hash.getHash(value);
        updateList();
        int index=Search.find(hsh,searchMethod);

        if (index==-1){
            user.notFound();
        }
        else if(index==-100){
            user.foundInTree();
        }
        else{
            user.found(index);
        }
    }

    /** Передать все ссылки на этот класс в остальные классы **/
    private void setAllReferences(){
        user.setInteractions(interactions);
        Search.setInteraction(interactions);
    }

    /** Заполнение коллекции набором из N случайных слов (и их хешей соответсвенно)**/
    private void fillMap(int size){
        String word;
        for (int i=0;i<size;i++){
            word=generator.getNewWord();
            hash.add(word);
        }
    }


    private void chessStart(){
        int fieldSize=user.getFieldSize();
        int figureCount= user.getFigureCount();

        Chess chess = new Chess(fieldSize,figureCount);
        chess.startApp();
    }

    /** Управляющий метод. Здесь происходит запуск тех команд, которые введет пользователь **/
    public void startApp(Interactions interactions){
        this.interactions=interactions;
        setAllReferences();

        user.sayHello();

        while (true){
            user.getCommand();

            switch (command){
                case HELP:
                    user.printHelp();
                    break;
                case CHESS:
                    chessStart();
                    break;
                case STOP:
                    System.exit(0);
                    break;
                case GENERATE:
                    //user.getRehashMethod();
                    fillMap(user.getSize());
                    break;
                case PRINT:
                    user.printCollection();
                    break;
                case ADD:
                    //user.getRehashMethod();
                    hash.add(user.getWord());
                    break;
                case FIND:
                     user.getSeacrhMethod();
                     findValue(user.getWord());
                    break;
                case DELETE:
                    hash.delete(user.getWord());
                    break;
            }
            user.saySuccess();
        }

    }

}
