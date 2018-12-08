package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SaveFileView {

    public SaveFileView() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showSaveDialog(new Stage());
    }
}
