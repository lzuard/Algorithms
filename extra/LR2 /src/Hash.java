import java.util.*;

/** Отвечает за все методы, связанные с хешом **/
public class Hash {

    public static final int REHASH_SIMPLE=0;
    public static final int REHASH_RANDOM=1;
    public static final int REHASH_CHAINS=2;

    private int rehashMethod;
    private int maxHashSize;

    private Random random= new Random();

    private HashMap<Integer,String> hashMap= new HashMap<>();
    private HashMap<Integer,ArrayList<String>> chainsMap = new HashMap<>();

    public Hash(int method, int size){
        rehashMethod=method;
        maxHashSize=size;
    }

    public HashMap<Integer,String> getTable(){
        return hashMap;
    }


    public int getHash(String value){
        int hash=1;
        for(int i=0; i<value.length(); i++){
            hash*=(int)value.charAt(i)*31;
        }
        return Math.abs(hash%maxHashSize);
    }


    public void add(String value){
        int hash=getHash(value);
        if(!hashMap.containsKey(hash)){
            if (rehashMethod==REHASH_CHAINS){
                LinkedList<String> list = new LinkedList<>();
                list.add(value);
                hashMap.put(hash,value);
            }
            else {
                hashMap.put(hash, value);
            }
        }
        else{
            switch (rehashMethod){
                case REHASH_SIMPLE:
                    rehashSimple(hash,value);
                    break;
                case REHASH_RANDOM:
                    rehashRandom(hash,value);
                    break;
                case REHASH_CHAINS:
                    rehashChains(hash,value);
                    break;
            }

        }
    }


    private void rehashSimple(int hash, String value){
        while(hashMap.containsKey(hash)){
            hash++;
        }
        hash%=maxHashSize;
        hashMap.put(hash,value);
    }
    private void rehashRandom(int hash, String value){
        while(hashMap.containsKey(hash)){
            hash+=random.nextInt();
        }
        hash%=maxHashSize;
        hashMap.put(hash,value);
    }
    private void rehashChains(int hash, String value){
        chainsMap.get(hash).add(value);
    }


    public int find(String value){
        if (rehashMethod!=REHASH_CHAINS) {
            for (int i : hashMap.keySet()) {
                if(hashMap.get(i).equals(value)){
                    return i;
                }
            }
        }
        else{
            for(int i: chainsMap.keySet()){
                if(chainsMap.get(i).contains(value)){
                    return i;
                }
            }
        }
        return -1;
    }

    public void delete(String value){
        int hash=find(value);
        if(hash>=0){
            if(rehashMethod!=REHASH_CHAINS){
                hashMap.remove(hash);
            }
            else{
                chainsMap.get(hash).remove(value);
            }
        }

    }

}
