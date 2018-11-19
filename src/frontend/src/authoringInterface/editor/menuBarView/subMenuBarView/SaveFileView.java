package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveFileView {

    public SaveFileView() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showSaveDialog(new Stage());
        // TODO: keyboard warrior, backend do the rest.
        if (file != null) {
            // if (file.isLegitimate) {
        }
    }
}
