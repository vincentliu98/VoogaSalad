package utils;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

/**
 * This class is a utility class that shows an error window with some basic settings.
 *
 * @author Haotian Wang
 */
public class ErrorWindow extends Alert {
    public ErrorWindow(String alertName, String content) {
        super(AlertType.ERROR);
        setTitle(alertName);
        setContentText(content);
    }
}
