import java.util.HashMap;

public class BuildableHashMap<K, V> extends HashMap<K, V> {
    public BuildableHashMap() {
        super();
    }

    public BuildableHashMap<K, V> withEntry(K key, V value) {
        put(key, value);
        return this;
    }
}
