package utils.path;

import authoringInterface.subEditors.AbstractGameObjectEditor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class PathUtility {
    private static final Path anchorPath = Paths.get(Objects.requireNonNull(AbstractGameObjectEditor.class.getClassLoader().getResource("anchor.txt")).toString().substring(5));

    /**
     * This method returns the relative path of the File.
     *
     * @param file: A File whose relative path will be returned.
     * @return A relative path in the form of String.
     */
    public static String getRelativePath(File file) {
        return anchorPath.relativize(file.toPath()).toString();
    }

    /**
     * This method returns the absolute URL path of the file.
     *
     * @param relativePath: A relative path of a file.
     * @return An absolute path that JavaFx understands.
     */
    public static String getAbsoluteURL(String relativePath) {
        return "file:" + anchorPath.resolve(relativePath).normalize();
    }
}
