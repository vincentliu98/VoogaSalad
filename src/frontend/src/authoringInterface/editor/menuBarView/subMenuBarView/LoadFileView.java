package authoringInterface.editor.menuBarView.subMenuBarView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * When the user clicks FileChooser
 * @author Amy Kim
 */

public class LoadFileView {

    public LoadFileView(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Game File");
        fileChooser.showOpenDialog(new Stage());

        //TODO: add functionality of loading file, this is just filechooser.

    }

}
