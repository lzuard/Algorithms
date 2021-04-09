import java.util.Random;

/** Генерирует случайную последовательность символов **/
public class WordGenerator {

    /** Максимальный размер последовательности **/
    private final int length=10;


    /** Генератор **/
    public String getNewWord(){
        int size =(int)(2+Math.random()*(length-2));

        String word="";
        int codeASCII;

        for (int i=0;i<size;i++){

            codeASCII=(int)(65+Math.random()*57);

            while(codeASCII>90 && codeASCII<97)
                codeASCII=(int)(65+Math.random()*57);

            word+=(char)codeASCII;
        }
        return word;

    }
}
