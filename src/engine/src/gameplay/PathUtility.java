package gameplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PathUtility {
    public static File getResourceAsFile(String url) {
        return new File(url.replace("../../../../", ""));
    }

    public static InputStream getResourceAsStream(String url) {
        try {
            return new FileInputStream(getResourceAsFile(url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } return null;
    }
}
