package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ErrorWindow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * This is responsible for saving Grid and GameObjects currently only.
 *
 * @author Haotian Wang
 */
public class SaveGridAndGameObjectsView {
    public SaveGridAndGameObjectsView(String xml) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Grid and GameObjects");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                var fs = new BufferedWriter(new FileWriter(file));
                fs.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                fs.newLine();
                fs.write(xml);
                fs.close();
            } catch (IOException e) {
                ErrorWindow.display("Error occurred while exporting data", e.toString());
            }
        }
    }
}
