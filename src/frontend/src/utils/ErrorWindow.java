package utils;

import javafx.scene.control.Alert;

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
