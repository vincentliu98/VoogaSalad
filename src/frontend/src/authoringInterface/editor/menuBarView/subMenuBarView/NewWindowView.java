package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * NewWindowView
 * @author Amy Kim
 */

public class NewWindowView {
    public NewWindowView(){
        ButtonType current = new ButtonType("This window");
        ButtonType new_window = new ButtonType("New window");
        Optional<ButtonType> result = customizeMsg(current, new_window).showAndWait();
        if (result.get() == current) {
            askSave();
        }
        else if (result.get() == new_window) {
        }
    }

    private Alert customizeMsg(ButtonType current, ButtonType new_window) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Open Project");
        alert.setHeaderText("How would you like to open the project");
        alert.setContentText("New projects can either be opened in a new window or replace the project in the existing window.\n");
        alert.getButtonTypes().setAll(current, ButtonType.CANCEL, new_window);
        return alert;
    }

    private void askSave(){
        Optional<ButtonType> result = secondMsg().showAndWait();
        if (result.get() == ButtonType.YES) {
            //TODO: saving
            refreshWindow();
        }
        else if (result.get() == ButtonType.NO) {
            refreshWindow();
        }
    }

    private Alert secondMsg(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Save Project");
        alert.setHeaderText("Do you want to save the changes before open new project?");
        alert.setContentText("Your changes will be lost if you do not save them.");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return alert;
    }


    private void refreshWindow(){

    }

}
