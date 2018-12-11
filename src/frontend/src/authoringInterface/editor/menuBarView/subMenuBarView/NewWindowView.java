package authoringInterface.editor.menuBarView.subMenuBarView;

import authoringInterface.View;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import static authoringInterface.MainAuthoringProgram.SCREEN_HEIGHT;
import static authoringInterface.MainAuthoringProgram.SCREEN_WIDTH;

/**
 * NewWindowView
 *
 * @author Amy Kim
 */

public class NewWindowView {
    public static final String STYLESHEET = "style.css";

    public NewWindowView() {
        ButtonType new_window = new ButtonType("New window");
        Optional<ButtonType> result = customizeMsg(new_window).showAndWait();
        if (result.get() == new_window) {
            popUpWindow();
        }
    }

    private Alert customizeMsg(ButtonType new_window) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Project");
        alert.setHeaderText("How would you like to start new project");
        alert.setContentText("New projects will be opened in a new window.");
        alert.getButtonTypes().setAll(ButtonType.CANCEL, new_window);
        return alert;
    }

    private void popUpWindow() {
        Stage newWindow = new Stage();
        newWindow.setTitle("VoogaSalad!");
        View myView = new View(newWindow);
        Scene newScene = new Scene(myView.getRootPane(), SCREEN_WIDTH, SCREEN_HEIGHT);
        newScene.getStylesheets().add(STYLESHEET);
        newWindow.setScene(newScene);

        newWindow.show();
    }
}
