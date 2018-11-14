package gameplay;

import java.util.HashMap;
import java.util.Map;

public class GlobalData<T> { // made a generic type just in case

    // PLACEHOLDER CLASS

    Map<String, T> myMap;

    public GlobalData(){
        myMap = new HashMap<>();
    }

    public void add(String key, T value){
        myMap.put(key, value);
    }

    public T get(String key){
        return myMap.get(key);
    }
}
