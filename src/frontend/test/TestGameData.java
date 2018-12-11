public class TestGameData {
    private static BuildableHashMap<String, BuildableHashMap<String, String>> map;

    public static void setup() {
        map = new BuildableHashMap<>();
    }

    public static void addEntry(String key1, String key2, String value) {
        map.put(key1, map.getOrDefault(key1, new BuildableHashMap<>()).withEntry(key2, value));
    }

    public static String getValue(String key1, String key2) {
        return map.get(key1).get(key2);
    }

    public static BuildableHashMap<String, String> getMap(String key1) {
        return map.get(key1);
    }
}
