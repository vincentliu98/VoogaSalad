package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class LoadFileView {

    public LoadFileView(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showOpenDialog(new Stage());
        // TODO: keyboard warrior, backend do the rest.
        if (file != null) {
            // if (file.isLegitimate) {
        }
    }

}
