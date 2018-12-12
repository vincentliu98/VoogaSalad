package gameplay;

public class PathUtility {
    public static String extractLast(String url) {
        var split = url.split("/");
        return split[split.length-1];
    }
}
