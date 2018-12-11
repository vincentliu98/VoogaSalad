package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ErrorWindow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

public class SaveFileView {
    public SaveFileView(Supplier<String> getGameXML) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project files");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                var fs = new BufferedWriter(new FileWriter(file));
                fs.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                fs.newLine();
                fs.write(getGameXML.get());
                fs.close();
            } catch (IOException e) {
                ErrorWindow.display("Error occurred while exporting data", e.toString());
            }
        }
    }
}
