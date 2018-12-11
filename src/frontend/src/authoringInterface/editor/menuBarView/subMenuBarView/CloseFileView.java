package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * LoadFileView opens alertMessage to select the options btw save, not save and cancel to close files.
 *
 * @author Amy Kim
 */

public class CloseFileView {
    private Runnable closeWindow;

    public CloseFileView(Runnable closeWindow) {
        this.closeWindow = closeWindow;
        alertMsg();
    }

    private void alertMsg() {
        ButtonType save = new ButtonType("Save");
        ButtonType notSave = new ButtonType("Don't Save");
        Optional<ButtonType> result = customizeMsg(save, notSave).showAndWait();
        if (result.get() == save) {
            //TODO: Saving function
        } else if (result.get() == notSave) {
            closeWindow.run();
        }
    }

    private Alert customizeMsg(ButtonType save, ButtonType notSave) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close the Window");
        alert.setHeaderText("Do you want to save the changes made to the program?");
        alert.setContentText("Your changes will be lost if you do not save them.");
        alert.getButtonTypes().setAll(save, ButtonType.CANCEL, notSave);
        return alert;
    }

}
