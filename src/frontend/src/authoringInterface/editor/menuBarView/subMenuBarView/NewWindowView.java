package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * LoadFileView opens alertMessage to select the options btw save, not save and cancel to close files.
 * @author Amy Kim
 */

public class NewWindowView {
    public NewWindowView(){
        ButtonType save = new ButtonType("Save and Start");
        ButtonType notSave = new ButtonType("Ignore");
        Optional<ButtonType> result = customizeMsg(save, notSave).showAndWait();
        if (result.get() == save) {
            //TODO: Saving function
            refreshWindow();
        }
        else if (result.get() == notSave) {
            System.exit(0);
            refreshWindow();
        }
    }

    private Alert customizeMsg(ButtonType save, ButtonType notSave) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Open new program");
        alert.setHeaderText("Do you want to save the changes and start new program?");
        alert.setContentText("Your changes will be lost if you do not save them.");
        alert.getButtonTypes().setAll(save, ButtonType.CANCEL, notSave);
        return alert;
    }

    private void refreshWindow(){

    }

}
